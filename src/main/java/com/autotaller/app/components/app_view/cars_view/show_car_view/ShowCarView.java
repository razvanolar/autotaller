package com.autotaller.app.components.app_view.cars_view.show_car_view;

import com.autotaller.app.components.app_view.utils.DefaultCarView;
import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 02.06.2017
 */
public class ShowCarView extends IterableView implements ShowCarController.IShowCarView {

  private DefaultCarView defaultCarView;
  private ToggleButton showFilterButton;
  private Button detailsButton;
  private Button continueButton;

  public ShowCarView() {
    init();
  }

  private void init() {
    showFilterButton = NodeProvider.createToolbarToggleButton("Filtreaza", ImageProvider.filterIcon());
    detailsButton = NodeProvider.createToolbarButton("Detalii", ImageProvider.detailsIcon());
    continueButton = NodeProvider.createToolbarButton("Continua", null);
    toolBar.getItems().addAll(new Separator(), showFilterButton, detailsButton, new FillToolItem(), continueButton);

    defaultCarView = new DefaultCarView();
    borderPane.setCenter(defaultCarView.asNode());
  }

  public DefaultCarView getDefaultCarView() {
    return defaultCarView;
  }

  public ToggleButton getShowFilterButton() {
    return showFilterButton;
  }

  public Button getDetailsButton() {
    return detailsButton;
  }

  public Button getContinueButton() {
    return continueButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
