package com.misa.cukcuklite.screen.reportdetail;

import static com.misa.cukcuklite.utils.AppConstant.DATE_FORMAT;
import static com.misa.cukcuklite.utils.AppConstant.EXTRA_DATES;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ReportDetail;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * - Mục đích Class :Màn báo cáo chi tiết - @created_by Hoàng Hiệp on 4/15/2019
 */
public class ReportDetailFragment extends Fragment implements IReportDetailContract.IView {
  private static final String TAG = ReportDetailFragment.class.getName();
  private IReportDetailContract.IPresenter mPresenter;
  private Date[] dates;
  private ReportDetailAdapter mAdapter;
  private PieChart mPieChart;
  private int[] colors;


  /**
   * Mục đích method: Khởi tạo ReportDetailFragment;
   *
   * @param dates: Mảng Date chứa ngày bắt đầu và ngày kết thúc
   * @return reportTotalFragment
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  public static ReportDetailFragment newInstance(Date[] dates) {
    ReportDetailFragment reportTotalFragment = new ReportDetailFragment();
    Bundle args = new Bundle();
    args.putSerializable(EXTRA_DATES, dates);
    reportTotalFragment.setArguments(args);
    return reportTotalFragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_report_detail, container, false);
    Bundle args = getArguments();
    dates = (Date[]) args.getSerializable(EXTRA_DATES);
    initView(view);
    return view;
  }

  /**
   * Mục đích method: Chỉnh thông số cho PieChart
   *
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  private void setupPieChart(List<ReportDetail> reportDetails) {
    try {
      long amount = 0;
      mPieChart.setUsePercentValues(true);
      mPieChart.setDescription(null);
      float margin = 1;
      mPieChart.setExtraOffsets(margin, margin, margin, margin);
      mPieChart.setHoleRadius(65.0f);
      mPieChart.setDrawCenterText(true);
      mPieChart.setRotationAngle(0.0f);
      mPieChart.setRotationEnabled(false);
      mPieChart.setHighlightPerTapEnabled(false);

      List<PieEntry> entries = new ArrayList<>();
      for (ReportDetail reportDetail : reportDetails) {
        PieEntry pieEntry = new PieEntry((float) reportDetail.getAmount());
        amount += (reportDetail.getAmount() * reportDetail.getQuantity());
        entries.add(pieEntry);
      }
      PieDataSet pieDataSet = new PieDataSet(entries, null);
      pieDataSet.setColors(getResources().getIntArray(R.array.color_report));
      pieDataSet.setValueTextSize(12);
      pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      PieData data = new PieData(pieDataSet);
      data.setValueFormatter(new PercentFormatter(mPieChart));

      mPieChart.setData(data);

      String textCenter = getString(R.string.totalRevenue);
      String textTotalRevenue = NumberFormat.getNumberInstance(Locale.US).format(amount);
      SpannableString spannableString = new SpannableString(textCenter + "\n" + textTotalRevenue);
      spannableString
          .setSpan(new RelativeSizeSpan(1.8f), textCenter.length() + 1, spannableString.length(),
              0);
      spannableString
          .setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_black)),
              textCenter.length() + 1, spannableString.length(), 0);

      spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, textCenter.length(), 0);
      spannableString
          .setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlackLight)), 0,
              textCenter.length(), 0);

      mPieChart.setCenterText(spannableString);
      mPieChart.animateY(1400);
      mPieChart.getLegend().setEnabled(false);
      mPieChart.invalidate();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  /**
   * Mục đích method: Ánh xạ và khai báo các view
   *
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  private void initView(View view) {

    mPieChart = view.findViewById(R.id.pieChart);
    mPresenter = new ReportDetailPresenter(getContext(), this);
    mPresenter.loadData(dates);

    mAdapter = new ReportDetailAdapter(getContext(), new ArrayList<ReportDetail>());
    RecyclerView rvReport = view.findViewById(R.id.rvReport);
    rvReport.setAdapter(mAdapter);
    rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
  }

  /**
   * Mục đích method: Add data và adapter và cặp nhật lại PieChart khi load dữ liêu thành công
   *
   * @param reportDetails: Danh sách ReportDetail
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  @Override
  public void onLoadDataDone(List<ReportDetail> reportDetails) {
    mAdapter.setData(reportDetails);
    setupPieChart(reportDetails);
  }
}
