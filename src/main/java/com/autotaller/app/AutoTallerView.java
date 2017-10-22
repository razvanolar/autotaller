package com.autotaller.app;

import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 14.04.2017
 */
public class AutoTallerView implements AutoTallerController.IAutoTallerView {

  private BorderPane mainContainer;
  private Node carsMenu;
  private Node adminMenu;
  private Node notificationsMenu;
  private Node exitMenu;
  private Node componentsMenu;

  public AutoTallerView() {
    super();
    init();
  }

  private void init() {
    carsMenu = NodeProvider.createAppMenu("Masini", ImageProvider.carMenuIcon());
    componentsMenu = NodeProvider.createAppMenu("Componente", ImageProvider.componentMenuIcon());
    Node searchMenu = NodeProvider.createAppMenu("Cautare", ImageProvider.searchMenuIcon());
    Node clientsMenu = NodeProvider.createAppMenu("Clienti", ImageProvider.clientsMenuIcon());
    adminMenu = NodeProvider.createAppMenu("Administrare", ImageProvider.adminMenuIcon());
    notificationsMenu = NodeProvider.createAppMenu("Notificari", ImageProvider.notificationMenuIcon());
    Node settingsMenu = NodeProvider.createAppMenu("Setari", ImageProvider.settingsMenuIcon());
    exitMenu = NodeProvider.createAppMenu("Deconectare", ImageProvider.exitMenuIcon());

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(10);
    gridPane.setHgap(10);

    gridPane.add(carsMenu, 0, 0);
    gridPane.add(componentsMenu, 1, 0);
    gridPane.add(searchMenu, 2, 0);
    gridPane.add(clientsMenu, 3, 0);
    gridPane.add(adminMenu, 0, 1);
    gridPane.add(notificationsMenu, 1, 1);
    gridPane.add(settingsMenu, 2, 1);
    gridPane.add(exitMenu, 3, 1);

    mainContainer = new BorderPane(gridPane);
  }

  public Node getCarsMenu() {
    return carsMenu;
  }

  public Node getComponentsMenu() {
    return componentsMenu;
  }

  public Node getAdminMenu() {
    return adminMenu;
  }

  public Node getNotificationsMenu() {
    return notificationsMenu;
  }

  public Node getExitMenu() {
    return exitMenu;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
