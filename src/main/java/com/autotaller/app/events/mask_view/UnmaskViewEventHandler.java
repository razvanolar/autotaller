package com.autotaller.app.events.mask_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 14.04.2017
 */
public interface UnmaskViewEventHandler extends EventHandler {
  void onUnmaskViewEvent(UnmaskViewEvent event);
}
