package com.blitzr.exceptions;

public class BlitzrException extends RuntimeException {

    private int mStatusCode;
    private String mMessage;

    public BlitzrException(int mStatusCode) {
        this.mStatusCode = mStatusCode;
    }

    public BlitzrException(int mStatusCode, String mMessage) {
        this.mStatusCode = mStatusCode;
        this.mMessage = mMessage;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public int getmStatusCode() {
        return mStatusCode;
    }

    public void setmStatusCode(int mStatusCode) {
        this.mStatusCode = mStatusCode;
    }
}
