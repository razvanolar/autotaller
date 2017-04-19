package com.autotaller.app.components.loading_view;

import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 10.04.2017
 */
public class LoadingView implements View {

  private StackPane mainContainer;
  private Text text;
  private ProgressIndicator progressIndicator;
  private GridPane gridPane;

  public LoadingView() {
    init();
  }

  private void init() {
    ImageView bgImage = new ImageView(ImageProvider.logo());
    bgImage.setFitWidth(800);
    bgImage.setFitHeight(420);
    bgImage.setPreserveRatio(true);

    progressIndicator = new ProgressIndicator();
    text = new Text("--Testare Conexiune--");

    gridPane = new GridPane();
    gridPane.add(progressIndicator, 0, 0);
    gridPane.add(text, 0, 1);
    gridPane.setAlignment(Pos.CENTER);

    mainContainer = new StackPane(bgImage, gridPane);

    text.getStyleClass().add(StyleProvider.LOADING_TEST_CONNECTION_TEXT_CLASS);
  }

  public void showFailedConnectionTest() {
    gridPane.getChildren().remove(progressIndicator);
    text.setText("Conexiunea la baza de date nu a reusit");
    text.getStyleClass().removeAll(StyleProvider.LOADING_TEST_CONNECTION_TEXT_CLASS);
    text.getStyleClass().add(StyleProvider.LOADING_TEST_CONNECTION_FAILED_TEXT_CLASS);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
