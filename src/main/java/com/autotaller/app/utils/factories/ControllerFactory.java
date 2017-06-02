package com.autotaller.app.utils.factories;

import com.autotaller.app.components.app_view.AutoTallerMenuBarController;
import com.autotaller.app.components.app_view.admin_view.AdminController;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.AdminComponentsController;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.AdminSaveComponentsController;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.utils.AdminSaveComponentValidatorController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.AdminDefineAllContextController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_kit_view.AdminCarKitController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view.AddCarMakeDialogController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view.AdminCarMakeController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.AdminCarModelController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view.AdminCarSubkitController;
import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.AdminRegisterCarController;
import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.AdminSaveCarController;
import com.autotaller.app.components.app_view.admin_view.admin_statistics_view.AdminStatisticsController;
import com.autotaller.app.components.app_view.cars_view.search_car_make_view.SearchCarMakeController;
import com.autotaller.app.components.app_view.cars_view.search_car_type_view.SearchCarTypeController;
import com.autotaller.app.components.app_view.cars_view.search_car_view.SearchCarController;
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
      case ADMIN_DEFINE_CONTEXT_VIEW:
        return new AdminDefineAllContextController();
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
      case ADMIN_SAVE_COMPONENTS_VIEW:
        return new AdminSaveComponentsController();
      case ADMIN_SAVE_COMPONENTS_VALIDATOR_VIEW:
        return new AdminSaveComponentValidatorController();
      case ADMIN_STATISTICS_VIEW:
        return new AdminStatisticsController();
      case SEARCH_CAR_MAKE_VIEW:
        return new SearchCarMakeController();
      case SEARCH_CAR_TYPE_VIEW:
        return new SearchCarTypeController();
      case SEARCH_CAR_VIEW:
        return new SearchCarController();
    }
    return null;
  }
}
