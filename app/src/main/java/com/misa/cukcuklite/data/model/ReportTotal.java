package com.misa.cukcuklite.data.model;

import com.misa.cukcuklite.enums.ReportTotalEnum;

import java.util.Date;

public class ReportTotal {
    private Date fromDate;
    private Date toDate;
    private String titleReportDetail;
    private long amount;
    private ReportTotalEnum mType;
    private int mDayOfMonth;
    private int mDayOfWeek;
    private int mMonthOfYear;
    private int mYear;

    public ReportTotal(ReportTotalEnum type) {
        mType = type;
        setData();
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public ReportTotalEnum getType() {
        return mType;
    }

    public void setType(ReportTotalEnum type) {
        mType = type;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }

    public int getDayOfWeek() {
        return mDayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        mDayOfWeek = dayOfWeek;
    }

    public int getMonthOfYear() {
        return mMonthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        mMonthOfYear = monthOfYear;
    }

    private void setData() {
        Date[] dates = new Date[2];
        switch (mType) {

        }
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
