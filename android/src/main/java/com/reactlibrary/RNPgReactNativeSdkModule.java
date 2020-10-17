package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.gocashfree.cashfreesdk.CFPaymentService;
import com.gocashfree.cashfreesdk.utils.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class RNPgReactNativeSdkModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    CFPaymentService cfPaymentService;
    private Callback mCallback;
    private ReadableMap mParams;

    public RNPgReactNativeSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "RNPgReactNativeSdk";
    }

    @ReactMethod
    public void startPaymentUPI(ReadableMap params, String cfToken, String stage) {
        Activity currentActivity = getCurrentActivity();
        mParams = params;
        Map<String, String> paramsMap = toMap(params);
        cfPaymentService.upiPayment(currentActivity, paramsMap, cfToken, stage);
    }

    @ReactMethod
    public void startPaymentGPAY(ReadableMap params, String cfToken, String stage) {
        Activity currentActivity = getCurrentActivity();
        mParams = params;
        Map<String, String> paramsMap = toMap(params);
        cfPaymentService.gPayPayment(currentActivity, paramsMap, cfToken, stage);
    }

    @ReactMethod
    public void startPaymentPhonePe(ReadableMap params, String cfToken, String stage) {
        Activity currentActivity = getCurrentActivity();
        mParams = params;
        Map<String, String> paramsMap = toMap(params);
        cfPaymentService.phonePePayment(currentActivity, paramsMap, cfToken, stage);
    }

    @ReactMethod
    public void startPaymentAmazon(ReadableMap params, String cfToken, String stage) {
        Activity currentActivity = getCurrentActivity();
        mParams = params;
        Map<String, String> paramsMap = toMap(params);
        cfPaymentService.doAmazonPayment(currentActivity, paramsMap, cfToken, stage);
    }

    @ReactMethod
    public void startPaymentWEB(ReadableMap params,String stage,Callback callback) {
        Log.d("","CF::SDK::Android::INVOKED");
        mCallback=callback;
        Activity currentActivity = getCurrentActivity();
        mParams = params;
        Map<String, String> paramsMap = toMap(params);
        cfPaymentService.doPayment(currentActivity, paramsMap, paramsMap.get("tokenData"), stage, "#784BD2", "#FFFFFF", false);
    }

    private String getJson(final Bundle bundle) {
        if (bundle == null) return null;
        JSONObject jsonObject = new JSONObject();

        for (String key : bundle.keySet()) {
            Object obj = bundle.get(key);
            try {
                jsonObject.put(key, wrap(bundle.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }

    public static Object wrap(Object o) {
        if (o == null) {
            return JSONObject.NULL;
        }
        if (o instanceof JSONArray || o instanceof JSONObject) {
            return o;
        }
        if (o.equals(JSONObject.NULL)) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return new JSONArray((Collection) o);
            } else if (o.getClass().isArray()) {
                return toJSONArray(o);
            }
            if (o instanceof Map) {
                return new JSONObject((Map) o);
            }
            if (o instanceof Boolean ||
                    o instanceof Byte ||
                    o instanceof Character ||
                    o instanceof Double ||
                    o instanceof Float ||
                    o instanceof Integer ||
                    o instanceof Long ||
                    o instanceof Short ||
                    o instanceof String) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static JSONArray toJSONArray(Object array) throws JSONException {
        JSONArray result = new JSONArray();
        if (!array.getClass().isArray()) {
            throw new JSONException("Not a primitive array: " + array.getClass());
        }
        final int length = Array.getLength(array);
        for (int i = 0; i < length; ++i) {
            result.put(wrap(Array.get(array, i)));
        }
        return result;
    }

    private final ActivityEventListener mActivityEventListener = new ActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) { ;
             if (data != null && mCallback != null) {
                mCallback.invoke(getJson(data.getExtras()));
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        if (bundle.getString(key) != null) {
                            Log.d("CF::SDK::Android", key + " : " + bundle.getString(key));
                        }
                    }
                }
            }
        }

        @Override
        public void onNewIntent(Intent intent) {

        }

    };

    public static Map<String, String> toMap(@Nullable ReadableMap readableMap) {
        if (readableMap == null) {
            return null;
        }

        com.facebook.react.bridge.ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        if (!iterator.hasNextKey()) {
            return null;
        }

        Map<String, String> result = new HashMap<>();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            result.put(key, toObject(readableMap, key));
        }

        return result;
    }

    public static String toObject(@Nullable ReadableMap readableMap, String key) {
        if (readableMap == null) {
            return null;
        }

        String result;
        result = readableMap.getString(key);
        return result;
    }

}