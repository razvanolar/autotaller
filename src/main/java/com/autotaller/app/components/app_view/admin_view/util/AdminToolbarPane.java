package com.autotaller.app.components.app_view.admin_view.util;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Created by razvanolar on 26.04.2017
 */
public class AdminToolbarPane implements View {

  protected VBox mainContainer;
  protected HBox toolbarContainer;
  protected SplitPane content;

  protected Button addButton;
  protected Button editButton;
  protected Button deleteButton;
  protected ToggleButton filterButton;
  private HBox imageContainer;
  private ImageView plusImageView;
  private ImageView minusImageView;

  private ScrollPane filterScrollPane;
  protected GridPane filterPane;

  private String title;

  private Timeline showContentTimeline;
  private Timeline hideContentTimeline;

  private double lastDividerPosition = .25;
  protected static int FIELD_WIDTH = 200;

  private boolean showToolButtons;

  public AdminToolbarPane(String title, boolean showToolButtons, boolean expandedByDefault) {
    this.title = title;
    this.showToolButtons = showToolButtons;
    init();
    initHandlers();

    if (expandedByDefault) {
      imageContainer.getChildren().clear();
      mainContainer.getChildren().add(content);
      imageContainer.getChildren().add(minusImageView);
      getShowContentTimeline().play();
    }
  }

  private void init() {
    plusImageView = new ImageView(ImageProvider.plusIcon());
    minusImageView = new ImageView(ImageProvider.minusIcon());

    imageContainer = new HBox(plusImageView);
    imageContainer.setAlignment(Pos.CENTER);
    imageContainer.setPadding(new Insets(0, 20, 0, 0));

    content = new SplitPane();

    Text textLabel = NodeProvider.createTextLabel(title, 16, false);
    HBox textContainer = new HBox(textLabel);
    textContainer.setPadding(new Insets(0, 0, 0, 20));
    textContainer.setAlignment(Pos.CENTER_LEFT);
    textContainer.setPrefWidth(150);

    toolbarContainer = new HBox(textContainer);
    if (showToolButtons) {
      addButton = NodeProvider.createToolbarButton("Adauga", ImageProvider.addIcon());
      editButton = NodeProvider.createToolbarButton("Editeaza", ImageProvider.editIcon());
      deleteButton = NodeProvider.createToolbarButton("Sterge", ImageProvider.deleteIcon());
      filterButton = NodeProvider.createToolbarToggleButton("Filtreaza", ImageProvider.filterIcon());
      Separator separator1 = new Separator(Orientation.VERTICAL);
      separator1.setPadding(new Insets(3, 0, 3, 0));
      Separator separator2 = new Separator(Orientation.VERTICAL);
      separator2.setPadding(new Insets(3, 0, 3, 0));
      toolbarContainer.getChildren().addAll(separator1, addButton, editButton, deleteButton, separator2, filterButton);
    }
    toolbarContainer.getChildren().addAll(new FillToolItem(), imageContainer);
    toolbarContainer.setPrefHeight(35);
    toolbarContainer.setAlignment(Pos.CENTER);
    toolbarContainer.setSpacing(10);
    toolbarContainer.getStyleClass().add(StyleProvider.ADMIN_TOOLBAR_PANE_CLASS);
    toolbarContainer.getStyleClass().add(StyleProvider.BUTTONS_CONTAINER_CLASS);

    mainContainer = new VBox(10, toolbarContainer);
    mainContainer.setAlignment(Pos.CENTER);

    mainContainer.setPadding(new Insets(5));

    filterPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    StackPane content = new StackPane(filterPane);
    filterScrollPane = NodeProvider.createScrollPane(content, true);
  }

  private void initHandlers() {
    toolbarContainer.setOnMouseClicked(event -> {
      if (event.getButton() == MouseButton.PRIMARY) {
        imageContainer.getChildren().clear();
        if (mainContainer.getChildren().contains(content)) {
          getHideTimeline().play();
          imageContainer.getChildren().add(plusImageView);
        } else {
          mainContainer.getChildren().add(content);
          imageContainer.getChildren().add(minusImageView);
          getShowContentTimeline().play();
        }
      }
    });

    if (showToolButtons) {
      filterButton.setOnAction(event -> {
        if (filterButton.isSelected()) {
          showFilterPane();
        } else {
          hideFilterPane();
        }
      });
    }
  }

  public void showFilterPane() {
    if (!content.getItems().contains(filterScrollPane)) {
      content.getItems().add(0, filterScrollPane);
      content.setDividerPositions(lastDividerPosition);
    }
  }

  public void hideFilterPane() {
    if (content.getItems().contains(filterScrollPane)) {
      double[] dividers = content.getDividerPositions();
      if (dividers != null && dividers.length > 0)
        lastDividerPosition = dividers[0];
      content.getItems().remove(filterScrollPane);
    }
  }

  private Timeline getHideTimeline() {
    if (hideContentTimeline == null) {
      hideContentTimeline = new Timeline(
              new KeyFrame(Duration.ZERO, new KeyValue(content.prefHeightProperty(), 500)),
              new KeyFrame(Duration.millis(300), new KeyValue(content.prefHeightProperty(), 0))
      );
      hideContentTimeline.setOnFinished(event -> mainContainer.getChildren().remove(content));
    }
    return hideContentTimeline;
  }

  private Timeline getShowContentTimeline() {
    if (showContentTimeline == null) {
      showContentTimeline = new Timeline(
              new KeyFrame(Duration.ZERO, new KeyValue(content.prefHeightProperty(), 0)),
              new KeyFrame(Duration.millis(300), new KeyValue(content.prefHeightProperty(), 500))
      );
    }
    return showContentTimeline;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
