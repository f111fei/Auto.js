package com.stardust.autojs.core.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Stardust on 2018/4/11.
 */

public class MutableOkHttp extends OkHttpClient {

    private OkHttpClient mOkHttpClient;
    private long mTimeout = 8 * 1000;

    public MutableOkHttp() {
        mOkHttpClient = newClient(new OkHttpClient.Builder());
    }

    public OkHttpClient client() {
        return mOkHttpClient;
    }

    protected OkHttpClient newClient(Builder builder) {
        builder.readTimeout(getTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(getTimeout(), TimeUnit.MILLISECONDS)
                .connectTimeout(getTimeout(), TimeUnit.MILLISECONDS);
        return builder.build();
    }

    public long getTimeout() {
        return mTimeout;
    }

    public void setTimeout(long timeout) {
        mTimeout = timeout;
        muteClient();
    }

    public synchronized void muteClient(Builder builder) {
        mOkHttpClient = newClient(builder);
    }

    protected synchronized void muteClient() {
        mOkHttpClient = newClient(mOkHttpClient.newBuilder());
    }
}
