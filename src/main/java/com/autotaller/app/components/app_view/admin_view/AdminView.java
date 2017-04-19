package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.View;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminView extends IterableView implements AdminController.IAdminView {

  private ToggleButton addCarMakeButton;
  private ToggleButton addCarModelButton;
  private ToggleButton addCarButton;

  public AdminView() {
    super();
    init();
  }

  private void init() {
    addCarMakeButton = new ToggleButton("Marca");
    addCarModelButton = new ToggleButton("Model");
    addCarButton = new ToggleButton("Masina");
    toolBar.getItems().addAll(
              new Separator(),
              new Text("Inregistrare: "),
              addCarMakeButton,
              addCarModelButton,
              addCarButton
            );

    ToggleGroup group = new ToggleGroup();
    group.getToggles().addAll(addCarMakeButton, addCarModelButton, addCarButton);
  }

  @Override
  public void setContent(View view) {
    Node node = view.asNode();
    Parent parent = (Parent) node;
    mainContainer.setCenter(node);
  }

  public ToggleButton getAddCarMakeButton() {
    return addCarMakeButton;
  }

  public ToggleButton getAddCarModelButton() {
    return addCarModelButton;
  }

  public ToggleButton getAddCarButton() {
    return addCarButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
