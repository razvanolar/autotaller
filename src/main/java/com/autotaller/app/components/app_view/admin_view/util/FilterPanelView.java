package com.autotaller.app.components.app_view.admin_view.util;

import com.autotaller.app.utils.View;
import com.autotaller.app.utils.filters.Filter;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 29.04.2017
 */
public class FilterPanelView<T> implements View {

  private FlowPane flowPane;
  private List<FilterView<T>> filterViews;
  private Filter<T> filter;
  private int filterWidth = -1;
  private int filterHeight = -1;

  public FilterPanelView(int width) {
    init(width);
  }

  public FilterPanelView(int width, int filterWidth, int filterHeight) {
    this(width);
    this.filterWidth = filterWidth;
    this.filterHeight = filterHeight;
  }

  private void init(int flowPaneWidth) {
    flowPane = new FlowPane();
    flowPane.setAlignment(Pos.TOP_CENTER);
    flowPane.setHgap(5);
    flowPane.setVgap(5);
    flowPane.setPadding(new Insets(3));
    if (flowPaneWidth > 0)
      flowPane.setPrefWidth(flowPaneWidth);

    filterViews = new ArrayList<>();
  }

  public void showFilterPanels(List<T> values) {
    ObservableList<Node> children = flowPane.getChildren();
    children.clear();
    filterViews.clear();

    for (T value : values) {
      FilterView<T> filterView = new FilterView<>(value, this, filterWidth, filterHeight);
      children.add(filterView.asNode());
      filterViews.add(filterView);
    }
  }

  public void onFieldsSelectionChanged(T value, boolean isSelected) {
    if (filter == null)
      return;
    List<T> fields = filter.getFields();
    if (isSelected && !fields.contains(value)) {
      fields.add(value);
    } else if (!isSelected) {
      int index = fields.indexOf(value);
      if (index >= 0)
        fields.remove(index);
    }
  }

  public void setFilterObject(Filter<T> filter) {
    this.filter = filter;
  }

  @Override
  public Node asNode() {
    return flowPane;
  }
}
