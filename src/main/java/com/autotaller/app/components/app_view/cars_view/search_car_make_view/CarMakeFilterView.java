package com.autotaller.app.components.app_view.cars_view.search_car_make_view;

import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Created by razvanolar on 02.06.2017
 */
public class CarMakeFilterView implements View {

  private GridPane gridPane;
  private TextField carMakeNameTextField;
  private Button filterButton;

  public CarMakeFilterView() {
    init();
  }

  private void init() {
    carMakeNameTextField = NodeProvider.createTextField();
    filterButton = NodeProvider.createButton("Cauta");

    HBox hBox = NodeProvider.createHBox(Pos.CENTER_RIGHT, 10, filterButton);

    gridPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    gridPane.add(NodeProvider.createFormTextLabel("Nume Marca: "), 0, 0);
    gridPane.add(carMakeNameTextField, 1, 0);
    gridPane.add(hBox, 0, 1, 2, 1);
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
