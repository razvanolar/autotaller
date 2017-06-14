package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view;

import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AdminCarSubkitView implements AdminCarSubkitController.IAdminCarSubkitView {

  private TableView<CarSubkitModel> carSubkitModelTable;

  public AdminCarSubkitView() {
    init();
  }

  private void init() {
    carSubkitModelTable = NodeProvider.createCarSubkitModelTable();
  }

  public TableView<CarSubkitModel> getCarSubkitModelTable() {
    return carSubkitModelTable;
  }

  @Override
  public Node asNode() {
    return carSubkitModelTable;
  }
}
