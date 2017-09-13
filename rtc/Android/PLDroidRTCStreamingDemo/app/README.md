# 直播 API - Android


Java接口类|	描述
---|---
LiveEngine 类	|直播的基础类，用于管理频道。
LiveSubscriber 类|	用于管理观众订阅主播的类。
LivePublisher 类|	用于管理主播推流的类。
LiveEngineHandler 类|	为 LiveEngine 类提供回调的抽象类。
LiveSubscriberHandler 类|	为观众提供回调的抽象类。
LivePublisherHandler 类|	为主播提供回调的抽象类。
LiveChannelConfig 类|用于为直播打开或关闭视频的公共静态类。
LiveStats 类	|公共静态类，用于提供回调，返回有关直播的统计数据。
LiveTranscoding 类	|用于管理 CDN 转码的公共类。<br/>TranscodingUser 类 ：LiveTranscoding 类中的公共静态类，用于提供与用户相关的音视频转码设置。
Constants 类	|用于为直播和通信定义常量的公共类。

>RtcEngine 类目前仍然开放。Agora 的老用户和高级编程人员如希望调用以下功能可以使用该类：
- Fullband
- 混音特效
- 视频自采集
- 通过 setParameters() 方法调用 Agora 的私有接口。
>您可以通过调用 getRtcEngine() 方法获取一个 RtcEngine 类的实例。有关 RtcEngine 类提供的 API 的更多详情，请见 直播 API - Android


# LiveEngine 类
该类为直播的基础类，用于管理频道。LiveEngine 类包括以下函数：

- getSdkVersion()
- createLiveEngine()
- getRtcEngine()
- destroy()
- joinChannel()
- leaveChannel()
- startPreview()
- stopPreview()

#### 获得 SDK 版本 (getSdkVersion)

```
public static String getSdkVersion()
```
<br/>



#### 创建 LiveEngine 实例 (createLiveEngine)

```
public static LiveEngine createLiveEngine(Context context, String appId, LiveEngineHandler handler)
```
参数	|描述
---|---
context	|Android 行为的上下文。
appId	|由 Agora 签发给应用程序开发者的 App ID 。若您的开发包中没有 App ID ，请向 Agora 重新申请一个。
handler	|LiveEngineHandler 是一个为 LiveEngine 类提供回调的抽象类。

<br/>

#### 获取 RTCEngine (getRtcEngine)

```
public abstract RtcEngine getRtcEngine();
```

该方法用于获取一个 RtcEngine 的实例。



>RtcEngine 类目前仍然开放。Agora 的老用户和高级编程人员如希望调用以下功能可以使用该类：

- Fullband
- 混音特效
- 视频自采集
- 通过 setParameters() 方法调用 Agora 的私有接口。
>有关 RtcEngine 类的更多详情，请见 [直播 API - Android](http://test-docs.agora.io/cn/live_phase1/user_guide/API/android_live_1_12.html)

<br/>

#### 销毁 (destroy)
```
public abstract void destroy();
```
>该方法用于销毁一个 LiveEngine 的实例。
<br/>

#### 加入频道 (joinChannel)

```
public abstract int joinChannel(String channelId, String channelKey, LiveChannelConfig channelConfig, int uid);
```
>该方法用于让用户加入频道.

参数	| 描述
---|---
channelId	|加入相同的频道可以理解为用户进入了相同的房间。
channelKey	|由 App 经过签名认证生成。
channelConfig	|频道的配置信息。
uid	|在频道中的每个用户独一无二的 ID 。
<br/>

#### 离开频道 (leaveChannel)

```
public abstract int leaveChannel();
```
<br/>



#### 开始本地视频预览 (startPreview)

```
public abstract int startPreview(SurfaceView view, int renderMode);
```
>该方法用于开始本地视频预览且不向服务器发送数据。

参数	|描述
---|---
view	|将要渲染的 view 。
renderMode	|渲染模式。更多有关 renderMode 的更多详情请见下表。




名称	|值	|描述
---|---|---
RENDER_MODE_HIDDEN|1|如果视频大小与显示窗口不同，大则裁剪小则拉伸，以达到适配的目的。
RENDER_MODE_FIT	|2|如果视频大小与显示窗口不同，按比例调整视频大小以适配窗口。
RENDER_MODE_ADAPTIVE|3|	如果两个呼叫者使用相同的屏幕模式，即同时使用纵屏或同时使用横屏， RENDER_MODE_HIDDEN 模式适用。<br/>如果两个呼叫者使用不同的屏幕模式，即一个纵屏一个横屏， RENDER_MODE_FIT 模式适用。
<br/>



#### 停止本地视频预览 (stopPreview)

```
public abstract int stopPreview();
```
<br/>

# LiveSubscriber 类
该类用于管理主播推流。LiveSubscriber 类包括以下函数：
- LiveSubscriber()
- getLiveSubscriberHandler()
- subscribe()
- unsubscribe()

# Live Subscriber (LiveSubscriber)
该类用于管理观众订阅主播。

```
public LiveSubscriber(LiveEngine engine, LiveSubscriberHandler handler)
```
>该方法用于构造一个 LiveSubscriber 的实例。

参数|描述
---|---
engine |	LiveEngine 类是直播的基础类，用于管理频道。
handler |	LiveSubscriberHandlerHandler 类是一个为 LiveEngine 类提供回调的抽象类。
<br/>

#### 获取一个 LiveSubscriberHandler 的实例 (getLiveSubscriberHandler)


```
public LiveSubscriberHandler getLiveSubscriberHandler()
```
<br/>

#### 订阅主播 (subscribe)

```
public void subscribe(int uid, int mediaType, Object view, int renderMode, int videoType)
```


参数|	描述
---|---
uid	|user id
mediaType	|1: 只有语音。<br/>2: 只有视频。<br/>3: 语音和视频<br/>0: 无语音无视频。
view	|将被渲染的 view 。
renderMode	|渲染模式。有关 renderMode 的更多详情请见下表。
videoType|VIDEO_STREAM_HIGH = 0: 高帧率高分辨率视频流，即大流。<br/>VIDEO_STREAM_LOW = 1: 低帧率低分辨率视频流，即小流。

名称	|值|	描述
---|---|---
RENDER_MODE_HIDDEN	|1	|如果视频大小与显示窗口不同，大则裁剪小则拉伸，以达到适配的目的。
RENDER_MODE_FIT	|2|	如果视频大小与显示窗口不同，按比例调整视频大小以适配窗口。
RENDER_MODE_ADAPTIVE	|3|	如果两个呼叫者使用相同的屏幕模式，即同时使用纵屏或同时使用横屏， RENDER_MODE_HIDDEN 模式适用。<br/>如果两个呼叫者使用不同的屏幕模式，即一个纵屏一个横屏， RENDER_MODE_FIT 模式适用。
<br/>

#### 解除订阅 (unsubscribe)

```
public void unsubscribe(int uid)
```
>该方法用于解除订阅主播。

参数|	描述
---|---
uid	|主播的 user id
<br/>

# LivePublisher 类
该类用于管理主播推流。 LivePublisher 类包括以下函数：

- LivePublisher()
- getLivePublisherHandler()
- setLiveTranscoding()
- setMediaType()
- publishWithPermissionKey()
- setVideoResolution()
- addStreamUrl()
- removeStreamUrl()
- unpublish()
- switchCamera()

#### 构造一个 LivePublisher 类的实例 (LivePublisher)

```
public LivePublisher(LiveEngine engine, LivePublisherHandler handler)
```
<br/>


参数|	描述
---|---
engine|	LiveEngine 类为直播的基础类，用于管理频道。
handler|	LivePublisherHandler 类是为 LivePublisher 类提供回调的抽象类。
<br/>

#### 获取一个 LivePublisherHandler 类的实例 (getLivePublisherHandler)

```
public LivePublisherHandler getLivePublisherHandler()
```
<br/>

#### 设置直播转码 (setLiveTranscoding)

```
public void setLiveTranscoding(LiveTranscoding transcoding)
```
>该方法用于CDN转码。 总而言之，该方法用于 CDN 推流的视图布局及音频设置等。

参数	|描述
---|---
transcoding	|直播转码的相关配置。
<br/>

#### 设置媒体类型 (setMediaType)

```
public void setMediaType(int mediaType)
```
参数	|描述
---|---
mediaType|	1: 只有语音。<br/>2: 只有视频。<br/>3: 语音和视频。<br/>0: 无语音无视频。
<br/>

#### 通过鉴权秘钥推流 (publishWithPermissionKey)
```
public void publishWithPermissionKey(String permissionKey)
```
参数	|描述
---|---
permissionKey	|默认值为 NULL 。 若您需要更多有关鉴权秘钥的信息，请联系 Agora 。
<br/>

#### 设置视频编码分辨率、帧率、比特率 (setVideoResolution)

```
public int setVideoResolution(int width, int height, int frameRate, int bitrate)
```
>该方法用于设置视频编码分辨率、帧率和码率。

参数	|描述
---|---
width	|视频编码分辨率：宽度
height	|视频编码分辨率：高度
frameRate	|视频编码帧率
bitrate	|视频编码码率

>有关该方法具体参数的设置以下纵屏和横屏配置表。

##### 纵屏配置表：

高|	宽|	fps|	kbps|	描述
---|---|---|---|---
160| 120|	15|	65	 
120|	120|	15|	50|	仅用于 iPhone 。
320|	180|	15|	140|	仅用于 iPhone 。
180|	180|	15|	100|	仅用于 iPhone 。
240|	180|	15|	120|	仅用于 iPhone 。
320|	240|	15|	200|	 
240|	240|	15|	140|	仅用于 iPhone 。
424|	240|	15|	220|	仅用于 iPhone 。
640|	360|	15|	400|	为 Agora 的默认和推荐配置
360	|360	|15	|260	|仅用于 iPhone 。
640	|360	|30	|600	 
360	|360	|30	|400	 
480	|360	|15	|320	 
480	|360	|30	|490	 
640	|360	|15	|800	 
640	|360	|24	|800	 
640	|360	|24	|1000	 
640	|480	|15	|500	 
480	|480	|15	|400	|仅用于 iPhone 。
640	|480	|30	|750	 
480	|480	|30	|600	 
848	|480	|15	|610	 
848	|480	|30	|930	 
640	|480	|10	|400	 
1280|	720	|15	|1130	 
1280|	720	|30	|1710	 
960	|720	|15	|910	 
960	|720	|30	|1380
<br/>

##### 横屏配置表：

高	|宽	|fps	|kbps	|描述
---|---|---|---|---
120	|160	|15	|65	 
120	|120	|15	|50	|仅用于 iPhone 。
180	|320	|15	|140|	仅用于 iPhone 。
180	|180	|15	|100|	仅用于 iPhone 。
180	|240	|15	|120|	仅用于 iPhone 。
240	|320	|15	|200	 
240	|240	|15	|140|	仅用于 iPhone 。
240	|424	|15	|220|	仅用于 iPhone 。
360	|640	|15	|400	 
360	|360	|15	|260|	仅用于 iPhone 。
360	|640	|30	|600	 
360	|360	|30	|400	 
360	|480	|15	|320	 
360	|480	|30	|490	 
360	|640	|15	|800	 
360	|640	|24	|800	 
360	|640	|24	|1000	 
480	|640	|15	|500	 
480	|480	|15	|400|	仅用于 iPhone 。
480	|640	|30	|750	 
480	|480	|30	|600	 
480	|848	|15	|610	 
480	|848	|30	|930	 
480	|640	|10	|400	 
720	|1280	|15	|1130	 
720	|1280	|30	|1710	 
720	|960	|15	|910	 
720	|960	|30	|1380
<br/>

#### 添加推流地址 (addStreamUrl)

```
public void addStreamUrl(String url, boolean isTranscodingEnabled)
```
>该方法用于CDN推流，用于添加推流地址。

参数|	描述
---|---
url	|推流地址
isTranscodingEnabled	|是否转码
<br/>

#### 删除推流地址 (removeStreamUrl)

```
public void removeStreamUrl(String url)
```
>该方法用于CDN推流，用于删除推流地址。

参数	|描述
---|---
url	|推流地址
<br/>

#### 停止推流 (Unpublish)

```
public void unpublish()
```
<br/>

#### 切换摄像头 (switchCamera)

```
public void switchCamera()
```
<br/>

# LiveEngineHandler 类
该抽象类为 LiveEngine 类提供回调。 LiveEngineHandler 类包括以下回调：

- onWarning()
- onError()
- onLeaveChannel()
- onJoinChannel()
- onLeaveChannel()
- onRejoinChannel()
- onReportLiveStats()
- onConnectionInterrupted()
- onConnectionLost()
- onNetworkQuality()

#### 发生警告回调 (onWarning)

```
public void onWarning(int warningCode)
```

>该回调用于提示在 SDK 运行时有警告发生。

参数	|描述
---|---
warningCode	| 警告代码：<br/>WARN_INVALID_VIEW = 8<br/>WARN_INIT_VIDEO = 16<br/>WARN_PENDING = 20<br/>WARN_NO_AVAILABLE_CHANNEL = 103<br/>WARN_LOOKUP_CHANNEL_TIMEOUT = 104<br/>WARN_LOOKUP_CHANNEL_REJECTED = 105<br/>WARN_OPEN_CHANNEL_TIMEOUT = 106<br/>WARN_OPEN_CHANNEL_REJECTED = 107<br/>WARN_SWITCH_LIVE_VIDEO_TIMEOUT = 111<br/>WARN_SET_CLIENT_ROLE_TIMEOUT = 118<br/>WARN_SET_CLIENT_ROLE_NOT_AUTHORIZED = 119<br/>WARN_AUDIO_MIXING_OPEN_ERROR = 701<br/>WARN_ADM_RUNTIME_PLAYOUT_WARNING = 1014<br/>WARN_ADM_RUNTIME_RECORDING_WARNING = 1016<br/>WARN_ADM_RECORD_AUDIO_SILENCE = 1019<br/>WARN_ADM_PLAYOUT_MALFUNCTION = 1020<br/>WARN_ADM_RECORD_MALFUNCTION = 1021<br/>WARN_APM_HOWLING = 1051
<br/>

#### 发生错误回调 (onError)

```
public void onError(int errorCode)
```
>该回调用于提示在 SDK 运行时有错误发生。

参数	|描述
---|---
errorCode | ERR\_OK = 0<br/>ERR\_FAILED = 1<br/>ERR\_INVALID\_ARGUMENT = 2<br/>ERR\_NOT\_READY = 3<br/>ERR\_NOT\_SUPPORTED = 4<br/>ERR\_REFUSED = 5<br/>ERR\_BUFFER\_TOO\_SMALL = 6<br/>ERR\_NOT\_INITIALIZED = 7<br/>ERR\_NO\_PERMISSION = 9<br/>ERR\_TIMEDOUT = 10<br/>ERR\_CANCELED = 11<br/>ERR\_TOO\_OFTEN = 12<br/>ERR\_BIND\_SOCKET = 13<br/>ERR\_NET\_DOWN = 14<br/>ERR\_NET\_NOBUFS = 15<br/>ERR\_JOIN\_CHANNEL\_REJECTED = 17<br/>ERR\_LEAVE\_CHANNEL\_REJECTED = 18<br/>ERR\_ALREADY\_IN\_USE = 19<br/>ERR\_INVALID\_APP\_ID = 101<br/>ERR\_INVALID\_CHANNEL\_NAME = 102<br/>ERR\_CHANNEL\_KEY\_EXPIRED = 109<br/>ERR\_INVALID\_CHANNEL\_KEY = 110<br/>ERR\_CONNECTION\_INTERRUPTED = 111<br/>ERR\_CONNECTION\_LOST = 112<br/>ERR\_NOT\_IN\_CHANNEL = 113<br/>ERR\_SIZE\_TOO\_LARGE = 114<br/>ERR\_BITRATE\_LIMIT = 115<br/>ERR\_TOO\_MANY\_DATA\_STREAMS = 116<br/>ERR\_DECRYPTION\_FAILED = 120<br/>ERR\_LOAD\_MEDIA\_ENGINE = 1001<br/>ERR\_START\_CALL = 1002<br/>ERR\_START\_CAMERA = 1003<br/>ERR\_START\_VIDEO\_RENDER = 1004<br/>ERR\_ADM\_GENERAL\_ERROR = 1005<br/>ERR\_ADM\_JAVA\_RESOURCE = 1006<br/>ERR\_ADM\_SAMPLE\_RATE = 1007<br/>ERR\_ADM\_INIT\_PLAYOUT = 1008<br/>ERR\_ADM\_START\_PLAYOUT = 1009<br/>ERR\_ADM\_STOP\_PLAYOUT = 1010<br/>ERR\_ADM\_INIT\_RECORDING = 1011<br/>ERR\_ADM\_START\_RECORDING = 1012<br/>ERR\_ADM\_STOP\_RECORDING = 1013<br/>ERR\_ADM\_RUNTIME\_PLAYOUT\_ERROR = 1015<br/>ERR\_ADM\_RUNTIME\_RECORDING\_ERROR = 1017<br/>ERR\_ADM\_RECORD\_AUDIO\_FAILED = 1018<br/>ERR\_ADM\_INIT\_LOOPBACK = 1022<br/>ERR\_ADM\_START\_LOOPBACK = 1023<br/>ERR\_AUDIO\_BT\_SCO\_FAILED = 1030<br/>ERR\_VDM\_CAMERA\_NOT\_AUTHORIZED = 1501; Note that the VDM error code starts off from 1500.<br/>ERR\_VCM\_UNKNOWN\_ERROR = 1600; Note that the VCM error code starts off from 1600.<br/>ERR\_VCM\_ENCODER\_INIT\_ERROR = 1601<br/>ERR\_VCM\_ENCODER\_ENCODE\_ERROR=1602<br/>ERR\_VCM\_ENCODER\_SET\\_ERROR = 1603


<br/>

#### 离开频道回调 (onLeaveChannel)

```
public void onLeaveChannel()
```
<br/>

#### 加入频道回调 (onJoinChannel)

```
public void onJoinChannel(String channel, int uid, int elapsed)
```
>该回调用于通知 App 用户已经通过分配的 Channel ID 和 user ID 加入频道。

参数|	描述
---|---
channel	|频道名称
uid	|User id:<br/>如果 uid 已经被 joinChannel 指定，则返回该 ID 。<br/>否则，将返回由 Agora 服务器自动分配的 ID 。
elapsed	 | 自加入频道直到该事件发生总共流逝的时间（ms）。
<br/>

#### 重新加入频道回调 (onRejoinChannel)

```
public void onRejoinChannel(String channel, int uid, int elapsed)
```
>当用户由于网络原因与服务器断开链接，SDK 将自动尝试重连。当 服务器与用户重新连接上时该回调被触发。

参数	|描述
---|---
channel	|频道名称
uid	|User ID
elapsed	|自加入频道直到该事件发生总共流逝的时间（ms）。
<br/>

#### LiveStats 回调 (onReportLiveStats)

```
public void onReportLiveStats(LiveEngine.LiveStats stats)
```
>该回调返回直播相关的统计数据。

参数	|描述
---|---
stats |duration<br/>txBytes<br/>rxBytes<br/>txAudioKBitrate<br/>rxAudioKBitrate<br/>txVideoKBitrate<br/>rxVideoKBitrate<br/>userCount<br/>cpuAppUsage<br/>cpuTotalUsage

<br/>

属性名称	|描述
---|---
duration	|通话时长（秒）
txBytes	|发送的总字节数
rxBytes	|接收的总字节数
txAudioKBitrate	|当前发送音频的比特率
rxAudioKBitrate	|当前接收音频的比特率
txVideoKBitrate	|当前发送视频的比特率
rxVideoKBitrate	|当前接收视频的比特率
userCount	|参与直播的用户总数
cpuAppUsage	|由 App 贡献的 CPU 占用率
cpuTotalUsage	|总的 CPU 占用率

<br/>

#### 连接中断回调 (onConnectionInterrupted)

```
public void onConnectionInterrupted()
```
>该回调提示发生了 SDK 与服务器断开连接的事件。这一事件会在连接断开的瞬间上报。与此同时，SDK自动尝试再次连接服务器直到 App 调用 leaveChannel() 为止。

<br/>

#### 连接丢失回调 (onConnectionLost)

```
public void onConnectionLost()
```
>该回调提示已发生与服务器失去连接的事件。这一事件发生在连接中断超过默认重试连接时间（默认10秒）。

<br/>

#### 网络质量回调 (onNetworkQuality)

```
public void onNetworkQuality(int uid, int txQuality, int rxQuality)
```
>该回调定期被触发，用于通知应用程序当前通信或直播频道的网络质量。

参数|	描述
---|---
uid	|User ID.
txQuality | 用户的上行网络质量：<br/>0: QUALITY\_UNKNOWN<br/>1: QUALITY\_EXCELLENT<br/>2: QUALITY\_GOOD<br/>3: QUALITY\_POOR<br/>4: QUALITY\_BAD<br/>5: QUALITY\_VBAD<br/>6: QUALITY\_DOWN
rxQuality | 用户的下行网络质量：<br/>0: QUALITY\_UNKNOWN<br/>1: QUALITY\_EXCELLENT<br/>2: QUALITY\_GOOD<br/>3: QUALITY\_POOR<br/>4: QUALITY\_BAD<br/>5: QUALITY\_VBAD<br/>6: QUALITY\_DOWN

<br/>

# LiveSubscriberHandler 类
该抽象类为观众提供回调。 LiveSubscriberHandler 类包括以下回调：
- publishedByHostUid()
- unpublishedByHostUid()
- onStreamTypeChangedTo()
- onFirstRemoteVideoDecoded()
- onVideoSizeChanged()
 
#### 主播推流回调 (publishedByHostUid)

```
public void publishedByHostUid(int uid, int streamType)
```
>该回调用于通知：主播已经开始推流。

参数	|描述
---|---
uid	|主播的 user ID
streamType | 大小流类型：<br/><br/>大流：高分辨率高帧率视频流<br/>小流：低分辨率低帧率视频流

<br/>

#### 主播停止推流回调 (unpublishedByHostUid)

```
public void unpublishedByHostUid(int uid)
```
参数	|描述
---|---
uid	|主播的 user ID 。

<br/>

#### 流类型改变回调 (onStreamTypeChangedTo)

```
public void onStreamTypeChangedTo(int streamType, int uid)
```
>该回调用于通知：大小流类型已经转换。

参数|	描述
---|---
streamType |大小流类型：<br/><br/>大流：高分辨率高帧率视频流<br/>小流：低分辨率低帧率视频流
uid	| User ID

<br/>

#### 第一帧远端视频已被解码回调 (onFirstRemoteVideoDecoded)

```
public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed)
```
参数	|描述
---|---
uid|	User ID 。
width|	第一帧视频帧的宽度 (像素) 。
height|	第一帧视频帧的高度 (像素) 。
elapsed|	自会话开始后流逝的时间 （ms）。
<br/>

#### 视频大小改变回调 (onVideoSizeChanged)

```
public void onVideoSizeChanged(int uid, int width, int height, int rotation)
```
参数	| 描述
---|---
uid	|User ID 。
width	|视频帧的宽度 (像素) 。
height	|视频帧的高度 (像素) 。
rotation	|视频新的旋转角度。它的值包括： 0, 90, 180, or 270 。默认为0 。

<br/>

# LivePublisherHandler 类
该抽象类为主播提供回调。 LivePublisherHandler 类包括以下回调：

- onPublishSuccess()
- onPUblishedFailed()
- onUnpublished()
- onPublisherTranscodingUpdated()

<br/>

#### 推流成功回调 (onPublishSuccess)

```
public void onPublishSuccess(St
```
>该回调用于通知主播 CDN 推流成功。

参数	|描述
---|---
url	|主播推流的 URL 地址。

<br/>

#### 推流失败回调 (onPublishedFailed)

```
public void onPublishedFailed(String url, int errorCode)
```
>该回调用于通知主播 CDN 推流失败。

参数	|描述
---|---
url	|主播推流的 URL 地址。
errorCode | ERR\_OK = 0<br/>ERR\_FAILED = 1<br/>ERR\_INVALID\_ARGUMENT = 2<br/>ERR\_NOT\_READY = 3<br/>ERR\_NOT\_SUPPORTED = 4<br/>ERR\_REFUSED = 5<br/>ERR\_BUFFER\_TOO\_SMALL = 6<br/>ERR\_NOT\_INITIALIZED = 7<br/>ERR\_NO\_PERMISSION = 9<br/>ERR\_TIMEDOUT = 10<br/>ERR\_CANCELED = 11<br/>ERR\_TOO\_OFTEN = 12<br/>ERR\_BIND\_SOCKET = 13<br/>ERR\_NET\_DOWN = 14<br/>ERR\_NET\_NOBUFS = 15<br/>ERR\_JOIN\_CHANNEL\_REJECTED = 17<br/>ERR\_LEAVE\_CHANNEL\_REJECTED = 18<br/>ERR\_ALREADY\_IN\_USE = 19<br/>ERR\_INVALID\_APP\_ID = 101<br/>ERR\_INVALID\_CHANNEL\_NAME = 102<br/>ERR\_CHANNEL\_KEY\_EXPIRED = 109<br/>ERR\_INVALID\_CHANNEL\_KEY = 110<br/>ERR\_CONNECTION\_INTERRUPTED = 111<br/>ERR\_CONNECTION\_LOST = 112<br/>ERR\_NOT\_IN\_CHANNEL = 113<br/>ERR\_SIZE\_TOO\_LARGE = 114<br/>ERR\_BITRATE\_LIMIT = 115<br/>ERR\_TOO\_MANY\_DATA\_STREAMS = 116<br/>ERR\_DECRYPTION\_FAILED = 120<br/>ERR\_LOAD\_MEDIA\_ENGINE = 1001<br/>ERR\_START\_CALL = 1002<br/>ERR\_START\_CAMERA = 1003<br/>ERR\_START\_VIDEO\_RENDER = 1004<br/>ERR\_ADM\_GENERAL\_ERROR = 1005<br/>ERR\_ADM\_JAVA\_RESOURCE = 1006<br/>ERR\_ADM\_SAMPLE\_RATE = 1007<br/>ERR\_ADM\_INIT\_PLAYOUT = 1008<br/>ERR\_ADM\_START\_PLAYOUT = 1009<br/>ERR\_ADM\_STOP\_PLAYOUT = 1010<br/>ERR\_ADM\_INIT\_RECORDING = 1011<br/>ERR\_ADM\_START\_RECORDING = 1012<br/>ERR\_ADM\_STOP\_RECORDING = 1013<br/>ERR\_ADM\_RUNTIME\_PLAYOUT\_ERROR = 1015<br/>ERR\_ADM\_RUNTIME\_RECORDING\_ERROR = 1017<br/>ERR\_ADM\_RECORD\_AUDIO\_FAILED = 1018<br/>ERR\_ADM\_INIT\_LOOPBACK = 1022<br/>ERR\_ADM\_START\_LOOPBACK = 1023<br/>ERR\_AUDIO\_BT\_SCO\_FAILED = 1030<br/>ERR\_VDM\_CAMERA\_NOT\_AUTHORIZED = 1501; Note that the VDM error code starts off from 1500.<br/>ERR\_VCM\_UNKNOWN\_ERROR = 1600; Note that the VCM error code starts off from 1600.<br/>ERR\_VCM\_ENCODER\_INIT\_ERROR = 1601<br/>ERR\_VCM\_ENCODER\_ENCODE\_ERROR=1602<br/>ERR\_VCM\_ENCODER\_SET\\_ERROR = 1603

<br/>

#### 停止推流回调 (onUnpublished)

```
public void onUnpublished(String url)
```
>该回调用于 CDN 推流，用于通知主播停止推流成功。

参数	|描述
---|---
url	|主播推流的 URL 地址。

<br/>

#### 主播转码更新回调 (onPublisherTranscodingUpdated)

```
public void onPublisherTranscodingUpdated(LivePublisher publisher)
```
>该回调用于通知 LiveTranscoding 类转码已成功更新。

参数	|描述
---|---
url	|主播推流的 URL 地址。

<br/>

# LiveChannelConfig 类
LiveChannelConfig 类为公共静态类，用于打开或关闭直播视频。该类包括函数： LiveChannelConfig() 。


```
public boolean videoEnabled;
```

# LiveStats 类
LiveStats 为公共静态类，用于提供回调，返回直播相关的统计数据。


```
public int duration;
public int txBytes;
public int txAudioKBitrate;
public int rxAudioKBitrate;
public int txVideoKBitrate;
public int rxVideoKBitrate;
public int userCount;
public double cpuAppUsage;
public double cpuTotalUsage;
public LiveStats(IRtcEngineEventHandler.RtcStats rtcStats)
```


属性名称	|描述
---|---
duration|	通话时长（秒）
txBytes	|发送的总字节数
rxBytes	|接收的总字节数
txAudioKBitrate	|当前发送音频的比特率
rxAudioKBitrate	|当前接收音频的比特率
txVideoKBitrate	|当前发送视频的比特率
rxVideoKBitrate	|当前接收视频的比特率
userCount	|参与直播的用户总数
cpuAppUsage	|由 App 贡献的 CPU 占用率
cpuTotalUsage	|总的 CPU 占用率

<br/>


# LiveTranscoding 类
该类用于管理 CDN 转码。LiveTranscoding 类包括以下函数和类：

- LiveTranscoding()
- getUsers()
- setUsers()
- addUser(TranscodingUser user)
- addUser(int index, TranscodingUser user)
- setUser()
- getRed()
- getGreen()
- getBlue()
- setRed()
- setGreen()
- setBlue()
- setBackgroundColor(int color)
- setBackgroundColor(int red, int green, int blue)
- TranscodingUser 类
 


```
public int width;
public int height;
public int bitrate;
public int framerate;
public boolean lowLatency;
public int gop;
public int sampleRate;
public int videoCodecProfile;
public int userCount;
public String userConfigExtraInfo;
public int backgroundColor;
```

参数|	描述
---|---
width	|画布宽度
height	|画布高度
bitrate	|用于旁路直播的输出数据流的比特率。默认值为 400 Kbps 。
framerate|	用于旁路直播的输出视频流的帧率。默认值为 15 fps 。
lowLatency|	FALSE 为默认值 。
sampleRate | 音频采样率<br/><br/>Constants.AUDIO\_SAMPLE\_RATE\_TYPE\_32000<br/>Constants.AUDIO\_SAMPLE\_RATE\_TYPE\_44100<br/>Constants.AUDIO\_SAMPLE\_RATE\_TYPE\_48000 （默认值）
gop |
VideoCodecProfile | 视频编解码类型：<br/><br/>Constants.VIDEO\_CODEC\_PROFILE\_TYPE\_BASELINE<br/>Constants.VIDEO\_CODEC\_PROFILE\_TYPE\_MAIN<br/>Constants.VIDEO\_CODEC\_PROFILE\_TYPE\_HIGH （默认值）<br/>

userCount	|参与合图的用户数量
---|---
userConfigExtraInfo	|预留参数：用户配置的额外信息。
backgroundColor	|背景色

<br/>

#### 构造一个 LiveTranscoding 对象 (LiveTranscoding)

```
public LiveTranscoding()
```
<br/>


#### 批量获取用户 (getUsers)

```
public ArrayList<TranscodingUser> getUsers()
```
>该方法用于获取参与合图的全部用户。

<br/>

Parameter	|Description
---|---
users	|所有参与合图的用户： ArrayList<TranscodingUser>

<br/>

#### 添加一个用户 (addUser)

```
public void addUser(int index, TranscodingUser user)
```
>该方法用于添加用户并设置其在队列中的位置。

Parameter	|Description
---|---
index	|将要添加的用户的序列号。
user	|transcodingUser 类是一个位于LiveTranscoding 类内的公共静态类，用于提供用户相关的音视频转码配置。

<br/>

#### 获取背景红 (getRed)

```
public int getRed()
```
>该方法用于获取背景色中的红色主色信息。
<br>

#### 获取背景绿 (getGreen)

```
public int getGreen()
```
>该方法用于获取背景色中的绿色主色信息。
<br>

#### 获取背景蓝 (getBlue)

```
public int getBlue()
```
>该方法用于获取背景色中的蓝色主色信息。
<br>

#### 设置背景红 (setRed)

```
public void setRed(int red)
```
>该方法用于设置背景的红色主色而保持其他两个主色不变。
<br/>


#### 设置背景绿 (setGreen)

```
public void setGreen(int green)
```
>该方法用于设置背景的绿色主色而保持其他两个主色不变。
<br/>


#### 设置背景蓝 (setBlue)
```
public void setBlue(int blue)
```
>该方法用于设置背景的蓝色主色而保持其他两个主色不变。
<br/>

#### 设置背景色 (setBackgroundColor)
```
public void setBackgroundColor(int color)
```
>该方法用于设置背景色。

参数	|描述
---|---
color	|背景色
<br/>

#### 设置背景色 RGB (setBackgroundColor)

```
public void setBackgroundColor(int red, int green, int blue)
```
>该方法用于设置背景色。

参数	|描述
---|---
red	|红
green	|绿
blue	|蓝

<br/>

# TranscodingUser 类

```
public int uid;
public int x;
public int y;
public int width;
public int height;
public int zOrder;
public float alpha;
public int renderMode;
```

该公共静态类位于 LiveTranscoding 内，用于定义与用户相关的语音视频转码设置。


参数	|描述
---|---
uid	|CDN 主播的 user ID 。
x	|视频帧左上角的横轴位置。
y	|视频帧左上角的纵轴位置。
width	|视频帧宽度。
height	|视频帧高度。
zOrder	|视频帧所处层数。
alpha	|视频帧的透明度。
renderMode | 视频帧的渲染模式。<br/><br/>RENDER\_MODE\_HIDDEN: 1<br/>RENDER\_MODE\_FIT: 2<br/>RENDER\_MODE\_ADAPTIVE: 3

<br/>

# Constants 类
该公共类定义直播和通信用到的常量。

#### 媒体或网络质量常量
名称	|描述
---|---
QUALITY_UNKNOWN	|0
QUALITY_EXCELLENT|	1
QUALITY_GOOD	|2
QUALITY_POOR	|3
QUALITY_BAD	|4
QUALITY_VBAD	|5
QUALITY_DOWN	|6

<br/>

#### 警告代码

名称	|描述
---|---
QUALITY_UNKNOWN	|0
QUALITY_EXCELLENT	|1
QUALITY_GOOD	|2
QUALITY_POOR	|3
QUALITY_BAD	|4
QUALITY_VBAD	|5
QUALITY_DOWN	|6

<br/>

#### 错误代码

名称	|描述
---|---
ERR_OK	|0
ERR_FAILED	|1
ERR_INVALID_ARGUMENT|	2
ERR_NOT_READY	|3
ERR_NOT_SUPPORTED	|4
ERR_REFUSED	|5
ERR_BUFFER_TOO_SMALL	|6
ERR_NOT_INITIALIZED	|7
ERR_NO_PERMISSION	|9
ERR_TIMEDOUT	|10
ERR_CANCELED	|11
ERR_TOO_OFTEN	|12
ERR_BIND_SOCKET	|13
ERR_NET_DOWN	|14
ERR_NET_NOBUFS	|15
ERR_JOIN_CHANNEL_REJECTED	|17
ERR_LEAVE_CHANNEL_REJECTED	|18
ERR_ALREADY_IN_USE	|19
ERR_INVALID_APP_ID	|101
ERR_INVALID_CHANNEL_NAME	|102
ERR_CHANNEL_KEY_EXPIRED	|109
ERR_INVALID_CHANNEL_KEY	|110
ERR_CONNECTION_INTERRUPTED	|111
ERR_CONNECTION_LOST	|112
ERR_NOT_IN_CHANNEL	|113
ERR_SIZE_TOO_LARGE	|114
ERR_BITRATE_LIMIT	|115
ERR_TOO_MANY_DATA_STREAMS	|116
ERR_DECRYPTION_FAILED	|120
ERR_LOAD_MEDIA_ENGINE	|1001
ERR_START_CALL	|1002
ERR_START_CAMERA|	1003
ERR_START_VIDEO_RENDER|	1004
ERR_ADM_GENERAL_ERROR||	1005
ERR_ADM_JAVA_RESOURCE|	1006
ERR_ADM_SAMPLE_RATE	|1007
ERR_ADM_INIT_PLAYOUT|	1008
ERR_ADM_START_PLAYOUT|	1009
ERR_ADM_STOP_PLAYOUT|	1010
ERR_ADM_INIT_RECORDING|	1011
ERR_ADM_START_RECORDING|	1012
ERR_ADM_STOP_RECORDING	|1013
ERR_ADM_RUNTIME_PLAYOUT_ERROR|	1015
ERR_ADM_RUNTIME_RECORDING_ERROR|	1017
ERR_ADM_RECORD_AUDIO_FAILED	|1018
ERR_ADM_INIT_LOOPBACK	|1022
ERR_ADM_START_LOOPBACK	|1023
ERR_AUDIO_BT_SCO_FAILED	|1030
ERR_VDM_CAMERA_NOT_AUTHORIZED	|1501 请注意 VDM 错误代码始于 1500 。
ERR_VCM_UNKNOWN_ERROR	|1600 请注意 VCM 错误代码始于 1600 。
ERR_VCM_ENCODER_INIT_ERROR|	1601
ERR_VCM_ENCODER_ENCODE_ERROR|	1602
ERR_VCM_ENCODER_SET_ERROR	|1603

<br/>

#### 频道类型

名称	| 描述
---|---
CHANNEL_PROFILE_COMMUNICATION	|0
CHANNEL_PROFILE_LIVE_BROADCASTING	|1
CHANNEL_PROFILE_GAME	|2

<br/>

#### 用户角色
名称	|描述
---|---
CLIENT_ROLE_BROADCASTER	|1
CLIENT_ROLE_AUDIENCE	|2

<br/>

#### 下线原因
名称|	描述
---|---
USER_OFFLINE_QUIT	|0
USER_OFFLINE_DROPPED	|1
USER_OFFLINE_BECOME_AUDIENCE|	2

<br/>

#### 质量汇报格式

名称	|描述
---|---
QUALITY_REPORT_FORMAT_JSON	|0
QUALITY_REPORT_FORMAT_HTML	|1

<br/>

#### 渲染模式

名称	|值	|描述
---|---|---
AgoraVideoRenderModeHidden	|1	|If the video size is different from that of the display window, crop the video (if it is bigger) or stretch the video (if it is smaller) to fit it into the window.
AgoraVideoRenderModeFit	|2|	If the video size is different from that of the display window, resize the video proportionally to fit the window.
AgoraVideoRenderModeAdaptive	|3|	If both callers use the same screen mode, i.e., both use landscape or both use portrait, the AgoraVideoRenderModeHidden mode applies.<br/>If they use different screen modes, i.e., one landscape and one portrait, the AgoraVideoRenderModeFit mode applies.

<br/>


#### Log Filter

名称	|描述
---|---
LOG_FILTER_OFF	|0
LOG_FILTER_INFO	|0x0f
LOG_FILTER_WARNING	|0x0e
LOG_FILTER_ERROR	|0x0c
LOG_FILTER_CRITICAL	|0x08

<br/>

#### 音频路由类型

名称	| 描述
---|---
AUDIO_ROUTE_DEFAULT	|-1
AUDIO_ROUTE_HEADSET	|0
AUDIO_ROUTE_EARPIECE	|1
AUDIO_ROUTE_HEADSETNOMIC|	2
AUDIO_ROUTE_SPEAKERPHONE|	3
AUDIO_ROUTE_LOUDSPEAKER	|4
AUDIO_ROUTE_HEADSETBLUETOOTH|	5

<br/>

#### 大小流常量

名称	|描述
---|---
VIDEO_STREAM_HIGH	|0
VIDEO_STREAM_LOW	|1
<br/>

#### 硬件或软件视频编码

名称	|描述
---|---
HARDWARE_ENCODER|	0
SOFEWARE_ENCODER|	1
<br/>

#### Raw Audio Frame OP Mode-specific Constants

名称|	描述
---|---
RAW_AUDIO_FRAME_OP_MODE_READ_ONLY	|0
RAW_AUDIO_FRAME_OP_MODE_WRITE_ONLY	|1
RAW_AUDIO_FRAME_OP_MODE_READ_WRITE	|2
<br/>

#### Media Engine Errors or Warnings

名称	|描述
---|---
MEDIA_ENGINE_RECORDING_ERROR|	0
MEDIA_ENGINE_PLAYOUT_ERROR	|1
MEDIA_ENGINE_RECORDING_WARNING	|2
MEDIA_ENGINE_PLAYOUT_WARNING	|3
MEDIA_ENGINE_AUDIO_FILE_MIX_FINISH	|10
<br/>


### 媒体引擎相关角色改变
#### RTMP 生命周期
名称	|描述
---|---
STREAM_LIFE_CYCLE_BIND2CHANNEL	|1
STREAM_LIFE_CYCLE_BIND2OWNER	|2
<br/>


#### 语音录制质量

名称	|描述
---|---
AUDIO_RECORDING_QUALITY_LOW	|0
AUDIO_RECORDING_QUALITY_MEDIUM	|1
AUDIO_RECORDING_QUALITY_HIGH	|2
<br/>

#### 媒体类型常量

名称	|描述
---|---
MEDIA_TYPE_NONE	|0
MEDIA_TYPE_AUDIO_ONLY	|1
MEDIA_TYPE_VIDEO_ONLY	|2
MEDIA_TYPE_AUDIO_AND_VIDEO	|3
<br/>


#### 音频采样率常量

名称	|描述
---|---
AUDIO_SAMPLE_RATE_TYPE_32000	|32000
AUDIO_SAMPLE_RATE_TYPE_44100	|44100
AUDIO_SAMPLE_RATE_TYPE_48000	|48000
<br/>

#### 视频编解码类型
名称	|描述
---|---
VIDEO_CODEC_PROFILE_TYPE_BASELINE	|66
VIDEO_CODEC_PROFILE_TYPE_MAIN	|77
VIDEO_CODEC_PROFILE_TYPE_HIGH	|100










