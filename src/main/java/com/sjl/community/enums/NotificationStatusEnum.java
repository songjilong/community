package com.sjl.community.enums;

/**
 * @author song
 * @create 2020/2/23 17:22
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1);

    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
