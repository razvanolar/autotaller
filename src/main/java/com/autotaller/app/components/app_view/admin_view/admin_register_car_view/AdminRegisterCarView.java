package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.app_view.utils.DefaultCarView;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminRegisterCarView extends IterableView implements AdminRegisterCarController.IAdminRegisterCarView {

  private DefaultCarView defaultCarView;
  private Button addCarButton;
  private Button editCarButton;
  private Button deleteCarButton;
  private ToggleButton showFilterCarButton;
  private Button carDetailsButton;
  private Button componentsButton;
  private Button statisticsButton;

  public AdminRegisterCarView() {
    init();
  }

  private void init() {
    addCarButton = NodeProvider.createToolbarButton("Adauga", ImageProvider.addIcon());
    editCarButton = NodeProvider.createToolbarButton("Editeaza", ImageProvider.editIcon());
    deleteCarButton = NodeProvider.createToolbarButton("Sterge", ImageProvider.deleteIcon());
    showFilterCarButton = NodeProvider.createToolbarToggleButton("Filtreaza", ImageProvider.filterIcon());
    carDetailsButton = NodeProvider.createToolbarButton("Detalii", ImageProvider.detailsIcon());
    componentsButton = NodeProvider.createToolbarButton("Componente", ImageProvider.componentsIcon());
    statisticsButton = NodeProvider.createToolbarButton("Statistici", null);

    toolBar.getItems().addAll(
            new Separator(),
            addCarButton,
            editCarButton,
            deleteCarButton,
            new Separator(),
            showFilterCarButton,
            new Separator(),
            carDetailsButton,
            componentsButton,
            statisticsButton
    );

    defaultCarView = new DefaultCarView();
    borderPane.setCenter(defaultCarView.asNode());
  }

  public DefaultCarView getDefaultCarView() {
    return defaultCarView;
  }

  public Button getAddCarButton() {
    return addCarButton;
  }

  public Button getEditCarButton() {
    return editCarButton;
  }

  public Button getDeleteCarButton() {
    return deleteCarButton;
  }

  public ToggleButton getShowFilterCarButton() {
    return showFilterCarButton;
  }

  public Button getCarDetailsButton() {
    return carDetailsButton;
  }

  public Button getComponentsButton() {
    return componentsButton;
  }

  public Button getStatisticsButton() {
    return statisticsButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
