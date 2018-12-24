# [RenderChunk rebuildChunk Hooks](https://cadiboo.github.io/projects/render_chunk_rebuild_chunk_hooks)
A small(ish) coremod for 1.12.2 to inject hooks into RenderChunk#rebuildChunk to allow Modders to add their own custom chunk rendering logic and other chunk rendering related modifications.
Made by [Cadiboo](https://github.com/Cadiboo/)
###### Here be dragons - You have been warned.

## [Download (click)](https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/releases/latest)

## Support
We currently support any combination of the below **on the same Forge version**

| Name          | Version       | Forge Version         | Minecraft Version|
| ------------- | ------------- | --------------------- | ---------------- |
| Forge         | 14.23.5.2795  | 1.12.2 - 14.23.5.2795 | 1.12.2           |
| BetterFoliage | MC1.12-2.2.0  | 1.12.2 - 14.23.5.2795 | 1.12.2           |
| Optifine      | HD_U_E3       | 1.12.2 - 14.23.5.2795 | 1.12.2           |
| Forge         | 14.23.5.2768  | 1.12.2 - 14.23.5.2768 | 1.12.2           |
| BetterFoliage | MC1.12-2.2.0  | 1.12.2 - 14.23.5.2768 | 1.12.2           |

Note: The mod _should_ work all Forge 1.12.2 versions (actually all 1.12.x versions if you remove the in-code version restrictions) but due to the amount of work required to test the mod on all of them, we only officially support the above versions.
## Compatibility
I've just added compatibility with Optifine HD_U_E3!
If another core-mod tries to tamper with RenderChunk#rebuildChunk, it is possible that the game will crash. Report it to me and them, and I'll try and fix it myself or work with them to solve the problem!
### It is up to the authors of mods who use these hooks to make sure they don't break anything!
#### Use Forge's Render Pipeline where possible!

## Why Coremod?
I submitted a [PR to forge](https://github.com/MinecraftForge/MinecraftForge/pull/5166) that didn't get accepted and with the 1.13 update imminent I decided to push out something that worked for 1.12.2. I will be trying to [work with other people to get similar hooks (with better performance etc.) into Forge](https://github.com/MinecraftForge/MinecraftForge/pull/5166#issuecomment-427589440).

#### Hooks Added
- The RebuildChunkPreEvent is called before any chunk rebuilding is done
    - RebuildChunkPreOptifineEvent is the same as the RebuildChunkPreEvent but allows access to Optifine-related objects
- The RebuildChunkBlockRenderInLayerEvent allows Modders to modify the BlockRenderLayers that blocks can render in
    - RebuildChunkBlockRenderInLayerOptifineEvent is the same as the RebuildChunkBlockRenderInLayerEvent but allows access to Optifine-related objects
- The RebuildChunkBlockRenderInTypeEvent allows Modders to modify the EnumBlockRenderType that blocks can render in
    - RebuildChunkBlockRenderInTypeOptifineEvent is the same as the RebuildChunkBlockRenderInTypeEvent but allows access to Optifine-related objects
- The RebuildChunkBlockEvent is called for each BlockRenderLayers of each block and allows Modders to add their own logic
    - RebuildChunkBlockOptifineEvent is the same as the RebuildChunkBlockEvent but allows access to Optifine-related objects
- The RebuildChunkPostEvent is called after all chunk rebuilding logic is done
    - RebuildChunkPostOptifineEvent is the same as the RebuildChunkPostEvent but allows access to Optifine-related objects

###### See Also
[MinecraftForge Forums - [1.12.2] Replace world renderer](http://www.minecraftforge.net/forum/topic/66516-1122-replace-world-renderer/)

###### Known Users:
- NoCubes (1.12.2) [https://github.com/Cadiboo/NoCubes](https://github.com/Cadiboo/NoCubes)
- AntiRix's Tiled Map Mod [https://map.mcserver.ml/](https://map.mcserver.ml/)
