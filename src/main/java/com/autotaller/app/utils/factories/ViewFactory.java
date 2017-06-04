package com.autotaller.app.utils.factories;

import com.autotaller.app.components.app_view.AutoTallerMenuBarView;
import com.autotaller.app.components.app_view.admin_view.AdminView;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.AdminComponentsView;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.AdminSaveComponentsView;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.utils.AdminSaveComponentValidatorView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.AdminDefineAllContextView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_kit_view.AdminCarKitView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view.AddCarMakeDialogView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view.AdminCarMakeView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.AdminCarModelView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view.AdminCarSubkitView;
import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.AdminRegisterCarView;
import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.AdminSaveCarView;
import com.autotaller.app.components.app_view.admin_view.admin_statistics_view.AdminStatisticsView;
import com.autotaller.app.components.app_view.cars_view.search_car_components_view.SearchCarComponentsView;
import com.autotaller.app.components.app_view.cars_view.search_car_make_view.SearchCarMakeView;
import com.autotaller.app.components.app_view.cars_view.search_car_type_view.SearchCarTypeView;
import com.autotaller.app.components.app_view.cars_view.search_car_view.SearchCarView;
import com.autotaller.app.components.app_view.cars_view.show_car_kits_view.ShowCarKitsView;
import com.autotaller.app.components.app_view.notifications_view.NotificationsView;
import com.autotaller.app.components.login_view.login.LoginView;
import com.autotaller.app.components.login_view.sign_up.SignUpView;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.View;

/**
 * Created by razvanolar on 11.04.2017
 */
public class ViewFactory {

  public View createView(ComponentType type) {
    switch (type) {
      case LOGIN_VIEW:
        return new LoginView();
      case SIGN_UP_VIEW:
        return new SignUpView();
      case APP_MENU_BAR:
        return new AutoTallerMenuBarView();
      case ADMIN_VIEW:
        return new AdminView();
      case ADMIN_DEFINE_CONTEXT_VIEW:
        return new AdminDefineAllContextView();
      case ADMIN_CAR_MAKE_VIEW:
        return new AdminCarMakeView();
      case ADD_CAR_MAKE_VIEW:
        return new AddCarMakeDialogView();
      case ADMIN_CAR_MODEL_VIEW:
        return new AdminCarModelView();
      case ADMIN_CAR_KIT_VIEW:
        return new AdminCarKitView();
      case ADMIN_CAR_SUBKIT_VIEW:
        return new AdminCarSubkitView();
      case ADMIN_REGISTER_CAR_VIEW:
        return new AdminRegisterCarView();
      case ADMIN_SAVE_CAR_VIEW:
        return new AdminSaveCarView();
      case ADMIN_COMPONENTS_VIEW:
        return new AdminComponentsView();
      case ADMIN_SAVE_COMPONENTS_VIEW:
        return new AdminSaveComponentsView();
      case ADMIN_SAVE_COMPONENTS_VALIDATOR_VIEW:
        return new AdminSaveComponentValidatorView();
      case ADMIN_STATISTICS_VIEW:
        return new AdminStatisticsView();
      case SEARCH_CAR_MAKE_VIEW:
        return new SearchCarMakeView();
      case SEARCH_CAR_TYPE_VIEW:
        return new SearchCarTypeView();
      case SEARCH_CAR_VIEW:
        return new SearchCarView();
      case SHOW_CAR_KITS_VIEW:
        return new ShowCarKitsView();
      case SEARCH_CAR_COMPONENTS_VIEW:
        return new SearchCarComponentsView();
      case NOTIFICATIONS_VIEW:
        return new NotificationsView();
    }
    return null;
  }
}
