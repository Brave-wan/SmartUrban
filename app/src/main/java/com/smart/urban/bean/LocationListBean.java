package com.smart.urban.bean;

/**
 * Created by root on 14.04.18.
 */

public class LocationListBean {


    /**
     * longitude : 114.323736
     * latitude : 38.079413
     * createUserName : null
     * name : 医院
     * type : 1
     * createUserId : 0
     * recordStatus : N
     * createTime : 1523341400000
     * modifyUserId : null
     * modifyTime : null
     * id : 1523332928836
     */

    private String longitude;
    private String latitude;
    private Object createUserName;
    private String name;
    private String type;
    private int createUserId;
    private String recordStatus;
    private long createTime;
    private Object modifyUserId;
    private Object modifyTime;
    private long id;
    private float calculate;

    public void setCalculate(float calculate) {
        this.calculate = calculate;
    }

    public float getCalculate() {
        return calculate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
