package net.optifine;

import java.nio.ByteBuffer;
import java.util.Properties;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class TextureAnimation {
   private String srcTex = null;
   private String dstTex = null;
   nf dstTexLoc = null;
   private int dstTextId = -1;
   private int dstX = 0;
   private int dstY = 0;
   private int frameWidth = 0;
   private int frameHeight = 0;
   private TextureAnimationFrame[] frames = null;
   private int currentFrameIndex = 0;
   private boolean interpolate = false;
   private int interpolateSkip = 0;
   private ByteBuffer interpolateData = null;
   byte[] srcData = null;
   private ByteBuffer imageData = null;
   private boolean active = true;
   private boolean valid = true;

   public TextureAnimation(String texFrom, byte[] srcData, String texTo, nf locTexTo, int dstX, int dstY, int frameWidth, int frameHeight, Properties props) {
      this.srcTex = texFrom;
      this.dstTex = texTo;
      this.dstTexLoc = locTexTo;
      this.dstX = dstX;
      this.dstY = dstY;
      this.frameWidth = frameWidth;
      this.frameHeight = frameHeight;
      int frameLen = frameWidth * frameHeight * 4;
      if (srcData.length % frameLen != 0) {
         .Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth + ", frameHeight: " + frameHeight);
      }

      this.srcData = srcData;
      int numFrames = srcData.length / frameLen;
      if (props.get("tile.0") != null) {
         for(int i = 0; props.get("tile." + i) != null; ++i) {
            numFrames = i + 1;
         }
      }

      String durationDefStr = (String)props.get("duration");
      int durationDef = Math.max(.Config.parseInt(durationDefStr, 1), 1);
      this.frames = new TextureAnimationFrame[numFrames];

      for(int i = 0; i < this.frames.length; ++i) {
         String indexStr = (String)props.get("tile." + i);
         int index = .Config.parseInt(indexStr, i);
         String durationStr = (String)props.get("duration." + i);
         int duration = Math.max(.Config.parseInt(durationStr, durationDef), 1);
         TextureAnimationFrame frm = new TextureAnimationFrame(index, duration);
         this.frames[i] = frm;
      }

      this.interpolate = .Config.parseBoolean(props.getProperty("interpolate"), false);
      this.interpolateSkip = .Config.parseInt(props.getProperty("skip"), 0);
      if (this.interpolate) {
         this.interpolateData = bia.c(frameLen);
      }

   }

   public boolean nextFrame() {
      TextureAnimationFrame frame = this.getCurrentFrame();
      if (frame == null) {
         return false;
      } else {
         ++frame.counter;
         if (frame.counter < frame.duration) {
            return this.interpolate;
         } else {
            frame.counter = 0;
            ++this.currentFrameIndex;
            if (this.currentFrameIndex >= this.frames.length) {
               this.currentFrameIndex = 0;
            }

            return true;
         }
      }
   }

   public TextureAnimationFrame getCurrentFrame() {
      return this.getFrame(this.currentFrameIndex);
   }

   public TextureAnimationFrame getFrame(int index) {
      if (this.frames.length <= 0) {
         return null;
      } else {
         if (index < 0 || index >= this.frames.length) {
            index = 0;
         }

         TextureAnimationFrame frame = this.frames[index];
         return frame;
      }
   }

   public int getFrameCount() {
      return this.frames.length;
   }

   public void updateTexture() {
      if (this.valid) {
         if (this.dstTextId < 0) {
            cds tex = TextureUtils.getTexture(this.dstTexLoc);
            if (tex == null) {
               this.valid = false;
               return;
            }

            this.dstTextId = tex.b();
         }

         if (this.imageData == null) {
            this.imageData = bia.c(this.srcData.length);
            this.imageData.put(this.srcData);
            this.imageData.flip();
            this.srcData = null;
         }

         this.active = SmartAnimations.isActive() ? SmartAnimations.isTextureRendered(this.dstTextId) : true;
         if (this.nextFrame()) {
            if (this.active) {
               int frameLen = this.frameWidth * this.frameHeight * 4;
               TextureAnimationFrame frame = this.getCurrentFrame();
               if (frame != null) {
                  int offset = frameLen * frame.index;
                  if (offset + frameLen <= this.imageData.limit()) {
                     if (this.interpolate && frame.counter > 0) {
                        if (this.interpolateSkip <= 1 || frame.counter % this.interpolateSkip == 0) {
                           TextureAnimationFrame frameNext = this.getFrame(this.currentFrameIndex + 1);
                           double k = 1.0D * (double)frame.counter / (double)frame.duration;
                           this.updateTextureInerpolate(frame, frameNext, k);
                        }
                     } else {
                        this.imageData.position(offset);
                        bus.i(this.dstTextId);
                        GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
                     }
                  }
               }
            }
         }
      }
   }

   private void updateTextureInerpolate(TextureAnimationFrame frame1, TextureAnimationFrame frame2, double k) {
      int frameLen = this.frameWidth * this.frameHeight * 4;
      int offset1 = frameLen * frame1.index;
      if (offset1 + frameLen <= this.imageData.limit()) {
         int offset2 = frameLen * frame2.index;
         if (offset2 + frameLen <= this.imageData.limit()) {
            this.interpolateData.clear();

            for(int i = 0; i < frameLen; ++i) {
               int c1 = this.imageData.get(offset1 + i) & 255;
               int c2 = this.imageData.get(offset2 + i) & 255;
               int c = this.mix(c1, c2, k);
               byte b = (byte)c;
               this.interpolateData.put(b);
            }

            this.interpolateData.flip();
            bus.i(this.dstTextId);
            GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.interpolateData);
         }
      }
   }

   private int mix(int col1, int col2, double k) {
      return (int)((double)col1 * (1.0D - k) + (double)col2 * k);
   }

   public String getSrcTex() {
      return this.srcTex;
   }

   public String getDstTex() {
      return this.dstTex;
   }

   public nf getDstTexLoc() {
      return this.dstTexLoc;
   }

   public boolean isActive() {
      return this.active;
   }
}
