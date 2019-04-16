package com.misa.cukcuklite.screen.calculator.model;

/**
 * - Mục đích Class :Đói tượng nút bàn phím - @created_by Hoàng Hiệp on 4/12/2019
 */
public class InputKeys {

  private int id;

  private String name;

  public InputKeys(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
