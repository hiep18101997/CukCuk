package com.misa.cukcuklite.screen.report;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ParamReport;
import com.misa.cukcuklite.data.model.ReportCurrent;
import com.misa.cukcuklite.enums.ParamReportEnum;
import com.misa.cukcuklite.screen.dialogparamreport.ParamReportDialog;
import com.misa.cukcuklite.screen.reportcurrent.ReportCurrentFragment;
import com.misa.cukcuklite.screen.reporttotal.ReportTotalFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * - Mục đích Class : Màn hình báo cáo
 * - @created_by Hoàng Hiệp on 7/4/2019
 */
public class ReportFragment extends Fragment implements IReportContract.IView, View.OnClickListener, ReportCurrentFragment.OnClickCurrentReport {
    private static final String TAG = ReportFragment.class.getName();
    private IReportContract.IPresenter mPresenter;
    private TextView tvTimeValue;
    private List<ParamReport> mParamReports;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        view.findViewById(R.id.lnTime).setOnClickListener(this);
        initView(view);
        mParamReports=getListParam();
        loadFragment(ReportCurrentFragment.newInstance(this));

        return view;

    }

    private void initView(View view) {
        tvTimeValue = view.findViewById(R.id.tvTimeValue);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnTime:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ParamReportDialog dialog = new ParamReportDialog();
                dialog.setParamReports(mParamReports);
                dialog.setCallBack(new ParamReportDialog.ParamCallBack() {
                    @Override
                    public void onClick(ParamReport paramReport) {
                        tvTimeValue.setText(paramReport.getTitleReportDetail());
                        if (paramReport.getParamType() == ParamReportEnum.CURRENT) {
                            loadFragment(ReportCurrentFragment.newInstance(ReportFragment.this));
                        } else {
                            loadFragment(ReportTotalFragment.newInstance(paramReport));
                        }
                    }
                });
                dialog.show(fm, getString(R.string.icon_fragment));
                break;
        }
    }
    private List<ParamReport> getListParam() {
        List<ParamReport> paramReports = new ArrayList<>();
        paramReports.add(new ParamReport(ParamReportEnum.CURRENT));
        paramReports.add(new ParamReport(ParamReportEnum.THIS_WEEK));
        paramReports.add(new ParamReport(ParamReportEnum.LAST_WEEK));
        paramReports.add(new ParamReport(ParamReportEnum.THIS_MONTH));
        paramReports.add(new ParamReport(ParamReportEnum.LAST_MONTH));
        paramReports.add(new ParamReport(ParamReportEnum.THIS_YEAR));
        paramReports.add(new ParamReport(ParamReportEnum.LAST_YEAR));
        paramReports.add(new ParamReport(ParamReportEnum.OTHER));
        paramReports.get(0).setSelected(true);
        return paramReports;
    }
    /**
     * Mục đích method: Replace Fragment
     *
     * @param fragment Fragment cần thay thế
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void loadFragment(Fragment fragment) {
        try {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.frContent, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(ReportCurrent reportCurrent) {
        switch (reportCurrent.getParamType()) {
            case TODAY:

                break;
            case THIS_WEEK:
                setSelected(1,mParamReports);
                tvTimeValue.setText(mParamReports.get(1).getTitleReportDetail());
                loadFragment(ReportTotalFragment.newInstance(mParamReports.get(1)));
                break;
            case THIS_YEAR:
                setSelected(5,mParamReports);
                tvTimeValue.setText(mParamReports.get(5).getTitleReportDetail());
                loadFragment(ReportTotalFragment.newInstance(mParamReports.get(5)));
                break;
            case YESTERDAY:
                break;
            case THIS_MONTH:
                setSelected(3,mParamReports);
                tvTimeValue.setText(mParamReports.get(3).getTitleReportDetail());
                loadFragment(ReportTotalFragment.newInstance(mParamReports.get(3)));
                break;
        }
    }
    private void setSelected(int position,List<ParamReport> mParamReports) {
        for (ParamReport paramReport : mParamReports) {
            paramReport.setSelected(false);
        }
        mParamReports.get(position).setSelected(true);
    }
}
