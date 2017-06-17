package com.autotaller.app.utils.filters.car_filters;

import com.autotaller.app.utils.StringValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 17.06.2017
 */
public abstract class CarStringFilter<T> {

  protected String filterValue = "";

  public void setFilterValue(String filterValue) {
    this.filterValue = filterValue;
    if (this.filterValue != null)
      this.filterValue = this.filterValue.trim().toLowerCase();
    else
      this.filterValue = "";
  }

  public List<T> filter(List<T> models) {
    if (StringValidator.isNullOrEmpty(filterValue)) {
      if (models == null)
        return new ArrayList<>();
    }
    if (models == null)
      return new ArrayList<>();

    List<T> result = new ArrayList<>();
    for (T elem : models) {
      if (check(getValue(elem)))
        result.add(elem);
    }
    return result;
  }

  protected boolean check(String value) {
    return value.toLowerCase().contains(filterValue);
  }

  protected abstract String getValue(T model);
}
