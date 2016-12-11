package com.statsystem.dbservice.execute;

public class DBException extends Exception {
    public DBException(String context, Throwable throwable) {
        super(context, throwable);
    }
}
