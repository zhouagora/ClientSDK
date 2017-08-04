package com.qiniu.pili.droid.rtcunion.demo.openlive.model;

import android.content.Context;

import com.qiniu.pili.droid.union.UNRTCEngineEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyEngineEventHandler {
    public MyEngineEventHandler(Context ctx, EngineConfig config) {
        this.mContext = ctx;
        this.mConfig = config;
    }

    private final EngineConfig mConfig;

    private final Context mContext;

    private UNRTCEventHandler mUNRTCEventHandler;

    public void setEventHandler(UNRTCEventHandler handler) {
        mUNRTCEventHandler = handler;
    }

    public final UNRTCEngineEventListener mUNRTCEngineEventListener = new UNRTCEngineEventListener() {
        private final Logger log = LoggerFactory.getLogger(this.getClass());

        @Override
        public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
            log.debug("onFirstRemoteVideoDecoded " + (uid & 0xFFFFFFFFL) + width + " " + height + " " + elapsed);
            mUNRTCEventHandler.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
        }

        @Override
        public void onFirstLocalVideoFrame(int width, int height, int elapsed) {
            log.debug("onFirstLocalVideoFrame " + width + " " + height + " " + elapsed);
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            log.debug("onUserJoined uid = " + uid);
            mUNRTCEventHandler.onUserJoined(uid, elapsed);
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            // FIXME this callback may return times
            mUNRTCEventHandler.onUserOffline(uid, reason);
        }

        @Override
        public void onUserMuteVideo(int uid, boolean muted) {
        }

        @Override
        public void onRtcStats(RtcStats stats) {
        }


        @Override
        public void onLeaveChannel(RtcStats stats) {

        }

        @Override
        public void onLastmileQuality(int quality) {
            log.debug("onLastmileQuality " + quality);
        }

        @Override
        public void onError(int err) {
            super.onError(err);
            log.debug("onError " + err);
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            log.debug("onJoinChannelSuccess " + channel + " " + uid + " " + (uid & 0xFFFFFFFFL) + " " + elapsed);

            mUNRTCEventHandler.onJoinChannelSuccess(channel, uid, elapsed);
        }

        public void onRejoinChannelSuccess(String channel, int uid, int elapsed) {
            log.debug("onRejoinChannelSuccess " + channel + " " + uid + " " + elapsed);
        }

        public void onWarning(int warn) {
            log.debug("onWarning " + warn);
        }
    };

}
