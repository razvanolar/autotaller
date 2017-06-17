package com.autotaller.app.utils.filters.car_filters.car_type_model_filters;

import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.filters.car_filters.CarStringFilter;

/**
 * Created by razvanolar on 17.06.2017
 */
public class CarTypeModelFrameFilter extends CarStringFilter<CarTypeModel> implements CarTypeModelFilter {

  public void setFrame(String frame) {
    super.setFilterValue(frame);
  }

  @Override
  protected String getValue(CarTypeModel model) {
    String frames = model.getFrames();
    return frames != null ? frames : "";
  }
}
