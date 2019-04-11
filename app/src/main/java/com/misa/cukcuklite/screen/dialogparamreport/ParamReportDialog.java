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
    private ParamCallBack mCallBack;
    private List<ParamReport> mParamReports;

    public void setParamReports(List<ParamReport> paramReports) {
        mParamReports = paramReports;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_param_report, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initComponent(rootView);
        initListener();
        return rootView;
    }

    private void initListener() {

    }

    private void initComponent(View rootView) {
        mAdapter = new ParamReportAdapter(getContext(), mParamReports, this);
        RecyclerView rvDialogReport = rootView.findViewById(R.id.rvDialogReport);
        rvDialogReport.setAdapter(mAdapter);
        rvDialogReport.setLayoutManager(new LinearLayoutManager(getContext()));
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

    public void setCallBack(ParamCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void onClick(ParamReport paramReport) {
      mCallBack.onClick(paramReport);
      dismiss();
    }
    public interface ParamCallBack{
       void onClick(ParamReport paramReport);
    }
}
