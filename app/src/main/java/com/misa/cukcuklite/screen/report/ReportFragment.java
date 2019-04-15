package com.misa.cukcuklite.screen.report;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ParamReport;
import com.misa.cukcuklite.data.model.ReportCurrent;
import com.misa.cukcuklite.data.model.ReportTotal;
import com.misa.cukcuklite.enums.ParamReportEnum;
import com.misa.cukcuklite.enums.ReportTotalEnum;
import com.misa.cukcuklite.screen.dialogparamreport.ParamReportDialog;
import com.misa.cukcuklite.screen.dialogpickdate.FromToPickerDialog;
import com.misa.cukcuklite.screen.reportcurrent.ReportCurrentFragment;
import com.misa.cukcuklite.screen.reportdetail.ReportDetailActivity;
import com.misa.cukcuklite.screen.reportdetail.ReportDetailFragment;
import com.misa.cukcuklite.screen.reporttotal.ReportTotalFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.misa.cukcuklite.utils.AppConstant.DATE_FORMAT;

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
        mParamReports = getListParam();
        loadFragment(ReportCurrentFragment.newInstance(this));
        return view;
    }

    /**
     * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initView(View view) {
        tvTimeValue = view.findViewById(R.id.tvTimeValue);
    }

    /**
     * Mục đích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.lnTime:
                    final FragmentManager fm = getActivity().getSupportFragmentManager();
                    final ParamReportDialog dialog = new ParamReportDialog();
                    dialog.setParamReports(mParamReports);
                    dialog.setCallBack(new ParamReportDialog.ParamCallBack() {
                        @Override
                        public void onClick(ParamReport paramReport) {
                            if (paramReport.getParamType() == ParamReportEnum.CURRENT) {
                                tvTimeValue.setText(paramReport.getTitleReportDetail());
                                loadFragment(ReportCurrentFragment.newInstance(ReportFragment.this));
                            } else if (paramReport.getParamType() == ParamReportEnum.OTHER) {
                                FromToPickerDialog fromToPickerDialog = new FromToPickerDialog();
                                fromToPickerDialog.setOnClickAcceptPickDate(new FromToPickerDialog.OnClickAcceptPickDate() {
                                    @Override
                                    public void onPickDate(Date fromDate, Date toDate) {
                                        Date[] dates = new Date[2];
                                        dates[0] = fromDate;
                                        dates[1] = toDate;
                                        loadFragment(ReportDetailFragment.newInstance(dates));
                                        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                                        tvTimeValue.setText(dateFormat.format(fromDate) + "-" + dateFormat.format(toDate));
                                        setSelected(7, mParamReports);
                                    }
                                });
                                fromToPickerDialog.show(fm, "date_picker");
                            } else {
                                tvTimeValue.setText(paramReport.getTitleReportDetail());
                                loadFragment(ReportTotalFragment.newInstance(paramReport));
                            }
                        }
                    });
                    dialog.show(fm, getString(R.string.icon_fragment));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Lấy danh sách mốc thời gian mặc định
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
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

    /**
     * Mục đích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(ReportCurrent reportCurrent) {
        try {
            switch (reportCurrent.getParamType()) {
                case TODAY:
                    ReportTotal reportTotalToday = new ReportTotal(ReportTotalEnum.TODAY);
                    reportTotalToday.setFromDate(reportCurrent.getFromDate());
                    reportTotalToday.setToDate(reportCurrent.getToDate());
                    startActivity(ReportDetailActivity.getIntent(getContext(), reportTotalToday));
                    break;
                case THIS_WEEK:
                    setSelected(1, mParamReports);
                    tvTimeValue.setText(mParamReports.get(1).getTitleReportDetail());
                    loadFragment(ReportTotalFragment.newInstance(mParamReports.get(1)));
                    break;
                case THIS_YEAR:
                    setSelected(5, mParamReports);
                    tvTimeValue.setText(mParamReports.get(5).getTitleReportDetail());
                    loadFragment(ReportTotalFragment.newInstance(mParamReports.get(5)));
                    break;
                case YESTERDAY:
                    ReportTotal reportTotalYes = new ReportTotal(ReportTotalEnum.YESTERDAY);
                    reportTotalYes.setFromDate(reportCurrent.getFromDate());
                    reportTotalYes.setToDate(reportCurrent.getToDate());
                    startActivity(ReportDetailActivity.getIntent(getContext(), reportTotalYes));
                    break;
                case THIS_MONTH:
                    setSelected(3, mParamReports);
                    tvTimeValue.setText(mParamReports.get(3).getTitleReportDetail());
                    loadFragment(ReportTotalFragment.newInstance(mParamReports.get(3)));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Set vị trí chọn của dialog chọn khoảng thời gian
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void setSelected(int position, List<ParamReport> mParamReports) {
        for (ParamReport paramReport : mParamReports) {
            paramReport.setSelected(false);
        }
        mParamReports.get(position).setSelected(true);
    }
}
