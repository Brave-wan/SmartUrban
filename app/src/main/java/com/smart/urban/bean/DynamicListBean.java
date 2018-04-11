package com.smart.urban.bean;

import com.smart.urban.config.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 18-4-9.
 */

public class DynamicListBean implements Serializable {


    /**
     * subtitle : null
     * isCheck : null
     * images : [{"belongId":1523333240795,"order":1,"address":"/data/uploads/87e73c5e-017a-4487-a760-fafe11f5bd8d.png","id":1523333240796,"type":"6"},{"belongId":1523333240795,"order":3,"address":"/data/uploads/d93f30c4-4484-4e14-9766-48a19b043c58.jpg","id":1523333240797,"type":"6"}]
     * title : 好纠结不不不
     * content : 刚回家健康i不能
     * createUserId : 1523007772370
     * recordStatus : null
     * createTime : 1523347539000
     * modifyUserId : null
     * modifyTime : null
     * id : 1523333240795
     */

    private String subtitle;
    private String isCheck;
    private String title;
    private String content;
    private long createUserId;
    private String recordStatus;
    private long createTime;
    private String modifyUserId;
    private String modifyTime;
    private long id;
    private List<ImagesBean> images;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
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
         * belongId : 1523333240795
         * order : 1
         * address : /data/uploads/87e73c5e-017a-4487-a760-fafe11f5bd8d.png
         * id : 1523333240796
         * type : 6
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
