package com.autotaller.app.components.app_view.admin_view.util;

import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 29.04.2017
 */
public class FilterView<T> implements View {

  private HBox panel;
  private T value;
  private FilterPanelView<T> listener;

  private boolean isSelected;

  public FilterView(T value, FilterPanelView<T> listener, int width, int height) {
    this.value = value;
    this.listener = listener;
    init(width, height);
    initHandlers();
  }

  private void init(int width, int height) {
    Text text = NodeProvider.createTextLabel(value.toString(), 11, false);
    panel = new HBox(text);
    panel.setAlignment(Pos.CENTER);
    panel.setPadding(new Insets(0, 5, 0, 5));
    if (width > 0)
      panel.setPrefWidth(width);
    if (height > 0)
      panel.setPrefHeight(height);
    setStyle();
  }

  private void initHandlers() {
    panel.setOnMouseClicked(event -> {
      isSelected = !isSelected;
      setStyle();
      listener.onFieldsSelectionChanged(value, isSelected);
    });
  }

  private void setStyle() {
    if (isSelected) {
      panel.getStyleClass().remove(StyleProvider.YEAR_VIEW_UNSELECTED_CLASS);
      panel.getStyleClass().add(StyleProvider.YEAR_VIEW_SELECTED_CLASS);
    } else {
      panel.getStyleClass().remove(StyleProvider.YEAR_VIEW_SELECTED_CLASS);
      panel.getStyleClass().add(StyleProvider.YEAR_VIEW_UNSELECTED_CLASS);
    }
  }

  public T getValue() {
    return value;
  }

  public boolean isSelected() {
    return isSelected;
  }

  @Override
  public Node asNode() {
    return panel;
  }
}
