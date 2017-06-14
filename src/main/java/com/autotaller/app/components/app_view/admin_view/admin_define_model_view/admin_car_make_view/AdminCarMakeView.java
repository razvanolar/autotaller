package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminCarMakeView implements AdminCarMakeController.ICarsAdminView {

  private TableView<CarMakeModel> carMakeTable;

  public AdminCarMakeView() {
    init();
  }

  private void init() {
    carMakeTable = NodeProvider.createCarMakeTable();
  }

  public TableView<CarMakeModel> getCarMakeTable() {
    return carMakeTable;
  }

  @Override
  public Node asNode() {
    return carMakeTable;
  }
}
