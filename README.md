# RenderChunk rebuildChunk Hooks
A small(ish) coremod (that is not very well made) for 1.12.2 to inject hooks into RenderChunk#rebuildChunk to allow modders to add their own custom rendering logic. Here be dragons - You have been warned.

## [Download (click)](https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/tree/master/build/libs)

## Compatability
Either 100% or 0%. If another core-mod tries to tamper with RenderChunk#rebuildChunk, it is likely that the game will crash. Report it to me and them, and I'll try and work with them to solve the problem (either add the functionality that they were adding to my mod or agree on a way to do things that allows both our mods to work)
### It is up to the authors of mods who use these hooks to make sure they don't break anything!
#### Use Forge's Render Pipeline where possible!

## Why coremod?
I submitted a [PR to forge](https://github.com/MinecraftForge/MinecraftForge/pull/5166) that didn't get accepted and with the 1.13 update imminent I decided to push out something that worked for 1.12.2. I will be trying to [work with other people to get similar hooks (with better performance etc.) into Forge](https://github.com/MinecraftForge/MinecraftForge/pull/5166#issuecomment-427589440).

###### See Also
[MinecraftForge Forums - [1.12.2] Replace world renderer](http://www.minecraftforge.net/forum/topic/66516-1122-replace-world-renderer/)

###### Known Users:
NoCubes (1.12.x) https://github.com/Cadiboo/NoCubes
