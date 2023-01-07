package com.zee.club.user.ui.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanzh on 2018/3/6.
 * 当前类只能用于界面只有一个下拉选择框
 * 使用时需要判断选择框是否是闭合状态
 * 需要传入下拉列表数据，主线程，显示控件
 * popup窗口
 * private PopupWindow typeSelectPopup;
 * 使用isShowing()检查popup窗口是否在显示状态
 * if (typeSelectPopup != null && !typeSelectPopup.isShowing()) {
 * typeSelectPopup.showAsDropDown(mSelectTv, 0, 10);
 * }
 */

public class CommonPopup {
    // popup窗口
    private PopupWindow typeSelectPopup;
    // popup窗口里的ListView
    private ListView mTypeLv;
    // 下拉列表数据
    private List<PopwindowsData> selectList;
    //下拉列表适配器
    private ArrayAdapter selecterAdapter;
    private Boolean showChoose = false;
    private Activity mContext;
    //TextView选择框
    private View mSelectTv;
    // 输入框
    private View mEditTv;
    //下拉列表的背景
    private int itemBackground;
    //设置点击事件跳转的页面
    private List<String> contextList;
    //子列表宽度
    private int width;
    //子列表高度
    private int height;
    //跳转页面是否被销毁
    private Boolean finish = true;
    private OnItemClickListener mItemClickListener;

    /**
     * 数据类型下拉选择框
     */
    public void initSelectPopup() {
        mTypeLv = new ListView(mContext);
        // 设置适配器
        List<String> itemName = new ArrayList<>();
        for (PopwindowsData item : selectList) {
            itemName.add(item.getItemName());
        }

        selecterAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, itemName);
        mTypeLv.setAdapter(selecterAdapter);
        // 设置ListView点击事件监听
        mTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里获取item数据
                String value = selectList.get(position).getItemName();
                if (showChoose) {
                    // 把选择的数据展示对应的TextView上
                    TextView select = (TextView) mSelectTv;
                    select.setText(value);
                    if (mEditTv != null) {
                        String itemShowId = selectList.get(position).getItemShowId();
                        TextView edit = (TextView) mEditTv;
                        edit.setTag(false);
                        edit.setText(itemShowId);
                    }
                }
                if (mItemClickListener != null) {
                    mItemClickListener.OnItemClick(selectList.get(position));
                }

                if (null != contextList) {
                    try {
                        Intent Intent = new Intent(mContext, Class.forName("文档路径" + contextList.get(position)));
                        Bundle Bundle = new Bundle();
                        Bundle.putString("oldText", "");
                        Intent.putExtras(Bundle);
                        mContext.startActivity(Intent);
                    } catch (ClassNotFoundException e) {
                        Log.w("CommonPopup Exception", "跳转页面未找到");
                    }
                    if (finish) {
                        mContext.finish();
                    }
                }
                // 选择完后关闭popup窗口
                typeSelectPopup.dismiss();

            }
        });

        if (selectList.size() > 10) {
            typeSelectPopup = new PopupWindow(mTypeLv, 500, 1000, false);//在这里可以设置弹出来的界面宽和高

        } else {
            typeSelectPopup = new PopupWindow(mTypeLv, 500, ActionBar.LayoutParams.WRAP_CONTENT, false);//在这里可以设置弹出来的界面宽和高

        }
        // 取得popup窗口的背景图片
        Drawable drawable = ContextCompat.getDrawable(mContext, itemBackground);
        typeSelectPopup.setBackgroundDrawable(drawable);
        typeSelectPopup.setFocusable(false);
        typeSelectPopup.setOutsideTouchable(true);
        typeSelectPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
    }

    /**
     * 放入执行的主线程
     *
     * @param mContext
     */
    public void setmContext(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * 放入点击事件的控件，用于定位
     *
     * @param mSelectTv
     */
    public void setmSelectTv(View mSelectTv) {
        this.mSelectTv = mSelectTv;
    }

    public void setmEditTv(View mEditTv) {
        this.mEditTv = mEditTv;
    }

    /**
     * 是否将选中的子列表显示内容放入点击控件中
     *
     * @param showChoose
     */
    public void showChoose(Boolean showChoose) {
        this.showChoose = showChoose;
    }

    /**
     * 填充下拉列表的显示项，一个arraylist数组，数组有多少个String就显示多少个下拉
     *
     * @param selectList
     */
    public void setSelectList(List<PopwindowsData> selectList) {
        this.selectList = selectList;
    }

    /**
     * 设置下拉列表的背景图片（只能存放图片）
     *
     * @param itemBackground
     */
    public void setItemBackground(int itemBackground) {
        this.itemBackground = itemBackground;
    }

    /**
     * 显示下拉，点击子列表之后收缩
     */
    public void show() {
        initSelectPopup();
        if (typeSelectPopup != null && !typeSelectPopup.isShowing()) {
            typeSelectPopup.showAsDropDown(mEditTv == null ? mSelectTv : mEditTv, 0, 0);
        }
    }

    /**
     * 设置下拉列表跳转页面，和下拉列表显示list下标相对应
     *
     * @param contextList
     */
    public void setContextList(List<String> contextList) {
        this.contextList = contextList;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    /**
     * 设置子列表宽度
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 设置子列表高度
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 设置当前页面跳转之后是否被销毁，默认销毁
     *
     * @param finish
     */
    public void setFinish(Boolean finish) {
        this.finish = finish;
    }


    public interface OnItemClickListener {
        //第一个参数是适配器的item，第二个参数是位置
        void OnItemClick(PopwindowsData value);
    }

    public static class PopwindowsData {
        String itemName;
        String itemId;
        String itemShowId;

        public String getItemShowId() {
            return itemShowId;
        }

        public void setItemShowId(String itemShowId) {
            this.itemShowId = itemShowId;
        }

        public PopwindowsData() { }
        public PopwindowsData(String itemName, String itemId) {
            this.itemName = itemName;
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }
    }
}