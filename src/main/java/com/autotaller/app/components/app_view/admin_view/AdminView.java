package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminView extends IterableView implements AdminController.IAdminView {

  private Node carMakeRegistrationMenu;

  public AdminView() {
    init();
  }

  private void init() {
    carMakeRegistrationMenu = NodeProvider.createAppMenu("Inregistrare Marca", null, 150, 50);
    Node carModelRegistrationMenu = NodeProvider.createAppMenu("Inregistrare Model", null, 150, 50);
    Node carRegistrationMenu = NodeProvider.createAppMenu("Adauga Masina", null, 150, 50);
    Node componentRegistrationMenu = NodeProvider.createAppMenu("Adauga Componenta", null, 150, 50);

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.add(carMakeRegistrationMenu, 0, 0);
    gridPane.add(carModelRegistrationMenu, 1, 0);
    gridPane.add(carRegistrationMenu, 2, 0);
    gridPane.add(componentRegistrationMenu, 3, 0);

    mainContainer.setCenter(gridPane);
  }

  public Node getCarMakeRegistrationMenu() {
    return carMakeRegistrationMenu;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
