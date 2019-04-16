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
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ReportCurrent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * - Mục đích Class :Adapter của mà Báo cáo gần đây - @created_by Hoàng Hiệp on 4/9/2019
 */
public class ReportCurrentAdapter extends RecyclerView.Adapter<ReportCurrentAdapter.ViewHolder> {

  private Context mContext;
  private List<ReportCurrent> mReportCurrents;
  private LayoutInflater mLayoutInflater;
  private OnCLickReport mCLickReport;

  public ReportCurrentAdapter(Context context, OnCLickReport cLickReport) {
    mContext = context;
    mReportCurrents = new ArrayList<>();
    mLayoutInflater = LayoutInflater.from(context);
    mCLickReport = cLickReport;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mLayoutInflater.inflate(R.layout.item_report_current, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final ReportCurrent reportCurrent = mReportCurrents.get(position);
    holder.tvTitle.setText(reportCurrent.getTitleReportDetail());
    holder.tvAmount
        .setText(NumberFormat.getNumberInstance(Locale.US).format(reportCurrent.getAmount()));
    Drawable drawableBg = mContext.getResources().getDrawable(R.drawable.bg_circle);
    switch (reportCurrent.getParamType()) {
      case TODAY:
        drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_6),
            PorterDuff.Mode.SRC);
        Glide.with(mContext).load(R.drawable.ic_calendar_today).into(holder.ivIcon);
        break;
      case THIS_WEEK:
        drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_2),
            PorterDuff.Mode.SRC);
        Glide.with(mContext).load(R.drawable.ic_calendar_week).into(holder.ivIcon);
        break;
      case THIS_YEAR:
        drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_5),
            PorterDuff.Mode.SRC);
        Glide.with(mContext).load(R.drawable.ic_calendar_year).into(holder.ivIcon);
        break;
      case YESTERDAY:
        drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_8),
            PorterDuff.Mode.SRC);
        Glide.with(mContext).load(R.drawable.ic_calendar_yesterday).into(holder.ivIcon);
        break;
      case THIS_MONTH:
        drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_3),
            PorterDuff.Mode.SRC);
        Glide.with(mContext).load(R.drawable.ic_calendar_month).into(holder.ivIcon);
        break;
    }
    Glide.with(mContext).load(drawableBg).into(holder.ivBackgroundColor);

    holder.lnContent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (reportCurrent.getAmount() > 0) {
          mCLickReport.onClick(reportCurrent);
        }

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

  interface OnCLickReport {

    void onClick(ReportCurrent reportCurrent);
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
