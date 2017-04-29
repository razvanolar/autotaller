package com.autotaller.app.components.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.components.app_view.admin_view.AdminToolbarPane;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;

/**
 * Created by razvanolar on 29.04.2017
 */
public class AdminCarKitView extends AdminToolbarPane implements AdminCarKitController.IAdminCarKitView {

  private SplitPane splitPane;
  private Button addCarKitButton;
  private Button filterCarKitsButton;

  public AdminCarKitView() {
    super("Ansamble");
    init();
    setContentNode(splitPane);
  }

  private void init() {
    splitPane = new SplitPane();
  }

  @Override
  protected void addToolbarButtons() {
    addCarKitButton = new Button("Adauga Ansamblu");
    filterCarKitsButton = new Button("Filtreaza");
    toolbarContainer.getChildren().addAll(addCarKitButton, filterCarKitsButton);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
