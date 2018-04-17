package com.smart.urban.bean;

import com.smart.urban.config.Constants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 18-4-9.
 */

public class DynamicListBean implements Serializable {


    /**
     * subtitle : null
     * isCheck : null
     * images : [{"belongId":1523535828422,"order":0,"address":"/data/uploads/20180413/f07a1412-fbfa-40df-821a-77d74adff62d.png","id":1523535828423,"type":"6"},{"belongId":1523535828422,"order":1,"address":"/data/uploads/20180413/8e66fbe3-e9ae-408a-bfad-52db9a427bf3.png","id":1523535828424,"type":"6"}]
     * title : too涂抹女兔兔www
     * commentCount : 0
     * viewCount : 0
     * content : two魔图我女兔兔
     * createUserId : 1523007772370
     * recordStatus : null
     * createTime : 1523592168000
     * modifyUserId : null
     * modifyTime : 1523592168000
     * id : 1523535828422
     */

    private Object subtitle;
    private Object isCheck;
    private String title;
    private int commentCount;
    private int viewCount;
    private String content;
    private long createUserId;
    private Object recordStatus;
    private long createTime;
    private Object modifyUserId;
    private long modifyTime;
    private long id;
    private List<ImagesBean> images;

    public Object getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Object subtitle) {
        this.subtitle = subtitle;
    }

    public Object getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Object isCheck) {
        this.isCheck = isCheck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
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

    public String getCreateTime() {
        Date date = new Date(createTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
         * belongId : 1523535828422
         * order : 0
         * address : /data/uploads/20180413/f07a1412-fbfa-40df-821a-77d74adff62d.png
         * id : 1523535828423
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
