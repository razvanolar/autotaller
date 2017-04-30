package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
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
  protected ToggleButton filterButton;
  private HBox imageContainer;
  private ImageView plusImageView;
  private ImageView minusImageView;

  private ScrollPane filterScrollPane;
  protected GridPane filterPane;

  private String title;
  private String addButtonText;
  private String filterButtonText;

  private Timeline showContentTimeline;
  private Timeline hideContentTimeline;

  private double lastDividerPosition = .25;
  protected static int FIELD_WIDTH = 200;

  public AdminToolbarPane(String title, String addButtonText, String filterButtonText) {
    this.title = title;
    this.addButtonText = addButtonText;
    this.filterButtonText = filterButtonText;
    init();
    initHandlers();
  }

  private void init() {
    plusImageView = new ImageView(ImageProvider.plusIcon());
    minusImageView = new ImageView(ImageProvider.minusIcon());

    addButton = new Button(addButtonText);
    filterButton = new ToggleButton(filterButtonText);

    imageContainer = new HBox(plusImageView);
    imageContainer.setAlignment(Pos.CENTER);
    imageContainer.setPadding(new Insets(0, 20, 0, 0));

    content = new SplitPane();

    Text textLabel = NodeProvider.createTextLabel(title, 16, false);
    HBox textContainer = new HBox(textLabel);
    textContainer.setPadding(new Insets(0, 0, 0, 20));
    textContainer.setAlignment(Pos.CENTER);

    toolbarContainer = new HBox(textContainer);
    toolbarContainer.getChildren().addAll(addButton, filterButton);
    toolbarContainer.getChildren().addAll(new FillToolItem(), imageContainer);
    toolbarContainer.setPrefHeight(35);
    toolbarContainer.setAlignment(Pos.CENTER);
    toolbarContainer.setSpacing(10);
    toolbarContainer.getStyleClass().add(StyleProvider.ADMIN_TOOLBAR_PANE_CLASS);
    toolbarContainer.getStyleClass().add(StyleProvider.BUTTONS_CONTAINER_CLASS);

    mainContainer = new VBox(10, toolbarContainer);
    mainContainer.setAlignment(Pos.CENTER);

    mainContainer.setPadding(new Insets(5));

    filterPane = new GridPane();
    filterPane.setAlignment(Pos.CENTER);
    filterPane.setVgap(10);
    filterPane.setHgap(10);
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

    filterButton.setOnAction(event -> {
      if (filterButton.isSelected()) {
        showFilterPane();
      } else {
        hideFilterPane();
      }
    });
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
