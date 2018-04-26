package com.kxg.livewallpaper.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kuangxiaoguo on 2018/3/21.
 */

public class BaseResponse<T> {

    @SerializedName("msg")
    private String message;

    @SerializedName("retCode")
    private String resultCode;

    private T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
