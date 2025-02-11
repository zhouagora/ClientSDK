package com.qiniu.pili.droid.rtcunion.demo.openlive.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiniu.pili.droid.rtcstreaming.demo.R;
import com.qiniu.pili.droid.rtcunion.demo.openlive.model.UNRTCEventHandler;
import com.qiniu.pili.droid.rtcunion.demo.openlive.model.ConstantApp;
import com.qiniu.pili.droid.rtcunion.demo.openlive.model.VideoStatusData;
import com.qiniu.pili.droid.union.UNRTCConstants;
import com.qiniu.pili.droid.union.UNRTCEngine;
import com.qiniu.pili.droid.union.UNRTCVideoCanvas;
import com.qiniu.pili.droid.union.UNRTCVideoCompositingLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class LiveRoomActivity extends BaseActivity implements UNRTCEventHandler {

    private final static Logger log = LoggerFactory.getLogger(LiveRoomActivity.class);

    private GridVideoViewContainer mGridVideoViewContainer;

    private RelativeLayout mSmallVideoViewDock;

    private UNRTCVideoCompositingLayout mCompositingLayout;
    private int mUserCount = 0;
    //    private int[] mUids = new int[4];
    private ArrayList<Integer> mUids = new ArrayList<>();

    private final HashMap<Integer, SurfaceView> mUidsList = new HashMap<>(); // uid = 0 || uid == EngineConfig.mUid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_room);
        mCompositingLayout = new UNRTCVideoCompositingLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    private boolean isBroadcaster(int cRole) {
        return cRole == UNRTCConstants.CLIENT_ROLE_BROADCASTER;
    }

    private boolean isBroadcaster() {
        return isBroadcaster(config().mClientRole);
    }

    @Override
    protected void initUIandEvent() {
        event().setEventHandler(this);

        Intent i = getIntent();
        int cRole = i.getIntExtra(ConstantApp.ACTION_KEY_CROLE, 0);

        if (cRole == 0) {
            throw new RuntimeException("Should not reach here");
        }

        String roomName = i.getStringExtra(ConstantApp.ACTION_KEY_ROOM_NAME);

        doConfigEngine(cRole);

        mGridVideoViewContainer = (GridVideoViewContainer) findViewById(R.id.grid_video_view_container);
        mGridVideoViewContainer.setItemEventHandler(new VideoViewEventListener() {
            @Override
            public void onItemDoubleClick(View v, Object item) {
                log.debug("onItemDoubleClick " + v + " " + item);

                if (mUidsList.size() < 2) {
                    return;
                }

                if (mViewType == VIEW_TYPE_DEFAULT)
                    switchToSmallVideoView(((VideoStatusData) item).mUid);
                else
                    switchToDefaultVideoView();
            }
        });

        ImageView button1 = (ImageView) findViewById(R.id.btn_1);
        ImageView button2 = (ImageView) findViewById(R.id.btn_2);
        ImageView button3 = (ImageView) findViewById(R.id.btn_3);

        if (isBroadcaster(cRole)) {
            SurfaceView surfaceV = UNRTCEngine.CreateRendererView(getApplicationContext());
            rtcEngine().setupLocalVideo(new UNRTCVideoCanvas(surfaceV, UNRTCVideoCanvas.RENDER_MODE_HIDDEN, 0));
            surfaceV.setZOrderOnTop(true);
            surfaceV.setZOrderMediaOverlay(true);

            mUidsList.put(0, surfaceV); // get first surface view

            mGridVideoViewContainer.initViewContainer(getApplicationContext(), 0, mUidsList); // first is now full view

            worker().preview(true, surfaceV, 0);
            broadcasterUI(button1, button2, button3);
        } else {
            audienceUI(button1, button2, button3);
        }

        boolean isOwner = getIntent().getBooleanExtra(ConstantApp.ACTION_KEY_IS_OWNER, false);
        worker().joinChannel(roomName, config().mUid, isOwner);

        TextView textRoomName = (TextView) findViewById(R.id.room_name);
        textRoomName.setText(roomName);
    }

    private void broadcasterUI(ImageView button1, ImageView button2, ImageView button3) {
        button1.setTag(true);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null && (boolean) tag) {
                    doSwitchToBroadcaster(false);
                } else {
                    doSwitchToBroadcaster(true);
                }
            }
        });
        button1.setColorFilter(getResources().getColor(R.color.agora_blue), PorterDuff.Mode.MULTIPLY);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worker().getUNRTCEngine().switchCamera();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                boolean flag = true;
                if (tag != null && (boolean) tag) {
                    flag = false;
                }
                worker().getUNRTCEngine().muteLocalAudioStream(flag);
                ImageView button = (ImageView) v;
                button.setTag(flag);
                if (flag) {
                    button.setColorFilter(getResources().getColor(R.color.agora_blue), PorterDuff.Mode.MULTIPLY);
                } else {
                    button.clearColorFilter();
                }
            }
        });
    }

    private void audienceUI(ImageView button1, ImageView button2, ImageView button3) {
        button1.setTag(null);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null && (boolean) tag) {
                    doSwitchToBroadcaster(false);
                } else {
                    doSwitchToBroadcaster(true);
                }
            }
        });
        button1.clearColorFilter();
        button2.setVisibility(View.GONE);
        button3.setTag(null);
        button3.setVisibility(View.GONE);
        button3.clearColorFilter();
    }

    private void doConfigEngine(int cRole) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int prefIndex = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX, ConstantApp.DEFAULT_PROFILE_IDX);
        if (prefIndex > ConstantApp.VIDEO_PROFILES.length - 1) {
            prefIndex = ConstantApp.DEFAULT_PROFILE_IDX;
        }
        int vProfile = ConstantApp.VIDEO_PROFILES[prefIndex];

        worker().configEngine(cRole, vProfile);
    }

    @Override
    protected void deInitUIandEvent() {
        doLeaveChannel();

        mUidsList.clear();
    }

    private void doLeaveChannel() {
        worker().leaveChannel(config().mChannel);
        if (isBroadcaster()) {
            worker().preview(false, null, 0);
        }
    }

    public void onClickClose(View view) {
        finish();
    }

    public void onShowHideClicked(View view) {
        boolean toHide = true;
        if (view.getTag() != null && (boolean) view.getTag()) {
            toHide = false;
        }
        view.setTag(toHide);

        doShowButtons(toHide);
    }

    private void doShowButtons(boolean hide) {
        View topArea = findViewById(R.id.top_area);
        topArea.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);

        View button1 = findViewById(R.id.btn_1);
        button1.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);

        View button2 = findViewById(R.id.btn_2);
        View button3 = findViewById(R.id.btn_3);
        if (isBroadcaster()) {
            button2.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
            button3.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
        } else {
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
        doRenderRemoteUi(uid);
    }

    private void doSwitchToBroadcaster(boolean broadcaster) {
        final int currentHostCount = mUidsList.size();
        final int uid = config().mUid;
        log.debug("doSwitchToBroadcaster " + currentHostCount + " " + (uid & 0XFFFFFFFFL) + " " + broadcaster);

        if (broadcaster) {
            doConfigEngine(UNRTCConstants.CLIENT_ROLE_BROADCASTER);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doRenderRemoteUi(uid);

                    ImageView button1 = (ImageView) findViewById(R.id.btn_1);
                    ImageView button2 = (ImageView) findViewById(R.id.btn_2);
                    ImageView button3 = (ImageView) findViewById(R.id.btn_3);
                    broadcasterUI(button1, button2, button3);

                    doShowButtons(false);
                }
            }, 1000); // wait for reconfig engine
        } else {
            stopInteraction(currentHostCount, uid);
        }
    }

    private void stopInteraction(final int currentHostCount, final int uid) {
        doConfigEngine(UNRTCConstants.CLIENT_ROLE_AUDIENCE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doRemoveRemoteUi(uid);

                ImageView button1 = (ImageView) findViewById(R.id.btn_1);
                ImageView button2 = (ImageView) findViewById(R.id.btn_2);
                ImageView button3 = (ImageView) findViewById(R.id.btn_3);
                audienceUI(button1, button2, button3);

                doShowButtons(false);
            }
        }, 1000); // wait for reconfig engine
    }

    private void doRenderRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                SurfaceView surfaceV = UNRTCEngine.CreateRendererView(getApplicationContext());
                surfaceV.setZOrderOnTop(true);
                surfaceV.setZOrderMediaOverlay(true);
                mUidsList.put(uid, surfaceV);
                if (config().mUid == uid) {
                    rtcEngine().setupLocalVideo(new UNRTCVideoCanvas(surfaceV, UNRTCVideoCanvas.RENDER_MODE_HIDDEN, uid));
                } else {
                    rtcEngine().setupRemoteVideo(new UNRTCVideoCanvas(surfaceV, UNRTCVideoCanvas.RENDER_MODE_HIDDEN, uid));
                }

                if (mViewType == VIEW_TYPE_DEFAULT) {
                    log.debug("doRenderRemoteUi VIEW_TYPE_DEFAULT" + " " + (uid & 0xFFFFFFFFL));
                    switchToDefaultVideoView();
                } else {
                    int bigBgUid = mSmallVideoViewAdapter.getExceptedUid();
                    log.debug("doRenderRemoteUi VIEW_TYPE_SMALL" + " " + (uid & 0xFFFFFFFFL) + " " + (bigBgUid & 0xFFFFFFFFL));
                    switchToSmallVideoView(bigBgUid);
                }
            }
        });
    }

    @Override
    public void onJoinChannelSuccess(final String channel, final int uid, final int elapsed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                mUids.add(uid);
                mUserCount++;
                setVideoCompositingLayout(mUserCount);

                if (mUidsList.containsKey(uid)) {
                    log.debug("already added to UI, ignore it " + (uid & 0xFFFFFFFFL) + " " + mUidsList.get(uid));
                    return;
                }

                final boolean isBroadcaster = isBroadcaster();
                log.debug("onJoinChannelSuccess " + channel + " " + uid + " " + elapsed + " " + isBroadcaster);

                worker().getEngineConfig().mUid = uid;

                SurfaceView surfaceV = mUidsList.remove(0);
                if (surfaceV != null) {
                    mUidsList.put(uid, surfaceV);
                }
            }
        });
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        mUids.add(uid);
        mUserCount++;
        setVideoCompositingLayout(mUserCount);
        log.debug("onUserJoined uid = " + uid + " mUserCount = " + mUserCount);
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        if (mUids.contains(uid)) {
            mUids.remove(mUids.indexOf(uid));
        }
        mUserCount--;
        setVideoCompositingLayout(mUserCount);
        log.debug("onUserOffline " + (uid & 0xFFFFFFFFL) + " " + reason);
        doRemoveRemoteUi(uid);
    }

    private void requestRemoteStreamType(final int currentHostCount) {
        log.debug("requestRemoteStreamType " + currentHostCount);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HashMap.Entry<Integer, SurfaceView> highest = null;
                for (HashMap.Entry<Integer, SurfaceView> pair : mUidsList.entrySet()) {
                    log.debug("requestRemoteStreamType " + currentHostCount + " local " + (config().mUid & 0xFFFFFFFFL) + " " + (pair.getKey() & 0xFFFFFFFFL) + " " + pair.getValue().getHeight() + " " + pair.getValue().getWidth());
                    if (pair.getKey() != config().mUid && (highest == null || highest.getValue().getHeight() < pair.getValue().getHeight())) {
                        if (highest != null) {
                            rtcEngine().setRemoteVideoStreamType(highest.getKey(), UNRTCConstants.VIDEO_STREAM_LOW);
                            log.debug("setRemoteVideoStreamType switch highest VIDEO_STREAM_LOW " + currentHostCount + " " + (highest.getKey() & 0xFFFFFFFFL) + " " + highest.getValue().getWidth() + " " + highest.getValue().getHeight());
                        }
                        highest = pair;
                    } else if (pair.getKey() != config().mUid && (highest != null && highest.getValue().getHeight() >= pair.getValue().getHeight())) {
                        rtcEngine().setRemoteVideoStreamType(pair.getKey(), UNRTCConstants.VIDEO_STREAM_LOW);
                        log.debug("setRemoteVideoStreamType VIDEO_STREAM_LOW " + currentHostCount + " " + (pair.getKey() & 0xFFFFFFFFL) + " " + pair.getValue().getWidth() + " " + pair.getValue().getHeight());
                    }
                }
                if (highest != null && highest.getKey() != 0) {
                    rtcEngine().setRemoteVideoStreamType(highest.getKey(), UNRTCConstants.VIDEO_STREAM_HIGH);
                    log.debug("setRemoteVideoStreamType VIDEO_STREAM_HIGH " + currentHostCount + " " + (highest.getKey() & 0xFFFFFFFFL) + " " + highest.getValue().getWidth() + " " + highest.getValue().getHeight());
                }
            }
        }, 500);
    }

    private void doRemoveRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                mUidsList.remove(uid);

                int bigBgUid = -1;
                if (mSmallVideoViewAdapter != null) {
                    bigBgUid = mSmallVideoViewAdapter.getExceptedUid();
                }

                log.debug("doRemoveRemoteUi " + (uid & 0xFFFFFFFFL) + " " + (bigBgUid & 0xFFFFFFFFL));

                if (mViewType == VIEW_TYPE_DEFAULT || uid == bigBgUid) {
                    switchToDefaultVideoView();
                } else {
                    switchToSmallVideoView(bigBgUid);
                }
            }
        });
    }

    private SmallVideoViewAdapter mSmallVideoViewAdapter;

    private void switchToDefaultVideoView() {
        if (mSmallVideoViewDock != null)
            mSmallVideoViewDock.setVisibility(View.GONE);
        mGridVideoViewContainer.initViewContainer(getApplicationContext(), config().mUid, mUidsList);

        mViewType = VIEW_TYPE_DEFAULT;

        int sizeLimit = mUidsList.size();
        if (sizeLimit > ConstantApp.MAX_PEER_COUNT + 1) {
            sizeLimit = ConstantApp.MAX_PEER_COUNT + 1;
        }
        for (int i = 0; i < sizeLimit; i++) {
            int uid = mGridVideoViewContainer.getItem(i).mUid;
            if (config().mUid != uid) {
                rtcEngine().setRemoteVideoStreamType(uid, UNRTCConstants.VIDEO_STREAM_HIGH);
                log.debug("setRemoteVideoStreamType VIDEO_STREAM_HIGH " + mUidsList.size() + " " + (uid & 0xFFFFFFFFL));
            }
        }
    }

    private void switchToSmallVideoView(int uid) {
        HashMap<Integer, SurfaceView> slice = new HashMap<>(1);
        slice.put(uid, mUidsList.get(uid));
        mGridVideoViewContainer.initViewContainer(getApplicationContext(), uid, slice);

        bindToSmallVideoView(uid);

        mViewType = VIEW_TYPE_SMALL;

        requestRemoteStreamType(mUidsList.size());
    }

    private void setVideoCompositingLayout(int videoCount) {
        rtcEngine().clearVideoCompositingLayout();
        if (videoCount == 1) {
            mCompositingLayout.regions = new UNRTCVideoCompositingLayout.Region[1];
            UNRTCVideoCompositingLayout.Region region = new UNRTCVideoCompositingLayout.Region();
            region.uid = mUids.get(0);
            region.x = 0.0;
            region.y = 0.0;
            region.width = 1.0;
            region.height = 1.0;
            region.zOrder = 0;
            region.alpha = 1.0;
            region.renderMode = 2;

            mCompositingLayout.regions[0] = region;
            rtcEngine().setVideoCompositingLayout(mCompositingLayout);
        } else if (videoCount == 2) {
            mCompositingLayout.regions = new UNRTCVideoCompositingLayout.Region[2];
            UNRTCVideoCompositingLayout.Region region1 = new UNRTCVideoCompositingLayout.Region();
            region1.uid = mUids.get(0);
            region1.x = 0.0;
            region1.y = 0.0;
            region1.width = 1.0;
            region1.height = 0.5;
            region1.zOrder = 0;
            region1.alpha = 1.0;
            region1.renderMode = 2;

            UNRTCVideoCompositingLayout.Region region2 = new UNRTCVideoCompositingLayout.Region();
            region2.uid = mUids.get(1);
            region2.x = 0.0;
            region2.y = 0.5;
            region2.width = 1.0;
            region2.height = 0.5;
            region2.zOrder = 0;
            region2.alpha = 1.0;
            region2.renderMode = 2;

            mCompositingLayout.regions[0] = region1;
            mCompositingLayout.regions[1] = region2;
            rtcEngine().setVideoCompositingLayout(mCompositingLayout);
        } else if (videoCount == 3) {
            mCompositingLayout.regions = new UNRTCVideoCompositingLayout.Region[3];
            UNRTCVideoCompositingLayout.Region region1 = new UNRTCVideoCompositingLayout.Region();
            region1.uid = mUids.get(0);
            region1.x = 0.0;
            region1.y = 0.0;
            region1.width = 1.0;
            region1.height = 0.5;
            region1.zOrder = 0;
            region1.alpha = 1.0;
            region1.renderMode = 2;

            UNRTCVideoCompositingLayout.Region region2 = new UNRTCVideoCompositingLayout.Region();
            region2.uid = mUids.get(1);
            region2.x = 0.0;
            region2.y = 0.5;
            region2.width = 0.5;
            region2.height = 0.5;
            region2.zOrder = 0;
            region2.alpha = 1.0;
            region2.renderMode = 2;

            UNRTCVideoCompositingLayout.Region region3 = new UNRTCVideoCompositingLayout.Region();
            region3.uid = mUids.get(2);
            region3.x = 0.5;
            region3.y = 0.5;
            region3.width = 0.5;
            region3.height = 0.5;
            region3.zOrder = 0;
            region3.alpha = 1.0;
            region3.renderMode = 2;

            mCompositingLayout.regions[0] = region1;
            mCompositingLayout.regions[1] = region2;
            mCompositingLayout.regions[2] = region3;
            rtcEngine().setVideoCompositingLayout(mCompositingLayout);
        } else if (videoCount == 4) {
            mCompositingLayout.regions = new UNRTCVideoCompositingLayout.Region[4];
            UNRTCVideoCompositingLayout.Region region1 = new UNRTCVideoCompositingLayout.Region();
            region1.uid = mUids.get(0);
            region1.x = 0.0;
            region1.y = 0.0;
            region1.width = 1.0;
            region1.height = 1.0;
            region1.zOrder = 0;
            region1.alpha = 1.0;
            region1.renderMode = 2;

            UNRTCVideoCompositingLayout.Region region2 = new UNRTCVideoCompositingLayout.Region();
            region2.uid = mUids.get(1);
            region2.x = 0.03;
            region2.y = 0.65;
            region2.width = 0.3;
            region2.height = 0.3;
            region2.zOrder = 100;
            region2.alpha = 1.0;
            region2.renderMode = 2;

            UNRTCVideoCompositingLayout.Region region3 = new UNRTCVideoCompositingLayout.Region();
            region3.uid = mUids.get(2);
            region3.x = 0.35;
            region3.y = 0.65;
            region3.width = 0.3;
            region3.height = 0.3;
            region3.zOrder = 100;
            region3.alpha = 1.0;
            region3.renderMode = 2;

            UNRTCVideoCompositingLayout.Region region4 = new UNRTCVideoCompositingLayout.Region();
            region4.uid = mUids.get(3);
            region4.x = 0.67;
            region4.y = 0.65;
            region4.width = 0.3;
            region4.height = 0.3;
            region4.zOrder = 100;
            region4.alpha = 1.0;
            region4.renderMode = 2;

            mCompositingLayout.regions[0] = region1;
            mCompositingLayout.regions[1] = region2;
            mCompositingLayout.regions[2] = region3;
            mCompositingLayout.regions[3] = region4;
            rtcEngine().setVideoCompositingLayout(mCompositingLayout);
        }
    }

    public int mViewType = VIEW_TYPE_DEFAULT;

    public static final int VIEW_TYPE_DEFAULT = 0;

    public static final int VIEW_TYPE_SMALL = 1;

    private void bindToSmallVideoView(int exceptUid) {
        if (mSmallVideoViewDock == null) {
            ViewStub stub = (ViewStub) findViewById(R.id.small_video_view_dock);
            mSmallVideoViewDock = (RelativeLayout) stub.inflate();
        }

        RecyclerView recycler = (RecyclerView) findViewById(R.id.small_video_view_container);

        boolean create = false;

        if (mSmallVideoViewAdapter == null) {
            create = true;
            mSmallVideoViewAdapter = new SmallVideoViewAdapter(this, exceptUid, mUidsList, new VideoViewEventListener() {
                @Override
                public void onItemDoubleClick(View v, Object item) {
                    switchToDefaultVideoView();
                }
            });
            mSmallVideoViewAdapter.setHasStableIds(true);
        }
        recycler.setHasFixedSize(true);

        recycler.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recycler.setAdapter(mSmallVideoViewAdapter);

        recycler.setDrawingCacheEnabled(true);
        recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        if (!create) {
            mSmallVideoViewAdapter.notifyUiChanged(mUidsList, exceptUid, null, null);
        }
        recycler.setVisibility(View.VISIBLE);
        mSmallVideoViewDock.setVisibility(View.VISIBLE);
    }
}
