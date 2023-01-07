package com.zee.club.user.data;

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
import com.zee.club.user.data.protocol.response.ActivitiesResp;
import com.zee.club.user.data.protocol.response.ApprovalResp;
import com.zee.club.user.data.protocol.response.AssociationResp;
import com.zee.club.user.data.protocol.response.DataInfoListResp;
import com.zee.club.user.data.protocol.response.EnterpriseResp;
import com.zee.club.user.data.protocol.response.GatherInfoResp;
import com.zee.club.user.data.protocol.response.ImageCaptchaResp;
import com.zee.club.user.data.protocol.response.MemberResp;
import com.zee.club.user.data.protocol.response.NoticeResp;
import com.zee.club.user.data.protocol.response.PersonEnergyResp;
import com.zee.club.user.data.protocol.response.PhoneCaptchaResp;
import com.zee.club.user.data.protocol.response.PhoneLoginResp;
import com.zee.club.user.data.protocol.response.UserAppInfoResp;
import com.zee.club.user.data.protocol.response.UserInfoResp;
import com.zee.club.user.data.source.http.service.UserService;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.utils.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;


public class UserRepository implements UserService {

    private static volatile UserRepository instance;
    private final UserService userService;

    private UserRepository(UserService userService) {
        this.userService = userService;
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository(RetrofitClient.getInstance().create(UserService.class));
                }
            }
        }
        return instance;
    }


    @Override
    public Observable<BaseResp<ImageCaptchaResp>> imageCaptchaReq(ImageCaptchaReq imageCaptchaReq) {
        return userService.imageCaptchaReq(imageCaptchaReq);
    }

    @Override
    public Observable<BaseResp<PhoneCaptchaResp>> phoneCaptchaReq(PhoneCaptchaReq phoneCaptchaReq) {
        return userService.phoneCaptchaReq(phoneCaptchaReq);
    }

    @Override
    public Observable<BaseResp<PhoneLoginResp>> phoneLoginReq(PhoneLoginReq phoneLoginReq) {
        return userService.phoneLoginReq(phoneLoginReq);
    }

    @Override
    public Observable<BaseResp<List<AssociationResp>>> getSelfList() {
        return userService.getSelfList();
    }

    @Override
    public Observable<BaseResp<List<MemberResp>>> getMemberList(MemberReq memberReq) {
        return userService.getMemberList(memberReq);
    }

    @Override
    public Observable<BaseResp<String>> forgetPassword(ForgetPasswordReq forgetPasswordReq) {
        return userService.forgetPassword(forgetPasswordReq);
    }

    @Override
    public Observable<BaseResp<UserInfoResp>> getUserInfo() {
        return userService.getUserInfo();
    }

    @Override
    public Observable<BaseResp<String>> bindCommunity(BindCommunityReq req) {
        return userService.bindCommunity(req);
    }

    @Override
    public Observable<BaseResp<String>> bindPerCommunity(JoinApplyReq req) {
        return userService.bindPerCommunity(req);
    }

    @Override
    public Observable<BaseResp<List<EnterpriseResp>>> getEnterpriseList(EnterpriseReq enterpriseReq) {
        return userService.getEnterpriseList(enterpriseReq);
    }

    @Override
    public Observable<BaseResp<DataInfoListResp<GatherInfoResp>>> getFavoritesPage(PageListReq req) {
        return userService.getFavoritesPage(req);
    }

    @Override
    public Observable<BaseResp<DataInfoListResp<GatherInfoResp>>> getBrowsePage(PageListReq req) {
        return userService.getBrowsePage(req);
    }

    @Override
    public Observable<BaseResp<DataInfoListResp<GatherInfoResp>>> getPraisePage(PageListReq req) {
        return userService.getPraisePage(req);
    }

    @Override
    public Observable<BaseResp<DataInfoListResp<ActiveResp>>> getActivePage(PageListReq req) {
        return userService.getActivePage(req);
    }

    @Override
    public Observable<BaseResp<PersonEnergyResp>> getEnergyGeneral(Integer associationInfo) {
        return userService.getEnergyGeneral(associationInfo);
    }

    @Override
    public Observable<BaseResp<DataInfoListResp<ApprovalResp>>> getOrderPage(OrderReq req) {
        return userService.getOrderPage(req);
    }

    @Override
    public Observable<BaseResp<String>> examineApproveReq(ExamineReq req) {
        return userService.examineApproveReq(req);
    }

    @Override
    public Observable<BaseResp<List<UserAppInfoResp>>> getReqInteractionList() {
        return userService.getReqInteractionList();
    }

    @Override
    public Observable<BaseResp<List<ActivitiesResp>>> getActivitiesList() {
        return userService.getActivitiesList();
    }

    @Override
    public Observable<BaseResp<String>> reqEnterApply(AssoEnterApplyReq req) {
        return userService.reqEnterApply(req);
    }

    @Override
    public Observable<BaseResp<DataInfoListResp<NoticeResp>>> getNoticeList(PageListReq req) {
        return userService.getNoticeList(req);
    }

}
