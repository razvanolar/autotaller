package com.autotaller.app.components.app_view.utils.extensions;

import com.autotaller.app.utils.UsageStateType;
import javafx.scene.control.CheckBox;

public class UsageTypeCheckBox extends CheckBox {

  private UsageStateType usageStateType;

  public UsageTypeCheckBox(UsageStateType usageStateType) {
    super(usageStateType.getName());
    this.usageStateType = usageStateType;
  }

  public UsageStateType getUsageStateType() {
    return usageStateType;
  }
}
