package com.autotaller.app.components.app_view.cars_view.search_car_make_view;

import com.autotaller.app.components.utils.ChipSet;
import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarMakeView extends IterableView implements SearchCarMakeController.ISearchCarMakeView {

  private BorderPane container;
  private HBox chipSetContainer;
  private TableView<CarMakeModel> carMakeTable;
  private Button continueButton;

  public SearchCarMakeView() {
    init();
  }

  private void init() {
    continueButton = NodeProvider.createToolbarButton("Continua", null);
    toolBar.getItems().addAll(new Separator(), new FillToolItem(), continueButton);

    chipSetContainer = new HBox(15);
    chipSetContainer.setPadding(new Insets(3));

    carMakeTable = NodeProvider.createCarMakeTable();
    container = NodeProvider.createBorderPane();

    container.setCenter(carMakeTable);
    container.setTop(chipSetContainer);
    borderPane.setCenter(container);
  }

  @Override
  public void addChipSet(ChipSet chipSet) {
    chipSetContainer.getChildren().add(chipSet.asNode());
  }

  public TableView<CarMakeModel> getCarMakeTable() {
    return carMakeTable;
  }

  public Button getContinueButton() {
    return continueButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
