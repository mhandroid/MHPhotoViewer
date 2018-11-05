package com.mh.android.photoviewer.exception;

import java.io.IOException;

/**
 * Exception class to get no network exception
 * Class for network exception
 * Created by @author Mubarak Hussain.
 */
public class NoNetworkException extends IOException {

    @Override
    public String getMessage() {
        return "Please check your internet connection.";
    }
}
