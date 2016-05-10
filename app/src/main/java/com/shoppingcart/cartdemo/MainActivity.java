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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.shoppingcart.cartdemo.Adapter.CartAdapter;
import com.shoppingcart.cartdemo.Adapter.CommonAdapter;
import com.shoppingcart.cartdemo.Adapter.ViewHolder;
import com.shoppingcart.cartdemo.Bean.Goods;
import com.shoppingcart.cartdemo.Utils.GoodsAnimUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvNum;
    private int num = 0;
    private List<Goods> goodsList;
    private Goods goods;
    private CartAdapter adapter;
    private ImageView iv;
    private SweetSheet mSweetSheet;
    private RelativeLayout rl;
    private ListView goodsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        goodsListView = (ListView) findViewById(R.id.lv_goods);

        rl = (RelativeLayout)findViewById(R.id.rl);

        iv = (ImageView) findViewById(R.id.m_list_car);

        tvNum = (TextView) findViewById(R.id.m_list_num);

        setupRecyclerView();

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSweetSheet.isShow()) {
                    mSweetSheet.dismiss();
                }
                mSweetSheet.toggle();
            }
        });
        final List<Goods> goodsList= new ArrayList<>();
        Goods goods = new Goods();
        for(int i = 0;i<50;i++){
            goods.price = new Random().nextInt(1000+i)+i;
            goods.id = i;
            goodsList.add(goods);
        }

        goodsListView.setAdapter(new CommonAdapter<Goods>(MainActivity.this,goodsList,R.layout.item_cart) {
            @Override
            public void convert(ViewHolder holder, Goods item) {
                holder.setText(R.id.tv_price, item.price + "");
                final Button buy = holder.getView(R.id.btn_addcart);
                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsAnimUtil.setAnim(MainActivity.this,buy,iv);
                        GoodsAnimUtil.setOnEndAnimListener(new EndAnim());
                    }
                });
            }
        });

    }

    private void setupRecyclerView() {

        final ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.mipmap.ic_launcher;
        menuEntity1.titleColor = 0xff000000;
        menuEntity1.title = "购物车";
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.iconId = R.mipmap.ic_launcher;
        menuEntity.titleColor = 0xffb3b3b3;
        menuEntity.title = "货品";
        list.add(menuEntity1);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(rl);

        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                //即时改变当前项的颜色
                list.get(position).titleColor = 0xff5823ff;
                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();

                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                Toast.makeText(MainActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                return false;
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

    class EndAnim implements GoodsAnimUtil.OnEndAnimListener {

        @Override
        public void onEndAnim() {
            num++;
            setNumAnim(tvNum);
            tvNum.setText(num + "");
        }
    }
}
