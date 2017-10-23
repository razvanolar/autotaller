package com.autotaller.app.model;

import com.autotaller.app.utils.StockType;
import com.autotaller.app.utils.UsageStateType;

import java.util.List;

public class SearchComponentModel {

  private String name;
  private String code;
  private List<UsageStateType> usageList;
  private List<StockType> stockList;
  private int minPrice;
  private int maxPrice;

  public SearchComponentModel(String name, String code, List<UsageStateType> usageList, List<StockType> stockList, int minPrice, int maxPrice) {
    this.name = name;
    this.code = code;
    this.usageList = usageList;
    this.stockList = stockList;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public List<UsageStateType> getUsageList() {
    return usageList;
  }

  public List<StockType> getStockList() {
    return stockList;
  }

  public int getMinPrice() {
    return minPrice;
  }

  public int getMaxPrice() {
    return maxPrice;
  }
}
