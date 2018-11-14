package net.optifine.util;

import java.util.Comparator;
import org.lwjgl.opengl.DisplayMode;

public class DisplayModeComparator implements Comparator {
   public int compare(Object o1, Object o2) {
      DisplayMode dm1 = (DisplayMode)o1;
      DisplayMode dm2 = (DisplayMode)o2;
      if (dm1.getWidth() != dm2.getWidth()) {
         return dm1.getWidth() - dm2.getWidth();
      } else if (dm1.getHeight() != dm2.getHeight()) {
         return dm1.getHeight() - dm2.getHeight();
      } else if (dm1.getBitsPerPixel() != dm2.getBitsPerPixel()) {
         return dm1.getBitsPerPixel() - dm2.getBitsPerPixel();
      } else {
         return dm1.getFrequency() != dm2.getFrequency() ? dm1.getFrequency() - dm2.getFrequency() : 0;
      }
   }
}
