package com.autotaller.app.components.utils;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

/**
 * Created by razvanolar on 18.04.2017
 */
public class IterableView {

  protected BorderPane mainContainer;
  protected ToolBar toolBar;
  private Button backButton;
  private Button homeButton;

  public IterableView() {
    init();
    initHandlers();
  }

  private void init() {
    backButton = new Button("Back");
    homeButton = new Button("Home");
    toolBar = new ToolBar(backButton, homeButton);
    mainContainer = new BorderPane();

    mainContainer.setTop(toolBar);
  }

  private void initHandlers() {
    backButton.setOnAction(event -> EventBus.fireEvent(new BackToPreviousViewEvent()));
  }
}
