#import "RNPgReactNativeSdk-Swift.h"
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif
//#import "CFSDK.xcframework/ios-armv7_arm64/CFSDK.framework/Headers/CFSDK-Swift.h"
@interface RNPgReactNativeSdk : NSObject <RCTBridgeModule,ResultDelegate>

@end
  
