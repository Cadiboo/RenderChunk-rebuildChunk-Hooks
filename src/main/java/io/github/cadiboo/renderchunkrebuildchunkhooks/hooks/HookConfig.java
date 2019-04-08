package io.github.cadiboo.renderchunkrebuildchunkhooks.hooks;

/**
 * Reduces overhead by not creating or posting events if no mods require them
 */
public final class HookConfig {

	// RebuildChunkPreEvent
	// RebuildChunkCheckWorldEvent
	// RebuildChunkPreRenderEvent
	// RebuildChunkPreIterationEvent
	// RebuildChunkIsFluidEmptyEvent
	// RebuildChunkCanFluidRenderInLayerEvent
	// RebuildChunkRenderFluidEvent
	// RebuildChunkCanBlockRenderTypeBeRenderedEvent
	// RebuildChunkCanBlockRenderInLayerEvent
	// RebuildChunkRenderBlockEvent
	// RebuildChunkPostIterationEvent
	// RebuildChunkPostRenderEvent
	// RebuildChunkPostEvent

	static boolean postRebuildChunkPreEvent = false;
	static boolean postRebuildChunkCheckWorldEvent = false;
	static boolean postRebuildChunkPreRenderEvent = false;
	static boolean postRebuildChunkPreIterationEvent = false;
	static boolean postRebuildChunkIsFluidEmptyEvent = false;
	static boolean postRebuildChunkCanFluidRenderInLayerEvent = false;
	static boolean postRebuildChunkRenderFluidEvent = false;
	static boolean postRebuildChunkCanBlockRenderTypeBeRenderedEvent = false;
	static boolean postRebuildChunkCanBlockRenderInLayerEvent = false;
	static boolean postRebuildChunkRenderBlockEvent = false;
	static boolean postRebuildChunkPostIterationEvent = false;
	static boolean postRebuildChunkPostRenderEvent = false;
	static boolean postRebuildChunkPostEvent = false;

	public static void enableRebuildChunkPreEvent() {
		postRebuildChunkPreEvent = true;
	}

	public static void enableRebuildChunkCheckWorldEvent() {
		postRebuildChunkCheckWorldEvent = true;
	}

	public static void enableRebuildChunkPreRenderEvent() {
		postRebuildChunkPreRenderEvent = true;
	}

	public static void enableRebuildChunkPreIterationEvent() {
		postRebuildChunkPreIterationEvent = true;
	}

	public static void enableRebuildChunkIsFluidEmptyEvent() {
		postRebuildChunkIsFluidEmptyEvent = true;
	}

	public static void enableRebuildChunkCanFluidRenderInLayerEvent() {
		postRebuildChunkCanFluidRenderInLayerEvent = true;
	}

	public static void enableRebuildChunkRenderFluidEvent() {
		postRebuildChunkRenderFluidEvent = true;
	}

	public static void enableRebuildChunkCanBlockRenderTypeBeRenderedEvent() {
		postRebuildChunkCanBlockRenderTypeBeRenderedEvent = true;
	}

	public static void enableRebuildChunkCanBlockRenderInLayerEvent() {
		postRebuildChunkCanBlockRenderInLayerEvent = true;
	}

	public static void enableRebuildChunkRenderBlockEvent() {
		postRebuildChunkRenderBlockEvent = true;
	}

	public static void enableRebuildChunkPostIterationEvent() {
		postRebuildChunkPostIterationEvent = true;
	}

	public static void enableRebuildChunkPostRenderEvent() {
		postRebuildChunkPostRenderEvent = true;
	}

	public static void enableRebuildChunkPostEvent() {
		postRebuildChunkPostEvent = true;
	}

	private static void disableAll() {
		postRebuildChunkPreEvent = false;
		postRebuildChunkCheckWorldEvent = false;
		postRebuildChunkPreRenderEvent = false;
		postRebuildChunkPreIterationEvent = false;
		postRebuildChunkIsFluidEmptyEvent = false;
		postRebuildChunkCanFluidRenderInLayerEvent = false;
		postRebuildChunkRenderFluidEvent = false;
		postRebuildChunkCanBlockRenderTypeBeRenderedEvent = false;
		postRebuildChunkCanBlockRenderInLayerEvent = false;
		postRebuildChunkRenderBlockEvent = false;
		postRebuildChunkPostIterationEvent = false;
		postRebuildChunkPostRenderEvent = false;
		postRebuildChunkPostEvent = false;
	}

	private static void enableAll() {
		postRebuildChunkPreEvent = true;
		postRebuildChunkCheckWorldEvent = true;
		postRebuildChunkPreRenderEvent = true;
		postRebuildChunkPreIterationEvent = true;
		postRebuildChunkIsFluidEmptyEvent = true;
		postRebuildChunkCanFluidRenderInLayerEvent = true;
		postRebuildChunkRenderFluidEvent = true;
		postRebuildChunkCanBlockRenderTypeBeRenderedEvent = true;
		postRebuildChunkCanBlockRenderInLayerEvent = true;
		postRebuildChunkRenderBlockEvent = true;
		postRebuildChunkPostIterationEvent = true;
		postRebuildChunkPostRenderEvent = true;
		postRebuildChunkPostEvent = true;
	}

}
