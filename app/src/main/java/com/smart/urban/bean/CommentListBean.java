package com.smart.urban.bean;

import com.smart.urban.config.Constants;

import java.util.List;

/**
 * Created by root on 18-4-13.
 */

public class CommentListBean {


    /**
     * belongId : 1523193891931
     * type : 1
     * userInfo : {"nickName":"xienashens","loginAcct":"18523047850","avatar":[{"belongId":1523007772372,"order":0,"address":"/data/uploads/20180412/e2655c25-e3a3-4676-8e00-f5ca92c8d9d5","id":1523446566147,"type":"4"}],"createUserId":null,"recordStatus":"Y","createTime":null,"modifyUserId":null,"modifyTime":1523624025000,"id":1523007772372}
     * content : SSDADAD
     * createUserId : 1523007772372
     * recordStatus : null
     * createTime : null
     * modifyUserId : null
     * modifyTime : null
     * id : 1523446566129
     */

    private long belongId;
    private String type;
    private UserInfoBean userInfo;
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

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
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

    public static class UserInfoBean {
        /**
         * nickName : xienashens
         * loginAcct : 18523047850
         * avatar : [{"belongId":1523007772372,"order":0,"address":"/data/uploads/20180412/e2655c25-e3a3-4676-8e00-f5ca92c8d9d5","id":1523446566147,"type":"4"}]
         * createUserId : null
         * recordStatus : Y
         * createTime : null
         * modifyUserId : null
         * modifyTime : 1523624025000
         * id : 1523007772372
         */

        private String nickName;
        private String loginAcct;
        private Object createUserId;
        private String recordStatus;
        private Object createTime;
        private Object modifyUserId;
        private long modifyTime;
        private long id;
        private List<AvatarBean> avatar;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLoginAcct() {
            return loginAcct;
        }

        public void setLoginAcct(String loginAcct) {
            this.loginAcct = loginAcct;
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

        public List<AvatarBean> getAvatar() {
            return avatar;
        }

        public void setAvatar(List<AvatarBean> avatar) {
            this.avatar = avatar;
        }

        public static class AvatarBean {
            /**
             * belongId : 1523007772372
             * order : 0
             * address : /data/uploads/20180412/e2655c25-e3a3-4676-8e00-f5ca92c8d9d5
             * id : 1523446566147
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
}
