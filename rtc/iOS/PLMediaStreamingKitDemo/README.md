# AgoraLiveKit 类
该类为直播的基础类，用于管理频道

## 方法

获取 SDK 版本 (getSdkVersion)

```bash
- (NSString *_Nonnull)getSdkVersion;
```

获取 RTC 引擎对象 (getRtcEngineKit)

```bash
- (AgoraRtcEngineKit *_Nonnull)getRtcEngineKit;
```

初始化AgoraLiveKit单例对象 (sharedLiveKitWithAppId)

```bash
+ (instancetype _Nonnull)sharedLiveKitWithAppId:(NSString *_Nonnull)appId;
```

|   参数    |   描述    |
|:----------------------------:|:----------------------------:|
|appId|由 Agora 签发给应用开发者|
|返回值|AgoraLiveKit 类的一个对象|
