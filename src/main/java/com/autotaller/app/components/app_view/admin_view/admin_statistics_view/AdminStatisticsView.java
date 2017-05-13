package com.autotaller.app.components.app_view.admin_view.admin_statistics_view;

import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 12.05.2017
 */
public class AdminStatisticsView extends IterableView implements AdminStatisticsController.IAdminStatisticsView {

  private SplitPane mainSplitPane;
  private SplitPane chartSplitPane;
  private FilterPanelView<CarMakeModel> carMakeFilter;
  private BarChart<String, Number> carMakeBarChart;

  private ToggleButton filterToggleButton;
  private ToggleButton detailsToggleButton;

  private double chartSplitPaneLastDividerPosition = 0.3;
  private double mainSplitPaneLastDividerPosition = 0.5;

  public AdminStatisticsView() {
    init();
  }

  private void init() {
    filterToggleButton = NodeProvider.createToolbarToggleButton("Filtrare", ImageProvider.filterIcon());
    detailsToggleButton = NodeProvider.createToolbarToggleButton("Detalii", ImageProvider.detailsIcon());
    toolBar.getItems().addAll(new Separator(), filterToggleButton, detailsToggleButton);

    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Model");
    yAxis.setLabel("Masini inregistrate");
    carMakeFilter = new FilterPanelView<>(NodeProvider.DEFAULT_FIELD_WIDTH, 120, 40);
    carMakeBarChart = new BarChart<>(xAxis, yAxis);
    carMakeBarChart.setBarGap(3);
    carMakeBarChart.setCategoryGap(3);

    chartSplitPane = new SplitPane(carMakeFilter.asNode(), carMakeBarChart);
    chartSplitPane.setDividerPosition(0, chartSplitPaneLastDividerPosition);

    mainSplitPane = new SplitPane(chartSplitPane);
    borderPane.setCenter(mainSplitPane);
  }

  public FilterPanelView<CarMakeModel> getCarMakeFilter() {
    return carMakeFilter;
  }

  public BarChart<String, Number> getCarMakeBarChart() {
    return carMakeBarChart;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
