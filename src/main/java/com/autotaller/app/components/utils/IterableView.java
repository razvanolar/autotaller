package com.autotaller.app.components.utils;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

/**
 * Created by razvanolar on 18.04.2017
 */
public class IterableView extends MaskableView {

  protected BorderPane borderPane;
  protected ToolBar toolBar;
  private Button backButton;
  private Button exitButton;

  public IterableView() {
    super();
    init();
    initHandlers();
  }

  private void init() {
    backButton = new Button("Back");
    exitButton = new Button("Exit");
    toolBar = NodeProvider.createToolBar();
    toolBar.getItems().addAll(backButton, exitButton);
    borderPane = new BorderPane();

    borderPane.setTop(toolBar);
    mainContainer.getChildren().add(borderPane);
  }

  private void initHandlers() {
    backButton.setOnAction(event -> EventBus.fireEvent(new BackToPreviousViewEvent()));
  }
}
