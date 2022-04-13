package com.example.test1.exception;


public class MyException extends RuntimeException{

    public String msg = "出错了";
    public MyException() {
    }

    public MyException(String msg) {
        this.msg = msg;
    }

    public MyException(String message, String msg) {
        super(message);
        this.msg = msg;
    }

    public MyException(String message, Throwable cause, String msg) {
        super(message, cause);
        this.msg = msg;
    }

    public MyException(Throwable cause, String msg) {
        super(cause);
        this.msg = msg;
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.msg = msg;
    }
}
