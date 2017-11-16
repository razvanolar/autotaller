package com.autotaller.app.components.app_view.utils.extensions;

import com.autotaller.app.model.CarBodyTypeModel;
import javafx.scene.control.CheckBox;

public class BodyTypeCheckBox extends CheckBox {

  private CarBodyTypeModel bodyType;

  public BodyTypeCheckBox(CarBodyTypeModel bodyType) {
    super(bodyType.getName());
    this.bodyType = bodyType;
  }

  public CarBodyTypeModel getBodyType() {
    return bodyType;
  }
}
