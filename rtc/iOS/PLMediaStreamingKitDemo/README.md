### [Agora官网 | 电话 400 632 6626](https://www.agora.io/cn/) 

# AgoraLiveKit 类
该类为直播的基础类，用于管理频道

## 方法

#### 获取 SDK 版本 (getSdkVersion)

```bash
- (NSString *_Nonnull)getSdkVersion;
```
 <br/>
 
#### 获取 RTC 引擎对象 (getRtcEngineKit)

```bash
- (AgoraRtcEngineKit *_Nonnull)getRtcEngineKit;
```
<br/>

#### 初始化AgoraLiveKit单例对象 (sharedLiveKitWithAppId)

```bash
+ (instancetype _Nonnull)sharedLiveKitWithAppId:(NSString *_Nonnull)appId;
```

|   参数    |   描述    |
|:----------------------------:|:----------------------------:|
|appId|由 Agora 签发给应用开发者|
|返回值|AgoraLiveKit 类的一个对象|

<br/>

#### 销毁直播对象(destroy)
```bash
+ (void)destroy;
```

<br/>
#### 开始本地视频预览 (startPreview)
```bash
- (int)startPreview:(VIEW_CLASS *_Nonnull)view
         renderMode:(AgoraVideoRenderMode)mode;
```
>该方法用于开始本地视频预览而不向服务器发送数据。


返回值 | 描述 
---|---
0 | 函数调用成功 
<0 | 函数调用失败  

```
typedef NS_ENUM(NSUInteger, AgoraVideoRenderMode)
```
<br/>
  
名称 | 值 | 描述
---|--- |---
AgoraVideoRenderModeHidden | 1 |如果视频大小与显示窗口不同，大则裁剪小则拉伸以达到适配的目的。
AgoraVideoRenderModeFit | 2 |如果视频大小与显示窗口不同，按比例调整视频大小以适配窗口。
AgoraVideoRenderModeAdaptive | 3 | 如果两个呼叫者使用相同的屏幕模式，即同时使用纵屏或同时使用横屏， AgoraVideoRenderModeHidden 模式适用。<br/>如果两个呼叫者使用不同的屏幕模式，即一个纵屏一个横屏， AgoraVideoRenderModeFit 模式适用。 
<br/>

#### 停止本地视频预览 (stopPreview)

```
- (int)stopPreview;
```

返回值 | 描述
---|---
0 | 函数调用成功
<0 | 函数调用失败
<br/>

#### 加入频道（joinChannel）

```
- (int)joinChannel:(NSString *_Nonnull)channelId
            key:(NSString *_Nullable)channelKey
         config:(AgoraLiveChannelConfig *_Nonnull)channelConfig
            uid:(NSUInteger)uid;
```
>在 AgoraLiveKit 云服务上创建一个开放的 UDP socket 以加入频道。在相同的的频道的用户可以通过相同的 App ID 互相通话。使用不同的 App ID 的用户不能互相通话。该方法为异步。

参数 | 描述
---|---
channelId | 加入同一个频道表示客户加入了同一个房间
channelKey | 由 App 通过 sign certificate 创建
channelConfig | 对频道的配置
uid | 该参数是每个成员在某一个频道的唯一 ID 。如果设为 0 ，则 SDK 将自动分配 ID 。这个 ID 可以在 didJoinChannel delegate 处得到。

```
__attribute__((visibility("default"))) @interface AgoraLiveChannelConfig: NSObject
@property (assign, nonatomic) BOOL videoEnabled;
@property (assign, nonatomic) AgoraLiveEncryptionMode encryptionMode;
@property (copy, nonatomic) NSString *_Nullable secret;
+(AgoraLiveChannelConfig *_Nonnull) defaultConfig;
@end
```

属性名称 | 描述
---|---
videoEnabled | NO ：纯音频模式；<br/>YES ：音视频模式
encryptionMode | 直播加密模式
secret | 加密密码

```
typedef NS_ENUM(NSInteger, AgoraLiveEncryptionMode)
```

名称 | 枚举值 
---|---
AgoraLiveEncryptionModeNone | 0
AgoraLiveEncryptionModeAES128XTS | 1
AgoraLiveEncryptionModeAES256XTS | 2
AgoraLiveEncryptionModeAES128ECB | 3
<br/>

#### 离开频道 (leaveChannel)

```
- (int)leaveChannel;
```

返回值 | 描述
---|---
0 | 执行成功
<0 | 执行失败
<br/>

## 事件
已发生警告 (didOccurWarning)

```
- (void)liveKit:(AgoraLiveKit *_Nonnull)kit didOccurWarning:(AgoraWarningCode)warningCode;
```
>SDK 内发生的警告。 APP可以忽略警告而 SDK 可以尝试自动恢复。

参数 | 描述
---|---
kit | 直播包
warningCode | 警告码

```
typedef NS_ENUM(NSInteger, AgoraWarningCode)
```
<br/>

typedef NS_ENUM(NSInteger, AgoraErrorCode)

名称 | 值 | 描述
---|---|---
AgoraErrorCodeNoError | 0
AgoraErrorCodeFailed | 1
AgoraErrorCodeInvalidArgument | 2
AgoraErrorCodeNotReady | 3
AgoraErrorCodeNotSupported | 4
AgoraErrorCodeRefused | 5
AgoraErrorCodeBufferTooSmall | 6
AgoraErrorCodeNotInitialized | 7
AgoraErrorCodeNoPermission | 9
AgoraErrorCodeTimedOut | 10
AgoraErrorCodeCanceled | 11
AgoraErrorCodeTooOften | 12
AgoraErrorCodeBindSocket | 13
AgoraErrorCodeNetDown | 14
AgoraErrorCodeNoBufs | 15
AgoraErrorCodeJoinChannelRejected | 17
AgoraErrorCodeLeaveChannelRejected | 18
AgoraErrorCodeAlreadyInUse | 19
AgoraErrorCodeInvalidAppId | 101
AgoraErrorCodeInvalidChannelName | 102
AgoraErrorCodeChannelKeyExpired | 109
AgoraErrorCodeInvalidChannelKey | 110
AgoraErrorCodeConnectionInterrupted | 111 |仅在 web SDK 使用。
AgoraErrorCodeConnectionLost | 112 | 仅在 web SDK 使用。
AgoraErrorCodeNotInChannel | 113
AgoraErrorCodeSizeTooLarge | 114
AgoraErrorCodeBitrateLimit | 115
AgoraErrorCodeTooManyDataStreams | 116
AgoraErrorCodeDecryptionFailed | 120
AgoraErrorCodePublishFailed | 150
AgoraErrorCodeLoadMediaEngine | 1001
AgoraErrorCodeStartCall | 1002
AgoraErrorCodeStartCamera | 1003
AgoraErrorCodeStartVideoRender | 1004
AgoraErrorCodeAdmGeneralError | 1005
AgoraErrorCodeAdmJavaResource | 1006
AgoraErrorCodeAdmSampleRate | 1007
AgoraErrorCodeAdmInitPlayout | 1008
AgoraErrorCodeAdmStartPlayout | 1009
AgoraErrorCodeAdmStopPlayout |	1010	 
AgoraErrorCodeAdmInitRecording|	1011	 
AgoraErrorCodeAdmStartRecording	|1012	 
AgoraErrorCodeAdmStopRecording	|1013	 
AgoraErrorCodeAdmRuntimePlayoutError|	1015	 
AgoraErrorCodeAdmRuntimeRecordingError|	1017	 
AgoraErrorCodeAdmRecordAudioFailed|	1018	 
AgoraErrorCodeAdmPlayAbnormalFrequency|	1020	 
AgoraErrorCodeAdmRecordAbnormalFrequency|	1021	 
AgoraErrorCodeAdmInitLoopback	|1022	 
AgoraErrorCodeAdmStartLoopback	|1023	| 1025: iOS 平台上 ADM 中断的警告码<br/>1026: iOS 平台上 ADM 路由变化警告码
AgoraErrorCodeVdmCameraNotAuthorized | 1501 |VDM错误代码始于 1500。
AgoraErrorCodeVcmUnknownError | 1600 | VCM 错误代码始于 1600。
AgoraErrorCodeVcmEncoderInitError | 1601	 
AgoraErrorCodeVcmEncoderEncodeError | 1602	 
AgoraErrorCodeVcmEncoderSetError | 1603
<br/>

####  本地用户已加入频道 (didJoinChannel)

```
- (void)liveKit:(AgoraLiveKit *_Nonnull)kit didJoinChannel:(NSString *_Nonnull)channel withUid:(NSUInteger)uid elapsed:(NSInteger) elapsed;
```
>用户加入频道的事件。

参数 | 描述
---|---
kit	| 直播包
channel	| 频道名称
uid	| 远端用户id
elapsed	| 从会话开始起流逝的时间

<br/>

####  本地用户已离开频道 (liveKitDidLeaveChannel)

```
- (void)liveKitDidLeaveChannel:(AgoraLiveKit *_Nonnull)kit;
```

参数|描述
---|---
kit	| 直播包
<br/>

#### 本地用户已重新加入频道 (didRejoinChannel)

```
- (void)liveKit:(AgoraLiveKit *_Nonnull)kit didRejoinChannel:(NSString *_Nonnull)channel withUid:(NSUInteger)uid elapsed:(NSInteger) elapsed;
```
<br/>

参数|描述
---|---
kit|直播包
channel|频道名称
uid|远端用户|id
elapsed|自会话开始起流逝的时间
<br/>

#### 直播统计数据 (reportLiveStats)

```
- (void)liveKit:(AgoraLiveKit *_Nonnull)kit reportLiveStats:(AgoraLiveStats *_Nonnull)stats;
```
>RTC 直播包的统计数据，每两秒更新一次。

参数|描述
---|---
kit|直播包
stats|RTC 状态的统计数据，包括时长、发送字节数和收到字节数

```
__attribute__((visibility("default"))) @interface AgoraLiveStats: NSObject
@property (assign, nonatomic) NSInteger duration;
@property (assign, nonatomic) NSInteger txBytes;
@property (assign, nonatomic) NSInteger rxBytes;
@property (assign, nonatomic) NSInteger txAudioKBitrate;
@property (assign, nonatomic) NSInteger rxAudioKBitrate;
@property (assign, nonatomic) NSInteger txVideoKBitrate;
@property (assign, nonatomic) NSInteger rxVideoKBitrate;
@property (assign, nonatomic) NSInteger userCount;
@property (assign, nonatomic) double cpuAppUsage;
@property (assign, nonatomic) double cpuTotalUsage;
@end
```

属性名称|描述
---|---
duration|通话时长，以秒记
txBytes|传输的字节总数
rxBytes|接收的字节总数
txAudioKBitrate|	 
rxAudioKBitrate|	 
txVideoKBitrate	| 
rxVideoKBitrate	| 
userCount	 |
cpuAppUsage	 |
cpuTotalUsage |
<br/>

#### 服务器连接已中断 (liveKitConnectionDidInterrupted)

```
- (void)liveKitConnectionDidInterrupted:(AgoraLiveKit *_Nonnull)kit;
```
>发生与服务器断开连接的事件。这一事件会在 SDK 与服务器断开连接的瞬间上报。与此同时， SDK 自动尝试再次连接服务器直到 App 调用 leaveChannel() 。

参数|描述
---|---
kit|直播包
<br/>

#### 与服务器连接已丢失 (liveKitConnectionDidLost)

```
- (void)liveKitConnectionDidLost:(AgoraLiveKit *_Nonnull)kit;
```
>发生与服务器失去连接的事件。这一事件发生在连接中断并超过默认重试时间（默认 10 秒）

参数|描述
---|---
kit|直播包
<br/>

#### 本地用户的网络质量 (networkQuality)

```
- (void)liveKit:(AgoraLiveKit *_Nonnull)kit networkQuality:(NSUInteger)uid txQuality:(AgoraNetworkQuality)txQuality rxQuality:(AgoraNetworkQuality)rxQuality;
```
>本地用户的网络质量。

参数|描述
---|---
kit|直播包
uid|用户id
txQuality|发送网络的质量
rxQuality|接受网络的质量

```
typedef NS_ENUM(NSUInteger, AgoraNetworkQuality)
```

名称|值	
---|---
AgoraNetworkQualityUnknown|0	 
AgoraNetworkQualityExcellent|1	 
AgoraNetworkQualityGood|2	 
AgoraNetworkQualityPoor|3	 
AgoraNetworkQualityBad|4	 
AgoraNetworkQualityVBad|5	 
AgoraNetworkQualityDown|6
<br/>

#### 本地视频第一帧已在屏幕上显示 (firstLocalVideoFrameWithSize)

```
- (void)liveKit:(AgoraLiveKit *_Nonnull)kit firstLocalVideoFrameWithSize:(CGSize)size elapsed:(NSInteger)elapsed;
```

参数|描述
---|---
kit|直播包
size|第一帧本地视频帧的大小
elapsed|自会话开始后流逝的时间（ms）
<br/>

# AgoraLivePublisher 类
该类用于管理主播推流。

```
@protocol AgoraLiveSubscriberDelegate <NSObject>
```
## 方法

#### 设置视频编码分辨率、帧率、码率 (setVideoResolution: andFrameRate: bitrate:)

```
- (int)setVideoResolution: (CGSize)size andFrameRate: (NSInteger)frameRate bitrate: (NSInteger) bitrate;
```

参数|描述
---|---
size|视频编码分辨率
frameRate|视频编码帧率
bitrate|视频编码码率
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

#### 设置代理 (setDelegate)

```
- (void)setDelegate:(_Nullable id<AgoraLivePublisherDelegate>)delegate;
```


参数 | 描述
---|---
delegate | 

<br/>

#### 初始化推流器 (initWithLiveKit)

```
- (instancetype _Nonnull)initWithLiveKit:(AgoraLiveKit * _Nonnull)kit;
```

参数 |	描述
---|---
kit	|直播包
返回值|返回一个推流器对象
<br/>
#### 设置直播转码 (setLiveTranscoding)

```
- (void)setLiveTranscoding:(AgoraLiveTranscoding *_Nullable) transcoding;
```

参数	|描述
---|---
transcoding	|转码配置

```
__attribute__((visibility("default"))) @interface AgoraLiveTranscoding: NSObject
@property (assign, nonatomic) CGSize size;
@property (assign, nonatomic) NSInteger bitrate;
@property (assign, nonatomic) NSInteger framerate;
@property (assign, nonatomic) BOOL lowLatency;
@property (strong, nonatomic) COLOR_CLASS *_Nullable backgroundColor;
@property (copy, nonatomic) NSArray<AgoraLiveTranscodingUser *> *_Nullable transcodingUsers;
@property (copy, nonatomic) NSString *_Nullable transcodingExtraInfo;
@end
```

属性名称	|描述
---|---
size	|视频大小（宽与高）
bitrate	|旁路直播输出数据流的比特率。 400 Kbps 为默认值。
framerate	|输出数据流的帧率用于旁路直播。 15 fps 为默认值。
lowLatency	|默认值为 FALSE 。
backgroundColor	|输入 RGB 定义下的任何六位数字符号。
transcodingUsers|	 
transcodingExtraInfo	|转码自定义信息
<br/>


```
__attribute__((visibility("default"))) @interface AgoraLiveTranscodingUser: NSObject
@property (assign, nonatomic) NSUInteger uid;
// Audio control
@property (assign, nonatomic) CGRect rect;
@property (assign, nonatomic) NSInteger zOrder; //optional, [0, 100] //0 (default): lowest, 100: highest
@property (assign, nonatomic) double alpha; //optional, [0, 1.0] where 0: completely transparent; 1.0 opaque
@property (assign, nonatomic) AgoraVideoRenderMode renderMode;
//+(AgoraLiveTranscodingUser *) defaultConfig
@end
```
属性名称|	描述
---|---
uid	|User ID
rect |	 
zOrder |	用于音频控制，为 0 到 100 之间的任意整型：<br/>0 : 默认值，最低<br/>100 : 最高
alpha	|在 0 到 1.0 之间：<br/>0: 完全透明<br/>1.0: 完全不透明
renderMode	|渲染模式，更多详情，请见下表。

```
typedef NS_ENUM(NSUInteger, AgoraVideoRenderMode)
```
名称	|值	|描述
---|---|---
AgoraVideoRenderModeHidden	|1	|如果视频大小与显示窗口不同，大则裁剪小则拉伸以达到适配的目的。
AgoraVideoRenderModeFit	|2	|如果视频大小与显示窗口不同，按比例调整视频大小以适配窗口。
AgoraVideoRenderModeAdaptive|3|如果两个呼叫者使用相同的屏幕模式，即同时使用纵屏或同时使用横屏，AgoraVideoRenderModeHidden模式试用。<br/>如果两个呼叫者使用不同的屏幕模式，即一个纵屏一个横屏，AgoraVideoRenderModeFit模式试用。
<br/>

#### 设置媒体类型 (setMediaType)

```
- (void)setMediaType:(AgoraLiveMediaType)mediaType;
```
参数|	描述
---|---
mediaType	|枚举型：直播媒体类型

```
typedef NS_ENUM(NSInteger, AgoraLiveMediaType)
```
名称|	值	|描述
---|---|---
AgoraLiveMediaTypeNone	|0|	无音视频
AgoraLiveMediaTypeAudioOnly	|1|	仅有音频
AgoraLiveMediaTypeVideoOnly	|2|	仅有视频
AgoraLiveMediaTypeAudioAndVideo	|3|	有音视频
<br/>

#### 增加推流地址 (addStreamUrl)

```
- (void)addStreamUrl:(NSString *_Nullable)url transcodingEnabled:(BOOL)transcodingEnabled;
```
>该方法用于 CDN 推流，用于添加推流地址。

参数|描述
---|---
url	|推流地址
transcodingEnabled	|是否转码
<br/>

#### 删除推流地址 (removeStreamUrl)

```
- (void)removeStreamUrl:(NSString *_Nullable)url;
```
>该方法用于 CDN 推流，用于删除推流地址。


参数|描述
---|---
url	|推流地址
<br/>

#### 通过鉴权秘钥推流 (publishWithPermissionKey)

```
- (void)publishWithPermissionKey:(NSString *_Nullable) permissionKey;
```
参数|	描述
---|---
permissionKey|默认值为NULL。若需了解更多有关通过鉴权秘钥推流的信息，请联系声网客服。
<br/>

#### 停止推流 (unpublish)

```
- (void)unpublish
```
<br/>

#### 切换相机 (switchCamera)

```
- (void)switchCamera
```
>该方法用于前后摄像头切换。
<br/>

## 事件
#### 已完成订阅主播 (didSubscribedToHostUid)

```
- (void)publisher: (AgoraLivePublisher *_Nonnull)publisher didSubscribedToHostUid:(NSUInteger)uid mediaType:(AgoraLiveMediaType) type;
```
参数|描述
---|---
uid	|主播的 User id
type |枚举型，直播媒体类型。更多详情，请见下表。


```
typedef NS_ENUM(NSInteger, AgoraLiveMediaType)
```
名称	|值	|描述
---|---|---
AgoraLiveMediaTypeNone	|0	|无音视频
AgoraLiveMediaTypeAudioOnly	|1	|仅有音频
AgoraLiveMediaTypeVideoOnly	|2	|仅有视频
AgoraLiveMediaTypeAudioAndVideo	|3	|有音视频
<br/>

#### 已解除订阅主播 (didUnsubscribedToHostUid)

```
- (void)publisher: (AgoraLivePublisher *_Nonnull)publisher didUnsubscribedToHostUid:(NSUInteger)uid;
```
参数	|描述
---|---
uid	|主播的 User id

# AgoraLiveSubscriber 类
该类为管理观众订阅主播的类。
## 方法
#### 初始化订阅器（initWithLiveKit）

```
- (instancetype _Nonnull)initWithLiveKit:(AgoraLiveKit * _Nonnull)kit;
```
参数|描述
---|---
kit	|直播包
返回值	|返回一个订阅器的对象
<br/>

#### 订阅（subscribeByUid）

```
- (void)subscribeByUid:(NSUInteger)uid
                     mediaType:(AgoraLiveMediaType)mediaType
                     view:(VIEW_CLASS *_Nullable)view
                     renderMode:(AgoraVideoRenderMode)mode
                     videoType：(AgoraLiveVideoStreamType)videoType;
```
>该方法用于订阅主播。

参数	|描述
---|---
uid	|User id
mediaType|	媒体类型：音频，视频，音视频，无。更多详情，请见下表。
view	|制定显示的 view
renderMode|	渲染模式
videoType|	视频大小流类型，更多详情，请见下表。

```
typedef NS_ENUM(NSInteger, AgoraLiveMediaType)
```
名称	|值	|描述
---|---|---
AgoraLiveMediaTypeNone	|0	|无音视频
AgoraLiveMediaTypeAudioOnly	|1	|仅有音频
AgoraLiveMediaTypeVideoOnly	|2	|仅有视频
AgoraLiveMediaTypeAudioAndVideo	|3	|有音视频


```
typedef NS_ENUM(NSUInteger, AgoraVideoRenderMode)
```

名称	|值	|描述
---|---|---
AgoraVideoRenderModeHidden	|1	|如果视频大小与显示窗口不同，大则裁剪小则拉伸以达到适配的目的。
AgoraVideoRenderModeFit	|2	|如果视频大小与显示窗口不同，按比例调整视频大小以适配窗口。
AgoraVideoRenderModeAdaptive|3|如果两个呼叫者使用相同的屏幕模式，即同时使用纵屏或同时使用横屏，AgoraVideoRenderModeHidden模式试用。<br/>如果两个呼叫者使用不同的屏幕模式，即一个纵屏一个横屏，AgoraVideoRenderModeFit模式试用。


```
typedef NS_ENUM(NSInteger, AgoraLiveVideoStreamType)
```
名称	|值	|描述
---|---|---
AgoraLiveVideoStreamHigh	|0	|高帧率高分辨率视频流
AgoraLiveVideoStreamLow	|1	|低帧率低分辨率视频流
<br/>

#### 解除订阅（unsubscribeByUid)

```
- (void)unsubcribeByUid:(NSUInteger)uid;
```
>该方法用于解除订阅。

参数	|描述
---|---
uid	|User id
<br/>

## 事件
#### 通过 uid 推流 (didPublishedByUid)

```
- (void)liveSubcriber: (AgoraLiveSubscriber *_Nonnull)subscriber didPublishedByUid:(NSUInteger)uid streamType:(AgoraLiveMediaType) type;
```
参数	|描述
---|---
uid	|User id
type	|枚举型：直播媒体类型

```
typedef NS_ENUM(NSInteger, AgoraLiveMediaType)
```
名称	|值	|描述
---|---|---
AgoraLiveMediaTypeNone	|0	|无音视频
AgoraLiveMediaTypeAudioOnly	|1	|仅有音频
AgoraLiveMediaTypeVideoOnly	|2	|仅有视频
AgoraLiveMediaTypeAudioAndVideo	|3	|有音视频
<br/>

#### 通过 uid 停止推流 (didUnpublishedByUid)

```
- (void)liveSubscriber: (AgoraLiveSubscriber *_Nonnull)subscriber didUnpublishedByUid:(NSUInteger)uid streamType:(AgoraLiveMediaType) type;
```

参数|	描述
---|---
uid	| User id
type | 枚举型：媒体类型

```
typedef NS_ENUM(NSInteger, AgoraLiveMediaType)
```
名称	|值	|描述
---|---|---
AgoraLiveMediaTypeNone	|0	|无音视频
AgoraLiveMediaTypeAudioOnly	|1	|仅有音频
AgoraLiveMediaTypeVideoOnly	|2	|仅有视频
AgoraLiveMediaTypeAudioAndVideo	|3	|有音视频
<br/>

#### 远端用户第一帧被渲染在屏幕上 (firstRemoteVideoFrameOfUid)

```
- (void)liveSubcriber:(AgoraLiveSubscriber *_Nonnull)subscriber firstRemoteVideoFrameOfUid:(NSUInteger)uid size:(CGSize)size elapsed:(NSInteger)elapsed;
```
参数	|描述
---|---
kit	|直播包
uid|	远端用户的 user id
size	|视频流的大小
elapsed	|自会话开始后流逝的时间（ms）
<br/>

#### 视频大小改变 (videoSizeChangedOfUid)

```
- (void)liveSubscriber:(AgoraLiveSubscriber *_Nonnull)subscriber videoSizeChangedOfUid:(NSUInteger)uid size:(CGSize)size rotation:(NSInteger)rotation;
```
>视频大小为本地或远端用户发生改变。

参数	|描述
---|---
kit	|直播包
uid	|User id
size	|视频流的大小
rotation	|视频的新旋转角度










	





































