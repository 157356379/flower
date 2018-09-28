package com.example.a15735.flower;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

public class LodingView extends RelativeLayout {
    private CycleView mleft, mcenter, mright;
    private int mTranslation = 20;
    private long time = 350;
    private int color;

    public LodingView(Context context) {
        this(context, null);
    }

    public LodingView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public LodingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mleft = getCycleView(context);
        mleft.exchange(Color.RED);
        mcenter = getCycleView(context);
        mcenter.exchange(Color.GREEN);
        mright = getCycleView(context);
        mright.exchange(Color.YELLOW);
        addView(mleft);
        addView(mcenter);
        addView(mright);
        post(new Runnable() {
            @Override
            public void run() {
                outAnimation();
            }
        });
    }

    //执行动画
    private void outAnimation() {
        //左边
        ObjectAnimator left = ObjectAnimator.ofFloat(mleft, "translationX", 0, -dip2x(mTranslation));

        //右边
        ObjectAnimator right = ObjectAnimator.ofFloat(mright, "translationX", 0, dip2x(mTranslation));
        //中间
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(time);
        animatorSet.playTogether(left, right);
        animatorSet.setInterpolator(new  DecelerateInterpolator(2f));
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int leftcolor=mleft.getcolor();
                int midcolor=mcenter.getcolor();
                int mrightcolor=mright.getcolor();
                mleft.exchange(leftcolor);
                mcenter.exchange(midcolor);
                mright.exchange(mrightcolor);
                innerAnimation();
            }
        });
        animatorSet.start();
    }

    private void innerAnimation() {
        //左边
        ObjectAnimator left = ObjectAnimator.ofFloat(mleft, "translationX", -dip2x(mTranslation), 0);

        //右边
        ObjectAnimator right = ObjectAnimator.ofFloat(mright, "translationX", dip2x(mTranslation), 0);
        //中间
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(time);
        animatorSet.playTogether(left, right);
        animatorSet.setInterpolator(new AccelerateInterpolator(2f));
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                outAnimation();
            }
        });
        animatorSet.start();
    }
    public CycleView getCycleView(Context context) {
        CycleView cycleView = new CycleView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dip2x(8), dip2x(8));
        layoutParams.addRule(CENTER_IN_PARENT);
        cycleView.setLayoutParams(layoutParams);
        return cycleView;
    }

    private int dip2x(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics());
    }

//    public void gone(View view) {
//        if (view == null) {
//            return;
//        }
//        ViewGroup viewGroup = (ViewGroup) view.getRootView();
//        viewGroup.removeView(view);
//
//    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE);
        mleft.clearAnimation();
        mright.clearAnimation();
        ViewGroup viewGroup = (ViewGroup)  getParent();
        if (viewGroup!=null){
            removeAllViews();
        }

    }
}
