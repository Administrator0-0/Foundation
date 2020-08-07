package com.almoon.foundation_lib.common;


import android.util.Log;
import com.almoon.foundation_lib.interfaces.HttpCallback;
import com.almoon.foundation_lib.interfaces.HttpMapFun;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * HttpTask is a node of http task list
 */
@SuppressWarnings("ALL")
public class HttpTask<T> {
    // Parent node
    private HttpTask parent;
    // Child node
    private HttpTask child;
    // Map function
    private HttpMapFun mapFun;
    // My Call
    private Call<T> call;
    // My HttpCallback
    private HttpCallback<T> callback;
    private static final String TAG = "HttpTask";

    HttpTask(HttpTask parent) {
        this.parent = parent;
    }

    public HttpTask(Call<T> call) {
        this.call = call;
    }

    /**
     * FlatMap() will make nested request be falten
     * @param map will make map for request A's result & request B's call
     * @param <V> child's class type
     * @return
     */
    public <V> HttpTask flatMap(HttpMapFun<T, V> map) {
        mapFun = map;
        child = new HttpTask<V>(this);
        return child;
    }

    /**
     * You can add an HttpCallback for any HttpTask
     */
    public HttpTask<T> addCallback(HttpCallback<T> callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Start executing all requests from root
     */
    public void execute() {
        HttpTask root= HttpTask.this.parent;
        while (true) {
            if (root.parent != null) {
                root = root.parent;
            } else {
                break;
            }
        }
        int position = 0;
        root.realExecute(position);
    }

    /**
     * Every task's excution, calling http request
     */
    public void realExecute(final int position) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.body() != null) {
                    if (callback != null) {
                        callback.onSuccess(new HttpResult(response.body()));
                    }
                    if (mapFun != null) {
                        Call child = mapFun.map(new HttpResult(response.body()));
                        HttpTask.this.child.call = child;
                        HttpTask.this.child.realExecute(position + 1);
                    }
                } else if (response.errorBody() != null){
                    if (callback != null) {
                        callback.onFail(response.errorBody());
                    }
                } else {
                    if (callback != null) {
                        callback.onFail();
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(t.getMessage());
                } else {
                    Log.e(TAG, "Task" + position + " fail: " + t.getMessage());
                }
            }
        });
    }
}
