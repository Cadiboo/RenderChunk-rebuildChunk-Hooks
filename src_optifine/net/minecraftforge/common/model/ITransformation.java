package net.minecraftforge.common.model;

import javax.vecmath.Matrix4f;

public interface ITransformation {
   Matrix4f getMatrix();

   fa rotate(fa var1);

   int rotate(fa var1, int var2);
}
