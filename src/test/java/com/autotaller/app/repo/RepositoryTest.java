package com.autotaller.app.repo;

import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.SearchComponentModel;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.StockType;
import com.autotaller.app.utils.UsageStateType;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

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
    assertTrue(repo.getCarModels() != null);
  }

  public void testGetCarKitCategories() throws Exception {
    assertTrue(repo.getCarKitCategories() != null);
  }

  public void testGetCarKits() throws Exception {
    assertTrue(repo.getCarKits() != null);
  }

  public void testGetCarComponentBySearchModel() throws Exception {
    List<UsageStateType> usageList = new ArrayList<>();
    List<StockType> stockList = new ArrayList<>();
    usageList.add(UsageStateType.OLD);
    SearchComponentModel searchModel = new SearchComponentModel("aripa", null, usageList, stockList, 0, 0);
    List<CarComponentModel> result = repo.getCarComponentBySearchModel(searchModel);
    assertTrue(result != null);
  }
}
