#import "RNPgReactNativeSdk-Swift.h"
#import "RNPgReactNativeSdk.h"
#import "UIKit/UIWindow.h"
#import "UIKit/UIViewController.h"
@implementation RNPgReactNativeSdk
RCTResponseSenderBlock callbackNew;
- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()
RCT_EXPORT_METHOD(startPaymentWEB:(NSDictionary *) parmas env:(NSString *)env callback:(RCTResponseSenderBlock)callback)
{
    NSLog(@"CF::SDK::IOS::INVOKED");
    NSString *appId=parmas[@"appId"];
    callbackNew=callback;
    UIWindow *keyWindow = [[UIApplication sharedApplication] keyWindow];
    UIViewController *mainViewController = keyWindow.rootViewController;
    
    CFViewController *cfVC = [[CFViewController alloc]initWithParams: parmas appId:appId env:env callBack: self];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        [mainViewController presentViewController:cfVC  animated:YES completion:nil];
    });;

}

- (void)onPaymentCompletionWithMsg:(NSString * _Nonnull)msg {
    NSLog(@"CF::SDK::IOS:: %@", msg);
    callbackNew(@[msg]);
}

@end
  
