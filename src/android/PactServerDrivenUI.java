package com.pact.sdui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pact.sdui.sdk.PactFrameworkCallback;
import com.pact.sdui.sdk.PactFrameworkConfiguration;
import com.pact.sdui.sdk.PactFrameworkEnvironment;
import com.pact.sdui.sdk.PactPresentationConfiguration;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PactServerDrivenUI extends CordovaPlugin {

//     @Override
//     public void initialize(CordovaInterface cordova, CordovaWebView webView) {
//         super.initialize(cordova, webView);
//         // your init code here
//     }

    private static final String ENV_Development = "development";
    private static final String ENV_Staging = "staging";
    private static final String ENV_Production = "production";

    private String key;

    private PactFrameworkConfiguration pactFrameworkConfiguration;

    private PactFrameworkEnvironment getPactFrameworkEnvironment(String env) {
        switch (env) {
            case ENV_Development:
                return PactFrameworkEnvironment.Development;
            case ENV_Staging:
                return PactFrameworkEnvironment.Staging;
            default:
                return PactFrameworkEnvironment.Production;
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("initialize".equals(action)) {
            callbackContext.success("init psdui ok");
            initialize(args, callbackContext);
            return true;
        } else if ("show".equals(action)) {
            callbackContext.success("show psdui ok");
            show(args, callbackContext);
            return true;
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }


    private void initialize(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String apiKey = args.getString(0);
        JSONObject configuration = args.getJSONObject(1);
        String environment = configuration.getString("environment");
        String supportPhoneNumber = configuration.getString("supportPhoneNumber");

        this.key = apiKey;
        PactFrameworkEnvironment pactFrameworkEnvironment = getPactFrameworkEnvironment(environment);
        pactFrameworkConfiguration = new PactFrameworkConfiguration(
                pactFrameworkEnvironment,
                supportPhoneNumber
        );

        callbackContext.success("PactServerDrivenUI has been initialized.");
    }

    private void show(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JsonObject initContext = new JsonObject();
        JSONObject initContextJSON = args.getJSONObject(0);
        if (initContextJSON != null) {
            String initContextJSONString = initContextJSON.toString();
            initContext = JsonParser.parseString(initContextJSONString).getAsJsonObject();
        }

        String landingUri = args.getString(1);
        PactPresentationConfiguration pactAppConfiguration = new PactPresentationConfiguration(
                initContext,
                landingUri,
                true,
                new PactFrameworkCallbackImpl()
        );
        com.pact.sdui.sdk.PactServerDrivenUI pactServerDrivenUI =
                com.pact.sdui.sdk.PactServerDrivenUI.Companion.initialize(
                        this.key, this.pactFrameworkConfiguration
                );
        pactServerDrivenUI.show(cordova.getActivity(), pactAppConfiguration);

        callbackContext.success("PactServerDrivenUI has been shown.");
    }
}


/**
 *
 */
class PactFrameworkCallbackImpl implements PactFrameworkCallback {

    @Override
    public void onFinish(boolean success) {
        // TODO
    }
}