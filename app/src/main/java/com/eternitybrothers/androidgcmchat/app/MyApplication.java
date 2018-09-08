package com.eternitybrothers.androidgcmchat.app;

import android.app.Application;
import com.eternitybrothers.androidgcmchat.helper.MyPreferenceManager;

/**
 * Created by Lincoln on 14/10/15.
 */

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.eternitybrothers.androidgcmchat.activity.LoginActivity;
import com.eternitybrothers.androidgcmchat.helper.MyPreferenceManager;

/**
 * Created by Ravi on 13/05/15.
 */

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    private static MyApplication mInstance;

    private MyPreferenceManager pref;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }

    // global request queue handler to add request queues.
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    // what is the point of the above since this is an overpopulated method.
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void logout() {
        pref.clear();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}