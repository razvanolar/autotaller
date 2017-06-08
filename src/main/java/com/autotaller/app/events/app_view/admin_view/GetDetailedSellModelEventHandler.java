package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 08.06.2017
 */
public interface GetDetailedSellModelEventHandler extends EventHandler {
  void onGetDetailedSellModelEvent(GetDetailedSellModelEvent event);
}
