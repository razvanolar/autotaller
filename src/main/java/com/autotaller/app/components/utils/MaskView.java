package com.autotaller.app.components.utils;

import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 14.04.2017
 */
public class MaskView implements View {

  private BorderPane mainContainer;
  private Label messageLabel;

  public MaskView() {
    init();
  }

  private void init() {
    GridPane maskPane = new GridPane();
    mainContainer = new BorderPane(maskPane);
    messageLabel = new Label();
    ProgressIndicator progressIndicator = new ProgressIndicator();

    maskPane.add(progressIndicator, 0, 0);
    maskPane.add(messageLabel, 1, 0);
    maskPane.setHgap(10);
    maskPane.setAlignment(Pos.CENTER);
    mainContainer.getStyleClass().add(StyleProvider.MASK_VIEW_BACKGROUND_CLASS);
  }

  public void setTextMessage(String text) {
    this.messageLabel.setText(text);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
