package com.smart.urban.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 16.04.18.
 */

public class InfoDetailsBean {


    /**
     * title : 一
     * createUserName : admin
     * subtitle : 你看
     * isOnline : N
     * viewCount : 3
     * images : [{"belongId":1523631146673,"order":0,"address":"http://img.baidu.com/hi/jx2/j_0001.gif","id":1523631146674,"type":"1"}]
     * content : <p>&nbsp; &nbsp; &nbsp; &nbsp;基本接口看见</p><p><br/></p><p><img src="http://img.baidu.com/hi/jx2/j_0001.gif" _src="http://img.baidu.com/hi/jx2/j_0001.gif"/><img src="http://img.baidu.com/hi/jx2/j_0045.gif" _src="http://img.baidu.com/hi/jx2/j_0045.gif"/></p>
     * createUserId : 0
     * recordStatus : Y
     * createTime : 1523631448000
     * modifyUserId : null
     * modifyTime : null
     * id : 1523631146673
     */

    private String title;
    private String createUserName;
    private String subtitle;
    private String isOnline;
    private int viewCount;
    private String content;
    private int createUserId;
    private String recordStatus;
    private long createTime;
    private Object modifyUserId;
    private Object modifyTime;
    private long id;
    private List<ImagesBean> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getCreateTime() {
        Date date = new Date(createTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
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
         * belongId : 1523631146673
         * order : 0
         * address : http://img.baidu.com/hi/jx2/j_0001.gif
         * id : 1523631146674
         * type : 1
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
