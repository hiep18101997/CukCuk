package com.misa.cukcuklite.screen.reportcurrent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ReportCurrent;
import com.misa.cukcuklite.screen.report.ReportFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * - Mục đích Class :Màn hình Báo cáo gần đây
 * - @created_by Hoàng Hiệp on 4/9/2019
 */
public class ReportCurrentFragment extends Fragment implements IReportCurrentContract.IView, ReportCurrentAdapter.OnCLickReport {
    private static final String TAG = ReportFragment.class.getName();
    private IReportCurrentContract.IPresenter mPresenter;
    private ReportCurrentAdapter mAdapter;
    private OnClickCurrentReport mOnClickCurrentReport;

    public ReportCurrentFragment() {
    }

    @SuppressLint("ValidFragment")
    public ReportCurrentFragment(OnClickCurrentReport onClickCurrentReport) {
        mOnClickCurrentReport = onClickCurrentReport;
    }

    public static ReportCurrentFragment newInstance(OnClickCurrentReport onClickCurrentReport) {
        return new ReportCurrentFragment(onClickCurrentReport);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_current, container, false);
        initView(v);
        return v;
    }

    /**
     * Mục đích method: Khai báo ánh xạ view
     *
     * @param v View
     * @created_by Hoàng Hiệp on 4/9/2019
     */
    private void initView(View v) {
        mPresenter = new ReportCurrentPresenter(this, getContext());
        mPresenter.getListReportCurrent();
        mAdapter = new ReportCurrentAdapter(getContext(), this);
        RecyclerView rvReport = v.findViewById(R.id.rvReport);
        rvReport.setAdapter(mAdapter);
        rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onLoadReportCurrentDone(List<ReportCurrent> reportCurrents) {
        mAdapter.setData(reportCurrents);
    }

    @Override
    public void onClick(ReportCurrent reportCurrent) {
        mOnClickCurrentReport.onClick(reportCurrent);
    }

    public interface OnClickCurrentReport {
        void onClick(ReportCurrent reportCurrent);
    }
}
