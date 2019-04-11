package com.misa.cukcuklite.screen.reporttotal;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ParamReport;
import com.misa.cukcuklite.data.model.ReportTotal;
import com.misa.cukcuklite.enums.ReportTotalEnum;
import com.misa.cukcuklite.screen.report.ReportFragment;
import com.misa.cukcuklite.screen.reportdetail.ReportDetailActivity;

import java.util.ArrayList;
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
public class ReportTotalFragment extends Fragment implements IReportTotalContract.IView, ReportTotalAdapter.OnClickItemTotalReport {
    public static final String EXTRA_PARAM_REPORT = "com.misa.cukcuklite.extra.param.report";
    private static final String TAG = ReportFragment.class.getName();
    private IReportTotalContract.IPresenter mPresenter;
    private ReportTotalAdapter mAdapter;
    private ParamReport paramReport;
    private LineChart mLineChart;

    public static ReportTotalFragment newInstance() {
        return new ReportTotalFragment();
    }

    public static ReportTotalFragment newInstance(ParamReport paramReport) {
        ReportTotalFragment reportTotalFragment = new ReportTotalFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PARAM_REPORT, paramReport);
        reportTotalFragment.setArguments(args);
        return reportTotalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_total, container, false);
        Bundle args = getArguments();
        paramReport = (ParamReport) args.getSerializable(EXTRA_PARAM_REPORT);
        initView(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadData(paramReport);
    }

    private void setupLineChart(List<ReportTotal> reportTotals) {
        mLineChart = getView().findViewById(R.id.lineChart);
        ReportTotalEnum type = reportTotals.get(0).getType();
        ArrayList<Entry> list = new ArrayList<>();
        for (int i = 0; i < reportTotals.size(); i++) {
            Entry entry = new Entry(i + 1, (float) reportTotals.get(i).getAmount());
            list.add(entry);
        }
        LineDataSet dataSet = new LineDataSet(list, null);
        dataSet.setValueTextSize(0f);
        dataSet.setCircleColor(Color.TRANSPARENT);
        dataSet.setCircleHoleColor(getResources().getColor(R.color.color_line_chart));
        dataSet.setCircleHoleRadius(2.5f);
        dataSet.setColor(getResources().getColor(R.color.color_line_chart));
        LineData lineData = new LineData(dataSet);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDescription(null);
        mLineChart.setTouchEnabled(false);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        switch (type) {
            case WEEK:
                xAxis.setAxisMinimum(1f);
                xAxis.setAxisMaximum(7f);
                xAxis.setLabelCount(7, true);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        if (value == 7) {
                            return "CN";
                        }
                        return "T" + ((int) value + 1);
                    }
                });
                break;
            case MONTH:
                xAxis.setAxisMinimum(1f);
                xAxis.setAxisMaximum(31f);
                xAxis.setLabelCount(11, true);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        return String.valueOf((int) value);
                    }
                });
                break;
            case YEAR:
                xAxis.setAxisMinimum(1f);
                xAxis.setAxisMaximum(12f);
                xAxis.setLabelCount(12, true);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        return String.valueOf((int) value);
                    }
                });
                break;
        }


        YAxis axisLeft = mLineChart.getAxisLeft();
        axisLeft.enableGridDashedLine(5.0f, 5.0f, 0.0f);
        axisLeft.setAxisLineColor(Color.TRANSPARENT);
        axisLeft.setAxisMinimum(0f);
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
    }

    /**
     * Mục đích method: Khai báo ánh xạ view
     *
     * @param v View
     * @created_by Hoàng Hiệp on 4/9/2019
     */
    private void initView(View v) {
        mPresenter = new ReportTotalPresenter(this, getContext());
        mAdapter = new ReportTotalAdapter(getContext(), new ArrayList<ReportTotal>());
        mAdapter.setOnClickItemTotalReport(this);
        RecyclerView rvReport = v.findViewById(R.id.rvReport);
        rvReport.setAdapter(mAdapter);
        rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onLoadDataDone(List<ReportTotal> reportTotals) {
        setupLineChart(reportTotals);
        //a();
        mAdapter.setData(reportTotals);
    }

    private void a() {
        ArrayList<Integer> listData = new ArrayList<>();
        listData.add(80);
        listData.add(60);
        listData.add(50);
        listData.add(45);
        listData.add(25);
        listData.add(35);
        listData.add(50);
        listData.add(30);
        listData.add(40);
        listData.add(100);

        ArrayList<Entry> list = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            Entry entry;

            if (i < listData.size()) {
                entry = new Entry(i + 1, listData.get(i));
                list.add(entry);
            }
        }
        LineDataSet dataSet = new LineDataSet(list, null);
        dataSet.setValueTextSize(0f);
        dataSet.setCircleColor(Color.TRANSPARENT);
        dataSet.setCircleHoleColor(Color.GREEN);
        LineData lineData = new LineData(dataSet);

        mLineChart.setDrawGridBackground(false);
        mLineChart.setDescription(null);
        mLineChart.setTouchEnabled(false);
//        mLineChart.setDragEnabled(true);
//        mLineChart.setScaleEnabled(true);
//        mLineChart.setPinchZoom(true);
//        mLineChart.setNoDataText("text");

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setAxisMinimum(1f);
        xAxis.setAxisMaximum(29f);
        xAxis.setLabelCount(8, true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return (int) value + "";
            }
        });

        YAxis axisLeft = mLineChart.getAxisLeft();
        axisLeft.enableGridDashedLine(5.0f, 5.0f, 0.0f);
//        axisLeft.removeAllLimitLines();
//        axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeft.setAxisLineColor(Color.TRANSPARENT);
        axisLeft.setAxisMinimum(0f);
//        axisLeft.setDrawZeroLine(true);

        mLineChart.getLegend().setEnabled(false);
        mLineChart.getAxisRight().setEnabled(false);

        mLineChart.setData(lineData);
    }
    /**
     * Mục đích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClickItem(ReportTotal reportTotal) {
        startActivity(ReportDetailActivity.getIntent(getContext(),reportTotal));
    }
}
