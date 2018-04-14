package com.smart.urban.bean;

/**
 * Created by root on 18-4-13.
 */

public class CommentListBean {


    /**
     * belongId : 1523193891931
     * type : 1
     * content : 评论内容
     * createUserId : 1523242982070
     * recordStatus : null
     * createTime : null
     * modifyUserId : null
     * modifyTime : null
     * id : 1523518020769
     */

    private long belongId;
    private String type;
    private String content;
    private long createUserId;
    private Object recordStatus;
    private Object createTime;
    private Object modifyUserId;
    private Object modifyTime;
    private long id;

    public long getBelongId() {
        return belongId;
    }

    public void setBelongId(long belongId) {
        this.belongId = belongId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public Object getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Object recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Object modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Object getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Object modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
