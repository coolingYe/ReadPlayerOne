package com.zee.club.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class CardRecyclerView extends RecyclerView {
    private float initialX = 0;
    private float initialY = 0;
    private ViewPager2 parentViewPager = null;
    private NestedScrollView parentScrollView = null;
    private boolean keepFlag = false;

    public CardRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CardRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setWillNotDraw(true);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setClipChildren(false);
        setClipToPadding(false);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)layoutManager;
            if(linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL){
                return super.dispatchTouchEvent(ev);
            }
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = ev.getX();
                initialY = ev.getY();
                keepFlag = false;
                getParentView();
                requestParentDisallow(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float dealtX = ev.getX() - initialX;
                float dealtY = ev.getY() - initialY;

                if (Math.abs(dealtX) >= Math.abs(dealtY)) {
                    if(!keepFlag) {
                        keepFlag = true;
                        if (canScrollHorizontally(-(int) dealtX)) {
                            requestParentDisallow(true);
                        } else {
                            requestParentDisallow(false);
                        }
                    }
                } else {
                    requestParentDisallow(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;

        }

        return super.dispatchTouchEvent(ev);
    }

    public void requestParentDisallow(boolean enable){
        if(parentViewPager != null) {
            parentViewPager.requestDisallowInterceptTouchEvent(enable);
        }

        if(parentScrollView != null) {
            parentScrollView.requestDisallowInterceptTouchEvent(enable);
        }
    }

    public void getParentView(){
        ViewParent parent = getParent();
        while (parent != null && !(parent instanceof ViewPager2)) {
            parent = parent.getParent();
        }
        if(parent != null) {
            parentViewPager = (ViewPager2)parent;
        }

        parent = getParent();
        while (parent != null && !(parent instanceof NestedScrollView)) {
            parent = parent.getParent();
        }
        if(parent != null) {
            parentScrollView = (NestedScrollView)parent;
        }
    }

}
