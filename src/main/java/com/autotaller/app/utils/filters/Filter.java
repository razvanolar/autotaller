package com.autotaller.app.utils.filters;

import java.util.List;

/**
 * Created by razvanolar on 13.05.2017
 */
public interface Filter<T> {
  List<T> getFields();
}
