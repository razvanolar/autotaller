package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_kit_view;

import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Created by razvanolar on 29.04.2017
 */
public class AdminCarKitView implements AdminCarKitController.IAdminCarKitView {

  private TableView<CarKitModel> carKitTable;

  public AdminCarKitView() {
    init();
  }

  private void init() {
    carKitTable = NodeProvider.createCarKitModelTable();
  }

  public TableView<CarKitModel> getCarKitTable() {
    return carKitTable;
  }

  @Override
  public Node asNode() {
    return carKitTable;
  }
}
