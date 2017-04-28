package com.autotaller.app;

import com.autotaller.app.components.utils.MaskableView;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.MaskViewEventHandler;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEventHandler;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 14.04.2017
 */
public class AutoTallerView extends MaskableView implements AutoTallerController.IAutoTallerView {

  private BorderPane mainContainer;
  private Node adminMenu;
  private Node exitMenu;

  public AutoTallerView() {
    super();
    init();
    addHandlers();
  }

  private void init() {
    Node carsMenu = NodeProvider.createAppMenu("Masini", ImageProvider.carMenuIcon());
    Node componentsMenu = NodeProvider.createAppMenu("Componente", ImageProvider.componentMenuIcon());
    Node searchMenu = NodeProvider.createAppMenu("Cautare", ImageProvider.searchMenuIcon());
    adminMenu = NodeProvider.createAppMenu("Administrare", ImageProvider.adminMenuIcon());
    Node settingsMenu = NodeProvider.createAppMenu("Setari", ImageProvider.settingsMenuIcon());
    exitMenu = NodeProvider.createAppMenu("Deconectare", ImageProvider.exitMenuIcon());

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(10);
    gridPane.setHgap(10);

    gridPane.add(carsMenu, 0, 0);
    gridPane.add(componentsMenu, 1, 0);
    gridPane.add(searchMenu, 2, 0);
    gridPane.add(adminMenu, 0, 1);
    gridPane.add(settingsMenu, 1, 1);
    gridPane.add(exitMenu, 2, 1);

    mainContainer = new BorderPane(gridPane);

    stackPane.getChildren().add(mainContainer);
  }

  private void addHandlers() {
    EventBus.addHandler(MaskViewEvent.TYPE, (MaskViewEventHandler) event -> {
      maskView(event.getMessage());
    }, true);

    EventBus.addHandler(UnmaskViewEvent.TYPE, (UnmaskViewEventHandler) event -> {
      unmaskView();
    }, true);
  }

  public Node getAdminMenu() {
    return adminMenu;
  }

  public Node getExitMenu() {
    return exitMenu;
  }

  @Override
  public Node asNode() {
    return stackPane;
  }
}
