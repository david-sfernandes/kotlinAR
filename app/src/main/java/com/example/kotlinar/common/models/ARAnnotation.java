package com.example.kotlinar.common.models;

import com.example.kotlinar.common.rendering.ObjectRenderer;

public class ARAnnotation {
  private final String annotation;
  private final ObjectRenderer ARObject;
  
  public ARAnnotation(String annotation, ObjectRenderer ARObject) {
    this.annotation = annotation;
    this.ARObject = ARObject;
  }
  
  public String getAnnotation() {
    return annotation;
  }
  
  public ObjectRenderer getARObject() {
    return ARObject;
  }
}
