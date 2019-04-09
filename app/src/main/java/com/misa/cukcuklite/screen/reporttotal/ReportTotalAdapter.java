package com.misa.cukcuklite.screen.reporttotal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ReportTotal;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * - Mục đích Class :Adapter của mà Báo cáo gần đây
 * - @created_by Hoàng Hiệp on 4/9/2019
 */
public class ReportTotalAdapter extends RecyclerView.Adapter<ReportTotalAdapter.ViewHolder> {
    private Context mContext;
    private List<ReportTotal> mReportTotals;
    private LayoutInflater mLayoutInflater;

    public ReportTotalAdapter(Context context, List<ReportTotal> reportTotals) {
        mContext = context;
        mReportTotals = reportTotals;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_report_total, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportTotal reportTotal = mReportTotals.get(position);
        holder.tvTitle.setText(reportTotal.getTitleReportDetail());
        holder.tvAmount.setText(String.valueOf(reportTotal.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mReportTotals != null ? mReportTotals.size() : 0;
    }

    public void setData(List<ReportTotal> reportTotals) {
        if (reportTotals == null) {
            return;
        }
        mReportTotals.clear();
        mReportTotals.addAll(reportTotals);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnContent;
        private TextView tvTitle, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnContent = itemView.findViewById(R.id.lnContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
