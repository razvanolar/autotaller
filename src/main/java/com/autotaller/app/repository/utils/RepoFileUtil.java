package com.autotaller.app.repository.utils;

import com.autotaller.app.utils.StringValidator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by razvanolar on 28.05.2017
 */
public class RepoFileUtil {

  private static String CARS_DIR = "cars\\";

  private String HOME;

  public RepoFileUtil(String HOME) {
    this.HOME = completePath(HOME);
  }

  public void copyCarImages(int carId, List<File> images) throws Exception {
    File destinationDir = getCarDir(carId);
    for (File file : images) {
      FileUtils.copyFileToDirectory(file, destinationDir);
    }
  }

  public List<File> getCarFiles(int carId) throws Exception {
    File carDir = getCarDir(carId);
    File[] files = carDir.listFiles();
    if (files == null || files.length == 0)
      return null;
    List<File> result = new ArrayList<>(files.length);
    result.addAll(Arrays.asList(files));
    return result;
  }

  public String completePath(String path) {
    if (!StringValidator.isNullOrEmpty(path) && !path.endsWith("\\")) {
      path += "\\";
    }
    return path;
  }

  public boolean isHomeSet() {
    return !StringValidator.isNullOrEmpty(HOME);
  }

  private File getCarDir(int carId) throws Exception {
    File file = new File(HOME + CARS_DIR + carId);
    if (!file.exists() && !file.mkdirs())
      throw new Exception("Unable to create home dir for car id. carId = " + carId);
    return file;
  }
}
