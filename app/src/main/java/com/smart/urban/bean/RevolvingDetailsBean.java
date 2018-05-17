package com.smart.urban.bean;

import com.blankj.utilcode.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 18-5-12.
 */

public class RevolvingDetailsBean {


    /**
     * createUserName : null
     * images : [{"belongId":1524934021542,"order":0,"address":"/data/uploads/20180429/8a14c91a-c7bd-487e-b618-3a58e16aa978.jpg","id":1524934021544,"type":"2"}]
     * allState : [{"belongId":1524934021542,"name":"处理完成","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525235733000,"modifyUserId":null,"modifyTime":1525235733000,"id":1525191963381},{"belongId":1524934021542,"name":"处理完成","content":"","createUserId":0,"recordStatus":"Y","createTime":1526029878000,"modifyUserId":null,"modifyTime":1526029878000,"id":1526028934721},{"belongId":1524934021542,"name":"处理完成","content":"","createUserId":1524881965847,"recordStatus":"Y","createTime":1526087667000,"modifyUserId":null,"modifyTime":1526087667000,"id":1526035029730},{"belongId":1524934021542,"name":"","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525168440000,"modifyUserId":null,"modifyTime":1525168440000,"id":1524934021635},{"belongId":1524934021542,"name":"跟进中","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525168450000,"modifyUserId":null,"modifyTime":1525168450000,"id":1524934021636},{"belongId":1524934021542,"name":"带跟进","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525051208000,"modifyUserId":null,"modifyTime":1525051208000,"id":1524934021609},{"belongId":1524934021542,"name":"跟进中","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525000690000,"modifyUserId":null,"modifyTime":1525000690000,"id":1524934021575},{"belongId":1524934021542,"name":"","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525001661000,"modifyUserId":null,"modifyTime":1525001661000,"id":1524934021578},{"belongId":1524934021542,"name":"跟进中","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525168580000,"modifyUserId":null,"modifyTime":1525168580000,"id":1524934021638},{"belongId":1524934021542,"name":"未通过审核","content":"","createUserId":0,"recordStatus":"Y","createTime":1526031148000,"modifyUserId":null,"modifyTime":1526031148000,"id":1526028934743},{"belongId":1524934021542,"name":"待跟进","content":null,"createUserId":null,"recordStatus":"Y","createTime":1524994301000,"modifyUserId":null,"modifyTime":1524994301000,"id":1524934021543},{"belongId":1524934021542,"name":"待审核","content":"","createUserId":0,"recordStatus":"Y","createTime":1526031156000,"modifyUserId":null,"modifyTime":1526031156000,"id":1526028934745},{"belongId":1524934021542,"name":"跟进中","content":"","createUserId":0,"recordStatus":"Y","createTime":1526035276000,"modifyUserId":null,"modifyTime":1526035276000,"id":1526035029682},{"belongId":1524934021542,"name":"","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525168560000,"modifyUserId":null,"modifyTime":1525168560000,"id":1524934021637},{"belongId":1524934021542,"name":"跟进中","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525168414000,"modifyUserId":null,"modifyTime":1525168414000,"id":1524934021632},{"belongId":1524934021542,"name":"","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525051215000,"modifyUserId":null,"modifyTime":1525051215000,"id":1524934021610},{"belongId":1524934021542,"name":"跟进中","content":"","createUserId":1524881965867,"recordStatus":"Y","createTime":1525051195000,"modifyUserId":null,"modifyTime":1525051195000,"id":1524934021608}]
     * state : 3
     * content : 天气好好，今天特别出去游玩
     * createUserId : 1523963132993
     * recordStatus : Y
     * createTime : 1524994301000
     * modifyUserId : null
     * modifyTime : 1526087667000
     * id : 1524934021542
     */

    private Object createUserName;
    private String state;
    private String content;
    private long createUserId;
    private String recordStatus;
    private long createTime;
    private Object modifyUserId;
    private long modifyTime;
    private long id;
    private List<ImagesBean> images;
    private List<AllStateBean> allState;

    public Object getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(Object createUserName) {
        this.createUserName = createUserName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return StringUtils.isEmpty(content) ? "暂无" : content;
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

    public List<AllStateBean> getAllState() {
        return allState;
    }

    public void setAllState(List<AllStateBean> allState) {
        this.allState = allState;
    }

    public static class ImagesBean {
        /**
         * belongId : 1524934021542
         * order : 0
         * address : /data/uploads/20180429/8a14c91a-c7bd-487e-b618-3a58e16aa978.jpg
         * id : 1524934021544
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

    public static class AllStateBean {
        /**
         * belongId : 1524934021542
         * name : 处理完成
         * content :
         * createUserId : 1524881965867
         * recordStatus : Y
         * createTime : 1525235733000
         * modifyUserId : null
         * modifyTime : 1525235733000
         * id : 1525191963381
         */

        private long belongId;
        private String name;
        private String content;
        private long createUserId;
        private String recordStatus;
        private long createTime;
        private Object modifyUserId;
        private long modifyTime;
        private long id;

        public long getBelongId() {
            return belongId;
        }

        public void setBelongId(long belongId) {
            this.belongId = belongId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            if (content == null) {
                return "暂无";
            }

            if (StringUtils.isEmpty(content)) {
                return "暂无";
            }
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


        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getModifyTime() {
            Date date = new Date(modifyTime);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return format.format(date);
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
