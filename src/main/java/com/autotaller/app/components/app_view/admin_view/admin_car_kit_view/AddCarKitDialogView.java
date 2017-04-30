package com.autotaller.app.components.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AddCarKitDialogView implements AddCarKitDialogController.IAddCarKitDialogView {

  private GridPane gridPane;
  private ComboBox<CarKitCategoryModel> carKitCategoriesCombo;
  private TextField carKitNameField;

  public static int FIELD_WIDTH = 220;

  public AddCarKitDialogView() {
    init();
  }

  private void init() {
    carKitCategoriesCombo = NodeProvider.createCarKitCategoriesCombo(FIELD_WIDTH);
    carKitNameField = NodeProvider.createTextField(FIELD_WIDTH);

    gridPane = new GridPane();
    gridPane.add(NodeProvider.createFormTextLabel("Categorie: "), 0 ,0);
    gridPane.add(carKitCategoriesCombo, 1, 0);
    gridPane.add(NodeProvider.createFormTextLabel("Nume: "), 0, 1);
    gridPane.add(carKitNameField, 1, 1);

    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);
  }

  public ComboBox<CarKitCategoryModel> getCarKitCategoriesCombo() {
    return carKitCategoriesCombo;
  }

  public TextField getCarKitNameField() {
    return carKitNameField;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
