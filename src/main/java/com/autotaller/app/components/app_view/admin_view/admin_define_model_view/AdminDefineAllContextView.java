package com.autotaller.app.components.app_view.admin_view.admin_define_model_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminDefineAllContextView extends IterableView implements AdminDefineAllContextController.IAdminDefineAllContextView {

  private VBox vBox;

  public AdminDefineAllContextView() {
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
    mainContainer.getStyleClass().add(StyleProvider.GENERAL_PANE_BACKGROUND_CLASS);
    vBox.getStyleClass().add(StyleProvider.GENERAL_PANE_BACKGROUND_CLASS);
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
