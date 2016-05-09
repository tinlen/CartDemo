package com.shoppingcart.cartdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shoppingcart.cartdemo.Adapter.CartAdapter;
import com.shoppingcart.cartdemo.Bean.Goods;
import com.shoppingcart.cartdemo.Utils.GoodsAnimUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GoodsAnimUtil.OnEndAnimListener {

    private TextView tvNum;
    private int num = 0;
    private ListView lv_cartlist;
    private List<Goods> goodsList;
    private Goods goods;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoodsAnimUtil.setOnEndAnimListener(this);

        initData();

        final ImageView iv = (ImageView) findViewById(R.id.m_list_car);

        tvNum = (TextView) findViewById(R.id.m_list_num);

        final Button button = (Button) findViewById(R.id.btn_buy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsAnimUtil.setAnim(MainActivity.this,button,iv);
            }
        });

    }


    private void initData() {
        goodsList = new ArrayList<>();
        for (int i = 0;i<50;i++){
            goods = new Goods();
            goods.id = 10000+i;
            goods.price = new Random().nextInt(1000)+i;

            goodsList.add(goods);
        }
    }

    @Override
    public void onEndAnim() {
        num++;
        setNumAnim(tvNum);
        tvNum.setText(num + "");
    }

    private void setNumAnim(View view){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f,0f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f,1.5f,1f,1.5f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        set.setInterpolator(new LinearInterpolator());
        set.setDuration(500);
        set.setFillBefore(true);
        view.setAnimation(set);
        set.startNow();

    }
}
