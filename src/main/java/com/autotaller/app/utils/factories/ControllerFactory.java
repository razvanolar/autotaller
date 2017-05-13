package com.autotaller.app.utils.factories;

import com.autotaller.app.components.app_view.AutoTallerMenuBarController;
import com.autotaller.app.components.app_view.admin_view.AdminController;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.AdminComponentsController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.AdminDefineModelController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_kit_view.AdminCarKitController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view.AddCarMakeDialogController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view.AdminCarMakeController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.AdminCarModelController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view.AdminCarSubkitController;
import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.AdminRegisterCarController;
import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.AdminSaveCarController;
import com.autotaller.app.components.app_view.admin_view.admin_statistics_view.AdminStatisticsController;
import com.autotaller.app.components.login_view.login.LoginController;
import com.autotaller.app.components.login_view.sign_up.SignUpController;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;

/**
 * Created by razvanolar on 11.04.2017
 */
public class ControllerFactory {

  public Controller createController(ComponentType type) {
    switch (type) {
      case LOGIN_VIEW:
        return new LoginController();
      case SIGN_UP_VIEW:
        return new SignUpController();
      case APP_MENU_BAR:
        return new AutoTallerMenuBarController();
      case ADMIN_VIEW:
        return new AdminController();
      case ADMIN_DEFINE_MODEL_VIEW:
        return new AdminDefineModelController();
      case ADMIN_CAR_MAKE_VIEW:
        return new AdminCarMakeController();
      case ADD_CAR_MAKE_VIEW:
        return new AddCarMakeDialogController();
      case ADMIN_CAR_MODEL_VIEW:
        return new AdminCarModelController();
      case ADMIN_CAR_KIT_VIEW:
        return new AdminCarKitController();
      case ADMIN_CAR_SUBKIT_VIEW:
        return new AdminCarSubkitController();
      case ADMIN_REGISTER_CAR_VIEW:
        return new AdminRegisterCarController();
      case ADMIN_SAVE_CAR_VIEW:
        return new AdminSaveCarController();
      case ADMIN_COMPONENTS_VIEW:
        return new AdminComponentsController();
      case ADMIN_STATISTICS_VIEW:
        return new AdminStatisticsController();
    }
    return null;
  }
}
