package com.misa.cukcuklite.screen.sale;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.PendingOrder;

import java.util.List;

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
        holder.tvPerson.setText(String.valueOf(orders.get(position).getmNumberPerson()));
        holder.tvAmount.setText("150.000");
        holder.tvTable.setText(String.valueOf(orders.get(position).getmNumberTable()));
        Drawable drawableBg = mContext.getResources().getDrawable(R.drawable.bg_circle);
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
        public TextView tvTable, tvPerson, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackground = itemView.findViewById(R.id.imgBackground);
            imgGotoDetail = itemView.findViewById(R.id.imgGotoDetail);
            lnCancel = itemView.findViewById(R.id.lnCancel);
            lnTakeMoney = itemView.findViewById(R.id.lnTakeMoney);
            tvTable = itemView.findViewById(R.id.tvTable);
            tvPerson = itemView.findViewById(R.id.tvPerson);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
