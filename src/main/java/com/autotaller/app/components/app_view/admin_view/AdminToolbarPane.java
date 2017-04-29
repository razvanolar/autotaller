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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Make sure to call setContentNode method after the components of the child class were initialized
 *
 * Created by razvanolar on 26.04.2017
 */
public class AdminToolbarPane implements View {

  protected VBox mainContainer;
  protected HBox toolbarContainer;
  private HBox imageContainer;
  private ImageView plusImageView;
  private ImageView minusImageView;

  private String title;
  protected Region content;

  private Timeline showContentTimeline;
  private Timeline hideContentTimeline;

  public AdminToolbarPane(String title) {
    this.title = title;
    init();
    initHandlers();
  }

  private void init() {
    plusImageView = new ImageView(ImageProvider.plusIcon());
    minusImageView = new ImageView(ImageProvider.minusIcon());

    imageContainer = new HBox(plusImageView);
    imageContainer.setAlignment(Pos.CENTER);
    imageContainer.setPadding(new Insets(0, 20, 0, 0));

    Text textLabel = NodeProvider.createTextLabel(title, 16, false);
    HBox textContainer = new HBox(textLabel);
    textContainer.setPadding(new Insets(0, 0, 0, 20));
    textContainer.setAlignment(Pos.CENTER);

    toolbarContainer = new HBox(textContainer);
    addToolbarButtons();
    toolbarContainer.getChildren().addAll(new FillToolItem(), imageContainer);
    toolbarContainer.setPrefHeight(35);
    toolbarContainer.setAlignment(Pos.CENTER);
    toolbarContainer.setSpacing(10);
    toolbarContainer.getStyleClass().add(StyleProvider.ADMIN_TOOLBAR_PANE_CLASS);
    toolbarContainer.getStyleClass().add(StyleProvider.BUTTONS_CONTAINER_CLASS);

    mainContainer = new VBox(10, toolbarContainer);
    mainContainer.setAlignment(Pos.CENTER);

    mainContainer.setPadding(new Insets(5));
  }

  protected void addToolbarButtons() {

  }

  protected void setContentNode(Region content) {
    this.content = content;
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
