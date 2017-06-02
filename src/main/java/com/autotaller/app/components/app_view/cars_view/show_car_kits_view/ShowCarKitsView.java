package com.autotaller.app.components.app_view.cars_view.show_car_kits_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.components.utils.listeners.KitSelectionListener;
import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by razvanolar on 02.06.2017
 */
public class ShowCarKitsView extends IterableView implements ShowCarKitsController.IShowCarKitsView {

  private FlowPane flowPane;

  public ShowCarKitsView() {
    init();
  }

  private void init() {
    flowPane = NodeProvider.createFlowPane(Pos.TOP_CENTER);
    flowPane.setHgap(20);

    borderPane.setCenter(NodeProvider.createScrollPane(flowPane, true));
  }

  public void createKitLabels(SystemModelsDTO systemModels, KitSelectionListener listener) {
    EventHandler<MouseEvent> mouseEvent = event -> {
      Text text = (Text) event.getSource();
      listener.onKitSelection((CarKitModel) text.getUserData());
    };

    for (CarKitCategoryModel carKitCategory : systemModels.getCarKitCategories()) {
      VBox pane = NodeProvider.createTitlePane(carKitCategory.getName(), null);
      pane.setMinWidth(400);
      List<CarKitModel> carKits = systemModels.getCarKitsByCategory(carKitCategory);
      if (carKits != null && !carKits.isEmpty()) {
        for (CarKitModel carKit : carKits) {
          Text label = NodeProvider.createTextLabel(carKit.getName(), 15, true);
          label.setUserData(carKit);
          label.setOnMouseClicked(mouseEvent);
          pane.getChildren().add(label);
        }
        flowPane.getChildren().add(pane);
      }
    }
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
