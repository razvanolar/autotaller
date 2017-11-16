package com.autotaller.app.components.app_view.utils.extensions;

import com.autotaller.app.model.FuelModel;
import javafx.scene.control.CheckBox;

public class FuelCheckBox extends CheckBox {

  private FuelModel fuel;

  public FuelCheckBox(FuelModel fuel) {
    super(fuel.getName());
    this.fuel = fuel;
  }

  public FuelModel getFuel() {
    return fuel;
  }
}
