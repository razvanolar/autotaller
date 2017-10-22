package com.autotaller.app.components.app_view.utils.extensions;

import com.autotaller.app.utils.StockType;
import javafx.scene.control.CheckBox;

public class StockTypeCheckBox extends CheckBox {

  private StockType stockType;

  public StockTypeCheckBox(StockType stockType) {
    super(stockType.getName());
    this.stockType = stockType;
  }

  public StockType getStockType() {
    return stockType;
  }
}
