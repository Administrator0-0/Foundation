package com.almoon.foundation_lib.common;


import android.util.Log;

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
                    if (mapFun != null) {
                        Call child = mapFun.map(new HttpResult(response.body()));
                        HttpTask.this.child.call = child;
                        HttpTask.this.child.realExecute(position + 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.e(TAG, "Task" + position + " fail: " + t.getMessage());
            }
        });
    }
}
