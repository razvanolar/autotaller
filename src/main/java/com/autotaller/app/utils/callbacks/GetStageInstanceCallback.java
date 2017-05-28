package com.autotaller.app.utils.callbacks;

import javafx.stage.Stage;

/**
 * Created by razvanolar on 27.05.2017
 */
public interface GetStageInstanceCallback extends Callback {
  void call(Stage stage);
}
