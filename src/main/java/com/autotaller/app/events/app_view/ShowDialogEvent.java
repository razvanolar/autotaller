package com.autotaller.app.events.app_view;

import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;
import com.jfoenix.controls.JFXDialog;

/**
 * Created by razvanolar on 19.04.2017
 */
public class ShowDialogEvent extends Event<ShowDialogEventHandler> {

  public static EventType<ShowDialogEventHandler> TYPE = new EventType<>();

  private JFXDialog dialog;

  public ShowDialogEvent(JFXDialog dialog) {
    this.dialog = dialog;
  }

  public JFXDialog getDialog() {
    return dialog;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(ShowDialogEventHandler handler) {
    handler.onShowDialogEvent(this);
  }
}
