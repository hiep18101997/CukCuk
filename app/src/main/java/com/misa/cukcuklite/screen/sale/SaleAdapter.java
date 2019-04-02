package com.misa.cukcuklite.screen.sale;

import android.content.Context;
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
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.PendingOrder;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    private Context mContext;
    private List<PendingOrder> orders;
    private LayoutInflater layoutInflater;
    private OnClickItem mOnClickItem;

    public SaleAdapter(Context context, List<PendingOrder> orders, OnClickItem onClickItem) {
        mContext = context;
        this.orders = orders;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<Dish, Integer> hashMap = orders.get(position).getListDish();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (Map.Entry<Dish, Integer> entry : hashMap.entrySet()) {
            Dish dish = entry.getKey();
            Integer count = entry.getValue();
            String s = dish.getName() + " (" + count.toString() + "), ";
            SpannableString span = new SpannableString(s);
            span.setSpan(new RelativeSizeSpan(0.8f),
                    dish.getName().length() + 1,
                    s.length() - 2, 0);
            span.setSpan(new ForegroundColorSpan(Color.parseColor("#3A8FC7")),
                    dish.getName().length() + 1,
                    s.length() - 2, 0);
            builder.append(span);
        }
        holder.tvContent.setText(builder);
        holder.tvPerson.setText(String.valueOf(orders.get(position).getNumberPerson()));
        holder.tvAmount.setText("150.000");
        holder.tvTable.setText(String.valueOf(orders.get(position).getNumberTable()));
        Drawable drawableBg = mContext.getResources().getDrawable(R.drawable.bg_table);
        drawableBg.setColorFilter(-14235942, PorterDuff.Mode.SRC);
        holder.imgBackground.setImageDrawable(drawableBg);
    }

    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    interface OnClickItem {
        void onClickItem(PendingOrder order);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
