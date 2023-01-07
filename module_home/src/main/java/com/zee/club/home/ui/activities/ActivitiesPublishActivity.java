package com.zee.club.home.ui.activities;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.app.ViewModelFactory;
import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.data.protocol.request.ActivityAddReq;
import com.zee.club.home.data.model.ActivityAppMo;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zee.club.home.ui.activities.adapter.PublishContentAdapter;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivitiesPublishActivity extends BaseActivity {

    private RecyclerView recyclerViewPublishContent;
    private ActivitiesPublishViewModel mViewModel;
    private TextView tvPublishDateStart, tvPublishTimeStart;
    private TextView tvPublishDateEnd, tvPublishTimeEnd;
    private TextView tvActivitiesPublish;
    private EditText etActivitiesPublishName;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final int TYPE_START = 0;
    private static final int TYPE_END = 1;
    private PublishContentAdapter publishContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ActivitiesPublishViewModel.class);

        setContentView(R.layout.activity_activities_publish);

        recyclerViewPublishContent = findViewById(R.id.recycler_view_activities_publish_content);
        tvPublishDateStart = findViewById(R.id.tv_activities_publish_date_start);
        tvPublishDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(TYPE_START);
            }
        });

        tvPublishTimeStart = findViewById(R.id.tv_activities_publish_time_start);
        tvPublishTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(TYPE_START);
            }
        });

        tvPublishDateEnd = findViewById(R.id.tv_activities_publish_date_end);
        tvPublishDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(TYPE_END);
            }
        });

        tvPublishTimeEnd = findViewById(R.id.tv_activities_publish_time_end);
        tvPublishTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(TYPE_END);
            }
        });

        etActivitiesPublishName = findViewById(R.id.et_activities_publish_name);

        tvActivitiesPublish = findViewById(R.id.tv_activities_publish);
        tvActivitiesPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePublishBtnClick();
            }
        });


        Calendar calendar = Calendar.getInstance();
        tvPublishDateStart.setText(DateTimeUtils.formatDateToString(calendar.getTime(), DATE_FORMAT));
        tvPublishTimeStart.setText(DateTimeUtils.formatDateToString(calendar.getTime(), TIME_FORMAT));
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        tvPublishDateEnd.setText(DateTimeUtils.formatDateToString(calendar.getTime(), DATE_FORMAT));
        tvPublishTimeEnd.setText(DateTimeUtils.formatDateToString(calendar.getTime(), TIME_FORMAT));

        publishContentAdapter = new PublishContentAdapter(new ArrayList<>());
        recyclerViewPublishContent.setAdapter(publishContentAdapter);

        initViewObservable();
        mViewModel.reqAppList(ProdConstants.ActivitiesModule.SPORTS_MEETING);
    }

    private void handlePublishBtnClick(){
        String startDateTimeString = tvPublishDateStart.getText().toString() + " " + tvPublishTimeStart.getText().toString();
        String endDateTimeString = tvPublishDateEnd.getText().toString() + " " + tvPublishTimeEnd.getText().toString();
        Date dateTimeStart = DateTimeUtils.formatDateStringToDate(startDateTimeString, DATE_TIME_FORMAT);
        Date dateTimeEnd = DateTimeUtils.formatDateStringToDate(endDateTimeString, DATE_TIME_FORMAT);
        if(dateTimeStart == null || dateTimeEnd == null){
            showToast("请先设置活动开始和结束时间！");
            return;
        }
        if(dateTimeEnd.getTime() < dateTimeStart.getTime()){
            showToast("请设置正确的活动结束时间！");
            return;
        }

        String publishName = etActivitiesPublishName.getText().toString();
        if(publishName.isEmpty()){
            showToast("请输入活动发起方！");
            return;
        }

        List<AppInfoResp> selectedAppInfoList = publishContentAdapter.getSelectedAppInfoList();
        if(selectedAppInfoList.size() == 0){
            showToast("请选择活动内容！");
            return;
        }

        List<ActivityAppMo> activityAppReqList = new ArrayList<>();
        for(int i=0; i<selectedAppInfoList.size(); i++){
            AppInfoResp appInfoResp = selectedAppInfoList.get(i);
            activityAppReqList.add(new ActivityAppMo(appInfoResp.getProductTitle(), appInfoResp.getSoftwareCode()));
        }

        ActivityAddReq activityAddReq = new ActivityAddReq(publishName, 1, startDateTimeString, endDateTimeString, activityAppReqList);

        mViewModel.reqActivityAdd(activityAddReq);
    }

    private void initViewObservable() {
        mViewModel.mldSportsAppInfoLoadState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Success == loadState){
                    publishContentAdapter.setDataList(mViewModel.sportsAppInfoList);
                }
            }
        });

        mViewModel.mldActivityAddLoadState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Success == loadState){
                    finish();
                }
            }
        });
    }

    private void showDatePickerDialog(final int type){
        Calendar calendar = Calendar.getInstance();

        String dateStartString = (type == TYPE_START) ? tvPublishDateStart.getText().toString() : tvPublishDateEnd.getText().toString();
        Date dateStart = DateTimeUtils.formatDateStringToDate(dateStartString, DATE_FORMAT);
        if(dateStart != null){
            calendar.setTime(dateStart);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, android.R.style.Theme_Material_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DATE, dayOfMonth);
                if(type == TYPE_START){
                    tvPublishDateStart.setText(DateTimeUtils.formatDateToString(calendar.getTime(), DATE_FORMAT));
                }else{
                    tvPublishDateEnd.setText(DateTimeUtils.formatDateToString(calendar.getTime(), DATE_FORMAT));
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),  calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        datePickerDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
        datePickerDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    private void showTimePickerDialog(final int type){
        Calendar calendar = Calendar.getInstance();

        String dateStartString = (type == TYPE_START) ? tvPublishTimeStart.getText().toString() : tvPublishTimeEnd.getText().toString();
        Date dateStart = DateTimeUtils.formatDateStringToDate(dateStartString, TIME_FORMAT);
        if(dateStart != null){
            calendar.setTime(dateStart);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Material_Light_Dialog,  new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                if(type == TYPE_START){
                    tvPublishTimeStart.setText(DateTimeUtils.formatDateToString(calendar.getTime(), TIME_FORMAT));
                }else{
                    tvPublishTimeEnd.setText(DateTimeUtils.formatDateToString(calendar.getTime(), TIME_FORMAT));
                }

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true); //true为24小时制
        timePickerDialog.show();
        timePickerDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
        timePickerDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

}