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
-  Fullband
- 混音特效
- 视频自采集
- 通过 setParameters() 方法调用 Agora 的私有接口。
<br/>您可以通过调用 getRtcEngine() 方法获取一个 RtcEngine 类的实例。有关 RtcEngine 类提供的 API 的更多详情，请见 直播 API - Android
