package com.misa.cukcuklite.screen.reportdetail;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ReportDetail;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportDetailAdapter extends RecyclerView.Adapter<ReportDetailAdapter.ViewHolder> {
    private Context mContext;
    private List<ReportDetail> mReportDetails;
    private LayoutInflater mInflater;
    private int[] colorBg;

    public ReportDetailAdapter(Context context, List<ReportDetail> reportDetails) {
        mContext = context;
        mReportDetails = reportDetails;
        mInflater = LayoutInflater.from(context);
        colorBg = context.getResources().getIntArray(R.array.arr_colors);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_report_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportDetail reportDetail = mReportDetails.get(position);
        holder.tvAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(reportDetail.getAmount()));
        holder.tvInventoryItemName.setText(reportDetail.getName());
        holder.tvNumber.setText(String.valueOf(position+1));
        holder.tvQuantity.setText(String.valueOf(reportDetail.getQuantity()+" "+reportDetail.getUnit()));
        Drawable drawableBg = mContext.getResources().getDrawable(R.drawable.bg_table);
        drawableBg.setColorFilter(colorBg[position % colorBg.length], PorterDuff.Mode.SRC);
        holder.ivBackgroundColor.setImageDrawable(drawableBg);
    }

    @Override
    public int getItemCount() {
        return mReportDetails != null ? mReportDetails.size() : 0;
    }

    public void setData(List<ReportDetail> reportDetails) {
        if (reportDetails == null) {
            return;
        }
        mReportDetails.clear();
        mReportDetails.addAll(reportDetails);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBackgroundColor;
        private TextView tvNumber, tvInventoryItemName, tvQuantity, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackgroundColor = itemView.findViewById(R.id.ivBackgroundColor);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvInventoryItemName = itemView.findViewById(R.id.tvInventoryItemName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
