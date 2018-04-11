package com.smart.urban.bean;

import com.smart.urban.config.Constants;

import java.util.List;

/**
 * Created by root on 18-4-10.
 */

public class LostBean {


    /**
     * contact : 好吧vv你看看吧vvv
     * images : [{"belongId":1523427100299,"order":0,"address":"/data/uploads/20180411/a1e47241-323e-4152-ba99-dbc87f693e83.jpg","id":1523427100300,"type":"5"},{"belongId":1523427100299,"order":1,"address":"/data/uploads/20180411/6db063dd-ef4a-471d-8209-7211370fa0e7.jpg","id":1523427100301,"type":"5"},{"belongId":1523427100299,"order":2,"address":"/data/uploads/20180411/1d40b659-6578-4876-91a7-e7fac4d696ee.jpg","id":1523427100302,"type":"5"}]
     * content : 黄家驹比较看看吧
     * createUserId : 1523007772370
     * recordStatus : null
     * createTime : 1523434047000
     * modifyUserId : null
     * modifyTime : null
     * id : 1523427100299
     */

    private String contact;
    private String content;
    private long createUserId;
    private Object recordStatus;
    private long createTime;
    private Object modifyUserId;
    private Object modifyTime;
    private long id;
    private List<ImagesBean> images;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
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

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * belongId : 1523427100299
         * order : 0
         * address : /data/uploads/20180411/a1e47241-323e-4152-ba99-dbc87f693e83.jpg
         * id : 1523427100300
         * type : 5
         */

        private long belongId;
        private int order;
        private String address;
        private long id;
        private String type;

        public long getBelongId() {
            return belongId;
        }

        public void setBelongId(long belongId) {
            this.belongId = belongId;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getAddress() {
            return Constants.BASE_URL+address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
