package com.misa.cukcuklite.screen.reportcurrent;

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
import com.misa.cukcuklite.data.model.ReportCurrent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * - Mục đích Class :Adapter của mà Báo cáo gần đây
 * - @created_by Hoàng Hiệp on 4/9/2019
 */
public class ReportCurrentAdapter extends RecyclerView.Adapter<ReportCurrentAdapter.ViewHolder> {
    private Context mContext;
    private List<ReportCurrent> mReportCurrents;
    private LayoutInflater mLayoutInflater;

    public ReportCurrentAdapter(Context context) {
        mContext = context;
        mReportCurrents = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportCurrent reportCurrent = mReportCurrents.get(position);
        holder.tvTitle.setText(reportCurrent.getTitleReportDetail());
        holder.tvAmount.setText(String.valueOf(reportCurrent.getAmount()));
        Drawable drawableBg = mContext.getResources().getDrawable(R.drawable.bg_circle);
        switch (reportCurrent.getParamType()) {
            case TODAY:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_6), PorterDuff.Mode.SRC);
                holder.ivIcon.setImageResource(R.drawable.ic_calendar_today);
                break;
            case THIS_WEEK:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_2), PorterDuff.Mode.SRC);
                holder.ivIcon.setImageResource(R.drawable.ic_calendar_week);
                break;
            case THIS_YEAR:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_5), PorterDuff.Mode.SRC);
                holder.ivIcon.setImageResource(R.drawable.ic_calendar_year);
                break;
            case YESTERDAY:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_8), PorterDuff.Mode.SRC);
                holder.ivIcon.setImageResource(R.drawable.ic_calendar_yesterday);
                break;
            case THIS_MONTH:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_3), PorterDuff.Mode.SRC);
                holder.ivIcon.setImageResource(R.drawable.ic_calendar_month);
                break;
        }
        holder.ivBackgroundColor.setImageDrawable(drawableBg);
        holder.lnContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mReportCurrents != null ? mReportCurrents.size() : 0;
    }

    public void setData(List<ReportCurrent> reportCurrents) {
        if (reportCurrents == null) {
            return;
        }
        mReportCurrents.clear();
        mReportCurrents.addAll(reportCurrents);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnContent;
        private ImageView ivBackgroundColor, ivIcon;
        private TextView tvTitle, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnContent = itemView.findViewById(R.id.lnContent);
            ivBackgroundColor = itemView.findViewById(R.id.ivBackgroundColor);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
