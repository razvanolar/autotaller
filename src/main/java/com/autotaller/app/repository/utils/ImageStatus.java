package com.autotaller.app.repository.utils;

/**
 * Created by razvanolar on 28.05.2017
 */
public enum ImageStatus {

  SUCCESS_IMAGE_SAVE("Imaginile pentru %s au fost salvate cu succes"),
  FAILED_IMAGE_SAVE("Imaginile pentru %s nu au putut fi salvate");

  String template;

  ImageStatus(String template) {
    this.template = template;
  }

  public String getText(String carName) {
    return String.format(template, carName);
  }
}
