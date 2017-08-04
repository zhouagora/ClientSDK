//
//  PLRTCOnlyViewController.m
//  PLRTCOnlyKitDemo
//
//  Created by lawder on 16/6/30.
//  Copyright © 2016年 NULL. All rights reserved.
//

#import "UNRTCViewController.h"
#import "UNRtcEngineKit.h"

@interface UNRTCViewController ()<AgoraRtcEngineDelegate>

@property (nonatomic, strong) NSString *roomName;
@property (nonatomic, strong) UNRtcEngineKit *rtcEngine;

@property (nonatomic, strong) UIButton *conferenceButton;
@property (nonatomic, strong) UIButton *backButton;
@property (nonatomic, strong) UIButton *toggleButton;

@property (nonatomic, strong) UIView *previewView;
@property (nonatomic, strong) UIView *remoteView;

@end

@implementation UNRTCViewController

#pragma mark - Managing the detail item

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    [self.navigationController setNavigationBarHidden:YES];
    
    [self setupUI];
    
    self.roomName = @"testRoom";
    
    #warning 需要替换成自己的 App Id
    self.rtcEngine = [UNRtcEngineKit sharedEngineWithAppId:@"your App Id" delegate:self];
    [self.rtcEngine enableDualStreamMode:YES];
    [self.rtcEngine enableVideo];
    [self.rtcEngine setChannelProfile:AgoraRtc_ChannelProfile_LiveBroadcasting];
    [self.rtcEngine setClientRole:AgoraRtc_ClientRole_Broadcaster withKey:nil];
    [self.rtcEngine setVideoProfile:AgoraRtc_VideoProfile_360P_11 swapWidthAndHeight:YES];
    
    [self.rtcEngine setLogFile:@"Library/Caches/agorasdk.log"];
    AgoraRtcVideoCanvas *canvas = [[AgoraRtcVideoCanvas alloc] init];
    canvas.view = self.previewView;
    canvas.renderMode = AgoraRtc_Render_Hidden;
    [self.rtcEngine setupLocalVideo:canvas];
    [self.rtcEngine startPreview];
}


- (void)setupUI
{
    self.previewView = [[UIView alloc] initWithFrame:self.view.bounds];
    [self.view addSubview:self.previewView];
    
    self.backButton = [[UIButton alloc] initWithFrame:CGRectMake(20, 20, 66, 66)];
    [self.backButton setTitle:@"返回" forState:UIControlStateNormal];
    [self.backButton addTarget:self action:@selector(backButtonClick:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:self.backButton];
    
    CGSize size = [[UIScreen mainScreen] bounds].size;
    self.conferenceButton = [[UIButton alloc] initWithFrame:CGRectMake(20, size.height - 66, 66, 66)];
    [self.conferenceButton setTitle:@"连麦" forState:UIControlStateNormal];
    [self.conferenceButton setTitle:@"停止" forState:UIControlStateSelected];
    [self.conferenceButton.titleLabel setFont:[UIFont systemFontOfSize:22]];
    [self.conferenceButton setTitleColor:[UIColor blueColor] forState:UIControlStateHighlighted];
    [self.conferenceButton setTitleColor:[UIColor colorWithRed:1 green:1 blue:1 alpha:0.1] forState:UIControlStateDisabled];
    [self.conferenceButton addTarget:self action:@selector(conferenceButtonClick:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:self.conferenceButton];

    self.toggleButton = [[UIButton alloc] initWithFrame:CGRectMake(size.width - 66 - 20, 20, 66, 66)];
    [self.toggleButton setTitle:@"切换" forState:UIControlStateNormal];
    [self.toggleButton addTarget:self action:@selector(toggleButtonClick:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:self.toggleButton];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)backButtonClick:(id)sender
{
    [self.rtcEngine leaveChannel:nil];
    [UNRtcEngineKit destroy];
    
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    [self.navigationController popViewControllerAnimated:YES];
    [self.navigationController setNavigationBarHidden:NO];
}

- (IBAction)toggleButtonClick:(id)sender
{
    [self.rtcEngine switchCamera];
}


- (IBAction)conferenceButtonClick:(id)sender
{
    self.conferenceButton.enabled = NO;
    if (!self.conferenceButton.isSelected) {
        NSInteger result = [self.rtcEngine joinChannelByKey:nil channelName:self.roomName info:nil uid:0 joinSuccess:^(NSString *channel, NSUInteger uid, NSInteger elapsed) {
            self.conferenceButton.enabled = YES;
            self.conferenceButton.selected = YES;
        }];
        
        if (result == 0) {
            [self.rtcEngine setEnableSpeakerphone:YES];
        }
        else {
            [self showAlertWithMessage:[NSString stringWithFormat:@"join channel failed, code: %ld", (long)result] completion:nil];
        }
    }
    else {
        [self.rtcEngine leaveChannel:^(AgoraRtcStats *stat) {
            self.conferenceButton.selected = NO;
            self.conferenceButton.enabled = YES;
        }];
    }
}

#pragma mark - AgoraRtcEngineDelegate 

- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstRemoteVideoDecodedOfUid:(NSUInteger)uid size:(CGSize)size elapsed:(NSInteger)elapsed {
    if (!self.remoteView) {
        CGSize screenSize = [UIScreen mainScreen].bounds.size;
        CGFloat width = screenSize.width * 108 / 352.0;
        CGFloat height = screenSize.height * 192 / 640.0;
        self.remoteView = [[UIView alloc] initWithFrame:CGRectMake(screenSize.width - width, screenSize.height - height, width, height)];
        self.remoteView.clipsToBounds = YES;
    }
    
    AgoraRtcVideoCanvas *canvas = [[AgoraRtcVideoCanvas alloc] init];
    canvas.view = self.remoteView;
    canvas.renderMode = AgoraRtc_Render_Fit;
    canvas.uid = uid;
    [self.rtcEngine setupRemoteVideo:canvas];
    [self.view addSubview:self.remoteView];
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOfflineOfUid:(NSUInteger)uid reason:(AgoraRtcUserOfflineReason)reason {
    [self.remoteView removeFromSuperview];
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOccurWarning:(AgoraRtcWarningCode)warningCode {
    NSLog(@"warning occur, code: %ld", (long)warningCode);
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOccurError:(AgoraRtcErrorCode)errorCode {
    NSLog(@"error occur, code: %ld", (long)errorCode);
}


- (void)showAlertWithMessage:(NSString *)message completion:(void (^)(void))completion
{
    UIAlertController *controller = [UIAlertController alertControllerWithTitle:@"错误" message:message preferredStyle:UIAlertControllerStyleAlert];
    [controller addAction:[UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        if (completion) {
            completion();
        }
    }]];
    [self presentViewController:controller animated:YES completion:nil];
}


- (void)dealloc
{
    NSLog(@"UNRTCViewController dealloc");
}



@end
