package com.autotaller.app.components.app_view.notifications_view;

import com.autotaller.app.components.utils.FillToolItem;
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
  private Button confirmSellButton;
  private Button cancelSellButton;

  public NotificationsView() {
    init();
  }

  private void init() {
    detailsButton = NodeProvider.createToolbarButton("Detalii Vanzare", ImageProvider.detailsIcon());
    confirmSellButton = NodeProvider.createToolbarButton("Confirma", ImageProvider.checkIcon());
    cancelSellButton = NodeProvider.createToolbarButton("Anuleaza", ImageProvider.lockIcon());
    toolBar.getItems().addAll(new Separator(), detailsButton, new FillToolItem(), confirmSellButton, cancelSellButton);

    sellModelTable = NodeProvider.createSellModelTable();
    borderPane.setCenter(sellModelTable);
  }

  public TableView<SimpleSellModel> getSellModelTable() {
    return sellModelTable;
  }

  public Button getDetailsButton() {
    return detailsButton;
  }

  public Button getConfirmSellButton() {
    return confirmSellButton;
  }

  public Button getCancelSellButton() {
    return cancelSellButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
