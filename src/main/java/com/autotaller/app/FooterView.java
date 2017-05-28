package com.autotaller.app;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Created by razvanolar on 28.05.2017
 */
public class FooterView implements View {

  private HBox mainContainer;
  private HBox pathContainer;

  public FooterView() {
    init();
  }

  private void init() {
    pathContainer = new HBox(5);
    pathContainer.setAlignment(Pos.CENTER_LEFT);

    mainContainer = new HBox(pathContainer, new FillToolItem());
    mainContainer.setAlignment(Pos.CENTER_LEFT);
    mainContainer.setPrefHeight(25);
  }

  public void addPath(String path) {
    pathContainer.getChildren().addAll(NodeProvider.createTextLabel(">", 11, false),
            NodeProvider.createTextLabel(path, 12, true));
  }

  public void removeLastPath() {
    ObservableList<Node> children = pathContainer.getChildren();
    if (!children.isEmpty()) {
      children.remove(children.size() - 1);
      children.remove(children.size() - 1);
    }
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
