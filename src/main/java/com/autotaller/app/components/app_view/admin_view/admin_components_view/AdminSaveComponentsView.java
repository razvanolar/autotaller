package com.autotaller.app.components.app_view.admin_view.admin_components_view;

import com.autotaller.app.components.app_view.admin_view.admin_components_view.utils.CarKitCategoriesTabView;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.utils.KitSelectionListener;
import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

/**
 * Created by razvanolar on 21.05.2017
 */
public class AdminSaveComponentsView extends IterableView implements AdminSaveComponentsController.IAdminSaveComponentsView {

  private TableView<CarComponentModel> carComponentTable;
  private CarKitCategoriesTabView carKitCategoriesTabView;
  private Button previousKitButton;
  private Button nextKitButton;
  private Button saveButton;

  private BorderPane saveBorderPane;

  public AdminSaveComponentsView() {
    init();
  }

  private void init() {
    carComponentTable = NodeProvider.createCarComponentTable(true);
    carComponentTable.setEditable(true);

    previousKitButton = NodeProvider.createToolbarButton("Ansamblul Anterior", ImageProvider.backIcon());
    nextKitButton = NodeProvider.createToolbarButton("Ansamblul Urmator", ImageProvider.nextIcon());
    saveButton = NodeProvider.createToolbarButton("Salveaza", null);

    nextKitButton.setContentDisplay(ContentDisplay.RIGHT);
    toolBar.getItems().addAll(new Separator(), new FillToolItem(), new Separator(), previousKitButton, nextKitButton,
            new Separator(), saveButton);

    saveBorderPane = new BorderPane(carComponentTable);

    borderPane.setCenter(saveBorderPane);
  }

  public TableView<CarComponentModel> getCarComponentTable() {
    return carComponentTable;
  }

  public void initButtonsBar(SystemModelsDTO systemModelsDTO) {
    carKitCategoriesTabView = new CarKitCategoriesTabView(systemModelsDTO);
    Region node = carKitCategoriesTabView.asNode();
    saveBorderPane.setTop(node);
    node.prefWidthProperty().bind(saveBorderPane.widthProperty());
  }

  public Button getPreviousKitButton() {
    return previousKitButton;
  }

  public Button getNextKitButton() {
    return nextKitButton;
  }

  public Button getSaveButton() {
    return saveButton;
  }

  @Override
  public void previousKit() {
    if (carKitCategoriesTabView != null)
      carKitCategoriesTabView.previousKit();
  }

  @Override
  public void nextKit() {
    if (carKitCategoriesTabView != null)
      carKitCategoriesTabView.nextKit();
  }

  @Override
  public void registerListener(KitSelectionListener listener) {
    carKitCategoriesTabView.registerListener(listener);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
