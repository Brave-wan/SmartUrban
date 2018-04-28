package com.smart.urban.bean;

import com.smart.urban.config.Constants;

public class BannerBean {

    /**
     * articleId : 1523965605256
     * articleType : 1
     * orderNumber : 1
     * isUrl : N
     * urlAddress : null
     * image : {"belongId":1523963132993,"order":0,"address":"/data/uploads/20180417/1d1cacf1-8bae-4547-9595-6c92290ef4f9.jpg","id":1523963132995,"type":"4"}
     * createUserId : null
     * recordStatus : Y
     * createTime : null
     * modifyUserId : null
     * modifyTime : null
     * id : 1523966637350
     */

    private long articleId;
    private String articleType;
    private int orderNumber;
    private String isUrl;
    private String urlAddress;
    private ImageBean image;
    private Object createUserId;
    private String recordStatus;
    private Object createTime;
    private Object modifyUserId;
    private Object modifyTime;
    private long id;

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getIsUrl() {
        return isUrl;
    }

    public void setIsUrl(String isUrl) {
        this.isUrl = isUrl;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
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

    public static class ImageBean {
        /**
         * belongId : 1523963132993
         * order : 0
         * address : /data/uploads/20180417/1d1cacf1-8bae-4547-9595-6c92290ef4f9.jpg
         * id : 1523963132995
         * type : 4
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
            return address;
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
