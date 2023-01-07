package com.zee.club.user.data.protocol.response;

public class UserInfoResp {


    /**
     * createTime :
     * telephone :
     * updateTime :
     * userCode :
     * userId : 0
     * userName :
     * userType : 0
     * whetherToBind : true
     */

    private String createTime;
    private String telephone;
    private String updateTime;
    private String userCode;
    private String userId;
    private String userName;
    private String pic;
    private int userType;
    private boolean whetherToBind;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean isWhetherToBind() {
        return whetherToBind;
    }

    public void setWhetherToBind(boolean whetherToBind) {
        this.whetherToBind = whetherToBind;
    }

    public UserInfoResp(String createTime, String telephone, String updateTime, String userCode, String userId, String userName, int userType, boolean whetherToBind) {
        this.createTime = createTime;
        this.telephone = telephone;
        this.updateTime = updateTime;
        this.userCode = userCode;
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.whetherToBind = whetherToBind;
    }
}
