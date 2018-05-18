package com.smart.urban.bean;

/**
 * Created by root on 18-5-18.
 */

public class SelectTypeBean {

    /**
     * {"errcode":"200","errmsg":"操作成功","pageInfo":null,"data":[{"createUserName":null,
     * "name":"露天烧烤",
     * "createUserId":0,"recordStatus":"Y","createTime":1526572229000,"modifyUserId":0,"modifyTime":1526572294000,"id":1526570957384},{"createUserName":null,"name":"占道经营","createUserId":0,"recordStatus":"Y","createTime":1526572178000,"modifyUserId":null,"modifyTime":1526572178000,"id":1526570957382}]}
     */


    private Object createUserName;
    private String name;
    private int createUserId;
    private long modifyTime;
    private long id;

    public Object getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(Object createUserName) {
        this.createUserName = createUserName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
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
}
