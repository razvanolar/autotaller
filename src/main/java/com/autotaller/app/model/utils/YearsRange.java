package com.autotaller.app.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 29.04.2017
 */
public class YearsRange {

  private int minYear;
  private int maxYear;

  public YearsRange(int minYear, int maxYear) {
    this.minYear = minYear;
    this.maxYear = maxYear;
  }

  public int getMinYear() {
    return minYear;
  }

  public int getMaxYear() {
    return maxYear;
  }

  public List<Integer> toList() {
    List<Integer> result = new ArrayList<>();
    for (int i = minYear; i <= maxYear; i++)
      result.add(i);
    return result;
  }
}
