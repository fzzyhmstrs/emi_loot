# Emi Loot
<p align="left">
<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-brightgreen.svg"></a>
</p>

Emi Loot is a plugin for [EMI](https://github.com/emilyploszaj/emi) that displays loot drops from chests, mobs, blocks, and more.

Credit for loot condition icon art goes to [lxly9](https://github.com/lxly9). Thank you!!

## Known Compatible Loot or Content Mods:
* [Amethyst Imbuement](https://modrinth.com/mod/amethyst-imbuement)
* [Botania](https://modrinth.com/mod/botania)
* [Dark Loot](https://www.curseforge.com/minecraft/mc-mods/darkloot-better-mob-loot)
* [Immersive Weathering](https://modrinth.com/mod/immersive-weathering)
* [Loot Config](https://www.curseforge.com/minecraft/mc-mods/loot-config)
* [Lootify](https://modrinth.com/mod/lootify)

## Known INCOMPATIBLE Mods
* (Neo)Forge / [Porting Lib](https://github.com/Fabricators-of-Create/Porting-Lib) Global Loot Modifiers: Affects drops on generation, doesn't modify loot tables themselves.
* [Better End](https://www.curseforge.com/minecraft/mc-mods/betterend): Hardcoded drops
* [Better Nether](https://www.curseforge.com/minecraft/mc-mods/betternether): Hardcoded drops
* [LootJS](https://modrinth.com/mod/lootjs): Affects drops on generation, doesn't modify loot tables themselves.
* [Paradise Lost](https://www.curseforge.com/minecraft/mc-mods/paradise-lost): Hardcoded drops

## TODO LIST

|Task|Status|Planned Release|
|----|------|---------------|
|_Released_|---|---|
|Add chest loot|Complete|0.1.0|
|Add mob loot|Complete|0.2.0|
|Add block drops|Complete|0.2.0|
|Add new widgets for gui updating|Complete|0.3.0|
|Update mob/block guis|Complete|0.3.0|
|Update Mob EMI recipes for new layouts|Complete|0.3.0|
|Update Block EMI recipes for new layouts|Complete|0.3.0|
|Fix smelting condition showing the unsmelted item|Complete|0.3.0|
|Add option for small mob loot tables shown to the right instead of below|Complete|0.3.1|
|Review and fix mob/block table percentages|Complete|0.3.1|
|Implement EntityPredicate Parser|Complete|0.4.0|
|Update icon widget impl to handle custom sprites|Complete|0.4.0|
|Implement other missing parsers as needed|Complete|0.4.0|
|Remove GUI wiggles|Complete|0.5.0|
|Starting impl of basic config|Complete|0.5.0|
|Check mob/block table percentages actually right|Complete|0.5.0|
|Port to 1.19.3 to match to EMI's update|Complete|0.5.0|
|Update parser to apply top level functions to inner stacks|Complete|0.5.0|
|Impl resource-driven mob offsetting overrides|Complete|0.5.0|
|Optimize packet sending size/number|Complete|0.5.0|
|Impl handler for code-based mob drops (nether star etc)|Complete|0.5.0|
|Impl entry post-processor system that allows for parsing certain entries at later stages of the data-loading lifecycle|Complete|0.6.0|
|Impl tag item entries using post-processing|Complete|0.6.0|
|Refactor client table builders for commonality|Complete|0.6.0|
|Remove test tag entry loot pool|Complete|0.6.0|
|Impl group item entries|Complete|0.6.0|
|Impl sequence item entries|Complete|0.6.0|
|Impl fishing/gameplay loot|Complete|0.6.0|
|Add table exclusion system to exclude certain tables from parsing|Complete|0.6.0|
|_In Development_|---|---|
|Working on other projects for the moment after the release of 0.6.0|---|---|
|_Not yet released_|---|---|
|Fix issue with compact chest loot stacking|Not started|?.?.?|
|Fix Itemstack.EMPTY getting enchanted with Random|Not started|?.?.?|
|Chgeck on the NBT tag too big problem|Not started|?.?.?|
|Impl dynamic item entries|Not started|?.?.?|
|Improve collation of same-condition stacks|Not started|?.?.?|
|Add item tile optimizer to improve layout of condition tiles|?.?.?|
|Implement resource-pack driven custom functions/conditions|Not started|?.?.?|
|Add method for displaying experience drops from mobs|Not started|?.?.?|
|Investigate way to calculate and convey item drop probability|Not started|?.?.?|
|Investigate the possibility to add LootJs support|Not started|?.?.?|
|Look into adding a worldgen "recipe" category|Not started|?.?.?|

## Direct Drops to Handle
* Creeper
  * Music discs
