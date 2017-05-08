package com.autotaller.app.components.utils;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Created by razvanolar on 14.04.2017
 */
public class MaskableView {

  protected StackPane mainContainer;
  private MaskView maskView;

  protected MaskableView() {
    init();
  }

  private void init() {
    mainContainer = new StackPane();
    maskView = new MaskView();
  }

  public void maskView(String message) {
    maskView.setTextMessage(message);
    ObservableList<Node> children = mainContainer.getChildren();
    Node node = maskView.asNode();
    if (children.contains(node)) {
      children.remove(node);
    }
    children.add(node);
  }

  public void unmaskView() {
    ObservableList<Node> children = mainContainer.getChildren();
    Node node = maskView.asNode();
    if (children.contains(node)) {
      children.remove(node);
    }
  }
}
