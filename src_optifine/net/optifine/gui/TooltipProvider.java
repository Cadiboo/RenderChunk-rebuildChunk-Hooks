package net.optifine.gui;

import java.awt.Rectangle;

public interface TooltipProvider {
   Rectangle getTooltipBounds(blk var1, int var2, int var3);

   String[] getTooltipLines(bja var1, int var2);

   boolean isRenderBorder();
}
