/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.kotlinar.rendering;

import android.content.Context;
import android.util.Log;

import com.example.kotlinar.common.models.ARAnnotation;
import com.example.kotlinar.common.rendering.ObjectRenderer;
import com.google.ar.core.Anchor;
import com.google.ar.core.Pose;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Renders an augmented image.
 */
public class AugmentedImageRenderer {
  private static final String TAG = "AugmentedImageRenderer";
  private final ArrayList<ARAnnotation> ARObjects = new ArrayList<>();
  
  public void createOnGlThreadPlane(Context context, float diffuse, String[] images) throws IOException {
    for (String image : images) {
      ARAnnotation ARAnnotation = new ARAnnotation(image, new ObjectRenderer());
      Log.d(TAG, "**** createOnGlThreadPlane: " + image);
      
      ARAnnotation.getARObject().createOnGlThread(context, "models/plane.obj", image);
      ARAnnotation.getARObject().setMaterialProperties(0.0f, diffuse, 1.0f, 6.0f);
      ARAnnotation.getARObject().setBlendMode(ObjectRenderer.BlendMode.AlphaBlending);
      ARObjects.add(ARAnnotation);
    }
  }
  
  public void draw(
      float[] viewMatrix,
      float[] projectionMatrix,
      Anchor centerAnchor,
      String annotation) throws Exception {
    Pose localBoundaryPose = Pose.makeTranslation(0.0f, 0.0f, 0.0f);
    Pose anchorPose = centerAnchor.getPose();
    Pose worldBoundaryPose = anchorPose.compose(localBoundaryPose);
    float scaleFactor = 0.08f;
    float[] modelMatrix = new float[16];
    float[] colorCorrectionRgba = new float[] {0.5f, 0.5f, 0.5f, 0.7f};
    worldBoundaryPose.toMatrix(modelMatrix, 0);
    
    ARAnnotation ARAnnotation = ARObjects
        .stream()
        .filter(obj -> obj.getAnnotation().equals(annotation))
        .findFirst()
        .orElseThrow(() -> new Exception("No ARObject found with annotation: " + annotation + "\n objects" + ARObjects));
    
    ARAnnotation.getARObject().updateModelMatrix(modelMatrix, scaleFactor);
    ARAnnotation.getARObject().draw(viewMatrix, projectionMatrix, colorCorrectionRgba);
  }
}