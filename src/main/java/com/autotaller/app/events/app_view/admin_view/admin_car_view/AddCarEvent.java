package com.autotaller.app.events.app_view.admin_view.admin_car_view;

import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.io.File;
import java.util.List;

/**
 * Created by razvanolar on 07.05.2017
 */
public class AddCarEvent extends Event<AddCarEventHandler> {

  public static EventType<AddCarEventHandler> TYPE = new EventType<>();

  private CarModel car;
  private List<File> images;

  public AddCarEvent(CarModel car, List<File> images) {
    this.car = car;
    this.images = images;
  }

  public CarModel getCar() {
    return car;
  }

  public List<File> getImages() {
    return images;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(AddCarEventHandler handler) {
    handler.onAddCarEvent(this);
  }
}
