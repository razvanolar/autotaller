package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view;

import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 19.04.2017
 */
public class AddCarMakeDialogView implements AddCarMakeDialogController.IAddCarMakeDialogView {

  private GridPane gridPane;
  private JFXTextField carMakeNameField;

  public AddCarMakeDialogView() {
    init();
  }

  private void init() {
    carMakeNameField = NodeProvider.createMaterialTextField("Nume Marca", true, 350);
    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.add(carMakeNameField, 0, 0);
    gridPane.setPadding(new Insets(15, 0, 10 ,0));
  }

  public TextField getCarMakeNameField() {
    return carMakeNameField;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
