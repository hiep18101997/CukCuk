package com.misa.cukcuklite.screen.reporttotal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ParamReport;
import com.misa.cukcuklite.screen.report.ReportFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * - Mục đích Class :Màn hình Báo cáo gần đây
 * - @created_by Hoàng Hiệp on 4/9/2019
 */
public class ReportTotalFragment extends Fragment implements IReportTotalContract.IView {
    private static final String TAG = ReportFragment.class.getName();
    public static final String EXTRA_PARAM_REPORT = "com.misa.cukcuklite.extra.param.report";
    private IReportTotalContract.IPresenter mPresenter;
    private ReportTotalAdapter mAdapter;

    public static ReportTotalFragment newInstance() {
        return new ReportTotalFragment();
    }

    public static ReportTotalFragment newInstance(ParamReport paramReport) {
        ReportTotalFragment reportTotalFragment = new ReportTotalFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PARAM_REPORT,paramReport);
        reportTotalFragment.setArguments(args);
        return reportTotalFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_total, container, false);
        Bundle args = getArguments();
        ParamReport paramReport= (ParamReport) args.getSerializable(EXTRA_PARAM_REPORT);
        loadData(paramReport);
        initView(v);
        return v;
    }

    private void loadData(ParamReport paramReport) {
        switch (paramReport.getParamType()){
            case THIS_WEEK:
            case LAST_WEEK:
                break;
            case THIS_MONTH:
            case LAST_MONTH:
                break;
            case LAST_YEAR:
            case THIS_YEAR:
                break;
        }
    }

    /**
      * Mục đích method: Khai báo ánh xạ view
      * @param v View
      * @created_by Hoàng Hiệp on 4/9/2019
      */
    private void initView(View v) {
        mPresenter=new ReportTotalPresenter(this,getContext());
    }


}
