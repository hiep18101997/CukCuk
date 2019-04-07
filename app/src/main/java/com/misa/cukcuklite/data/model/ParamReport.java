package com.misa.cukcuklite.data.model;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.aplication.CukCukApplication;
import com.misa.cukcuklite.enums.ParamReportEnum;

import java.util.Date;

public class ParamReport {
    private Date fromDate;
    private boolean isSelected;
    private ParamReportEnum paramType;
    private String titleReportDetail;
    private Date toDate;

    public ParamReport(ParamReportEnum paramType) {
        this.paramType = paramType;
        setSetTitle();
        setDate();
    }

    private void setDate() {
        switch (paramType) {
            case OTHER:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_other);
                break;
            case CURRENT:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_current);
                break;
            case TODAY:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_today);
                break;
            case LAST_WEEK:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_last_week);
                break;
            case LAST_YEAR:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_last_year);
                break;
            case THIS_WEEK:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_this_week);
                break;
            case THIS_YEAR:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_this_year);
                break;
            case YESTERDAY:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_yesterday);
                break;
            case LAST_MONTH:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_last_month);
                break;
            case THIS_MONTH:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_this_month);
                break;
        }

    }

    public Date getFromDate() {
        return fromDate;
    }

    public ParamReport setFromDate(Date fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public ParamReport setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public ParamReportEnum getParamType() {
        return paramType;
    }

    public ParamReport setParamType(ParamReportEnum paramType) {
        this.paramType = paramType;
        return this;
    }

    public String getTitleReportDetail() {
        return titleReportDetail;
    }

    public ParamReport setTitleReportDetail(String titleReportDetail) {
        this.titleReportDetail = titleReportDetail;
        return this;
    }

    public Date getToDate() {
        return toDate;
    }

    public ParamReport setToDate(Date toDate) {
        this.toDate = toDate;
        return this;
    }

    private void setSetTitle() {
        switch (paramType) {
            case OTHER:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_other);
                break;
            case CURRENT:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_current);
                break;
            case TODAY:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_today);
                break;
            case LAST_WEEK:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_last_week);
                break;
            case LAST_YEAR:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_last_year);
                break;
            case THIS_WEEK:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_this_week);
                break;
            case THIS_YEAR:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_this_year);
                break;
            case YESTERDAY:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_yesterday);
                break;
            case LAST_MONTH:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_last_month);
                break;
            case THIS_MONTH:
                titleReportDetail=CukCukApplication.getContext().getString(R.string.param_report_this_month);
                break;
        }

    }
}
