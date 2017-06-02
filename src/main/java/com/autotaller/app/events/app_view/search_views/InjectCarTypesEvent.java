package com.autotaller.app.events.app_view.search_views;

import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

import java.util.List;

/**
 * Created by razvanolar on 02.06.2017
 */
public class InjectCarTypesEvent extends Event<InjectCarTypesEventHandler> {

  public static EventType<InjectCarTypesEventHandler> TYPE = new EventType<>();

  private List<CarTypeModel> carTypes;

  public InjectCarTypesEvent(List<CarTypeModel> carTypes) {
    this.carTypes = carTypes;
  }

  public List<CarTypeModel> getCarTypes() {
    return carTypes;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(InjectCarTypesEventHandler handler) {
    handler.onInjectSystemDefinedModelsEvent(this);
  }
}
