package com.shoppingcart.cartdemo.Utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shoppingcart.cartdemo.R;
import com.shoppingcart.cartdemo.View.CartEvaluator;

/**
 * Created by lun on 16-5-9.
 */
public class GoodsAnimUtil {
    private static Animation animation;

    /**
     * 动画层
     */
    private static ViewGroup anim_mask_layout;
    private static Activity mActivity;
    private static View mBuy;
    private static View mImgcar;
    private static OnEndAnimListener onEndAnimListener;
    private static ImageView bugImg;

    /**
     * 动画结束之后的接口
     */
    public interface OnEndAnimListener{
        void onEndAnim();
    }
    public static void setOnEndAnimListener (OnEndAnimListener listener){
        onEndAnimListener = listener;
    }

    public static void setAnim(Activity activity ,View imphoto,View imgcar){
        mBuy = imphoto;
        mActivity = activity;
        mImgcar = imgcar;
        //存储购买按钮在屏幕的坐标
        int[] start_location = new int[2];
        imphoto.getLocationInWindow(start_location);
        int[] start_location1 = new int[]{start_location[0],start_location[1]};
        bugImg = new ImageView(mActivity);
        bugImg.setImageResource(R.mipmap.aii);
        //开始执行动画
        startAnim(bugImg, start_location1);
    }

    /**
     * 开始动画
     */
    private static void startAnim(final View v,int[] start_location ){
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层

        int[] end_location = new int[2];//动画结束时的位置
        mImgcar.getLocationInWindow(end_location);

        PointF startPointF = new PointF();
        startPointF.x = start_location[0];
        startPointF.y = start_location[1];
        PointF endPointF = new PointF();
        endPointF.x = end_location[0]+mImgcar.getWidth()/2;
        endPointF.y = end_location[1]+mImgcar.getHeight()/2;

        PointF midPointF = new PointF();
        midPointF.x = endPointF.x;
        midPointF.y = startPointF.y-200;

        ValueAnimator animator = getCartValueAnimator(v, startPointF, midPointF, endPointF);
        animator.setDuration(800);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束后删除红点
                anim_mask_layout.removeAllViews();
                onEndAnimListener.onEndAnim();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private static ValueAnimator getCartValueAnimator(View targe,PointF startP,PointF midP,PointF endP){
        //初始化一个贝塞尔计算器
        CartEvaluator evaluator = new CartEvaluator(midP);

        ValueAnimator animator = ValueAnimator.ofObject(evaluator,startP,endP);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new CartListener(targe));
        animator.setTarget(targe);
        return animator;
    }



    public static class CartListener implements ValueAnimator.AnimatorUpdateListener{

        private View target;

        public CartListener(View target){
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //获取到贝塞尔曲线计算处理的x y 值 ， 赋值给view
            PointF pointF = (PointF)animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
        }
    }

    /**
     * 创建动画层
     */
    private static ViewGroup createAnimLayout(){
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

}
