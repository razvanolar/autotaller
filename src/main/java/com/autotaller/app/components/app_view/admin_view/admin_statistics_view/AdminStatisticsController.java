package com.autotaller.app.components.app_view.admin_view.admin_statistics_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.components.utils.statistics.CarTypeStatisticsModel;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.GetAllSystemDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarsByTypeIdEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_statistics_view.GetCarTypeStatistics;
import com.autotaller.app.events.app_view.admin_view.admin_statistics_view.GetCarTypeStatisticsHandler;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.simple_models.SimpleCarTypeModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.filters.car_filters.MultiCarModelMakeFilter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.util.Map;

/**
 * Created by razvanolar on 12.05.2017
 */
public class AdminStatisticsController implements Controller<AdminStatisticsController.IAdminStatisticsView> {

  public interface IAdminStatisticsView extends View {
    TableView<CarModel> getCarTable();
    FilterPanelView<CarMakeModel> getCarMakeFilter();
    BarChart<String, Number> getCarMakeBarChart();
  }

  private SystemModelsDTO modelsDTO;
  private Repository repository;
  private MultiCarModelMakeFilter multiCarModelMakeFilter;
  private IAdminStatisticsView view;

  @Override
  public void bind(IAdminStatisticsView view) {
    this.view = view;

    multiCarModelMakeFilter = new MultiCarModelMakeFilter();
    view.getCarMakeFilter().setFilterObject(multiCarModelMakeFilter);

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> this.repository = event.getRepository());

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> {
      loadDefinedModels();
    }, true);

    initStatisticsHandlers();
  }

  private void loadDefinedModels() {
    EventBus.fireEvent(new GetAllSystemDefinedModelsEvent(models -> {
      this.modelsDTO = models;
      this.view.getCarMakeFilter().showFilterPanels(models.getCarMakes());

      loadCarMakeChart();
    }));
  }

  private void loadCarMakeChart() {
    EventBus.fireEvent(new GetCarTypeStatistics(carTypeStatistics -> {
      Map<Integer, Map<SimpleCarTypeModel, Integer>> map = carTypeStatistics.getMap();
      ObservableList<XYChart.Series<String, Number>> data = view.getCarMakeBarChart().getData();
      data.clear();

      for (CarMakeModel carMake : modelsDTO.getCarMakes()) {
        for (Integer carMakeId : map.keySet()) {
          if (carMake.getId() == carMakeId) {
            XYChart.Series series = new XYChart.Series();
            series.setName(carMake.getName());
            Map<SimpleCarTypeModel, Integer> typesMap = map.get(carMakeId);
            for (SimpleCarTypeModel simpleType : typesMap.keySet()) {
              XYChart.Data value = new XYChart.Data(simpleType.getCarTypeName(), typesMap.get(simpleType), simpleType.getCarTypeId());
              series.getData().add(value);
            }
            data.add(series);
            break;
          }
        }
      }

      EventHandler<MouseEvent> mouseEvent = event -> {
        if (event.getSource() instanceof Node) {
          Node node = (Node) event.getSource();
          int carTypeId = (Integer) node.getUserData();
          loadCarsByCarType(carTypeId);
        }
      };

      for (XYChart.Series<String, Number> series : data) {
        ObservableList<XYChart.Data<String, Number>> dt = series.getData();
        for (XYChart.Data<String, Number> d : dt) {
          Node node = d.getNode();
          if (node != null) {
            node.setUserData(d.getExtraValue());
            node.setOnMouseClicked(mouseEvent);
            Tooltip t = new Tooltip(d.getXValue() + "\n" + d.getYValue().intValue() + " inregistrari");
            Tooltip.install(node, t);
          }
        }
      }
    }));
  }

  private void loadCarsByCarType(int carTypeId) {
    EventBus.fireEvent(new GetCarsByTypeIdEvent(carTypeId, cars -> {
      ObservableList<CarModel> items = view.getCarTable().getItems();
      items.clear();
      items.addAll(cars);
    }));
  }

  private void initStatisticsHandlers() {
    EventBus.addHandler(GetCarTypeStatistics.TYPE, (GetCarTypeStatisticsHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare date"));
        Thread thread = new Thread(() -> {
          try {
            CarTypeStatisticsModel statistics = repository.getCarTypeStatistics();
            Platform.runLater(() -> {
              event.getCallback().call(statistics);
              EventBus.fireEvent(new UnmaskViewEvent());
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });
  }
}
