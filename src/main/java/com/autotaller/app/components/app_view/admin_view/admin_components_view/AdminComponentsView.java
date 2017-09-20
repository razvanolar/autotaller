package com.autotaller.app.components.app_view.admin_view.admin_components_view;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 13.05.2017
 */
public class AdminComponentsView extends IterableView implements AdminComponentsController.IAdminComponentsView {

  private SplitPane mainSplitPane;
  private GridPane filterPane;
  private TableView<CarComponentModel> carComponentsTable;

  private Button addComponentButton;
  private Button editComponentButton;
  private Button deleteComponentButton;
  private ToggleButton filterComponentsButton;
  private Button detailsButton;

  private Text pagesCounterLabel;
  private Button previousPageButton;
  private Button nextPageButton;

  private double lastDividerPosition = 0.3;
  private Separator separator;

  public AdminComponentsView() {
    init();
  }

  private void init() {
    addComponentButton = NodeProvider.createToolbarButton("Adauga", ImageProvider.addIcon());
    editComponentButton = NodeProvider.createToolbarButton("Editeaza", ImageProvider.editIcon());
    deleteComponentButton = NodeProvider.createToolbarButton("Sterge", ImageProvider.deleteIcon());
    filterComponentsButton = NodeProvider.createToolbarToggleButton("Filtreaza", ImageProvider.filterIcon());
    detailsButton = NodeProvider.createToolbarButton("Detalii", ImageProvider.detailsIcon());
    pagesCounterLabel = NodeProvider.createTextLabel("Pagina 0/0", 12, false);
    previousPageButton = NodeProvider.createToolbarButton("Pagina Anterioara", ImageProvider.backIcon());
    nextPageButton = NodeProvider.createToolbarButton("Pagina Urmatoare", ImageProvider.nextIcon());

    separator = new Separator();
    toolBar.getItems().addAll(new Separator(), addComponentButton, editComponentButton, deleteComponentButton,
            new Separator(), filterComponentsButton, detailsButton, new FillToolItem(), pagesCounterLabel,
            separator, previousPageButton, nextPageButton);

    carComponentsTable = NodeProvider.createCarComponentTable(true);
    filterPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);

    mainSplitPane = new SplitPane(carComponentsTable);
    mainSplitPane.setDividerPosition(0, lastDividerPosition);

    borderPane.setCenter(mainSplitPane);
    nextPageButton.setContentDisplay(ContentDisplay.RIGHT);
  }

  public void hidePaginatingComponents() {
    toolBar.getItems().removeAll(pagesCounterLabel, separator, previousPageButton, nextPageButton);
  }

  public Button getAddComponentButton() {
    return addComponentButton;
  }

  public Button getEditComponentButton() {
    return editComponentButton;
  }

  public Button getDeleteComponentButton() {
    return deleteComponentButton;
  }

  public ToggleButton getFilterComponentsButton() {
    return filterComponentsButton;
  }

  public Button getDetailsButton() {
    return detailsButton;
  }

  public TableView<CarComponentModel> getCarComponentsTable() {
    return carComponentsTable;
  }

  public Text getPagesCounterLabel() {
    return pagesCounterLabel;
  }

  public Button getPreviousPageButton() {
    return previousPageButton;
  }

  public Button getNextPageButton() {
    return nextPageButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
