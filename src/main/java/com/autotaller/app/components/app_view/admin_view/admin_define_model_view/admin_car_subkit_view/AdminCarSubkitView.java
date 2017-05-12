package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view;

import com.autotaller.app.components.app_view.admin_view.util.AdminToolbarPane;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AdminCarSubkitView extends AdminToolbarPane implements AdminCarSubkitController.IAdminCarSubkitView {

  private TableView<CarSubkitModel> carSubkitModelTable;

  public AdminCarSubkitView() {
    super("Sub-Ansamble", true, true);
    init();
  }

  private void init() {
    carSubkitModelTable = NodeProvider.createCarSubkitModelTable();
    content.getItems().add(carSubkitModelTable);

    initFilterPane();
  }

  private void initFilterPane() {

  }

  public TableView<CarSubkitModel> getCarSubkitModelTable() {
    return carSubkitModelTable;
  }

  @Override
  public Button getAddButton() {
    return addButton;
  }

  @Override
  public ToggleButton getFilterButton() {
    return filterButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
