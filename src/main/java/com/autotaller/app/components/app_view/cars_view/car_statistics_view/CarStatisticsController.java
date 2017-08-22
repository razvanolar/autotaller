package com.autotaller.app.components.app_view.cars_view.car_statistics_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.GetCarComponentsByCarIdEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarEventHandler;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.List;

/**
 * Created by razvanolar on 17.06.2017
 */
public class CarStatisticsController implements Controller<CarStatisticsController.ICarStatisticsView> {

  public interface ICarStatisticsView extends View {
    BarChart<String, Number> getComponentsInfoChart();
    PieChart getComponentsPieChart();
  }

  private ICarStatisticsView view;
  private CarModel car;

  @Override
  public void bind(ICarStatisticsView view) {
    this.view = view;

    EventBus.addHandler(InjectCarEvent.TYPE, (InjectCarEventHandler) event -> this.car = event.getCar(), true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> startLoad(), true);
  }

  private void startLoad() {
    EventBus.fireEvent(new GetCarComponentsByCarIdEvent(car.getId(), carComponents -> {
      loadComponentsInfoChart(carComponents);
    }));
  }

  private void loadComponentsInfoChart(List<CarComponentModel> components) {
    BarChart<String, Number> chart = view.getComponentsInfoChart();
    PieChart pie = view.getComponentsPieChart();
    chart.getData().clear();
    pie.getData().clear();

    XYChart.Series<String, Number> totalSeries = new XYChart.Series<>();
    XYChart.Series<String, Number> soldSeries = new XYChart.Series<>();
    XYChart.Series<String, Number> leftSeries = new XYChart.Series<>();
    totalSeries.setName("Total");
    soldSeries.setName("Vandute");
    leftSeries.setName("Ramase");

    int soldPieces = 0;
    int leftPieces = 0;
    for (CarComponentModel component : components) {
      String name = component.getName();
      int sold = component.getSoldPieces();
      int left = component.getLeftPieces();
      soldPieces += sold;
      leftPieces += left;
      totalSeries.getData().add(new XYChart.Data<>(name, component.getInitialPieces()));
      soldSeries.getData().add(new XYChart.Data<>(name, sold));
      leftSeries.getData().add(new XYChart.Data<>(name, left));
    }

    chart.getData().addAll(totalSeries, soldSeries, leftSeries);
    pie.getData().add(new PieChart.Data("Vandute", soldPieces));
    pie.getData().add(new PieChart.Data("Ramase", leftPieces));
  }
}
