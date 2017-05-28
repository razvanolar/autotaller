package com.autotaller.app.components.utils;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.GetStageInstanceEvent;
import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by razvanolar on 28.05.2017
 */
public class ImageDialog extends JFXDialog {

  private ScrollPane scrollPane;

  public ImageDialog(Image image) {
    ImageView imageView = new ImageView(image);
    scrollPane = NodeProvider.createScrollPane(imageView, true);
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    dialogLayout.setBody(scrollPane);
    this.setContent(scrollPane);
    this.setTransitionType(JFXDialog.DialogTransition.CENTER);

    EventBus.fireEvent(new GetStageInstanceEvent(stage -> {
      scrollPane.maxWidthProperty().bind(stage.widthProperty().subtract(150));
      scrollPane.maxHeightProperty().bind(stage.heightProperty().subtract(100));
    }));
  }

  @Override
  public void close() {
    scrollPane.maxWidthProperty().unbind();
    scrollPane.maxHeightProperty().unbind();
    scrollPane = null;
    super.close();
  }
}
