package com.autotaller.app.utils;

/**
 * Created by razvanolar on 29.12.2016
 */
public interface Controller<T extends View> {
  void bind(T view);
}
