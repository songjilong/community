package com.sjl.community.model;

import java.io.Serializable;

public class Notification  implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.notifier_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private Long notifierId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.notify_name
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private String notifyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.receiver_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private Long receiverId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.target_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private Long targetId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.target_title
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private String targetTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.type
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.status
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    private Long gmtCreate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.id
     *
     * @return the value of notification.id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.id
     *
     * @param id the value for notification.id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.notifier_id
     *
     * @return the value of notification.notifier_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public Long getNotifierId() {
        return notifierId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.notifier_id
     *
     * @param notifierId the value for notification.notifier_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setNotifierId(Long notifierId) {
        this.notifierId = notifierId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.notify_name
     *
     * @return the value of notification.notify_name
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public String getNotifyName() {
        return notifyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.notify_name
     *
     * @param notifyName the value for notification.notify_name
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setNotifyName(String notifyName) {
        this.notifyName = notifyName == null ? null : notifyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.receiver_id
     *
     * @return the value of notification.receiver_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public Long getReceiverId() {
        return receiverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.receiver_id
     *
     * @param receiverId the value for notification.receiver_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.target_id
     *
     * @return the value of notification.target_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public Long getTargetId() {
        return targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.target_id
     *
     * @param targetId the value for notification.target_id
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.target_title
     *
     * @return the value of notification.target_title
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public String getTargetTitle() {
        return targetTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.target_title
     *
     * @param targetTitle the value for notification.target_title
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle == null ? null : targetTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.type
     *
     * @return the value of notification.type
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.type
     *
     * @param type the value for notification.type
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.status
     *
     * @return the value of notification.status
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.status
     *
     * @param status the value for notification.status
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.gmt_create
     *
     * @return the value of notification.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.gmt_create
     *
     * @param gmtCreate the value for notification.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:48:54 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}