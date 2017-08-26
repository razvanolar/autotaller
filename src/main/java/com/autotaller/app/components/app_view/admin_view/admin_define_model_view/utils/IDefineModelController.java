package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.utils;

import com.autotaller.app.utils.View;

/**
 * Created by razvanolar on 13.06.2017
 */
public interface IDefineModelController {
  void loadIfEmpty();
  void addEntity();
  void deleteEntity();
  View getView();
}
