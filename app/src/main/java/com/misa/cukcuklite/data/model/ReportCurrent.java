package com.misa.cukcuklite.data.model;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.aplication.CukCukApplication;
import com.misa.cukcuklite.enums.ParamReportEnum;
import com.misa.cukcuklite.utils.DateUtil;

import java.util.Date;
/**
 * - Mục đích Class :Đối tượng báo cáo gần đây
 * - @created_by Hoàng Hiệp on 4/12/2019
 */
public class ReportCurrent {
    private Date fromDate;
    private Date toDate;
    private String titleReportDetail;
    private ParamReportEnum paramType;
    private long amount;

    public ReportCurrent(ParamReportEnum paramType) {
        this.paramType = paramType;
        setData();
    }

    private void setData() {
        Date[] dates = new Date[2];
        switch (paramType) {
            case TODAY:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_today);
                dates=DateUtil.getToday();
                break;
            case THIS_WEEK:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_this_week);
                dates=DateUtil.getThisWeek();
                break;
            case THIS_YEAR:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_this_year);
                dates=DateUtil.getThisYear();
                break;
            case YESTERDAY:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_yesterday);
                dates=DateUtil.getYesterday();
                break;
            case THIS_MONTH:
                titleReportDetail = CukCukApplication.getContext().getString(R.string.param_report_this_month);
                dates=DateUtil.getThisMonth();
                break;
        }
        fromDate=dates[0];
        toDate=dates[1];
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getTitleReportDetail() {
        return titleReportDetail;
    }

    public void setTitleReportDetail(String titleReportDetail) {
        this.titleReportDetail = titleReportDetail;
    }

    public ParamReportEnum getParamType() {
        return paramType;
    }

    public void setParamType(ParamReportEnum paramType) {
        this.paramType = paramType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
