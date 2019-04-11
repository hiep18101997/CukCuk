package com.misa.cukcuklite.screen.reportdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReportDetailFragment extends Fragment implements IReportDetailContract.IView {
    private static final String TAG = ReportDetailFragment.class.getName();
    private IReportDetailContract.IPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_report_detail,container,false);
        mPresenter = new ReportDetailPresenter(this);
        return view;
    }

}
