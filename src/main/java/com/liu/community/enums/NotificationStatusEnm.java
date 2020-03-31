package com.liu.community.enums;

public enum NotificationStatusEnm {
    UNREAD(0),
    READ(1)
    ;
    private int status;

    NotificationStatusEnm(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
