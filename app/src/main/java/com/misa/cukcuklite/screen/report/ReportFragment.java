package com.misa.cukcuklite.screen.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.dialogparamreport.ParamReportDialog;
import com.misa.cukcuklite.screen.reportcurrent.ReportCurrentFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        view.findViewById(R.id.lnTime).setOnClickListener(this);
        loadFragment(ReportCurrentFragment.newInstance());
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnTime:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ParamReportDialog dialog = new ParamReportDialog();
                dialog.show(fm, getString(R.string.icon_fragment));
                break;
        }
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
}
