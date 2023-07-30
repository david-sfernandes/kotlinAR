package com.example.kotlinar.common.utils;

import android.graphics.Bitmap;

import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Session;

import java.io.IOException;

public class CreateARImageDB extends Thread {
  private final Session session;
  private final Config config;
  private final String[] urls;
  
  public CreateARImageDB(Session session, Config config, String[] urls) {
    this.session = session;
    this.config = config;
    this.urls = urls;
  }
  
  public void run() {
    AugmentedImageDatabase augmentedImageDatabase = new AugmentedImageDatabase(session);
    float imgWidth = 0.10f;
    for (String url : urls) {
      Bitmap bitmap;
      try {
        bitmap = GetBitmap.get(url);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      augmentedImageDatabase.addImage(url, bitmap, imgWidth);
    }
    config.setAugmentedImageDatabase(augmentedImageDatabase);
  }
}
