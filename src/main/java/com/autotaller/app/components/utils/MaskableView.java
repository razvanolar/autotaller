package com.autotaller.app.components.utils;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.MaskViewEventHandler;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEventHandler;
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
    addHandlers();
  }

  private void init() {
    mainContainer = new StackPane();
    maskView = new MaskView();
  }

  protected void maskView(String message) {
    maskView.setTextMessage(message);
    ObservableList<Node> children = mainContainer.getChildren();
    Node node = maskView.asNode();
    if (children.contains(node)) {
      children.remove(node);
    }
    children.add(node);
  }

  protected void unmaskView() {
    ObservableList<Node> children = mainContainer.getChildren();
    Node node = maskView.asNode();
    if (children.contains(node)) {
      children.remove(node);
    }
  }

  private void addHandlers() {
    EventBus.addHandler(MaskViewEvent.TYPE, (MaskViewEventHandler) event -> maskView(event.getMessage()));

    EventBus.addHandler(UnmaskViewEvent.TYPE, (UnmaskViewEventHandler) event -> unmaskView());
  }
}
