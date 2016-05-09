package com.shoppingcart.cartdemo.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.shoppingcart.cartdemo.Bean.Goods;
import com.shoppingcart.cartdemo.R;

import java.util.List;

/**
 * Created by lun on 16-5-9.
 */
public class CartAdapter extends CommonAdapter<Goods>{
    private SparseArray<Boolean> sparseArray = new SparseArray<>();
    private ViewHolder holder;

    public CartAdapter(Context context, List<Goods> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(final ViewHolder holder, final Goods item) {
        this.holder = holder;
        holder.setText(R.id.tv_price, item.price + "");

    }
}
