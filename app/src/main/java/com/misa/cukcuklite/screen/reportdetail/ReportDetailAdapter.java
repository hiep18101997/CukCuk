package com.misa.cukcuklite.screen.reportdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ReportDetail;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportDetailAdapter extends RecyclerView.Adapter<ReportDetailAdapter.ViewHolder> {
    private Context mContext;
    private List<ReportDetail> mReportDetails;
    private LayoutInflater mInflater;

    public ReportDetailAdapter(Context context, List<ReportDetail> reportDetails) {
        mContext = context;
        mReportDetails = reportDetails;
        mInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_report_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
