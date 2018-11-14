package net.minecraftforge.client.model.pipeline;

public interface IVertexConsumer {
   cea getVertexFormat();

   void setQuadTint(int var1);

   void setQuadOrientation(fa var1);

   void setQuadColored();

   void put(int var1, float... var2);
}
