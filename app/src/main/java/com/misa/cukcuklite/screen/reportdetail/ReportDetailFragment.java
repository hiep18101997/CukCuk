package com.misa.cukcuklite.screen.reportdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ParamReport;
import com.misa.cukcuklite.data.model.ReportDetail;
import com.misa.cukcuklite.screen.reporttotal.ReportTotalFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.utils.AppConstant.EXTRA_DATES;

public class ReportDetailFragment extends Fragment implements IReportDetailContract.IView {
    private static final String TAG = ReportDetailFragment.class.getName();
    private IReportDetailContract.IPresenter mPresenter;
    private Date[] dates;
    private ReportDetailAdapter mAdapter;

    public static ReportDetailFragment newInstance(Date[] dates) {
        ReportDetailFragment reportTotalFragment = new ReportDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATES, dates);
        reportTotalFragment.setArguments(args);
        return reportTotalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_report_detail,container,false);
        Bundle args = getArguments();
        dates= (Date[]) args.getSerializable(EXTRA_DATES);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        mPresenter = new ReportDetailPresenter(getContext(),this);
        mPresenter.loadData(dates);
        mAdapter=new ReportDetailAdapter(getContext(),new ArrayList<ReportDetail>());
        RecyclerView rvReport=view.findViewById(R.id.rvReport);
        rvReport.setAdapter(mAdapter);
        rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onLoadDataDone(List<ReportDetail> reportDetails) {
        mAdapter.setData(reportDetails);
    }
}
