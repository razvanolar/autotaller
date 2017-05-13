package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminView extends IterableView implements AdminController.IAdminView {

  private Node defineModelMenu;
  private Node addCarMenu;
  private Node addComponentMenu;
  private Node statisticsComponentMenu;
  private Node manageUsersMenu;

  public AdminView() {
    init();
  }

  private void init() {
    defineModelMenu = NodeProvider.createAdminAppMenu("Definire Model", null);
    addCarMenu = NodeProvider.createAdminAppMenu("Inregistrare Masina", null);
    addComponentMenu = NodeProvider.createAdminAppMenu("Inregistrare Componenta", null);
    statisticsComponentMenu = NodeProvider.createAdminAppMenu("Statistici", null);
    manageUsersMenu = NodeProvider.createAdminAppMenu("Administrare Utilizatori", null);

    GridPane gridPane = new GridPane();
    gridPane.add(defineModelMenu, 0, 0);
    gridPane.add(addCarMenu, 0, 1);
    gridPane.add(addComponentMenu, 0, 2);
    gridPane.add(statisticsComponentMenu, 0, 3);
    gridPane.add(manageUsersMenu, 0, 4);

    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(10);

    borderPane.setCenter(NodeProvider.createScrollPane(gridPane, true));
  }

  public Node getDefineModelMenu() {
    return defineModelMenu;
  }

  public Node getAddCarMenu() {
    return addCarMenu;
  }

  public Node getAddComponentMenu() {
    return addComponentMenu;
  }

  public Node getStatisticsComponentMenu() {
    return statisticsComponentMenu;
  }

  public Node getManageUsersMenu() {
    return manageUsersMenu;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
