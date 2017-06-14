package com.autotaller.app.components.app_view.admin_view.admin_define_model_view;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminDefineAllContextView extends IterableView implements AdminDefineAllContextController.IAdminDefineAllContextView {

  private BorderPane container;
  private Button refreshButton;
  private Button addButton;
  private Button editButton;
  private Button deleteButton;
  private Button filterButton;
  private ToggleButton showCarMakePaneButton;
  private ToggleButton showCarTypePaneButton;
  private ToggleButton showCarKitPaneButton;
  private ToggleButton showCarSubKitPaneButton;

  public AdminDefineAllContextView() {
    super();
    init();
  }

  private void init() {
    refreshButton = NodeProvider.createToolbarButton("Reincarca", null);
    addButton = NodeProvider.createToolbarButton("Adauga", ImageProvider.addIcon());
    editButton = NodeProvider.createToolbarButton("Editeaza", ImageProvider.editIcon());
    deleteButton = NodeProvider.createToolbarButton("Sterge", ImageProvider.deleteIcon());
    filterButton = NodeProvider.createToolbarButton("Filtreaza", ImageProvider.filterIcon());
    showCarMakePaneButton = NodeProvider.createToolbarToggleButton("Marca", null);
    showCarTypePaneButton = NodeProvider.createToolbarToggleButton("Model", null);
    showCarKitPaneButton = NodeProvider.createToolbarToggleButton("Ansamblu", null);
    showCarSubKitPaneButton = NodeProvider.createToolbarToggleButton("Sub Ansamblu", null);
    toolBar.getItems().addAll(new Separator(), refreshButton, new Separator(), addButton, editButton, deleteButton,
            new Separator(), filterButton, new FillToolItem(), showCarMakePaneButton, showCarTypePaneButton,
            showCarKitPaneButton, showCarSubKitPaneButton);

    container = NodeProvider.createBorderPane();

    ToggleGroup group = new ToggleGroup();
    group.getToggles().addAll(showCarMakePaneButton, showCarTypePaneButton, showCarKitPaneButton, showCarSubKitPaneButton);

    borderPane.setCenter(NodeProvider.createScrollPane(container, true));
    mainContainer.getStyleClass().add(StyleProvider.GENERAL_PANE_BACKGROUND_CLASS);
  }

  @Override
  public void setContent(Node content) {
    container.setCenter(content);
  }

  public Button getRefreshButton() {
    return refreshButton;
  }

  public Button getAddButton() {
    return addButton;
  }

  public Button getEditButton() {
    return editButton;
  }

  public Button getDeleteButton() {
    return deleteButton;
  }

  public Button getFilterButton() {
    return filterButton;
  }

  public ToggleButton getShowCarMakePaneButton() {
    return showCarMakePaneButton;
  }

  public ToggleButton getShowCarTypePaneButton() {
    return showCarTypePaneButton;
  }

  public ToggleButton getShowCarKitPaneButton() {
    return showCarKitPaneButton;
  }

  public ToggleButton getShowCarSubKitPaneButton() {
    return showCarSubKitPaneButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
