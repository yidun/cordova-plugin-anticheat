package com.netease.anticheat.cordovaplugin;

import android.util.Log;

import com.netease.mobsec.GetTokenCallback;
import com.netease.mobsec.InitCallback;
import com.netease.mobsec.WatchMan;
import com.netease.mobsec.WatchManConf;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hzhuqi on 2020/6/11
 */
public class AntiCheating extends CordovaPlugin {
    private static final String TAG = "AntiCheating";
    private KeepAliveCallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        super.execute(action, args, callbackContext);
        this.callbackContext = KeepAliveCallbackContext.newInstance(callbackContext);
        boolean isSuccess = false;
        Log.d(TAG, "action:" + action + " args:" + args);
        if ("init".equals(action)) {
            try {
                init(args);
                isSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("getToken".equals(action)) {
            int timeout = args.getInt(0);
            getToken(timeout);
            isSuccess = true;
        } else if ("setSeniorCollectStatus".equals(action)) {
            boolean flag = args.getBoolean(0);
            setSeniorCollectStatus(flag);
            isSuccess = true;
        } else {
            callbackContext.error("invalid action");
        }
        return isSuccess;
    }


    public void init(JSONArray options) throws Exception {
        Log.d(TAG, "-----------------init-------------");
        String productNumber = options.getString(0);
        boolean isCollectApk = options.getBoolean(1);
        boolean isCollectSensor = options.getBoolean(2);
        String channel = options.getString(3);

        WatchManConf conf = new WatchManConf();
        conf.setCollectApk(isCollectApk);
        conf.setCollectSensor(isCollectSensor);
        conf.setChannel(channel);
        WatchMan.init(cordova.getContext(), productNumber, conf, new InitCallback() {
            @Override
            public void onResult(int code, String msg) {
                final JSONObject callBackJson = new JSONObject();
                try {
                    if (code == 200) {
                        callBackJson.put("is_init_success", true);
                    } else {
                        callBackJson.put("is_init_success", false);
                        callBackJson.put("msg", msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callbackContext.success(callBackJson, true);
            }
        });

    }


    public void getToken(int timeout) {
        WatchMan.getToken(timeout, new GetTokenCallback() {
            @Override
            public void onResult(int code, String msg, String token) {
                final JSONObject callBackJson = new JSONObject();
                try {
                    if (code == 200) {
                        callBackJson.put("is_get_token_success", true);
                        callBackJson.put("token", token);
                    } else {
                        callBackJson.put("is_get_token_success", false);
                        callBackJson.put("msg", msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callbackContext.success(callBackJson, true);
            }
        });
    }


    public void setSeniorCollectStatus(boolean flag) {
        WatchMan.setSeniorCollectStatus(flag);
    }

    public void start() {

    }

    public void startDetect() {
    }

    public void stopDetect() {
    }
}
