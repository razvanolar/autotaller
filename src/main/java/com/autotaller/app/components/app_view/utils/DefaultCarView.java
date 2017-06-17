package com.autotaller.app.components.app_view.utils;

import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.components.utils.filter_views.DefaultCarFilterView;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * Created by razvanolar on 17.06.2017
 */
public class DefaultCarView implements DefaultCarController.IDefaultCarView {

  private BorderPane container;
  private SplitPane splitPane;
  private FilterPanelView<Integer> yearFilterPane;
  private TableView<CarModel> carsTable;
  private DefaultCarFilterView filterView;
  private ScrollPane filterScrollPane;

  private double lastFilterDividerPosition = .3;

  public DefaultCarView() {
    init();
  }

  private void init() {
    yearFilterPane = new FilterPanelView<>(100);
    carsTable = NodeProvider.createCarTable();
    filterView = new DefaultCarFilterView();
    filterScrollPane = NodeProvider.createScrollPane(filterView.asRegion(), true);

    splitPane = new SplitPane(carsTable);

    container = NodeProvider.createBorderPane();
    container.setCenter(splitPane);
    container.setTop(yearFilterPane.asNode());
  }

  public TableView<CarModel> getCarsTable() {
    return carsTable;
  }

  public FilterPanelView<Integer> getYearFilterPane() {
    return yearFilterPane;
  }

  public DefaultCarFilterView getFilterView() {
    return filterView;
  }

  public void showFilterPane() {
    ObservableList<Node> items = splitPane.getItems();
    if (!items.contains(filterScrollPane)) {
      items.add(0, filterScrollPane);
      splitPane.setDividerPosition(0, lastFilterDividerPosition);
    }
  }

  public void hideFilterPane() {
    ObservableList<Node> items = splitPane.getItems();
    if (items.contains(filterScrollPane)) {
      double[] dividers = splitPane.getDividerPositions();
      if (dividers != null && dividers.length > 0)
        lastFilterDividerPosition = dividers[0];
      items.remove(filterScrollPane);
    }
  }

  @Override
  public Node asNode() {
    return container;
  }
}
