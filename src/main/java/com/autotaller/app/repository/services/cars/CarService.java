package com.autotaller.app.repository.services.cars;

import com.autotaller.app.repository.services.GenericService;
import com.autotaller.app.repository.utils.JDBCUtil;

/**
 * Created by razvanolar on 02.05.2017
 */
public class CarService extends GenericService {

  public CarService(JDBCUtil jdbcUtil) {
    super(jdbcUtil);
  }
}
