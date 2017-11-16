package com.autotaller.app.components.app_view.utils.extensions;

import com.autotaller.app.utils.CarWheelSideType;
import javafx.scene.control.CheckBox;

public class WheelTypeCheckBox extends CheckBox {

  private CarWheelSideType wheelType;

  public WheelTypeCheckBox(CarWheelSideType wheelType) {
    super(wheelType.getValue());
    this.wheelType = wheelType;
  }

  public CarWheelSideType getWheelType() {
    return wheelType;
  }
}
