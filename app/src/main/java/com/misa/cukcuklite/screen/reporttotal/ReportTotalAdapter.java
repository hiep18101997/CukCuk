package com.misa.cukcuklite.screen.reporttotal;

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
public class ReportTotalAdapter extends RecyclerView.Adapter<ReportTotalAdapter.ViewHolder> {
    private Context mContext;
    private List<ReportCurrent> mReportCurrents;
    private LayoutInflater mLayoutInflater;

    public ReportTotalAdapter(Context context) {
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
