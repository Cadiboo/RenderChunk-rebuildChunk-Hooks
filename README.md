# RenderChunk rebuildChunk Hooks
A small(ish) coremod for 1.12.2 to inject hooks into RenderChunk#rebuildChunk to allow modders to add their own custom chunk rendering logic and other chunk rendering related modifications.
###### Here be dragons - You have been warned.

## [Download (click)](https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/releases)

## Support
We currently support
- Forge 1.12.2 - 14.23.5.2775
- Forge 1.12.2 - 14.23.5.2768
- Forge 1.12.2 - 14.23.4.2725
- Optifine HD_U_E2 on Forge 1.12.2 - 14.23.4.2725

Note: The mod _should_ work all Forge 1.12.2 versions (actually all 1.12.x versions if you mess with the code of the mod) but due to the amount of work required to test the mod on all of them, we only officially support the above versions.
## Compatability
I've just added compatibility with Optifine HD_U_E2! 
I'm working to add compatability with BetterFolliage. If another core-mod tries to tamper with RenderChunk#rebuildChunk, it is possible that the game will crash. Report it to me and them, and I'll try and fix it myself or work with them to solve the problem! 
### It is up to the authors of mods who use these hooks to make sure they don't break anything!
#### Use Forge's Render Pipeline where possible!

## Why coremod?
I submitted a [PR to forge](https://github.com/MinecraftForge/MinecraftForge/pull/5166) that didn't get accepted and with the 1.13 update imminent I decided to push out something that worked for 1.12.2. I will be trying to [work with other people to get similar hooks (with better performance etc.) into Forge](https://github.com/MinecraftForge/MinecraftForge/pull/5166#issuecomment-427589440).

###### See Also
[MinecraftForge Forums - [1.12.2] Replace world renderer](http://www.minecraftforge.net/forum/topic/66516-1122-replace-world-renderer/)

###### Known Users:
NoCubes (1.12.2) [https://github.com/Cadiboo/NoCubes](https://github.com/Cadiboo/NoCubes)
AntiRix's Tiled Map Mod [https://map.mcserver.ml/](https://map.mcserver.ml/)
