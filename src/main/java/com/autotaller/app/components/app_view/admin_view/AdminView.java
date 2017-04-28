package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.components.utils.IterableView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminView extends IterableView implements AdminController.IAdminView {

  private VBox vBox;

  public AdminView() {
    super();
    init();
  }

  private void init() {
    toolBar.getItems().addAll(
              new Separator()
            );

    vBox = new VBox();
    ScrollPane scrollPane = new ScrollPane(vBox) {public void requestFocus() {}};
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    mainContainer.setCenter(scrollPane);
    vBox.prefWidthProperty().bind(mainContainer.widthProperty());
  }

  @Override
  public void addToolbarPane(Node toolbarPane) {
    vBox.getChildren().add(toolbarPane);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
