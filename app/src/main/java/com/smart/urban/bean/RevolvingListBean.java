package com.smart.urban.bean;

import com.smart.urban.config.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingListBean implements Serializable {


    /**
     * createUserName : null
     * images : [{"belongId":1524149457122,"order":1,"address":"/data/uploads/20180420/ed1cad65-1490-4921-b26b-7d5f3c788fe8.jpg","id":1524149457126,"type":"2"},{"belongId":1524149457122,"order":2,"address":"/data/uploads/20180420/f47ceaeb-0c20-43ff-96b2-7cc5b297b636.jpg","id":1524149457125,"type":"2"},{"belongId":1524149457122,"order":0,"address":"/data/uploads/20180420/4f9b759d-cf0a-40d5-9638-7500b6345f99.jpg","id":1524149457124,"type":"2"}]
     * allState : null
     * state : 1
     * content : 测试版本测试
     * createUserId : 1523963132993
     * recordStatus : Y
     * createTime : 1524160042000
     * modifyUserId : null
     * modifyTime : 1524160042000
     * id : 1524149457122
     */

    private Object createUserName;
    private Object allState;
    private String state;
    private String content;
    private long createUserId;
    private String recordStatus;
    private long createTime;
    private Object modifyUserId;
    private long modifyTime;
    private long id;
    private List<ImagesBean> images;

    public Object getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(Object createUserName) {
        this.createUserName = createUserName;
    }

    public Object getAllState() {
        return allState;
    }

    public void setAllState(Object allState) {
        this.allState = allState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
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

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean implements Serializable{
        /**
         * belongId : 1524149457122
         * order : 1
         * address : /data/uploads/20180420/ed1cad65-1490-4921-b26b-7d5f3c788fe8.jpg
         * id : 1524149457126
         * type : 2
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
