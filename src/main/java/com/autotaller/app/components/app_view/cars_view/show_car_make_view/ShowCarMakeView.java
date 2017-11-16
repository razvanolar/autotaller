package com.autotaller.app.components.app_view.cars_view.show_car_make_view;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by razvanolar on 02.06.2017
 */
public class ShowCarMakeView extends IterableView implements ShowCarMakeController.IShowCarMakeView {

  private BorderPane container;
  private BorderPane filterHeader;
  private TextField carMakeNameField;
  private Button searchCarMakeButton;
  private TableView<CarMakeModel> carMakeTable;
  private Button continueButton;

  public ShowCarMakeView() {
    init();
  }

  private void init() {
    carMakeNameField = NodeProvider.createTextField();
    searchCarMakeButton = NodeProvider.createButton("Cauta");
    continueButton = NodeProvider.createToolbarButton("Continua", null);
    toolBar.getItems().addAll(new Separator(), new FillToolItem(), continueButton);

    HBox filterPane = NodeProvider.createHBox(Pos.CENTER, 10, NodeProvider.createFormTextLabel("Nume marca: "), carMakeNameField, searchCarMakeButton);
    filterPane.setPadding(new Insets(5, 0, 5, 0));

    carMakeTable = NodeProvider.createCarMakeTable();
    container = NodeProvider.createBorderPane();
    filterHeader = NodeProvider.createBorderPane();

    filterHeader.setTop(filterPane);

    container.setCenter(carMakeTable);
    container.setTop(filterHeader);
    borderPane.setCenter(container);
  }

  public TableView<CarMakeModel> getCarMakeTable() {
    return carMakeTable;
  }

  public Button getContinueButton() {
    return continueButton;
  }

  public TextField getCarMakeNameField() {
    return carMakeNameField;
  }

  public Button getSearchCarMakeButton() {
    return searchCarMakeButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
