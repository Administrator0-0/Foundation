package com.almoon.foundation_lib.common;


import android.util.Log;

import com.almoon.foundation_lib.interfaces.HttpCallback;
import com.almoon.foundation_lib.interfaces.HttpMapFun;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class HttpTask<T> {
    private HttpTask parent;
    private HttpTask child;
    private HttpMapFun mapFun;
    private Call<T> call;
    private HttpCallback<T> callback;
    private static final String TAG = "HttpTask";

    HttpTask(HttpTask parent) {
        this.parent = parent;
    }

    public HttpTask(Call<T> call) {
        this.call = call;
    }

    public <V> HttpTask flatMap(HttpMapFun<T, V> map) {
        mapFun = map;
        child = new HttpTask<V>(this);
        return child;
    }

    public HttpTask<T> addCallback(HttpCallback<T> callback) {
        this.callback = callback;
        return this;
    }

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
