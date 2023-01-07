package com.zee.club.user.data.protocol.response;

public class NoticeResp {


    /**
     * detailsAddr :
     * noticeId : 0
     * noticeName :
     * noticeTime :
     * noticeType : 0
     * noticeTypeName :
     * objId :
     */

    private String detailsAddr;
    private int noticeId;
    private String noticeName;
    private String noticeTime;
    private int noticeType;
    private String noticeTypeName;
    private String objId;

    public String getDetailsAddr() {
        return detailsAddr;
    }

    public void setDetailsAddr(String detailsAddr) {
        this.detailsAddr = detailsAddr;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeTypeName() {
        return noticeTypeName;
    }

    public void setNoticeTypeName(String noticeTypeName) {
        this.noticeTypeName = noticeTypeName;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
