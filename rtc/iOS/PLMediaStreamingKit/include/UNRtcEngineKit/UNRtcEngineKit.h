//
//  UNRtcEngineKit.h
//  PLMediaStreamingKit
//
//  Created by lawder on 2017/8/2.
//  Copyright © 2017年 Pili Engineering, Qiniu Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AgoraRtcEngineKit/AgoraRtcEngineKit.h>

@protocol AgoraRtcEngineDelegate <NSObject>
@optional

/**
 *  The warning occurred in SDK. The APP could igonre the warning, and the SDK could try to resume automically.
 *
 *  @param engine      The engine kit
 *  @param warningCode The warning code
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOccurWarning:(AgoraRtcWarningCode)warningCode;

/**
 *  The error occurred in SDK. The SDK couldn't resume to normal state, and the app need to handle it.
 *
 *  @param engine    The engine kit
 *  @param errorCode The error code
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOccurError:(AgoraRtcErrorCode)errorCode;

/**
 *  The sdk reports the volume of a speaker. The interface is disable by default, and it could be enable by API "enableAudioVolumeIndication"
 *
 *  @param engine      The engine kit
 *  @param speakers    AgoraRtcAudioVolumeInfos array
 *  @param totalVolume The total volume of speakers
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine reportAudioVolumeIndicationOfSpeakers:(NSArray*)speakers totalVolume:(NSInteger)totalVolume;

/**
 *  Event of the first local frame starts rendering on the screen.
 *
 *  @param engine  The engine kit
 *  @param size    The size of local video stream
 *  @param elapsed The elapsed time(ms) from the beginning of the session.
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstLocalVideoFrameWithSize:(CGSize)size elapsed:(NSInteger)elapsed;

/**
 *  Event of the first frame of remote user is decoded successfully.
 *
 *  @param engine  The engine kit
 *  @param uid     The remote user id
 *  @param size    The size of video stream
 *  @param elapsed The elapsed time(ms) from the beginning of the session.
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstRemoteVideoDecodedOfUid:(NSUInteger)uid size:(CGSize)size elapsed:(NSInteger)elapsed;

/**
 *  Event of video size changed for local or remote user
 *
 *  @param engine  The engine kit
 *  @param uid     The user id
 *  @param size    The new size of video
 *  @param rotation  The new rotate of video
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine videoSizeChangedOfUid:(NSUInteger)uid size:(CGSize)size rotation:(NSInteger)rotation;

/**
 *  Event of the first frame of remote user is rendering on the screen.
 *
 *  @param engine  The engine kit
 *  @param uid     The remote user id
 *  @param size    The size of video stream
 *  @param elapsed The elapsed time(ms) from the beginning of the session.
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstRemoteVideoFrameOfUid:(NSUInteger)uid size:(CGSize)size elapsed:(NSInteger)elapsed;

/**
 *  Event of remote user joined
 *
 *  @param engine  The engine kit
 *  @param uid     The remote user id
 *  @param elapsed The elapsed time(ms) from the beginning of the session.
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didJoinedOfUid:(NSUInteger)uid elapsed:(NSInteger)elapsed;

/**
 *  Event of remote user offlined
 *
 *  @param engine The engine kit
 *  @param uid    The remote user id
 *  @param reason Reason of user offline, quit, drop or became audience
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOfflineOfUid:(NSUInteger)uid reason:(AgoraRtcUserOfflineReason)reason;

/**
 *  Event of remote user audio muted or unmuted
 *
 *  @param engine The engine kit
 *  @param muted  Muted or unmuted
 *  @param uid    The remote user id
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didAudioMuted:(BOOL)muted byUid:(NSUInteger)uid;

/**
 *  Event of remote user video muted or unmuted
 *
 *  @param engine The engine kit
 *  @param muted  Muted or unmuted
 *  @param uid    The remote user id
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didVideoMuted:(BOOL)muted byUid:(NSUInteger)uid;

/**
 *  Event of remote user video muted or unmuted
 *
 *  @param engine The engine kit
 *  @param routing the current audio output routing
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didAudioRouteChanged:(AudioOutputRouting)routing;


/**
 *  Event of remote user video enabled or disabled
 *
 *  @param engine The engine kit
 *  @param enabled  Enabled or disabled
 *  @param uid    The remote user id
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didVideoEnabled:(BOOL)enabled byUid:(NSUInteger)uid;

/**
 *  The statistics of local video stream. Update every two seconds.
 *
 *  @param engine        The engine kit
 *  @param stats         The statistics of local video, including sent bitrate, sent framerate
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine localVideoStats:(AgoraRtcLocalVideoStats*)stats;

/**
 *  The statistics of remote video stream. Update every two seconds.
 *
 *  @param engine            The engine kit
 *  @param stats             The statistics of remote video, including user id, delay, resolution, received bitrate, received framerate, video stream type
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine remoteVideoStats:(AgoraRtcRemoteVideoStats*)stats;

/**
 *  Event of load media engine success
 *
 *  @param engine The engine kit
 */
- (void)rtcEngineMediaEngineDidLoaded:(AgoraRtcEngineKit *)engine;

/**
 *  Event of media engine start call success
 *
 *  @param engine The engine kit
 */
- (void)rtcEngineMediaEngineDidStartCall:(AgoraRtcEngineKit *)engine;

/**
 *  Event of meida engine finish audio mixing.
 *
 *  @param engine The engine kit
 */
- (void)rtcEngineMediaEngineDidAudioMixingFinish:(AgoraRtcEngineKit *)engine;

/**
 *  Event of camera opened
 *
 *  @param engine The engine kit
 */
- (void)rtcEngineCameraDidReady:(AgoraRtcEngineKit *)engine;

/**
 *  Event of camera stopped
 *
 *  @param engine The engine kit
 */
- (void)rtcEngineVideoDidStop:(AgoraRtcEngineKit *)engine;

/**
 *  Event of disconnected with server. This event is reported at the moment SDK loses connection with server.
 *  In the mean time SDK automatically tries to reconnect with the server until APP calls leaveChannel.
 *
 *  @param engine The engine kit
 */
- (void)rtcEngineConnectionDidInterrupted:(AgoraRtcEngineKit *)engine;

/**
 *  Event of loss connection with server. This event is reported after the connection is interrupted and exceed the retry period (10 seconds by default).
 *  In the mean time SDK automatically tries to reconnect with the server until APP calls leaveChannel.
 *
 *  @param engine The engine kit
 */
- (void)rtcEngineConnectionDidLost:(AgoraRtcEngineKit *)engine;

/**
 *  Event of the user joined the channel.
 *
 *  @param engine  The engine kit
 *  @param channel The channel name
 *  @param uid     The remote user id
 *  @param elapsed The elapsed time (ms) from session beginning
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didJoinChannel:(NSString*)channel withUid:(NSUInteger)uid elapsed:(NSInteger) elapsed;

/**
 *  Event of the user rejoined the channel
 *
 *  @param engine  The engine kit
 *  @param channel The channel name
 *  @param uid     The user id
 *  @param elapsed The elapsed time (ms) from session beginning
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didRejoinChannel:(NSString*)channel withUid:(NSUInteger)uid elapsed:(NSInteger) elapsed;

/**
 *  Statistics of rtc engine status. Updated every two seconds.
 *
 *  @param engine The engine kit
 *  @param stats  The statistics of rtc status, including duration, sent bytes and received bytes
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine reportRtcStats:(AgoraRtcStats*)stats;

/**
 *  The statistics of the call when leave channel
 *
 *  @param engine The engine kit
 *  @param stats  The statistics of the call, including duration, sent bytes and received bytes
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didLeaveChannelWithStats:(AgoraRtcStats*)stats;

/**
 *  The audio quality of the user. updated every two seconds.
 *
 *  @param engine  The engine kit
 *  @param uid     The id of user
 *  @param quality The audio quality
 *  @param delay   The delay from the remote user
 *  @param lost    The percentage of lost packets
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine audioQualityOfUid:(NSUInteger)uid quality:(AgoraRtcQuality)quality delay:(NSUInteger)delay lost:(NSUInteger)lost;

/**
 *  The network quality of local user.
 *
 *  @param engine  The engine kit
 *  @param uid     The id of user
 *  @param txQuality The sending network quality
 *  @param rxQuality The receiving network quality
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine networkQuality:(NSUInteger)uid txQuality:(AgoraRtcQuality)txQuality rxQuality:(AgoraRtcQuality)rxQuality;

/**
 *  The network quality of lastmile test.
 *
 *  @param engine  The engine kit
 *  @param quality The network quality
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine lastmileQuality:(AgoraRtcQuality)quality;

#if (!(TARGET_OS_IPHONE) && (TARGET_OS_MAC))
/**
 *  the notificaitoin of device added removed
 *
 *  @param engine The engine kit
 *  @param deviceId   the identification of device
 *  @param deviceType type of device: -1: audio unknown; 0: audio recording ; 1: audio playout ; 2: render; 4: capture
 *  @param state      state of device: 0: added; 1: removed
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine device:(NSString*) deviceId type:(AgoraRtcDeviceType) deviceType stateChanged:(NSInteger) state;

#endif

/**
 *  Event of API call executed
 *
 *  @param engine The engine kit
 *  @param api    The API description
 *  @param error  The error code
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didApiCallExecute:(NSString*)api error:(NSInteger)error;

/**
 *  This callback returns the status code after executing the refreshRecordingServiceStatus method successfully.
 *
 *  @param engine The engine kit
 *  @param status 0：Recording is stopped. 1：Recording is ongoing.
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didRefreshRecordingServiceStatus:(NSInteger)status;

/**
 *  receive custom data from remote user
 *
 *  @param engine The engine kit
 *  @param uid    The remote user id
 *  @param streamId The stream id
 *  @param data   The user defined data
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine receiveStreamMessageFromUid:(NSUInteger)uid streamId:(NSInteger)streamId data:(NSData*)data;

/**
 *  the local user has not received the data stream from the other user within 5 seconds.
 *
 *  @param engine The engine kit
 *  @param uid    The remote user id
 *  @param streamId The stream id
 *  @param error    The error code
 *  @param missed   The number of lost messages
 *  @param cached   The number of incoming cached messages when the data stream is interrupted
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOccurStreamMessageErrorFromUid:(NSUInteger)uid streamId:(NSInteger)streamId error:(NSInteger)error missed:(NSInteger)missed cached:(NSInteger)cached;


/**
 * when channel key is enabled, and specified channel key is invalid or expired, this function will be called.
 * APP should generate a new channel key and call renewChannelKey() to refresh the key.
 * NOTE: to be compatible with previous version, ERR_CHANNEL_KEY_EXPIRED and ERR_INVALID_CHANNEL_KEY are also reported via onError() callback.
 * You should move renew of channel key logic into this callback.
 *  @param engine The engine kit
 */
- (void)rtcEngineRequestChannelKey:(AgoraRtcEngineKit *)engine;

/**
 *  Event of the first audio frame is sent.
 *
 *  @param engine  The engine kit
 *  @param elapsed The elapsed time(ms) from the beginning of the session.
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstLocalAudioFrame:(NSInteger)elapsed;

/**
 *  Event of the first audio frame from remote user is received.
 *
 *  @param engine  The engine kit
 *  @param uid     The remote user id
 *  @param elapsed The elapsed time(ms) from the beginning of the session.
 */
- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstRemoteAudioFrameOfUid:(NSUInteger)uid elapsed:(NSInteger)elapsed;

@end


__attribute__((visibility("default"))) @interface UNRtcEngineKit : NSObject

/**
 *  Get the version of Agora SDK.
 *
 *  @return string, sdk version
 */
+ (NSString *)getSdkVersion;

/**
 *  Get the version of Media Engine
 *
 *  @return string, engine version
 */
+ (NSString *)getMediaEngineVersion;

/**
 *  Get the native handler of sdk Engine
 */
- (void*)getNativeHandle;

/**
 *  Get the AgoraRtcEngineKit
 */
- (AgoraRtcEngineKit *)getAgoraRtcEngineKit;


/**
 *  Initializes the AgoraRtcEngineKit object.
 *
 *  @param appId The appId is issued to the application developers by Agora.
 *  @param delegate The AgoraRtcEngineDelegate
 *
 *  @return an object of AgoraRtcEngineKit class
 */
+ (instancetype)sharedEngineWithAppId:(NSString*)appId
                             delegate:(id<AgoraRtcEngineDelegate>)delegate;

/**
 *  deprecated
 */
+ (instancetype)sharedEngineWithAppId:(NSString*)AppId
                                error:(void(^)(AgoraRtcErrorCode errorCode))errorBlock __deprecated;

+ (void)destroy;

/**
 * deprecated blocks
 */
- (void)audioVolumeIndicationBlock:(void(^)(NSArray *speakers, NSInteger totalVolume))audioVolumeIndicationBlock __deprecated;

- (void)firstLocalVideoFrameBlock:(void(^)(NSInteger width, NSInteger height, NSInteger elapsed))firstLocalVideoFrameBlock __deprecated;
- (void)firstRemoteVideoDecodedBlock:(void(^)(NSUInteger uid, NSInteger width, NSInteger height, NSInteger elapsed))firstRemoteVideoDecodedBlock __deprecated;
- (void)firstRemoteVideoFrameBlock:(void(^)(NSUInteger uid, NSInteger width, NSInteger height, NSInteger elapsed))firstRemoteVideoFrameBlock __deprecated;
- (void)userJoinedBlock:(void(^)(NSUInteger uid, NSInteger elapsed))userJoinedBlock __deprecated;
- (void)userOfflineBlock:(void(^)(NSUInteger uid))userOfflineBlock __deprecated;
- (void)userMuteAudioBlock:(void(^)(NSUInteger uid, BOOL muted))userMuteAudioBlock __deprecated;
- (void)userMuteVideoBlock:(void(^)(NSUInteger uid, BOOL muted))userMuteVideoBlock __deprecated;
- (void)localVideoStatBlock:(void(^)(NSInteger sentBitrate, NSInteger sentFrameRate))localVideoStatBlock __deprecated;
- (void)remoteVideoStatBlock:(void(^)(NSUInteger uid, NSInteger delay, NSInteger receivedBitrate, NSInteger receivedFrameRate))remoteVideoStatBlock __deprecated;
- (void)cameraReadyBlock:(void(^)())cameraReadyBlock __deprecated;
- (void)connectionLostBlock:(void(^)())connectionLostBlock __deprecated;
- (void)rejoinChannelSuccessBlock:(void(^)(NSString* channel, NSUInteger uid, NSInteger elapsed))rejoinChannelSuccessBlock __deprecated;
- (void)rtcStatsBlock:(void(^)(AgoraRtcStats* stat))rtcStatsBlock __deprecated;
- (void)leaveChannelBlock:(void(^)(AgoraRtcStats* stat))leaveChannelBlock __deprecated;
- (void)audioQualityBlock:(void(^)(NSUInteger uid, AgoraRtcQuality quality, NSUInteger delay, NSUInteger lost))audioQualityBlock __deprecated;
- (void)networkQualityBlock:(void(^)(NSUInteger uid, AgoraRtcQuality txQuality, AgoraRtcQuality rxQuality))networkQualityBlock __deprecated;
- (void)lastmileQualityBlock:(void(^)(AgoraRtcQuality quality))lastmileQualityBlock __deprecated;
- (void)mediaEngineEventBlock:(void(^)(NSInteger code))mediaEngineEventBlock __deprecated;


/**
 *  Enable / Disable dispatching delegate to main queue. if disable, the app should dispatch UI operating to main queue by himself.
 *
 *  @param enabled YES: dispatch delegate method to main queue. NO: not dispatch delegate methods to main queue
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int) enableMainQueueDispatch: (BOOL) enabled;

/** BEGIN OF COMMON METHODS */

/**
 *  Create an open UDP socket to the AgoraRtcEngineKit cloud service to join a channel.
 Users in the same channel can talk to each other with same vendor key.
 Users using different vendor keys cannot call each other.
 The method is asynchronous.
 *
 *  @param channelKey        Channel key generated by APP using sign certificate.
 *  @param channelName       Joining in the same channel indicates those clients have entered in one room.
 *  @param info              Optional, this argument can be whatever the programmer likes personally.
 *  @param uid               Optional, this argument is the unique ID for each member in one channel.
 If not specified, or set to 0, the SDK automatically allocates an ID, and the id could be gotten in onJoinChannelSuccess.
 *  @param joinSuccessBlock  This callback indicates that the user has successfully joined the specified channel. Same as rtcEngine:didJoinChannel:withUid:elapsed:. If nil, the callback rtcEngine:didJoinChannel:withUid:elapsed: will works.
 *
 *  @return 0 when executed successfully, and return negative value when failed.
 */
- (int)joinChannelByKey:(NSString *)channelKey
            channelName:(NSString *)channelName
                   info:(NSString *)info
                    uid:(NSUInteger)uid
            joinSuccess:(void(^)(NSString* channel, NSUInteger uid, NSInteger elapsed))joinSuccessBlock;

/**
 *  lets the user leave a channel, i.e., hanging up or exiting a call.
 After joining a channel, the user must call the leaveChannel method to end the call before joining another one.
 It is synchronous, i.e., it only returns until the call ends and all resources are released.
 *  @param leaveChannelBlock indicate the statistics of this call, from joinChannel to leaveChannel, including duration, tx bytes and rx bytes in the call.
 *
 *  @return 0 if executed successfully, or return negative value if failed.
 */
- (int)leaveChannel:(void(^)(AgoraRtcStats* stat))leaveChannelBlock;

/**
 *  Enables the network quality test. When enabled, the callback 'networkQualityBlock' notifies the application about the user's network connection quality.
 Note: Once the network test is enabled, it uses the network bandwidth even when the application is not in a call.
 Recommandation: When the application is foreground, enable the network connection test; and when the application is switched to background, disable network test  in order to reduce network traffic.
 By default, the network test is disabled
 *
 *  @return 0 when executed successfully, and return negative value when failed.
 */
- (int)enableLastmileTest;

/**
 *  Disables the network quality test.
 *
 *  @return 0 when executed successfully, and return negative value when failed.
 */
- (int)disableLastmileTest;

/**
 *  Enables video mode.  Switches from audio to video mode.
 It could be called during a call and before entering a channel.
 *
 *  @return 0 when this method is called successfully, or negative value when this method failed.
 */
- (int)enableVideo;

/**
 *  Disable video mode. Switch from video to audio mode.
 It could be called during a call and before entering a channel.
 *
 *  @return 0 when this method is called successfully, or negative value when this method failed.
 */
- (int)disableVideo;

/**
 *  Enables local video.
 *
 *  @param enabled YES to enabled local video capture and render (by default), NO to disable using local camera device.
 *  @return 0 when this method is called successfully, or negative value when this method failed.
 */
- (int)enableLocalVideo:(BOOL)enabled;

/**
 *  start local video preview, while not sending data to server
 *
 *  @return 0 when this method is called successfully, or negative value when this method failed.
 */
- (int)startPreview;

/**
 *  stop local video preview
 *
 *  @return 0 when this method is called successfully, or negative value when this method failed.
 */
- (int)stopPreview;

/**
 *  Enables audio function, which is enabled by default.
 *
 *  @return 0 when this method is called successfully, or negative value when this method failed.
 */
- (int)enableAudio;

/**
 *  Disable audio function.
 *
 *  @return 0 when this method is called successfully, or negative value when this method failed.
 */
- (int)disableAudio;

/**
 *  Specify sdk parameters
 *
 *  @param options sdk options in json format.
 */
- (int)setParameters:(NSString *)options;

- (NSString*)getParameter:(NSString*)parameter
                     args:(NSString*)args;

/**
 *  The call id of current client. The call id could be used in 'rate' and 'complain' method.
 *
 *  @return The current call id.
 */
- (NSString*) getCallId;

/**
 *  Let user rate the call. Often called after the call ends.
 *
 *  @param callId      The call ID retrieved from the 'getCallId' method.
 *  @param rating      The rating for the call between 1 (lowest score) to 10 (highest score).
 *  @param description Optional, decribed by user for the call with a length less than 800 bytes.
 *
 *  @return 0 when executed successfully. return ERR_INVALID_ARGUMENT (-2)：The passed argument is invalid, e.g., callId invalid. return ERR_NOT_READY (-3)：The SDK status is incorrect, e.g., initialization failed.
 */
- (int) rate:(NSString*) callId
      rating:(NSInteger) rating
 description:(NSString*) description;

/**
 *  Let user complain the quality of the call. Often called after the call ends.
 *
 *  @param callId      The call ID retrieved from the 'getCallId' method.
 *  @param description Optional, decribed by user for the call with a length less than 800 bytes.
 *
 *  @return 0 when executed successfully. return ERR_INVALID_ARGUMENT (-2)：The passed argument is invalid, e.g., callId invalid. return ERR_NOT_READY (-3)：The SDK status is incorrect, e.g., initialization failed.
 */
- (int) complain:(NSString*) callId
     description:(NSString*) description;

/** END OF COMMON METHODS */

/** BEGIN OF AUDIO METHODS */


/**
 *  Enable / Disable speaker of device
 *
 *  @param enableSpeaker YES: Switches to speakerphone. NO: Switches to headset.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setEnableSpeakerphone:(BOOL)enableSpeaker;

/**
 *  test if the speakerphone is enabled or not.
 *
 *  @return YES when speakerphone is enabled. NO when speakerphone is not enabled.
 */
- (BOOL)isSpeakerphoneEnabled;


/**
 *  Set default audio route to Speakerphone
 *
 *  @param defaultToSpeaker YES: default to speakerphone. NO: default to earpiece for voice chat, speaker for video chat.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setDefaultAudioRouteToSpeakerphone:(BOOL)defaultToSpeaker;


/**
 *  Sets the speakerphone volume. The speaker volume could be adjust by MPMusicPlayerController and other iOS API easily.
 *
 *  @param volume between 0 (lowest volume) to 255 (highest volume).
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setSpeakerphoneVolume:(NSUInteger)volume __deprecated;


/**
 *  Enables to report to the application about the volume of the speakers.
 *
 *  @param interval Specifies the time interval between two consecutive volume indications.
 <=0: Disables volume indication.
 >0 : The volume indication interval in milliseconds. Recommandation: >=200ms.
 *  @param smooth   The smoothing factor. Recommended: 3.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)enableAudioVolumeIndication:(NSInteger)interval
                            smooth:(NSInteger)smooth;


/**
 *  Start recording conversation to file specified by the file path.
 *
 *  @param filePath file path to save recorded conversation.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)startAudioRecording:(NSString*)filePath;


/**
 *  Stop conversation recording
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)stopAudioRecording;

- (int)startAudioMixing:(NSString*) filePath
               loopback:(BOOL) loopback
                replace:(BOOL) replace
                  cycle:(NSInteger) cycle;
- (int)stopAudioMixing;
- (int)pauseAudioMixing;
- (int)resumeAudioMixing;
- (int)adjustAudioMixingVolume:(NSInteger) volume;
- (int)getAudioMixingDuration;
- (int)getAudioMixingCurrentPosition;
- (int)setAudioMixingPosition:(NSInteger) pos;

/**
 *  Start screen capture
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)startScreenCapture:(NSUInteger)windowId;


/**
 *  Stop screen capture
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)stopScreenCapture;


- (int)setScreenCaptureId:(NSUInteger)windowId;


/**
 *  Launches an echo test to test if the audio devices (e.g., headset and speaker) and the network connection work properly. In the test, the user speaks first, and the recording will be played back in 10 seconds. If the user can hear what he said in 10 seconds, it indicates that the audio devices and network connection work properly.
 Notes: Must call stopEchoTest to end the test, or won't start new test or join channel.
 *
 *  @param successBlock The callback indicates that the user has successfully joined the specified channel.
 *
 *  @return 0 when executed successfully. return negative value if failed. e.g. ERR_REFUSED (-5)：Failed to launch the echo test, e.g., initialization failed.
 */
- (int)startEchoTest:(void(^)(NSString* channel, NSUInteger uid, NSInteger elapsed))successBlock;

/**
 *  Stop echo test.
 *
 *  @return 0 when executed successfully. return negative value if failed. e.g. ERR_REFUSED(-5)：Failed to stop the echo test. It could be that the echo test is not running.
 */
- (int)stopEchoTest;

/**
 *  Mutes / Unmutes local audio.
 *
 *  @param mute true: Mutes the local audio. false: Unmutes the local audio.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)muteLocalAudioStream:(BOOL)mute;


/**
 *  Mutes / Unmutes all remote audio.
 *
 *  @param mute true: Mutes all remote received audio. false: Unmutes all remote received audio.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */

- (int)muteAllRemoteAudioStreams:(BOOL)mute;

- (int)muteRemoteAudioStream:(NSUInteger)uid
                        mute:(BOOL)mute;

/** END OF AUDIO METHODS */

/** BEGIN OF VIDIO METHODS */

/**
 *  Set up the local video view. The video canvus is initialized with video display setting. And it could be called before entering a channel.
 *
 *  @param local the canvas is composed of view, renderMode and uid. How to initialize 'local'? please take a look at 'AgoraRtcVideoCanvas'
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setupLocalVideo:(AgoraRtcVideoCanvas*)local;


/**
 *  Set up the remote video view. The video canvus is initialized with video display setting. It could be called after receiving the remote video streams to configure the video settings.
 *
 *  @param remote the canvas is composed of view, renderMode and uid. How to initialize 'remote'? please take a look at 'AgoraRtcVideoCanvas'
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setupRemoteVideo:(AgoraRtcVideoCanvas*)remote;


/**
 *  Configure display setting of local view. And it could be called mutiple times during a call.
 *
 *  @param mode There are Hidden(1), Fit(2) and Adaptive(3) mode. Please take a look at definition of enum AgoraRtcRenderMode
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setLocalRenderMode:(AgoraRtcRenderMode) mode;


/**
 *  Configure display setting of remote view. And it could be called mutiple times during a call.
 *
 *  @param uid  The user id of remote view.
 *  @param mode There are Hidden(1), Fit(2) and Adaptive(3) mode. Please take a look at definition of enum AgoraRtcRenderMode
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setRemoteRenderMode: (NSUInteger)uid
                      mode:(AgoraRtcRenderMode) mode;

/**
 *  API deprecated. Please refer to open source https://github.com/AgoraLab/AgoraDemo, the function "onSwitchRemoteUsers" in demo agora-easycall-ios-oc
 *   Switches between video display views of two different users.
 *
 *  @param uid1 The user ID of the user whose video is to be switched.
 *  @param uid2 The user ID of another user whose video is to be switched.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)switchView:(NSUInteger)uid1
       andAnother:(NSUInteger)uid2 __deprecated;


/**
 *  Switches between front and back cameras.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)switchCamera;

/**
 *  set video profile, including resolution, fps, kbps
 *
 *  @param profile enumeration definition about the video resolution, fps and max kbps
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setVideoProfile:(AgoraRtcVideoProfile)profile
    swapWidthAndHeight:(BOOL)swapWidthAndHeight;


/**
 *  Enable / disable sending local video streams to the network.
 *
 *  @param mute YES: stop sending local video stream to the network, NO: start sending local video stream.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)muteLocalVideoStream:(BOOL)mute;


/**
 *  Enables / disables playing all remote callers’ video streams.
 *
 *  @param mute YES: stop playing, NO: start playing.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)muteAllRemoteVideoStreams:(BOOL)mute;

/**
 *  Enable / disable a remote user's video stream
 *
 *  @param uid  The remote user id
 *  @param mute YES: discard the video stream from remote user, NO: start receiving remote video stream.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)muteRemoteVideoStream:(NSUInteger)uid
                        mute:(BOOL)mute;

/** END OF VIDIO METHODS */

/**
 *  Specifies the SDK output log file.
 *
 *  @param filePath The full file path of the log file.
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setLogFile:(NSString*)filePath;

/**
 *  Specifiy the log level of output
 *
 *  @param filter The log level
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setLogFilter:(NSUInteger)filter;

/**
 *  Renew channel key, refresh the new key into agora engine. APP should call this API when SDK reports error ERR_CHANNEL_KEY_EXPIRED.
 *
 *  @param channelKey new channel key
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)renewChannelKey:(NSString*)channelKey;


/**
 *  Set the channel profile: such as communication, live broadcasting
 *
 *  @param profile the channel profile
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setChannelProfile:(AgoraRtcChannelProfile)profile;

/**
 *  Set the role of user: such as broadcaster, audience
 *
 *  @param role the role of client
 *  @param permissionKey the permission key of role change
 *
 *  @return 0 when executed successfully. return negative value if failed.
 */
- (int)setClientRole:(AgoraRtcClientRole)role withKey: (NSString *)permissionKey;

- (int)enableDualStreamMode:(BOOL)enabled;

/**
	* Specify encryption mode of AES encryption algorithm.
	* @param [in] encryptionMode
	*        encryption mode of AES algorithm, could be one of the following:
	*          "aes-128-xts", "aes-256-xts".
	*          The default value is "aes-128-xts". specify NULL value will use default encryption mode.
	* @return return 0 if success or an error code
	*/
- (int)setEncryptionMode:(NSString*)encryptionMode;

/**
	* Specifying encryption secret enables built-in AES-128 encryption. Leaving channel will clear the secret specified in last channel
	* @param [in] secret
	*        secret to enable encryption
	* @return return 0 if success or an error code
	*/
- (int)setEncryptionSecret:(NSString*)secret;

- (int) setRemoteVideoStream: (NSUInteger) uid
                        type: (AgoraRtcVideoStreamType) streamType;


- (int) startRecordingService:(NSString*)recordingKey;
- (int) stopRecordingService:(NSString*)recordingKey;
- (int) refreshRecordingServiceStatus;

- (int)createDataStream:(NSInteger*)streamId
               reliable:(BOOL)reliable
                ordered:(BOOL)ordered;
- (int)sendStreamMessage:(NSInteger)streamId
                    data:(NSData*)data;

- (int)setRecordingAudioFrameParametersWithSampleRate:(NSInteger)sampleRate
                                              channel:(NSInteger)channel
                                                 mode:(AgoraRtcRawAudioFrameOpMode)mode
                                       samplesPerCall:(NSInteger)samplesPerCall;
- (int)setPlaybackAudioFrameParametersWithSampleRate:(NSInteger)sampleRate
                                             channel:(NSInteger)channel
                                                mode:(AgoraRtcRawAudioFrameOpMode)mode
                                      samplesPerCall:(NSInteger)samplesPerCall;
- (int)setMixedAudioFrameParametersWithSampleRate:(NSInteger)sampleRate
                                   samplesPerCall:(NSInteger)samplesPerCall;

/**
 * adjust recording signal volume
 *
 * @param [in] volume range from 0 to 400
 *
 * @return return 0 if success or an error code
 */
- (int)adjustRecordingSignalVolume:(NSInteger)volume;

/**
 * adjust playback signal volume
 *
 * @param [in] volume range from 0 to 400
 *
 * @return return 0 if success or an error code
 */
- (int)adjustPlaybackSignalVolume:(NSInteger)volume;

- (int)setHighQualityAudioParametersWithFullband:(BOOL)fullband
                                          stereo:(BOOL)stereo
                                     fullBitrate:(BOOL)fullBitrate;
- (int)enableInEarMonitoring:(BOOL)enabled;

- (int)enableWebSdkInteroperability:(BOOL)enabled;

- (int)setVideoQualityParameters:(BOOL)preferFrameRateOverImageQuality;

- (int)configPublisher:(AgoraPublisherConfiguration *)config;

- (int)setVideoCompositingLayout:(AgoraRtcVideoCompositingLayout*)layout;

- (int)clearVideoCompositingLayout;

#if (!(TARGET_OS_IPHONE) && (TARGET_OS_MAC))

- (void) monitorDeviceChange: (BOOL)enabled;
- (NSArray*) enumerateDevices:(AgoraRtcDeviceType)type;  // return array of AgoraRtcDeviceInfo
- (NSString*) getDeviceId:(AgoraRtcDeviceType)type;
- (int) setDevice:(AgoraRtcDeviceType)type deviceId:(NSString *) deviceId;
- (int) getDeviceVolume:(AgoraRtcDeviceType)type;
- (int) setDeviceVolume:(AgoraRtcDeviceType)type volume:(int)volume;

- (int) startRecordingDeviceTest:(int) indicationInterval;
- (int) stopRecordingDeviceTest;

- (int) startPlaybackDeviceTest:(NSString *) audioFileName;
- (int) stopPlaybackDeviceTest;

- (int) startCaptureDeviceTest:(NSView *) view;
- (int) stopCaptureDeviceTest;
#endif

- (int) checkAVUrlCompatibility:(NSURL*)url
                completionBlock:(void(^)())checkCompletionBlock;

//Audio Effects
- (double) getEffectsVolume;
- (int) setEffectsVolume:(double) volume;
- (int) playEffect:(int) soundId
          filePath:(NSString*)filePath
              loop:(BOOL)loop
             pitch:(double) pitch
               pan:(double) pan
              gain:(double) gain;
- (int) stopEffect:(int) soundId;
- (int) stopAllEffects;
- (int) preloadEffect:(int) soundId
             filePath:(NSString*) filePath;
- (int) unloadEffect:(int) soundId;
- (int) pauseEffect:(int) soundId;
- (int) pauseAllEffects;
- (int) resumeEffect:(int) soundId;
- (int) resumeAllEffects;

/**
 * External video source support
 * For new application, uses this interface instead of the one in libvideoprp
 */
// query whether texture can be supported (always returns YES)
//+ (BOOL) isTextureEncodeSupported;
// If external video source is to use, call this API before enableVideo/startPreview
- (void) setExternalVideoSource:(BOOL) enable useTexture:(BOOL)useTexture pushMode:(BOOL)pushMode;
// Push a video frame to SDK
- (BOOL) pushExternalVideoFrame:(AgoraVideoFrame *)frame;
@end
