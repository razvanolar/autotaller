package com.autotaller.app.components.app_view.cars_view.search_car_view;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarView extends IterableView implements SearchCarController.ISearchCarView {

  private TableView<CarModel> carsTable;
  private Button continueButton;
  private Button detailsButton;

  public SearchCarView() {
    init();
  }

  private void init() {
    detailsButton = NodeProvider.createToolbarButton("Detalii", ImageProvider.detailsIcon());
    continueButton = NodeProvider.createToolbarButton("Continua", null);
    toolBar.getItems().addAll(new Separator(), detailsButton, new FillToolItem(), continueButton);

    carsTable = NodeProvider.createCarTable();
    borderPane.setCenter(carsTable);
  }

  public TableView<CarModel> getCarsTable() {
    return carsTable;
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
