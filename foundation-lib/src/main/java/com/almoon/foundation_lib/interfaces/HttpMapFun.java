package com.almoon.foundation_lib.interfaces;

import com.almoon.foundation_lib.common.HttpResult;
import retrofit2.Call;

public interface HttpMapFun<T, V> {
    Call<V> map(HttpResult<T> result);
}
