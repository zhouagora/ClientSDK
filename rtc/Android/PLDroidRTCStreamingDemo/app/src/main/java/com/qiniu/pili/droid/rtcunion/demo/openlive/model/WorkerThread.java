package com.qiniu.pili.droid.rtcunion.demo.openlive.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.SurfaceView;

import com.qiniu.pili.droid.rtcstreaming.demo.R;
import com.qiniu.pili.droid.rtcunion.demo.common.Constant;
import com.qiniu.pili.droid.union.UNRTCConstants;
import com.qiniu.pili.droid.union.UNRTCEngine;
import com.qiniu.pili.droid.union.UNRTCVideoCanvas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import io.agora.videoprp.AgoraYuvEnhancer;

public class WorkerThread extends Thread {
    private final static Logger log = LoggerFactory.getLogger(WorkerThread.class);

    private final Context mContext;

    private static final int ACTION_WORKER_THREAD_QUIT = 0X1010; // quit this thread

    private static final int ACTION_WORKER_JOIN_CHANNEL = 0X2010;

    private static final int ACTION_WORKER_LEAVE_CHANNEL = 0X2011;

    private static final int ACTION_WORKER_CONFIG_ENGINE = 0X2012;

    private static final int ACTION_WORKER_PREVIEW = 0X2014;

    private static final class WorkerThreadHandler extends Handler {

        private WorkerThread mWorkerThread;

        WorkerThreadHandler(WorkerThread thread) {
            this.mWorkerThread = thread;
        }

        public void release() {
            mWorkerThread = null;
        }

        @Override
        public void handleMessage(Message msg) {
            if (this.mWorkerThread == null) {
                log.warn("handler is already released! " + msg.what);
                return;
            }

            switch (msg.what) {
                case ACTION_WORKER_THREAD_QUIT:
                    mWorkerThread.exit();
                    break;
                case ACTION_WORKER_JOIN_CHANNEL:
                    String[] data = (String[]) msg.obj;
                    boolean isOwner = (msg.arg2 == 1);
                    mWorkerThread.joinChannel(data[0], msg.arg1, isOwner);
                    break;
                case ACTION_WORKER_LEAVE_CHANNEL:
                    String channel = (String) msg.obj;
                    mWorkerThread.leaveChannel(channel);
                    break;
                case ACTION_WORKER_CONFIG_ENGINE:
                    Object[] configData = (Object[]) msg.obj;
                    mWorkerThread.configEngine((int) configData[0], (int) configData[1]);
                    break;
                case ACTION_WORKER_PREVIEW:
                    Object[] previewData = (Object[]) msg.obj;
                    mWorkerThread.preview((boolean) previewData[0], (SurfaceView) previewData[1], (int) previewData[2]);
                    break;
            }
        }
    }

    private WorkerThreadHandler mWorkerHandler;

    private boolean mReady;

    public final void waitForReady() {
        while (!mReady) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("wait for " + WorkerThread.class.getSimpleName());
        }
    }

    @Override
    public void run() {
        log.trace("start to run");
        Looper.prepare();

        mWorkerHandler = new WorkerThreadHandler(this);

        ensureRtcEngineReadyLock();

        mReady = true;

        // enter thread looper
        Looper.loop();
    }

    private UNRTCEngine mUNRTCEngine;

    private AgoraYuvEnhancer mVideoEnhancer = null;

    public final void enablePreProcessor() {
        if (mEngineConfig.mClientRole == UNRTCConstants.CLIENT_ROLE_BROADCASTER) {
            if (Constant.PRP_ENABLED) {
                if (mVideoEnhancer == null) {
                    mVideoEnhancer = new AgoraYuvEnhancer(mContext);
                    mVideoEnhancer.SetLighteningFactor(Constant.PRP_DEFAULT_LIGHTNESS);
                    mVideoEnhancer.SetSmoothnessFactor(Constant.PRP_DEFAULT_SMOOTHNESS);
                    mVideoEnhancer.StartPreProcess();
                }
            }
        }
    }

    public final void setPreParameters(float lightness, float smoothness) {
        if (mEngineConfig.mClientRole == UNRTCConstants.CLIENT_ROLE_BROADCASTER) {
            if (Constant.PRP_ENABLED) {
                if (mVideoEnhancer == null) {
                    mVideoEnhancer = new AgoraYuvEnhancer(mContext);
                }
                mVideoEnhancer.StartPreProcess();
            }
        }

        Constant.PRP_DEFAULT_LIGHTNESS = lightness;
        Constant.PRP_DEFAULT_SMOOTHNESS = smoothness;

        if (mVideoEnhancer != null) {
            mVideoEnhancer.SetLighteningFactor(Constant.PRP_DEFAULT_LIGHTNESS);
            mVideoEnhancer.SetSmoothnessFactor(Constant.PRP_DEFAULT_SMOOTHNESS);
        }
    }

    public final void disablePreProcessor() {
        if (mVideoEnhancer != null) {
            mVideoEnhancer.StopPreProcess();
            mVideoEnhancer = null;
        }
    }

    public final void joinChannel(final String channel, int uid, boolean isOwner) {
        if (Thread.currentThread() != this) {
            log.warn("joinChannel() - worker thread asynchronously " + channel + " " + uid);
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_JOIN_CHANNEL;
            envelop.obj = new String[]{channel};
            envelop.arg1 = uid;
            if (isOwner) {
                envelop.arg2 = 1;
            } else {
                envelop.arg2 = 0;
            }
            mWorkerHandler.sendMessage(envelop);
            return;
        }

        ensureRtcEngineReadyLock();
        if (isOwner) {
            // if the broadcaster is owner, you can set the publish url below and set the owner as true
            String json = "{\"streamName\":\"rtmp://xxx\", \"owner\": true}";
            mUNRTCEngine.joinChannel(null, channel, json, uid);
        } else {
            // if the broadcaster is not owner, you can set the optionalInfo as null
            mUNRTCEngine.joinChannel(null, channel, null, uid);
        }

        mEngineConfig.mChannel = channel;

        enablePreProcessor();
        log.debug("joinChannel " + channel + " " + uid);
    }

    public final void leaveChannel(String channel) {
        if (Thread.currentThread() != this) {
            log.warn("leaveChannel() - worker thread asynchronously " + channel);
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_LEAVE_CHANNEL;
            envelop.obj = channel;
            mWorkerHandler.sendMessage(envelop);
            return;
        }

        if (mUNRTCEngine != null) {
            mUNRTCEngine.leaveChannel();
        }

        disablePreProcessor();

        int clientRole = mEngineConfig.mClientRole;
        mEngineConfig.reset();
        log.debug("leaveChannel " + channel + " " + clientRole);
    }

    private EngineConfig mEngineConfig;

    public final EngineConfig getEngineConfig() {
        return mEngineConfig;
    }

    private final MyEngineEventHandler mEngineEventHandler;

    public final void configEngine(int cRole, int vProfile) {
        if (Thread.currentThread() != this) {
            log.warn("configEngine() - worker thread asynchronously " + cRole + " " + vProfile);
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_CONFIG_ENGINE;
            envelop.obj = new Object[]{cRole, vProfile};
            mWorkerHandler.sendMessage(envelop);
            return;
        }

        ensureRtcEngineReadyLock();
        mEngineConfig.mClientRole = cRole;
        mEngineConfig.mVideoProfile = vProfile;

        mUNRTCEngine.setVideoProfile(mEngineConfig.mVideoProfile, true);

        mUNRTCEngine.setClientRole(cRole, "");

        log.debug("configEngine " + cRole + " " + mEngineConfig.mVideoProfile);
    }

    public final void preview(boolean start, SurfaceView view, int uid) {
        if (Thread.currentThread() != this) {
            log.warn("preview() - worker thread asynchronously " + start + " " + view + " " + (uid & 0XFFFFFFFFL));
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_PREVIEW;
            envelop.obj = new Object[]{start, view, uid};
            mWorkerHandler.sendMessage(envelop);
            return;
        }

        ensureRtcEngineReadyLock();
        if (start) {
            mUNRTCEngine.setupLocalVideo(new UNRTCVideoCanvas(view, UNRTCVideoCanvas.RENDER_MODE_HIDDEN, uid));
            mUNRTCEngine.startPreview();
        } else {
            mUNRTCEngine.stopPreview();
        }
    }

    public static String getDeviceID(Context context) {
        // XXX according to the API docs, this value may change after factory reset
        // use Android id as device id
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private UNRTCEngine ensureRtcEngineReadyLock() {
        if (mUNRTCEngine == null) {
            String appId = mContext.getString(R.string.private_app_id);
            if (TextUtils.isEmpty(appId)) {
                throw new RuntimeException("NEED TO use your App ID, get your own ID at https://dashboard.agora.io/");
            }
            if (UNRTCEngine.initializeNativeLibs()) {
                mUNRTCEngine = new UNRTCEngine(mContext, appId, mEngineEventHandler.mUNRTCEngineEventListener);
                mUNRTCEngine.setChannelProfile(UNRTCConstants.CHANNEL_PROFILE_LIVE_BROADCASTING);
                mUNRTCEngine.enableVideo();
                mUNRTCEngine.setLogFile(Environment.getExternalStorageDirectory()
                        + File.separator + mContext.getPackageName() + "/log/agora-rtc.log");
                mUNRTCEngine.enableDualStreamMode(true);
            }
        }
        return mUNRTCEngine;
    }

    public MyEngineEventHandler eventHandler() {
        return mEngineEventHandler;
    }

    public UNRTCEngine getUNRTCEngine() {
        return mUNRTCEngine;
    }

    /**
     * call this method to exit
     * should ONLY call this method when this thread is running
     */
    public final void exit() {
        if (Thread.currentThread() != this) {
            log.warn("exit() - exit app thread asynchronously");
            mWorkerHandler.sendEmptyMessage(ACTION_WORKER_THREAD_QUIT);
            return;
        }

        mReady = false;

        // TODO should remove all pending(read) messages

        mVideoEnhancer = null;

        log.debug("exit() > start");

        // exit thread looper
        Looper.myLooper().quit();

        mWorkerHandler.release();

        log.debug("exit() > end");
    }

    public WorkerThread(Context context) {
        this.mContext = context;

        this.mEngineConfig = new EngineConfig();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEngineConfig.mUid = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_UID, 0);

        this.mEngineEventHandler = new MyEngineEventHandler(mContext, this.mEngineConfig);
    }
}
