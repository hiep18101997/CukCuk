package com.misa.cukcuklite.screen.addorder;

import static com.misa.cukcuklite.utils.AppConstant.IMAGE_ASSETS;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.Dish;
import com.misa.cukcuklite.data.model.DishOrder;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * - Mục đích Class : Adapter của RecyclerView đặt món - @created_by Hoàng Hiệp on 4/5/2019
 */
public class AddOrderAdapter extends RecyclerView.Adapter<AddOrderAdapter.ViewHolder> {

  private List<DishOrder> mList;
  private Context mContext;
  private OnClickItem mOnClickItem;
  private LayoutInflater mLayoutInflater;


  public AddOrderAdapter(Context context, List<DishOrder> list, OnClickItem onClickItem) {
    mList = list;
    mContext = context;
    mOnClickItem = onClickItem;
    mLayoutInflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = mLayoutInflater.inflate(R.layout.item_add_order, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    try {
      Dish dish = mList.get(position).getDish();
      final int quantity = mList.get(position).getQuantity();
      holder.tvName.setText(dish.getName());
      holder.tvPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(dish.getCost()));
      holder.tvQuantity.setText(String.valueOf(quantity));
      Drawable drawableBg = mContext.getResources().getDrawable(R.drawable.bg_table);
      drawableBg.setColorFilter(dish.getColor(), PorterDuff.Mode.SRC);
      holder.imgBg.setImageDrawable(drawableBg);
      StateListDrawable stateListDrawable = new StateListDrawable();
      stateListDrawable.addState(new int[]{android.R.attr.state_pressed},
          new ColorDrawable(mContext.getResources().getColor(R.color.color_primary_40)));
      stateListDrawable.addState(new int[]{android.R.attr.state_focused},
          new ColorDrawable(mContext.getResources().getColor(R.color.color_primary_40)));
      stateListDrawable.addState(new int[]{android.R.attr.state_selected},
          new ColorDrawable(mContext.getResources().getColor(R.color.color_primary_40)));
      Glide.with(mContext).load(getBitmapFromAssets(dish.getIcon()))
          .apply(new RequestOptions().centerCrop()).into(holder.imgIcon);
      if (quantity == 0) {
        holder.realIcon.setVisibility(View.VISIBLE);
        holder.lnQuantity.setVisibility(View.GONE);
        holder.imgSelected.setVisibility(View.GONE);
        stateListDrawable.addState(new int[]{},
            new ColorDrawable(mContext.getResources().getColor(R.color.white)));
        holder.rlItem.setBackground(stateListDrawable);
      } else {
        holder.realIcon.setVisibility(View.GONE);
        holder.lnQuantity.setVisibility(View.VISIBLE);
        holder.imgSelected.setVisibility(View.VISIBLE);
        stateListDrawable.addState(new int[]{},
            new ColorDrawable(mContext.getResources().getColor(R.color.color_gray)));
        holder.rlItem.setBackground(stateListDrawable);
      }
      holder.imgPlus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mList.get(position).setQuantity(quantity + 1);
          notifyItemChanged(position);
          mOnClickItem.onClick(mList);
        }
      });
      holder.imgMinus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mList.get(position).setQuantity(quantity - 1);
          notifyItemChanged(position);
          mOnClickItem.onClick(mList);
        }
      });
      holder.rlItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mList.get(position).setQuantity(quantity + 1);
          notifyItemChanged(position);
          mOnClickItem.onClick(mList);
        }
      });
      holder.imgSelected.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mList.get(position).setQuantity(0);
          notifyItemChanged(position);
          mOnClickItem.onClick(mList);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public int getItemCount() {
    return mList != null ? mList.size() : 0;
  }

  /**
   * Mục đích method:get bitmap từ tên
   *
   * @created_by Hoàng Hiệp on 4/5/2019
   */
  private Bitmap getBitmapFromAssets(String fileName) {
    AssetManager assetManager = mContext.getAssets();
    InputStream istr = null;
    try {
      istr = assetManager.open(IMAGE_ASSETS + fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return BitmapFactory.decodeStream(istr);
  }

  public void setData(List<DishOrder> list) {
    if (list == null) {
      return;
    }
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public List<DishOrder> getList() {
    return mList != null ? mList : new ArrayList<DishOrder>();
  }

  interface OnClickItem {

    void onClick(List<DishOrder> mList);
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private RelativeLayout realIcon, rlItem;
    private LinearLayout lnQuantity;
    private ImageView imgBg, imgIcon, imgSelected, imgMinus, imgPlus;
    private TextView tvName, tvPrice, tvQuantity;

    private ViewHolder(@NonNull View itemView) {
      super(itemView);
      realIcon = itemView.findViewById(R.id.realIcon);
      rlItem = itemView.findViewById(R.id.rlItem);
      lnQuantity = itemView.findViewById(R.id.lnQuantity);
      imgBg = itemView.findViewById(R.id.imgBg);
      imgIcon = itemView.findViewById(R.id.imgIcon);
      imgSelected = itemView.findViewById(R.id.imgSelected);
      imgMinus = itemView.findViewById(R.id.imgMinus);
      imgPlus = itemView.findViewById(R.id.imgPlus);
      tvName = itemView.findViewById(R.id.tvName);
      tvPrice = itemView.findViewById(R.id.tvPrice);
      tvQuantity = itemView.findViewById(R.id.tvQuantity);
    }
  }
}
