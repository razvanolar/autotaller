package com.autotaller.app.utils.callbacks;

import com.autotaller.app.model.CarKitCategoryModel;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public interface LoadingCarKitCategoriesCallback extends Callback {
  void call(List<CarKitCategoryModel> carKitCategories);
}
