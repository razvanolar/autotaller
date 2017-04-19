package com.autotaller.app.components.utils;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Created by razvanolar on 14.04.2017
 */
public class MaskableView {

  protected StackPane stackPane;
  protected MaskView maskView;

  protected MaskableView() {
    init();
  }

  private void init() {
    stackPane = new StackPane();
    maskView = new MaskView();
  }

  protected void maskView(String message) {
    maskView.setTextMessage(message);
    ObservableList<Node> children = stackPane.getChildren();
    Node node = maskView.asNode();
    if (children.contains(node)) {
      children.remove(node);
    }
    children.add(node);
  }

  protected void unmaskView() {
    ObservableList<Node> children = stackPane.getChildren();
    Node node = maskView.asNode();
    if (children.contains(node)) {
      children.remove(node);
    }
  }
}
