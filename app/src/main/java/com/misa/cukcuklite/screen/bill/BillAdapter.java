package com.misa.cukcuklite.screen.bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.DishOrder;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * - Mục đích Class :Adapter Recyvlerview màn hóa đơn
 * - @created_by Hoàng Hiệp on 4/5/2019
 */
public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<DishOrder> mOrders;

    public BillAdapter(Context context, List<DishOrder> orders) {
        mContext = context;
        mOrders = orders;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvInventoryItemName.setText(mOrders.get(position).getDish().getName());
            holder.tvQuantity.setText(String.valueOf(mOrders.get(position).getQuantity()));
            holder.tvUnitPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(mOrders.get(position).getDish().getCost()));
            holder.tvAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(mOrders.get(position).getDish().getCost() * mOrders.get(position).getQuantity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mOrders != null ? mOrders.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvInventoryItemName, tvQuantity, tvUnitPrice, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInventoryItemName = itemView.findViewById(R.id.tvInventoryItemName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
