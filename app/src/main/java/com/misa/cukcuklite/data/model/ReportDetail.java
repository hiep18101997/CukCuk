package com.misa.cukcuklite.data.model;

/**
 * - Mục đích Class :Đối tượng Báo cáo chi tiết - @created_by Hoàng Hiệp on 4/12/2019
 */
public class ReportDetail {

  private String mName;
  private long mAmount;
  private int mQuantity;
  private String mUnit;

  public ReportDetail(Builder builder) {
    mName = builder.mName;
    mAmount = builder.mAmount;
    mQuantity = builder.mQuantity;
    mUnit = builder.mUnit;

  }

  public ReportDetail() {
  }

  public long getAmount() {
    return mAmount;
  }

  public void setAmount(long amount) {
    mAmount = amount;
  }

  public int getQuantity() {
    return mQuantity;
  }

  public void setQuantity(int quantity) {
    mQuantity = quantity;
  }

  public String getUnit() {
    return mUnit;
  }

  public void setUnit(String unit) {
    mUnit = unit;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public void addAmount(long amount) {
    mAmount += amount;
  }

  public void addQuantity(int quantity) {
    mQuantity += quantity;
  }

  public static class Builder {

    private String mName;
    private long mAmount;
    private int mQuantity;
    private String mUnit;

    public Builder setName(String name) {
      mName = name;
      return this;
    }

    public Builder setAmount(long amount) {
      mAmount = amount;
      return this;
    }

    public Builder setQuantity(int quantity) {
      mQuantity = quantity;
      return this;
    }

    public Builder setUnit(String unit) {
      mUnit = unit;
      return this;
    }

    public ReportDetail build() {
      return new ReportDetail(this);
    }
  }
}
