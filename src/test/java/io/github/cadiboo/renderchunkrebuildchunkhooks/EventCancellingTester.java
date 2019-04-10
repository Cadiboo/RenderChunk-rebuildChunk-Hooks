package io.github.cadiboo.renderchunkrebuildchunkhooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderTypeBeRenderedEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanFluidRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCheckWorldEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkIsFluidEmptyEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostIterationEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreIterationEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkRenderBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkRenderFluidEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.concurrent.locks.ReentrantLock;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.util.Refs.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public final class EventCancellingTester {

	private static boolean enabled = true;
	static {
		if (enabled) {
			HookConfig.enableAll();
		}
	}

	@SubscribeEvent
	public static void onRebuildChunkPreEvent(final RebuildChunkPreEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkCheckWorldEvent(final RebuildChunkCheckWorldEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkPreRenderEvent(final RebuildChunkPreRenderEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkPreIterationEvent(final RebuildChunkPreIterationEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkIsFluidEmptyEvent(final RebuildChunkIsFluidEmptyEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkCanFluidRenderInLayerEvent(final RebuildChunkCanFluidRenderInLayerEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkRenderFluidEvent(final RebuildChunkRenderFluidEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkCanBlockRenderTypeBeRenderedEvent(final RebuildChunkCanBlockRenderTypeBeRenderedEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkCanBlockRenderInLayerEvent(final RebuildChunkCanBlockRenderInLayerEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkRenderBlockEvent(final RebuildChunkRenderBlockEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkPostIterationEvent(final RebuildChunkPostIterationEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkPostRenderEvent(final RebuildChunkPostRenderEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkPostEvent(final RebuildChunkPostEvent event) {
		if (!enabled) {
			return;
		}

		event.setCanceled(true);
	}

}
