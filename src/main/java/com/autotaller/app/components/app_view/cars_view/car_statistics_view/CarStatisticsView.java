package com.autotaller.app.components.app_view.cars_view.car_statistics_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

/**
 * Created by razvanolar on 17.06.2017
 */
public class CarStatisticsView extends IterableView implements CarStatisticsController.ICarStatisticsView {

  private BarChart<String, Number> componentsInfoChart;
  private PieChart componentsPieChart;

  public CarStatisticsView() {
    init();
  }

  private void init() {
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    componentsInfoChart = new BarChart<>(xAxis, yAxis);
    componentsPieChart = new PieChart();
    componentsInfoChart.setCategoryGap(30);

    VBox container = NodeProvider.createVBox(10,
            NodeProvider.createTitlePane("Informatii Componente", null),
            componentsInfoChart,
            NodeProvider.createTitlePane("Vanzari", null),
            componentsPieChart
    );
    borderPane.setCenter(NodeProvider.createScrollPane(container, true));
  }

  public BarChart<String, Number> getComponentsInfoChart() {
    return componentsInfoChart;
  }

  public PieChart getComponentsPieChart() {
    return componentsPieChart;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
