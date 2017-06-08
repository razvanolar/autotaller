package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.model.notifications.SimpleSellModel;
import com.autotaller.app.utils.callbacks.LoadDetailedSellModelCallback;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 08.06.2017
 */
public class GetDetailedSellModelEvent extends Event<GetDetailedSellModelEventHandler> {

  public static EventType<GetDetailedSellModelEventHandler> TYPE = new EventType<>();

  private SimpleSellModel simpleSellModel;
  private LoadDetailedSellModelCallback callback;

  public GetDetailedSellModelEvent(SimpleSellModel simpleSellModel, LoadDetailedSellModelCallback callback) {
    this.simpleSellModel = simpleSellModel;
    this.callback = callback;
  }

  public SimpleSellModel getSimpleSellModel() {
    return simpleSellModel;
  }

  public LoadDetailedSellModelCallback getCallback() {
    return callback;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(GetDetailedSellModelEventHandler handler) {
    handler.onGetDetailedSellModelEvent(this);
  }
}
