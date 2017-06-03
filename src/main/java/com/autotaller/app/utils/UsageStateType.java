package com.autotaller.app.utils;

/**
 * Created by razvanolar on 03.06.2017
 */
public enum UsageStateType {

  NEW(1, "Nou"), MEDIUM(2, "Mediu"), OLD(3, "Vechi");

  int id;
  String name;

  UsageStateType(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }



  public static UsageStateType fromId(int id) {
    if (id == MEDIUM.id)
      return MEDIUM;
    if (id == OLD.id)
      return OLD;
    return NEW;
  }

  @Override
  public String toString() {
    return name;
  }
}
