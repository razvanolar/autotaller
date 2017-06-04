package com.autotaller.app.components.app_view.notifications_view;

import com.autotaller.app.components.utils.IterableView;
import javafx.scene.Node;

/**
 * Created by razvanolar on 04.06.2017
 */
public class NotificationsView extends IterableView implements NotificationsController.INotificationsView {

  public NotificationsView() {
    init();
  }

  private void init() {
    
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
