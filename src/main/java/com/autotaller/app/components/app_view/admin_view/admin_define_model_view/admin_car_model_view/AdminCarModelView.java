package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view;

import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AdminCarModelView implements AdminCarModelController.IAdminCarModelView {

  private TableView<CarTypeModel> carModelTable;

  public AdminCarModelView() {
    init();
  }

  private void init() {
    carModelTable = NodeProvider.createCarModelTable();
  }

  public TableView<CarTypeModel> getCarModelTable() {
    return carModelTable;
  }

  @Override
  public Node asNode() {
    return carModelTable;
  }
}
