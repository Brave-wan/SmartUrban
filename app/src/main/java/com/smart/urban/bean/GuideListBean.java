package com.smart.urban.bean;

import java.io.Serializable;

/**
 * Created by root on 18-4-9.
 */

public class GuideListBean implements Serializable{


    /**
     * title : 办事指引标题
     * subtitle : null
     * isOnline : Y
     * content : 办事指引内容
     * createTime : 1523193991000
     * createUserId : null
     * recordStatus : null
     * modifyUserId : null
     * modifyTime : 1523193991000
     * id : 1523196063374
     */

    private String title;
    private Object subtitle;
    private String isOnline;
    private String content;
    private long createTime;
    private Object createUserId;
    private Object recordStatus;
    private Object modifyUserId;
    private long modifyTime;
    private long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Object subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public Object getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Object recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Object getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Object modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
