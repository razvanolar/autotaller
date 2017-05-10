package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.utils;

import com.autotaller.app.model.utils.YearsRange;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.filters.car_model_filters.CarModelYearFilter;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 29.04.2017
 */
public class YearsPanelView implements View {

  private FlowPane flowPane;
  private List<YearView> yearViews;
  private CarModelYearFilter yearFilter;

  public YearsPanelView(int width) {
    init(width);
  }

  private void init(int flowPaneWidth) {
    flowPane = new FlowPane();
    flowPane.setAlignment(Pos.TOP_CENTER);
    flowPane.setHgap(5);
    flowPane.setVgap(5);
    if (flowPaneWidth > 0)
      flowPane.setPrefWidth(flowPaneWidth);

    yearViews = new ArrayList<>();
  }

  public void showYearPanels(YearsRange range) {
    ObservableList<Node> children = flowPane.getChildren();
    children.clear();
    yearViews.clear();

    for (int i = range.getMinYear(); i <= range.getMaxYear(); i++) {
      YearView yearView = new YearView(i, this);
      children.add(yearView.asNode());
      yearViews.add(yearView);
    }
  }

  public void onYearSelectionChanged(int year, boolean isSelected) {
    ObservableList<Integer> years = yearFilter.getYears();
    if (isSelected && !years.contains(year)) {
      years.add(year);
    } else if (!isSelected) {
      int index = years.indexOf(year);
      if (index >= 0)
        years.remove(index);
    }
  }

  public void setFilterObject(CarModelYearFilter filter) {
    this.yearFilter = filter;
  }

  @Override
  public Node asNode() {
    return flowPane;
  }
}
