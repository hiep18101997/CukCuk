package com.misa.cukcuklite.screen.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.dialogparamreport.ParamReportDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * - Mục đích Class : Màn hình báo cáo
 * - @created_by Hoàng Hiệp on 7/4/2019
 */
public class ReportFragment extends Fragment implements IReportContract.IView, View.OnClickListener {
    private static final String TAG = ReportFragment.class.getName();
    private IReportContract.IPresenter mPresenter;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.fragment_report, container, false);
      view.findViewById(R.id.lnTime).setOnClickListener(this);
      return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lnTime:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ParamReportDialog dialog = new ParamReportDialog();
                dialog.show(fm, getString(R.string.icon_fragment));
                break;
        }
    }
}
