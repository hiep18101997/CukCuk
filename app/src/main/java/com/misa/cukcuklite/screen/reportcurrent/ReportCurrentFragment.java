package com.misa.cukcuklite.screen.reportcurrent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.report.ReportFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReportCurrentFragment extends Fragment implements IReportCurrentContract.IView {
    private static final String TAG = ReportFragment.class.getName();
    private IReportCurrentContract.IPresenter mPresenter;

    public static ReportCurrentFragment newInstance() {
        return new ReportCurrentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_current, container, false);
        return v;
    }
}
