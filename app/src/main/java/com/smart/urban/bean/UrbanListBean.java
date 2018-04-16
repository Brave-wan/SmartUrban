package com.smart.urban.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 18-4-9.
 */

public class UrbanListBean implements Serializable {


    /**
     * title : 最新动态
     * subtitle :
     * isOnline : Y
     * viewCount : 0
     * images : [{"belongId":1523193891931,"order":0,"address":"http://img.baidu.com/hi/jx2/j_0034.gif","id":1523193891975,"type":"3"},{"belongId":1523193891931,"order":0,"address":"http://tpc.googlesyndication.com/daca_images/simgad/1138649454590774938","id":1523631146676,"type":"3"}]
     * commentCount : 16
     * content : 最新动态的内容
     * createUserId : null
     * recordStatus : null
     * createTime : 1523193991000
     * modifyUserId : null
     * modifyTime : 1523193991000
     * id : 1523193891931
     */

    private String title;
    private String subtitle;
    private String isOnline;
    private int viewCount;
    private int commentCount;
    private String content;
    private String createUserId;
    private String recordStatus;
    private long createTime;
    private String modifyUserId;
    private long modifyTime;
    private long id;
    private List<ImagesBean> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Object getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
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

    public void setModifyUserId(String modifyUserId) {
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

    public static class ImagesBean  implements Serializable{
        /**
         * belongId : 1523193891931
         * order : 0
         * address : http://img.baidu.com/hi/jx2/j_0034.gif
         * id : 1523193891975
         * type : 3
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
