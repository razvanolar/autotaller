package com.autotaller.app.components.app_view.admin_view.admin_components_view.utils;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Created by razvanolar on 24.05.2017
 */
public class CarKitCategoriesTabView {

  private VBox mainContainer;
  private HBox kitCategoriesContainer;
  private HBox kitsContainer;

  private SystemModelsDTO systemModelsDTO;
  private EventHandler<MouseEvent> kitCategoryMouseClickEvent;
  private EventHandler<MouseEvent> kitMouseClickEvent;

  private VBox lastSelectedCarKitCategory;
  private VBox lastSelectedCarKit;

  private KitSelectionListener listener;

  public CarKitCategoriesTabView(SystemModelsDTO systemModelsDTO) {
    this.systemModelsDTO = systemModelsDTO;

    kitCategoryMouseClickEvent = event -> {
      Object source = event.getSource();
      if (!(source instanceof VBox))
        return;
      onKitCategorySelection((VBox) source);
    };

    kitMouseClickEvent = event -> {
      Object source = event.getSource();
      if (!(source instanceof VBox)) {
        lastSelectedCarKit = null;
        return;
      }
      onKitSelection((VBox) source);
    };

    init();
  }

  private void init() {
    kitCategoriesContainer = new HBox();
    kitsContainer = new HBox();

    List<CarKitCategoryModel> carKitCategories = systemModelsDTO.getCarKitCategories();
    for (int i = 0; i < carKitCategories.size(); i++) {
      kitCategoriesContainer.getChildren().add(createKitCategoryNode(carKitCategories.get(i)));
      if (i < carKitCategories.size() - 1)
        kitCategoriesContainer.getChildren().add(createKitCategorySeparatorNode());
    }

    kitCategoriesContainer.setAlignment(Pos.CENTER);
    kitsContainer.setAlignment(Pos.CENTER);

    mainContainer = new VBox(0, kitCategoriesContainer, kitsContainer);
    mainContainer.setAlignment(Pos.CENTER);
    kitsContainer.prefWidthProperty().bind(mainContainer.prefWidthProperty());

    kitCategoriesContainer.getStyleClass().add(StyleProvider.KIT_CATEGORY_TAB_BACKGROUND_CLASS);
    kitsContainer.getStyleClass().add(StyleProvider.KIT_TAB_BACKGROUND_CLASS);
  }

  private void onKitCategorySelection(VBox node) {
    if (node.getUserData() == null || !(node.getUserData() instanceof CarKitCategoryModel))
      return;

    setCarKitCategoryNodeColor(node, Color.ORANGE, StyleProvider.KIT_CATEGORY_TAB_SELECTED_BACKGROUND_CLASS);
    setCarKitCategoryNodeColor(lastSelectedCarKitCategory, Color.BLACK, StyleProvider.KIT_CATEGORY_TAB_BACKGROUND_CLASS);
    lastSelectedCarKitCategory = node;

    kitsContainer.getChildren().clear();
    CarKitCategoryModel carKitCategory = (CarKitCategoryModel) node.getUserData();
    List<CarKitModel> carKits = systemModelsDTO.getCarKitsByCategory(carKitCategory);
    if (carKits == null || carKits.isEmpty()) {
      kitsContainer.getChildren().add(NodeProvider.createTextLabel("Nici un ansamblu definit pentru acest nivel", 15, false));
      onKitSelection(null);
      return;
    }
    for (int i = 0; i < carKits.size(); i++) {
      kitsContainer.getChildren().add(createKitNode(carKits.get(i)));
      if (i < carKits.size() - 1)
        kitsContainer.getChildren().add(createKitSeparatorNode());
    }
    onKitSelection((VBox) kitsContainer.getChildren().get(0));
  }

  private void onKitSelection(VBox node) {
    if (node == null || node.getUserData() == null || !(node.getUserData() instanceof CarKitModel)) {
      lastSelectedCarKit = null;
      if (listener != null)
        listener.onKitSelection(null);
      return;
    }

    setCarKitNodeColor(node, Color.ORANGE);
    setCarKitNodeColor(lastSelectedCarKit, Color.BLACK);
    lastSelectedCarKit = node;
    if (listener != null) {
      listener.onKitSelection((CarKitModel) node.getUserData());
    }
  }

  private VBox createKitCategoryNode(CarKitCategoryModel carKitCategory) {
    VBox node = new VBox(0);
    Rectangle rectangle = new Rectangle(5, 5);
    node.getChildren().addAll(rectangle, NodeProvider.createTextLabel(carKitCategory.getName(), 15, false));
    node.setPrefHeight(35);
    node.setAlignment(Pos.CENTER);
    rectangle.widthProperty().bind(node.widthProperty());
    node.getStyleClass().add(StyleProvider.KIT_CATEGORY_TAB_BACKGROUND_CLASS);
    node.setOnMouseClicked(kitCategoryMouseClickEvent);
    node.setUserData(carKitCategory);
    return node;
  }

  private VBox createKitCategorySeparatorNode() {
    int width = 10;
    VBox node = new VBox(0);
    Rectangle rectangle = new Rectangle(width, 5);
    node.getChildren().addAll(rectangle, NodeProvider.createTextLabel("|", 15, false));
    node.setPrefSize(width, 35);
    node.setAlignment(Pos.CENTER);
    node.getStyleClass().add(StyleProvider.KIT_CATEGORY_TAB_BACKGROUND_CLASS);
    return node;
  }

  private VBox createKitNode(CarKitModel carKit) {
    VBox node = new VBox(0);
    Rectangle rectangle = new Rectangle(5, 5);
    node.getChildren().addAll(NodeProvider.createTextLabel(carKit.getName(), 15, false), rectangle);
    node.setPrefHeight(35);
    node.setAlignment(Pos.CENTER);
    rectangle.widthProperty().bind(node.widthProperty());
    node.getStyleClass().add(StyleProvider.KIT_TAB_BACKGROUND_CLASS);
    node.setOnMouseClicked(kitMouseClickEvent);
    node.setUserData(carKit);
    return node;
  }

  private VBox createKitSeparatorNode() {
    int width = 10;
    VBox node = new VBox(0);
    Rectangle rectangle = new Rectangle(width, 5);
    node.getChildren().addAll(NodeProvider.createTextLabel("|", 15, false), rectangle);
    node.setPrefSize(width, 35);
    node.setAlignment(Pos.CENTER);
    node.getStyleClass().add(StyleProvider.KIT_TAB_BACKGROUND_CLASS);
    return node;
  }

  private void setCarKitCategoryNodeColor(VBox node, Color color, String style) {
    if (node == null || node.getChildren().isEmpty())
      return;
    node.getStyleClass().clear();
    node.getStyleClass().add(style);
    Node bar = node.getChildren().get(0);
    if (!(bar instanceof Rectangle))
      return;
    Rectangle rectangle = (Rectangle) bar;
    rectangle.setFill(color);
  }

  private void setCarKitNodeColor(VBox node, Color color) {
    if (node == null || node.getChildren().isEmpty())
      return;
    Node bar = node.getChildren().get(1);
    if (!(bar instanceof Rectangle))
      return;
    Rectangle rectangle = (Rectangle) bar;
    rectangle.setFill(color);
  }

  private void setDefaultSelection() {
    if (kitCategoriesContainer.getChildren().isEmpty() || !(kitCategoriesContainer.getChildren().get(0) instanceof VBox))
      return;
    onKitCategorySelection((VBox) kitCategoriesContainer.getChildren().get(0));
  }

  public void registerListener(KitSelectionListener listener) {
    this.listener = listener;
    setDefaultSelection();
  }

  public void previousKit() {
    ObservableList<Node> kits = kitsContainer.getChildren();
    int kitSize = kits.size();
    // go to previous kit category
    if (lastSelectedCarKit == null || kits.indexOf(lastSelectedCarKit) == 0) {
      ObservableList<Node> kitCategories = kitCategoriesContainer.getChildren();
      int kitCategorySize = kitCategories.size();
      if (lastSelectedCarKitCategory != null) {
        int lastKitCategoryIndex = kitCategories.indexOf(lastSelectedCarKitCategory);
        if (lastKitCategoryIndex - 2 >= 0) {
          onKitCategorySelection((VBox) kitCategories.get(lastKitCategoryIndex - 2));
        }
      }
    }
    // go to previous kit
    else {
      int lastKitIndex = kits.indexOf(lastSelectedCarKit);
      if (lastKitIndex - 2 >= 0) {
        onKitSelection((VBox) kits.get(lastKitIndex - 2));
      }
    }
  }

  public void nextKit() {
    ObservableList<Node> kits = kitsContainer.getChildren();
    int kitSize = kits.size();
    // go to next kit category
    if (lastSelectedCarKit == null || kits.indexOf(lastSelectedCarKit) == kitSize - 1) {
      ObservableList<Node> kitCategories = kitCategoriesContainer.getChildren();
      int kitCategorySize = kitCategories.size();
      if (lastSelectedCarKitCategory != null) {
        int lastKitCategoryIndex = kitCategories.indexOf(lastSelectedCarKitCategory);
        if (lastKitCategoryIndex >= 0 && lastKitCategoryIndex < kitCategorySize && lastKitCategoryIndex + 2 < kitCategorySize) {
          onKitCategorySelection((VBox) kitCategories.get(lastKitCategoryIndex + 2));
        }
      }
    }
    // go to next kit
    else {
      int lastKitIndex = kits.indexOf(lastSelectedCarKit);
      if (lastKitIndex >= 0 && lastKitIndex + 2 < kitSize) {
        onKitSelection((VBox) kits.get(lastKitIndex + 2));
      }
    }
  }

  public Region asNode() {
    return mainContainer;
  }
}
