package com.example.kotlinar.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GetBitmap {
  public static Bitmap get(String url) throws IOException {
    return BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
  }
}
