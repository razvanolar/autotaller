package com.autotaller.app.repository.services;

import com.autotaller.app.repository.utils.JDBCUtil;

/**
 * Created by razvanolar on 11.04.2017
 */
public abstract class GenericService {

  protected JDBCUtil jdbcUtil;

  public GenericService(JDBCUtil jdbcUtil) {
    this.jdbcUtil = jdbcUtil;
  }
}
