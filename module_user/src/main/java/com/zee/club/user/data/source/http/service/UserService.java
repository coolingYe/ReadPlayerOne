package com.zee.club.user.data.source.http.service;


import com.zee.club.user.data.protocol.request.AssoEnterApplyReq;
import com.zee.club.user.data.protocol.request.BindCommunityReq;
import com.zee.club.user.data.protocol.request.EnterpriseReq;
import com.zee.club.user.data.protocol.request.ExamineReq;
import com.zee.club.user.data.protocol.request.ForgetPasswordReq;
import com.zee.club.user.data.protocol.request.JoinApplyReq;
import com.zee.club.user.data.protocol.request.OrderReq;
import com.zee.club.user.data.protocol.request.PageListReq;
import com.zee.club.user.data.protocol.request.ImageCaptchaReq;
import com.zee.club.user.data.protocol.request.MemberReq;
import com.zee.club.user.data.protocol.request.PhoneCaptchaReq;
import com.zee.club.user.data.protocol.request.PhoneLoginReq;
import com.zee.club.user.data.protocol.response.ActiveResp;
import com.zee.club.user.data.protocol.response.ApprovalResp;
import com.zee.club.user.data.protocol.response.AssociationResp;
import com.zee.club.user.data.protocol.response.DataInfoListResp;
import com.zee.club.user.data.protocol.response.EnterpriseResp;
import com.zee.club.user.data.protocol.response.GatherInfoResp;
import com.zee.club.user.data.protocol.response.ImageCaptchaResp;
import com.zee.club.user.data.protocol.response.ActivitiesResp;
import com.zee.club.user.data.protocol.response.MemberResp;
import com.zee.club.user.data.protocol.response.NoticeResp;
import com.zee.club.user.data.protocol.response.PersonEnergyResp;
import com.zee.club.user.data.protocol.response.PhoneCaptchaResp;
import com.zee.club.user.data.protocol.response.PhoneLoginResp;
import com.zee.club.user.data.protocol.response.UserAppInfoResp;
import com.zee.club.user.data.protocol.response.UserInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    // 图片验证码
    @POST(BaseConstants.basePath + "/captcha/captcha/image")
    Observable<BaseResp<ImageCaptchaResp>> imageCaptchaReq(@Body ImageCaptchaReq imageCaptchaReq);

    // 手机验证码
    @POST(BaseConstants.basePath + "/captcha/captcha/sms")
    Observable<BaseResp<PhoneCaptchaResp>> phoneCaptchaReq(@Body PhoneCaptchaReq phoneCaptchaReq);

    // 手机号登录
    @POST(BaseConstants.basePath + "/amuser/user/login")
    Observable<BaseResp<PhoneLoginResp>> phoneLoginReq(@Body PhoneLoginReq phoneLoginReq);

    // 获取我的社团列表
    @POST(BaseConstants.basePath + "/amuser/association/member/self/list")
    Observable<BaseResp<List<AssociationResp>>> getSelfList();


    // 获取社团列表
    @POST(BaseConstants.basePath + "/amuser/association/member/list")
    Observable<BaseResp<List<MemberResp>>> getMemberList(@Body MemberReq memberReq);


    // 忘记密码
    @POST(BaseConstants.basePath + "/amuser/user/createPwd")
    Observable<BaseResp<String>> forgetPassword(@Body ForgetPasswordReq forgetPasswordReq);

    // 获取用户信息
    @POST(BaseConstants.basePath + "/amuser/user/info")
    Observable<BaseResp<UserInfoResp>> getUserInfo();


    // 社团员工 企业 加入社团
    @POST(BaseConstants.basePath + "/amuser/association/member/join/apply")
    Observable<BaseResp<String>> bindCommunity(@Body BindCommunityReq req);

    // 企业员工加入社团
    @POST(BaseConstants.basePath + "/amuser/enterprise/member/join/apply")
    Observable<BaseResp<String>> bindPerCommunity(@Body JoinApplyReq req);


    // 获取企业列表
    @POST(BaseConstants.basePath + "/amuser/enterprise/member/list")
    Observable<BaseResp<List<EnterpriseResp>>> getEnterpriseList(@Body EnterpriseReq enterpriseReq);


    @POST(BaseConstants.basePath + "/usercentre/favorites/list/page")
    Observable<BaseResp<DataInfoListResp<GatherInfoResp>>> getFavoritesPage(@Body PageListReq req);

    @POST(BaseConstants.basePath + "/usercentre/browse/list/page")
    Observable<BaseResp<DataInfoListResp<GatherInfoResp>>> getBrowsePage(@Body PageListReq req);

    @POST(BaseConstants.basePath + "/usercentre/notice/list/page")
    Observable<BaseResp<DataInfoListResp<NoticeResp>>> getNoticeList(@Body PageListReq req);

    @POST(BaseConstants.basePath + "/usercentre/praise/list/page")
    Observable<BaseResp<DataInfoListResp<GatherInfoResp>>> getPraisePage(@Body PageListReq req);


    @POST(BaseConstants.basePath + "/usercentre/praise/list/page")
    Observable<BaseResp<DataInfoListResp<ActiveResp>>> getActivePage(@Body PageListReq req);


    @POST(BaseConstants.basePath + "/association/user/energy/general")
    Observable<BaseResp<PersonEnergyResp>> getEnergyGeneral(@Header("X-ASSOCIATION-INFO") Integer associationInfo);

    // 审核列表
    @POST(BaseConstants.basePath + "/amuser/association/order/list/page")
    Observable<BaseResp<DataInfoListResp<ApprovalResp>>> getOrderPage(@Body OrderReq req);

    // 社团审核审批服务
    @POST(BaseConstants.basePath + "/amuser/association/order/approve")
    Observable<BaseResp<String>> examineApproveReq(@Body ExamineReq req);


    // TODO: 2022/12/29 获取我的互动 互动
    @POST(BaseConstants.basePath + "/amuser/association/order/approve")
    Observable<BaseResp<List<UserAppInfoResp>>> getReqInteractionList( );

    // TODO: 2022/12/29 获取我的互动 活动
    @POST(BaseConstants.basePath + "/amuser/association/order/approve")
    Observable<BaseResp<List<ActivitiesResp>>> getActivitiesList( );

    // 社团入驻申请接口
    @POST(BaseConstants.basePath + "/amuser/association/enter/apply")
    Observable<BaseResp<String>> reqEnterApply( @Body AssoEnterApplyReq req);


}
