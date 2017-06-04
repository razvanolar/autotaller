package com.autotaller.app.model;

/**
 * Created by razvanolar on 03.06.2017
 */
public class UserModel {

  private int id;
  private String name;
  private String username;

  public UserModel(int id, String name, String username) {
    this.id = id;
    this.name = name;
    this.username = username;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }
}
