package com.autotaller.app.components.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.components.app_view.admin_view.AdminToolbarPane;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 29.04.2017
 */
public class AdminCarKitView extends AdminToolbarPane implements AdminCarKitController.IAdminCarKitView {

  private TableView<CarKitModel> carKitTable;

  public AdminCarKitView() {
    super("Ansamble", "Adauga Ansamblu", "Filtrare");
    init();
  }

  private void init() {
    carKitTable = NodeProvider.createCarKitModelTable();
    content.getItems().add(carKitTable);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
