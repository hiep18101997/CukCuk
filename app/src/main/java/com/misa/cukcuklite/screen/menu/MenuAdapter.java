package com.misa.cukcuklite.screen.menu;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.Dish;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.utils.AppConstant.IMAGE_ASSETS;

/**
 * ‐ Adapter RecyclerView cho màn hình danh sách món
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/23/2019
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private Context mContext;
    private List<Dish> mDishes;
    private OnItemClick mOnItemClick;
    private LayoutInflater mInflater;

    public MenuAdapter(Context context, List<Dish> dishes, OnItemClick onItemClick) {
        mContext = context;
        mDishes = dishes;
        mOnItemClick = onItemClick;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_menu, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(mContext, mDishes.get(i), viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return mDishes != null ? mDishes.size() : 0;
    }

    Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = mContext.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(IMAGE_ASSETS + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

    public void addData(List<Dish> dishes) {
        if (dishes == null) {
            return;
        }
        mDishes.clear();
        mDishes.addAll(dishes);
        notifyDataSetChanged();
    }

    interface OnItemClick {
        void onClick(Dish dish);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mLayout;
        private ImageView ivProductIcon;
        private TextView mName;
        private TextView mCost;
        private TextView mState;
        private Dish mDish;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.rlIconContainer);
            ivProductIcon = itemView.findViewById(R.id.ivProductIcon);
            mName = itemView.findViewById(R.id.tvName);
            mCost = itemView.findViewById(R.id.tvCost);
            mState = itemView.findViewById(R.id.tvState);
        }

        void bindView(Context context, final Dish dish, View itemView) {
            mDish = dish;
            mName.setText(dish.getName());
            mCost.setText("Giá bán: " + dish.getCost());
            if (!dish.isSell()) {
                mState.setVisibility(View.VISIBLE);
            } else {
                mState.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClick.onClick(mDish);
                }
            });
            Drawable drawableBg = context.getResources().getDrawable(R.drawable.bg_circle);
            drawableBg.setColorFilter(dish.getColor(), PorterDuff.Mode.SRC);
            mLayout.setBackground(drawableBg);
            Glide.with(context).load(getBitmapFromAssets(dish.getIcon())).apply(new RequestOptions().centerCrop()).into(ivProductIcon);
        }
    }
}
