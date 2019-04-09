package com.misa.cukcuklite.data.model;

import java.util.Date;

public class ReportTotal {
    private Date fromDate;
    private Date toDate;
    private String titleReportDetail;
    private long amount;

    public ReportTotal() {
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
