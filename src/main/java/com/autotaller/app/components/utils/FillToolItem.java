package com.autotaller.app.components.utils;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Created by razvanolar on 27.04.2017
 */
public class FillToolItem extends Region {
  public FillToolItem() {
    HBox.setHgrow(this, Priority.ALWAYS);
    this.setMinWidth(Region.USE_PREF_SIZE);
  }
}
