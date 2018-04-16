package com.smart.urban.bean;

import com.smart.urban.config.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingListBean implements Serializable {


    /**
     * images : [{"belongId":1523323595982,"order":0,"address":"/static/upload/de33663b-c038-48e5-89ab-55619e84296c.jpg","id":1523323595983,"type":"2"}]
     * state : 1
     * content : 顾家家居
     * createTime : 1523331163000
     * createUserId : 1523007772370
     * modifyTime : 1523331163000
     * id : 1523323595982
     */

    private String state;
    private String content;
    private long createTime;
    private long createUserId;
    private long modifyTime;
    private long id;
    private int type = 1;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    private List<ImagesBean> images;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
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

    public static class ImagesBean implements Serializable {
        /**
         * belongId : 1523323595982
         * order : 0
         * address : /static/upload/de33663b-c038-48e5-89ab-55619e84296c.jpg
         * id : 1523323595983
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
            return Constants.BASE_URL + address;
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
