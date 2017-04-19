package com.autotaller.app.components.app_view.admin_view.admin_car_make_view;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminCarMakeView implements AdminCarMakeController.ICarsAdminView {

  private GridPane gridPane;

  public AdminCarMakeView() {
    init();
  }

  private void init() {
    gridPane = new GridPane();
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
