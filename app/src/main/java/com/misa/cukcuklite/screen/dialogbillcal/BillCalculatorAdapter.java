package com.misa.cukcuklite.screen.dialogbillcal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misa.cukcuklite.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * - Mục đích Class :Adapter của BillCal
 * - @created_by Hoàng Hiệp on 4/12/2019
 */
public class BillCalculatorAdapter extends RecyclerView.Adapter<BillCalculatorAdapter.ViewHolder> {
    private Context mContext;
    private List<Long> mSuggestMoneys;
    private LayoutInflater mLayoutInflater;
    private OnClickSuggestMoney mOnClickSuggestMoney;

    public BillCalculatorAdapter(Context context, List<Long> suggestMoneys, OnClickSuggestMoney onClickSuggestMoney) {
        mContext = context;
        mSuggestMoneys = suggestMoneys;
        mOnClickSuggestMoney = onClickSuggestMoney;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_suggest_money, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMoneySuggest.setText(NumberFormat.getNumberInstance(Locale.US).format(mSuggestMoneys.get(position)));
        holder.tvMoneySuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickSuggestMoney.onClick(mSuggestMoneys.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSuggestMoneys != null ? mSuggestMoneys.size() : 0;
    }

    public interface OnClickSuggestMoney {
        void onClick(long money);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMoneySuggest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMoneySuggest = itemView.findViewById(R.id.tvMoneySuggest);
        }
    }
}
