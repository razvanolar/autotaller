package com.autotaller.app.components.utils;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.GetStageInstanceEvent;
import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXDialog;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Created by razvanolar on 28.05.2017
 */
public class ImageGalleryDialog extends JFXDialog {

  private BorderPane mainContainer;
  private ScrollPane displayImageScrollPane;
  private HBox previewImagesPane;

  private PreviewImageNode lastSelectedNode;

  public ImageGalleryDialog(List<Image> images) {
    init();
    initPreviewImages(images);

    this.setContent(mainContainer);
    this.setTransitionType(JFXDialog.DialogTransition.CENTER);

    EventBus.fireEvent(new GetStageInstanceEvent(stage -> {
      mainContainer.prefWidthProperty().bind(stage.widthProperty().subtract(150));
      mainContainer.prefHeightProperty().bind(stage.heightProperty().subtract(100));
    }));
  }

  private void init() {
    displayImageScrollPane = NodeProvider.createScrollPane(null, true);

    GridPane displayImagePane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    displayImagePane.add(displayImageScrollPane, 0, 0);

    previewImagesPane = NodeProvider.createHBox(Pos.BOTTOM_CENTER, 10);

    mainContainer = new BorderPane(displayImagePane);
    mainContainer.setBottom(NodeProvider.createScrollPane(previewImagesPane, true));

    previewImagesPane.setPadding(new Insets(5));
  }

  private void initPreviewImages(List<Image> images) {
    if (images == null || images.isEmpty()) {
      mainContainer.setCenter(NodeProvider.createTextLabel("Nici o imagine disponibila", 17, false));
      return;
    }

    EventHandler<MouseEvent> mouseEvent = event -> {
      if (lastSelectedNode != null)
        lastSelectedNode.unselect();
      PreviewImageNode node = (PreviewImageNode) event.getSource();
      node.select();
      displayImageScrollPane.setContent(new ImageView(node.getImageView().getImage()));
      lastSelectedNode = node;
    };

    for (Image image : images) {
      ImageView imageView = new ImageView(image);
      imageView.setFitHeight(100);
      imageView.setPreserveRatio(true);
      PreviewImageNode previewImageNode = new PreviewImageNode(5, Pos.BOTTOM_CENTER, imageView);
      previewImagesPane.getChildren().add(previewImageNode);
      previewImageNode.setOnMouseClicked(mouseEvent);

      if (lastSelectedNode == null)
        lastSelectedNode = previewImageNode;
    }

    lastSelectedNode.select();
    displayImageScrollPane.setContent(new ImageView(lastSelectedNode.getImageView().getImage()));
  }

  @Override
  public void close() {
    mainContainer.prefWidthProperty().unbind();
    mainContainer.prefHeightProperty().unbind();
    super.close();
  }

  class PreviewImageNode extends VBox {

    private ImageView imageView;

    public PreviewImageNode(int spacing, Pos pos, ImageView imageView) {
      super(spacing);
      this.setAlignment(pos);
      this.imageView = imageView;
      this.getChildren().add(imageView);
    }

    public void select() {
      FillToolItem bar = new FillToolItem();
      bar.setPrefHeight(5);
      bar.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
      this.getChildren().add(0, bar);
    }

    public void unselect() {
      this.getChildren().clear();
      this.getChildren().add(imageView);
    }

    public ImageView getImageView() {
      return imageView;
    }
  }
}
