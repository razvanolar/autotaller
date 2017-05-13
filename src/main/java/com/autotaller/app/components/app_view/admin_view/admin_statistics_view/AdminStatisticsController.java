package com.autotaller.app.components.app_view.admin_view.admin_statistics_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.components.utils.statistics.CarTypeStatisticsModel;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.GetAllCarDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_statistics_view.GetCarTypeStatistics;
import com.autotaller.app.events.app_view.admin_view.admin_statistics_view.GetCarTypeStatisticsHandler;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.simple_models.SimpleCarTypeModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.filters.car_model_filters.MultiCarModelMakeFilter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.Map;

/**
 * Created by razvanolar on 12.05.2017
 */
public class AdminStatisticsController implements Controller<AdminStatisticsController.IAdminStatisticsView> {

  public interface IAdminStatisticsView extends View {
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
    EventBus.fireEvent(new GetAllCarDefinedModelsEvent(models -> {
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
              XYChart.Data value = new XYChart.Data(simpleType.getCarTypeName(), typesMap.get(simpleType));
              series.getData().add(value);
            }
            data.add(series);
            break;
          }
        }
      }
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
