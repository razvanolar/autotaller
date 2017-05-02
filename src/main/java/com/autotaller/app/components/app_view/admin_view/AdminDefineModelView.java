package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminDefineModelView extends IterableView implements AdminDefineModelController.IAdminDefineModelView {

  private VBox vBox;

  public AdminDefineModelView() {
    super();
    init();
  }

  private void init() {
    toolBar.getItems().addAll(
              new Separator()
            );

    vBox = new VBox();

    borderPane.setCenter(NodeProvider.createScrollPane(vBox));
    vBox.prefWidthProperty().bind(borderPane.widthProperty());
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
