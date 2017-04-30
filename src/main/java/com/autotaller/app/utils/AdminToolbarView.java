package com.autotaller.app.utils;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 30.04.2017
 */
public interface AdminToolbarView extends View {
  Button getAddButton();
  ToggleButton getFilterButton();
  void showFilterPane();
}
