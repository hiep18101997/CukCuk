package com.misa.cukcuklite.screen.dialogparamreport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ParamReport;
import com.misa.cukcuklite.enums.ParamReportEnum;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ParamReportDialog extends DialogFragment implements ParamReportAdapter.OnClickParam {
    private ParamReportAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_param_report, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initComponent(rootView);
        initListener();
        return rootView;
    }

    private void initListener() {

    }

    private void initComponent(View rootView) {
        mAdapter = new ParamReportAdapter(getContext(), getListParam(), this);
        RecyclerView rvDialogReport = rootView.findViewById(R.id.rvDialogReport);
        rvDialogReport.setAdapter(mAdapter);
        rvDialogReport.setLayoutManager(new LinearLayoutManager(getContext()));
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
        return paramReports;
    }

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

    @Override
    public void onClick(ParamReport paramReport) {
    }
}
