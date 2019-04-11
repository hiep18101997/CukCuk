package com.misa.cukcuklite.data.model;

import com.misa.cukcuklite.enums.ReportTotalEnum;

import java.io.Serializable;
import java.util.Date;

public class ReportTotal implements Serializable {
    private Date fromDate;
    private Date toDate;
    private String titleReportDetail;
    private long amount;
    private ReportTotalEnum mType;


    public ReportTotalEnum getType() {
        return mType;
    }

    public void setType(ReportTotalEnum type) {
        mType = type;
    }

    public ReportTotal(ReportTotalEnum type) {
        mType=type;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
