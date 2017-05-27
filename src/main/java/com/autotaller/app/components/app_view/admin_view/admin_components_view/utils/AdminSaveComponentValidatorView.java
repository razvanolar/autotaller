package com.autotaller.app.components.app_view.admin_view.admin_components_view.utils;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * Created by razvanolar on 26.05.2017
 */
public class AdminSaveComponentValidatorView implements AdminSaveComponentValidatorController.IAdminSaveCarComponentValidatorView {

  private BorderPane mainContainer;
  private TableView<CarComponentModel> carComponentTable;

  public AdminSaveComponentValidatorView() {
    init();
  }

  private void init() {
    carComponentTable = NodeProvider.createCarComponentValidatorTable();
    mainContainer = new BorderPane(carComponentTable);
  }

  public TableView<CarComponentModel> getCarComponentTable() {
    return carComponentTable;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
