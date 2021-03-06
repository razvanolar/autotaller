package com.autotaller.app.components.utils;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.GetStageInstanceEvent;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by razvanolar on 27.05.2017
 */
public class ImageGalleryPane {

  private BorderPane mainContainer;
  private ScrollPane scrollPane;
  private FlowPane imageContainer;
  private Button uploadImagesButton;
  private Button clearImagesButton;
  private Button openGalleryButton;

  private GridPane defaulPane;
  private ImageView defaultImageView;

  private HashSet<File> images;

  public ImageGalleryPane() {
    images = new HashSet<>();
    init();
    addHandlers();
  }

  private void init() {
    defaultImageView = new ImageView(ImageProvider.cameraIcon());
    defaulPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    defaulPane.add(defaultImageView, 0, 0);
    defaulPane.add(NodeProvider.createFormTextLabel("Nici o imagine disponibila"), 0, 1);

    imageContainer = NodeProvider.createFlowPane(Pos.TOP_CENTER);
    imageContainer.setHgap(10);
    imageContainer.setVgap(10);

    uploadImagesButton = NodeProvider.createToolbarButton("Incarca Imagini", ImageProvider.uploadIcon());
    clearImagesButton = NodeProvider.createToolbarButton("Sterge Imaginile", ImageProvider.binIcon());
    openGalleryButton = NodeProvider.createToolbarButton("Deschide Galeria", ImageProvider.galleryIcon());

    ToolBar toolBar = NodeProvider.createToolBar();
    toolBar.getItems().addAll(uploadImagesButton, clearImagesButton, new Separator(), openGalleryButton);

    scrollPane = NodeProvider.createScrollPane(defaulPane, true);
    mainContainer = new BorderPane(scrollPane);
    mainContainer.setBottom(toolBar);
  }

  private void addHandlers() {
    EventHandler<MouseEvent> imageMouseEvent = mouseEvent -> {
      ImageView imageView = (ImageView) mouseEvent.getSource();
      EventBus.fireEvent(new ShowDialogEvent(new ImageDialog(imageView.getImage())));
    };

    uploadImagesButton.setOnAction(event -> EventBus.fireEvent(new GetStageInstanceEvent(stage -> {
      FileChooser fileChooser = new FileChooser();
      FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Imagini", "*.png", "*.jpg", "*.jpeg");
      fileChooser.getExtensionFilters().add(filter);
      List<File> files = fileChooser.showOpenMultipleDialog(stage);
      if (files != null && !files.isEmpty()) {
        List<File> filesCopy = new ArrayList<>(files);
        filesCopy.removeAll(images);
        for (File file : filesCopy) {
          ImageView imageView = new ImageView(ImageProvider.getImageFromPath(file.getAbsolutePath()));
          imageView.setPreserveRatio(true);
          imageView.setFitHeight(150);
          imageView.setOnMouseClicked(imageMouseEvent);
          imageContainer.getChildren().add(imageView);
          images.add(file);
        }
        showImageContainer();
      }
    })));

    clearImagesButton.setOnAction(event -> {
      if (images.isEmpty())
        return;
      YesNoDialog dialog = new YesNoDialog("Atentie", "Esti sigur ca doresti sa stergi toate imaginile din galerie?");
      EventBus.fireEvent(new ShowDialogEvent(dialog));
      dialog.getYesButton().setOnAction(event1 -> {
        imageContainer.getChildren().clear();
        images.clear();
        showPreviewImage();
        dialog.close();
      });
    });

    openGalleryButton.setOnAction(event -> {
      EventBus.fireEvent(new ShowDialogEvent(new ImageGalleryDialog(collectImages())));
    });
  }

  private List<Image> collectImages() {
    List<Image> result = new ArrayList<>();
    if (scrollPane.getContent() == imageContainer) {
      ObservableList<Node> children = imageContainer.getChildren();
      for (Node node : children) {
        result.add(((ImageView) node).getImage());
      }
    }
    return result;
  }

  private void showPreviewImage() {
    if (scrollPane.getContent() != defaulPane)
      scrollPane.setContent(defaulPane);
  }

  private void showImageContainer() {
    if (scrollPane.getContent() != imageContainer)
      scrollPane.setContent(imageContainer);
  }

  public List<File> getImages() {
    return new ArrayList<>(images);
  }

  public Region asNode() {
    return mainContainer;
  }
}
