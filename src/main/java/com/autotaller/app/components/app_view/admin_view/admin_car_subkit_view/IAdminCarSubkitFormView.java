package com.autotaller.app.components.app_view.admin_view.admin_car_subkit_view;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.View;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by razvanolar on 01.05.2017
 */
public interface IAdminCarSubkitFormView extends View {
  ComboBox<CarKitCategoryModel> getCarKitCategoriesCombo();
  ComboBox<CarKitModel> getCarKitsCombo();
  TextField getCarSubkitNameField();
  CheckBox getGasolineCheckBox();
  CheckBox getDieselCheckBox();
  CheckBox getGplCheckBox();
  CheckBox getElectricCheckBox();
  void injectData(List<CarKitCategoryModel> carKitCategories, List<CarKitModel> carKits);
}
