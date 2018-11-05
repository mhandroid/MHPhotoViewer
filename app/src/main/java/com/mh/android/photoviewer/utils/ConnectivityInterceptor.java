package com.mh.android.photoviewer.utils;

import android.content.Context;

import com.mh.android.photoviewer.exception.NoNetworkException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp interceptor class to detect no network connection
 * Created by @author Mubarak Hussain.
 */
public class ConnectivityInterceptor implements Interceptor {
    private Context mContext;

    /**
     * Constructor to initialize ConnectivityInterceptor
     *
     * @param context
     */
    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!Utils.isNetworkAvailable(mContext)) {
            throw new NoNetworkException();
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
