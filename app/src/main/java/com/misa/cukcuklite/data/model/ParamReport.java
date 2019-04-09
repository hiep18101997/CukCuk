package com.misa.cukcuklite.data.model;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.aplication.CukCukApplication;
import com.misa.cukcuklite.enums.ParamReportEnum;
import com.misa.cukcuklite.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class ParamReport implements Serializable {
    private Date fromDate;
    private boolean isSelected;
    private ParamReportEnum paramType;
    private String titleReportDetail;
    private Date toDate;

    public ParamReport(ParamReportEnum paramType) {
        this.paramType = paramType;
        setData();
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ParamReportEnum getParamType() {
        return paramType;
    }

    public void setParamType(ParamReportEnum paramType) {
        this.paramType = paramType;
    }

    public String getTitleReportDetail() {
        return titleReportDetail;
    }

    public void setTitleReportDetail(String titleReportDetail) {
        this.titleReportDetail = titleReportDetail;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    private void setData() {
        Date[] dates = new Date[2];
        switch (paramType) {
            case OTHER:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_other);
                break;
            case CURRENT:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_current);
                break;
            case TODAY:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_today);
                dates = DateUtil.getToday();
                break;
            case LAST_WEEK:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_last_week);
                dates = DateUtil.getLastWeek();
                break;
            case LAST_YEAR:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_last_year);
                dates = DateUtil.getLastYear();
                break;
            case THIS_WEEK:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_this_week);
                dates = DateUtil.getThisWeek();
                break;
            case THIS_YEAR:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_this_year);
                dates = DateUtil.getThisYear();
                break;
            case YESTERDAY:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_yesterday);
                dates = DateUtil.getYesterday();
                break;
            case LAST_MONTH:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_last_month);
                dates = DateUtil.getLastMonth();
                break;
            case THIS_MONTH:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_this_month);
                dates = DateUtil.getThisMonth();
                break;
        }
        fromDate = dates[0];
        toDate = dates[1];
    }
}
