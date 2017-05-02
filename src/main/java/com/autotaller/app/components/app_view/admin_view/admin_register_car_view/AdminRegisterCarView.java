package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.utils.IterableView;
import javafx.scene.Node;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminRegisterCarView extends IterableView implements AdminRegisterCarController.IAdminRegisterCarView {

  

  public AdminRegisterCarView() {
    init();
  }

  private void init() {

  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
