package com.autotaller.app.repository.utils;

/**
 * Created by razvanolar on 28.05.2017
 */
public enum CarStatus {

  SUCCESS_SAVE("Masina %s a fost adaugata cu succes"),
  FAILED_SAVE("Masina %s nu a putut fi adaugata");

  String template;

  CarStatus(String template) {
    this.template = template;
  }

  public String getText(String carName) {
    return String.format(template, carName);
  }
}
