package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.SellModelStatus;
import com.autotaller.app.utils.callbacks.LoadSimpleSellModelsCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 07.06.2017
 */
public class GetSimpleSellModelsEvent extends Event<GetSimpleSellModelsEventHandler> {

  public static EventType<GetSimpleSellModelsEventHandler> TYPE = new EventType<>();

  private SellModelStatus retrieveStatus;
  private LoadSimpleSellModelsCallback callback;

  public GetSimpleSellModelsEvent(SellModelStatus retrieveStatus, LoadSimpleSellModelsCallback callback) {
    this.retrieveStatus = retrieveStatus;
    this.callback = callback;
  }

  public SellModelStatus getRetrieveStatus() {
    return retrieveStatus;
  }

  public LoadSimpleSellModelsCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetSimpleSellModelsEventHandler handler) {
    handler.onGetSimpleSellModelsEvent(this);
  }
}
