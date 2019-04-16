package com.misa.cukcuklite.screen.dialogparamreport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ParamReport;
import java.util.List;

/**
 * - Mục đích Class :Dialog chọn khoảng thời gian - @created_by Hoàng Hiệp on 4/12/2019
 */
public class ParamReportDialog extends DialogFragment implements ParamReportAdapter.OnClickParam {

  private ParamReportAdapter mAdapter;
  private ParamCallBack mCallBack;
  private List<ParamReport> mParamReports;

  public void setParamReports(List<ParamReport> paramReports) {
    mParamReports = paramReports;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.dialog_param_report, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    initComponent(rootView);
    initListener();
    return rootView;
  }

  private void initListener() {

  }

  /**
   * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initComponent(View rootView) {
    mAdapter = new ParamReportAdapter(getContext(), mParamReports, this);
    RecyclerView rvDialogReport = rootView.findViewById(R.id.rvDialogReport);
    rvDialogReport.setAdapter(mAdapter);
    rvDialogReport.setLayoutManager(new LinearLayoutManager(getContext()));
  }

  /**
   * Mục đích method: Thay đổi kích cỡ cho dialog
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  @Override
  public void onResume() {
    super.onResume();
    try {
      ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
      params.width = ViewGroup.LayoutParams.MATCH_PARENT;
      params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
      getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Truyền vào Callback
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  public void setCallBack(ParamCallBack callBack) {
    mCallBack = callBack;
  }

  /**
   * Mục đích method: Xử lý sự kiện
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onClick(ParamReport paramReport) {
    mCallBack.onClick(paramReport);
    dismiss();
  }

  public interface ParamCallBack {

    void onClick(ParamReport paramReport);
  }
}
