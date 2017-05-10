package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.utils;

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
public class YearView implements View {

  private HBox panel;
  private int year;
  private YearsPanelView listener;

  private boolean isSelected;

  public YearView(int year, YearsPanelView listener) {
    this.year = year;
    this.listener = listener;
    init();
    initHandlers();
  }

  private void init() {
    Text text = NodeProvider.createTextLabel(String.valueOf(year), 11, false);
    panel = new HBox(text);
    panel.setAlignment(Pos.CENTER);
    panel.setPadding(new Insets(0, 5, 0, 5));

    setStyle();
  }

  private void initHandlers() {
    panel.setOnMouseClicked(event -> {
      isSelected = !isSelected;
      setStyle();
      listener.onYearSelectionChanged(year, isSelected);
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

  public int getYear() {
    return year;
  }

  public boolean isSelected() {
    return isSelected;
  }

  @Override
  public Node asNode() {
    return panel;
  }
}
