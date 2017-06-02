package com.autotaller.app.components.utils;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.layout.Region;

/**
 * Created by razvanolar on 02.06.2017
 */
public class FilterDialog extends JFXDialog {

  public FilterDialog(Region filterNode) {
    this.setContent(filterNode);
    this.setTransitionType(JFXDialog.DialogTransition.CENTER);
  }
}
