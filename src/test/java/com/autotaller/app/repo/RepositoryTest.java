package com.autotaller.app.repo;

import com.autotaller.app.repository.Repository;
import junit.framework.TestCase;

/**
 * Created by razvanolar on 28.04.2017
 */
public class RepositoryTest extends TestCase {

  private Repository repo;

  public RepositoryTest() throws Exception {
    super(RepositoryTest.class.getName());
    repo = new Repository();
    repo.testConnection();
  }

  public void testGetAllCarModels() throws Exception {
    assertTrue(!repo.getCarModels().isEmpty());
  }
}
