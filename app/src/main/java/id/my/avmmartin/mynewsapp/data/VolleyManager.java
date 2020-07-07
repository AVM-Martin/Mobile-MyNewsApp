package id.my.avmmartin.mynewsapp.data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import id.my.avmmartin.mynewsapp.data.manager.OnlineNewsManager;

class VolleyManager {
    private OnlineNewsManager onlineNewsManager;

    // constructor

    private RequestQueue requestQueue;

    VolleyManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        onlineNewsManager = new OnlineNewsManager(requestQueue);
    }

    // destructor

    void endTask() {
        requestQueue.stop();
    }

    // getter

    OnlineNewsManager getOnlineNewsManager() {
        return onlineNewsManager;
    }
}
