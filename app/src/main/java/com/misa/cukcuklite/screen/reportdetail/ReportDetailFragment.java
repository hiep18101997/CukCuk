package com.misa.cukcuklite.screen.reportdetail;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ReportDetail;

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
    private PieChart mPieChart;
    private int[] colors;

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
        View view = inflater.inflate(R.layout.fragment_report_detail, container, false);
        Bundle args = getArguments();
        dates = (Date[]) args.getSerializable(EXTRA_DATES);
        initView(view);
        return view;
    }

    private void setupPieChart(List<ReportDetail> reportDetails) {
        colors = getContext().getResources().getIntArray(R.array.arr_colors);
        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription(null);
        mPieChart.setExtraOffsets(10.0f, 10.0f, 10.0f, 10.0f);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.TRANSPARENT);
        mPieChart.setTransparentCircleColor(-1);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setHoleRadius(65.0f);
        mPieChart.setTransparentCircleRadius(60.0f);
        mPieChart.setDrawCenterText(true);
        mPieChart.setRotationAngle(0.0f);
        mPieChart.setRotationEnabled(false);
        mPieChart.setHighlightPerTapEnabled(false);
        mPieChart.setDrawSliceText(false);
        List<PieEntry> entries = new ArrayList<>();
        for (ReportDetail reportDetail:reportDetails){
            PieEntry pieEntry=new PieEntry((float) reportDetail.getAmount());
            entries.add(pieEntry);
        }
        PieDataSet pieDataSet = new PieDataSet(entries,"pie_chart");
        pieDataSet.setColors(colors);
        pieDataSet.setValueLinePart1OffsetPercentage(80.0f);
        pieDataSet.setValueLinePart1Length(0.3f);
        pieDataSet.setValueLinePart2Length(0.4f);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(pieDataSet);
        data.setValueFormatter(new PercentFormatter());
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        mPieChart.setCenterText("Tổng doanh thu");
        mPieChart.animateY(1400);
        mPieChart.getLegend().setEnabled(false);
        mPieChart.invalidate();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        mPieChart = view.findViewById(R.id.pieChart);
        mPresenter = new ReportDetailPresenter(getContext(), this);
        mPresenter.loadData(dates);
        mAdapter = new ReportDetailAdapter(getContext(), new ArrayList<ReportDetail>());
        RecyclerView rvReport = view.findViewById(R.id.rvReport);
        rvReport.setAdapter(mAdapter);
        rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onLoadDataDone(List<ReportDetail> reportDetails) {
        mAdapter.setData(reportDetails);
        setupPieChart(reportDetails);
    }
}
