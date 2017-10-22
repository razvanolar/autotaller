package com.autotaller.app.components.app_view.search_components_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SearchComponentsMainView extends IterableView implements SearchComponentsMainController.ISearchComponentsMainView {

  private Node subkitSearchNode;

  public SearchComponentsMainView() {
    init();
  }

  private void init() {
    Text title = NodeProvider.createTextLabel("Cautare Componente", 25, false);
    subkitSearchNode = NodeProvider.createAdminAppMenu("Cautare pe subansamble", null);

    GridPane gridPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    int row = 0;
    gridPane.add(NodeProvider.createHBox(Pos.CENTER, 10, title), 0, row++);
    gridPane.add(subkitSearchNode, 0, row);

    borderPane.setCenter(NodeProvider.createScrollPane(gridPane, true));
  }

  public Node getSubkitSearchNode() {
    return subkitSearchNode;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
