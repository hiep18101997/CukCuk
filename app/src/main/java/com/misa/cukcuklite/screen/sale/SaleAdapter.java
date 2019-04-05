package com.misa.cukcuklite.screen.sale;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.DishOrder;
import com.misa.cukcuklite.data.db.model.Order;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    private Context mContext;
    private List<Order> mOrders;
    private LayoutInflater layoutInflater;
    private OnClickItem mOnClickItem;

    public SaleAdapter(Context context, List<Order> orders, OnClickItem onClickItem) {
        mContext = context;
        this.mOrders = orders;
        layoutInflater = LayoutInflater.from(context);
        mOnClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_sale, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            List<DishOrder> dishOrders = mOrders.get(position).getListDish();
            SpannableStringBuilder builder = new SpannableStringBuilder();
            for (DishOrder dishOrder : dishOrders) {
                if (dishOrder.getQuantity() != 0) {
                    Dish dish = dishOrder.getDish();
                    Integer count = dishOrder.getQuantity();
                    String s = dish.getName() + " (" + count.toString() + "), ";
                    SpannableString span = new SpannableString(s);
                    span.setSpan(new RelativeSizeSpan(0.8f),
                            dish.getName().length() + 1,
                            s.length() - 2, 0);
                    span.setSpan(new ForegroundColorSpan(Color.parseColor("#3A8FC7")),
                            dish.getName().length() + 1,
                            s.length() - 2, 0);
                    builder.append(span);
                } else {

                }
            }
            holder.tvContent.setText(builder.delete(builder.length()-2,builder.length()-1));
            holder.tvPerson.setText(String.valueOf(mOrders.get(position).getNumberPerson()));
            holder.tvAmount.setText(String.valueOf(getAmount(dishOrders)));
            holder.tvTable.setText(String.valueOf(mOrders.get(position).getNumberTable()));
            Drawable drawableBg = mContext.getResources().getDrawable(R.drawable.bg_table);
            drawableBg.setColorFilter(-14235942, PorterDuff.Mode.SRC);
            holder.imgBackground.setImageDrawable(drawableBg);
            holder.rlContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickItem.onClickItem(mOrders.get(position));
                }
            });
            holder.lnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickItem.onClickCancel(mOrders.get(position));
                }
            });
            holder.lnTakeMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickItem.onClickTakeMoney(mOrders.get(position));
                }
            });
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private long getAmount(List<DishOrder> dishOrders) {
        long amount = 0;
        for (DishOrder entry : dishOrders) {
            amount += entry.getDish().getCost() * entry.getQuantity();
        }
        return amount;
    }

    @Override
    public int getItemCount() {
        return mOrders != null ? mOrders.size() : 0;
    }

    public void addData(List<Order> orders) {
        if (orders == null) {
            return;
        }
        mOrders.clear();
        mOrders.addAll(orders);
        notifyDataSetChanged();
    }

    interface OnClickItem {
        void onClickItem(Order order);

        void onClickCancel(Order order);

        void onClickTakeMoney(Order order);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlContent;
        public LinearLayout lnCancel, lnTakeMoney;
        public ImageView imgBackground, imgGotoDetail;
        public TextView tvTable, tvPerson, tvAmount, tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackground = itemView.findViewById(R.id.imgBackground);
            imgGotoDetail = itemView.findViewById(R.id.imgGotoDetail);
            lnCancel = itemView.findViewById(R.id.lnCancel);
            lnTakeMoney = itemView.findViewById(R.id.lnTakeMoney);
            tvTable = itemView.findViewById(R.id.tvTable);
            tvPerson = itemView.findViewById(R.id.tvPerson);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvContent = itemView.findViewById(R.id.tvContent);
            rlContent = itemView.findViewById(R.id.rlContent);
        }
    }
}
