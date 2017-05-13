package com.autotaller.app.components.app_view.admin_view.admin_statistics_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 12.05.2017
 */
public class AdminStatisticsView extends IterableView implements AdminStatisticsController.IAdminStatisticsView {

  private SplitPane mainSplitPane;
  private SplitPane chartSplitPane;



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

    chartSplitPane = new SplitPane();

    mainSplitPane = new SplitPane(chartSplitPane);
    borderPane.setCenter(mainSplitPane);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
