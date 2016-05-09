package com.shoppingcart.cartdemo.View;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by lun on 16-5-9.
 * 二次赛贝尔曲线实现抛物线效果
 */
public class CartEvaluator implements TypeEvaluator<PointF> {

    public PointF midPoint;

    public CartEvaluator(PointF midPoint){
        this.midPoint = midPoint;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

        float t = 1.0f - fraction;
        PointF point = new PointF();
        point.x = t*t*startValue.x+2*fraction*t*midPoint.x+fraction*fraction*endValue.x;
        point.y = t*t*startValue.y+2*fraction*t*midPoint.y+fraction*fraction*endValue.y;

        return point;
    }
}