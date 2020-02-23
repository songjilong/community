package com.sjl.community.dto;

import lombok.Data;

/**
 * @author song
 * @create 2020/2/23 17:13
 */
@Data
public class NotificationDto {
    private Long id;
    private Long notifierId;
    private String notifyName;
    private Long receiverId;
    private Long targetId;
    private String targetTitle;
    private Integer type;
    private String typeDescription;
    private Integer status;
    private Long gmtCreate;
}
