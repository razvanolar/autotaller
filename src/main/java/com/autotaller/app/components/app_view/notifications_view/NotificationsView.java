package com.autotaller.app.components.app_view.notifications_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.notifications.SimpleSellModel;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 04.06.2017
 */
public class NotificationsView extends IterableView implements NotificationsController.INotificationsView {

  private TableView<SimpleSellModel> sellModelTable;
  private Button detailsButton;

  public NotificationsView() {
    init();
  }

  private void init() {
    detailsButton = NodeProvider.createToolbarButton("Detalii", ImageProvider.detailsIcon());
    toolBar.getItems().addAll(new Separator(), detailsButton);

    sellModelTable = NodeProvider.createSellModelTable();
    borderPane.setCenter(sellModelTable);
  }

  public TableView<SimpleSellModel> getSellModelTable() {
    return sellModelTable;
  }

  public Button getDetailsButton() {
    return detailsButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
