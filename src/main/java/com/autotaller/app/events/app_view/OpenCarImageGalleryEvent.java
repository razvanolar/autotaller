package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 28.05.2017
 */
public class OpenCarImageGalleryEvent extends Event<OpenCarImageGalleryEventHandler> {

  public static EventType<OpenCarImageGalleryEventHandler> TYPE = new EventType<>();

  private int carId;

  public OpenCarImageGalleryEvent(int carId) {
    this.carId = carId;
  }

  public int getCarId() {
    return carId;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(OpenCarImageGalleryEventHandler handler) {
    handler.onOpenCarImageGalleryEvent(this);
  }
}
