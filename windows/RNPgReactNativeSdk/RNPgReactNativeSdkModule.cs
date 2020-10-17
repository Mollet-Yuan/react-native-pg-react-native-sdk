using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Pg.React.Native.Sdk.RNPgReactNativeSdk
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNPgReactNativeSdkModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNPgReactNativeSdkModule"/>.
        /// </summary>
        internal RNPgReactNativeSdkModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNPgReactNativeSdk";
            }
        }
    }
}
