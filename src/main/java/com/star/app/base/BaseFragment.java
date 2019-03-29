package com.star.app.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.gjmetal.star.event.BusProvider;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: Guimingxing
 * Date: 2017/12/5  9:44
 * Description: This is a BaseFragment.
 */

public abstract class BaseFragment extends Fragment implements View.OnTouchListener{

    private Unbinder unbinder;
    protected View v;
    protected boolean mHasNewMsg=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(setRootView(), container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent则从parent删除，防止发生这个rootview已经有parent的错误。
        ViewGroup mViewGroup = (ViewGroup) v.getParent();
        if (mViewGroup != null) {
            mViewGroup.removeView(v);
        }
        unbinder = ButterKnife.bind(this, v);
        initView();
        return v;
    }

    protected abstract int setRootView();

    protected abstract void initView();

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getBus().register(this);//注册EventBus
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // 优化切换界面卡顿问题
        view.setOnTouchListener(this);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getBus().unregister(this);//解绑EventBus
    }

    @Override
    public void onDestroyView() {
        if (this.unbinder != null) {
            this.unbinder.unbind();
        }
        if(v!=null){
            ((ViewGroup) v.getParent()).removeView(v);
        }
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
