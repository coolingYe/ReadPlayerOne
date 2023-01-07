package com.zee.club.user.config;


public class UserConstants {


    public static final String Gather_Type = "Gathers_Types";


    public static final String Gather_Type_Browse = "Gather_Type_Browse";
    public static final String Gather_Type_Thumbs = "Gather_Type_Thumbs";
    public static final String Gather_Type_Collect = "Gather_Type_Collect";

    public static final String Bind_Statue = "Bind_Statue";

    public static int Approve_Agree = 2;
    public static int Approve_Reject = 3;

    public static String Approve_Order_Id = "Approve_Order_Id";
    public static String Approve_Order_Name = "Approve_Order_Name";


    public static class BindStatueType {
        //  角色类型 1-普通用户 2-运维人员 3-管理员 4-超级管理员
        public static final int Bind_Statue_wait = 1;
        public static final int Bind_Statue_refuse = 2;
        public static final int Bind_Statue_allow = 3;
    }


    public static final int Order_Check = 1;
    public static final int Order_accept = 2;
    public static final int Order_reject = 3;


    public static class LoginType {
        public static final int PHONE = 1;
        public static final int ACCOUNT = 0;
    }


    public static final String USER_TYPE = "user_type";

    public static class UserType {
        //  角色类型 1-普通用户 2-运维人员 3-管理员 4-超级管理员
        public static final int Ordinary_Users = 1;
        public static final int Operation_Users = 2;
        public static final int Admin_User = 3;
        public static final int SuperAdmin_User = 4;
    }

    public static class NoticeMessageType {
        public static final int Application_Update = 0;
        public static final int Community_Residence = 1;
        public static final int League_Membership = 2;
    }

    public static class CaptchaType {
        public static final int USER_LOGIN = 0;
        public static final int USER_REGISTER = 1;
        public static final int FIND_PASSWORD = 2;
        public static final int MODIFY_TELEPHONE = 3;
    }

}
