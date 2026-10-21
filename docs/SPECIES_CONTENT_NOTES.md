# Species content notes

This covers the Wickerbeast, Felkin and Manokit content, the species serums, and the optional Origins integration.
Everything is registered through the existing `ModBlocks`, `ModItems`, `ModEntities`, `ModBlockEntities` and
`ModFeatures` registers and the existing datagen providers. No new creative tabs were added: every new item is in
`galaxy_comes_alive_tab`.

Status: `./gradlew build` and `./gradlew runData` pass. None of this has been tested in-game yet, and the Origins
integration hasn't been run against either Origins jar.

## Changing species

There is one species system: the `avaliproject:species` player attachment (`ModAttachments.SPECIES`). It's written in
one place, `species/SpeciesUtil.setSpecies(ServerPlayer, Species)`. That method sets the attachment, sends
`SpeciesSyncPayload`, and tells the Origins compat layer, if one is active. Both the command and the serums call it.

```
/species set <human|expie|avali|sergal|protogen|wickerbeast|felkin|manokit>
/species reset
```

`WICKERBEAST`, `FELKIN` and `MANOKIT` were appended to `Species` after the existing values, so saved ordinals don't
change. Blood colours: Wickerbeast dark teal `#1F5F5B`, Felkin warm amber `#D98E2B`, Manokit sea teal `#2FB5A8`.
`SpeciesRenderHandler` draws the matching placeholder entity in place of the player.

## Species serums

| Item id | Sets species | Creative category tag | Recipe (shapeless) |
|---|---|---|---|
| `human_serum` | HUMAN (reset) | none, shown under All Items only | glass bottle + apple |
| `expie_serum` | EXPIE | `avaliproject:expie` | glass bottle + geofruit |
| `avali_serum` | AVALI | `avaliproject:avali` | glass bottle + lume |
| `sergal_serum` | SERGAL | `avaliproject:sergal` | glass bottle + agate |
| `protogen_serum` | PROTOGEN | `avaliproject:protogen` | glass bottle + novulite |
| `wickerbeast_serum` | WICKERBEAST | `avaliproject:wickerbeasts` | glass bottle + mane tuft |
| `felkin_serum` | FELKIN | `avaliproject:felkin` | glass bottle + felkin hide |
| `manokit_serum` | MANOKIT | `avaliproject:manokit` | glass bottle + sea opal |

Behaviour (`item/custom/SpeciesSerumItem`):

- Stacks to 16. You drink it like a potion (32 ticks).
- If you're already that species it refuses, doesn't start drinking, and shows "You are already a ...".
- On finish, on the server: calls `SpeciesUtil.setSpecies`, plays a drink sound, and says "You are now a ...".
  One serum is used up, except in creative.
- The textures are copies of existing bottle textures (`water_bottle`, `temporary_bottle`, `avali_bottle`,
  `groou_juice`, `saline`, `apple_juice`).

## Species bonuses

| Species | Bonus | Where it comes from |
|---|---|---|
| Wickerbeast | 25% chance of an extra Crystalithium Crystal from Crystalithium ore (no Silk Touch). Sees 54 Hoard Pile slots instead of 27 | Java, always |
| Felkin | +10% movement speed on land | Java, **only when no Origins port is loaded** |
| Manokit | +35% swim speed, Water Breathing while submerged | Java, **only when no Origins port is loaded** |
| Manokit | Coral Cultivation reach +2 and longer effects. Two better rolls when shearing an Arcane Manokit | Java, always |
| All non-human | Attribute and effect powers listed under Origins compatibility | Origins JSON, only when an Origins port is loaded |

When Origins is loaded, the Java speed, swim and water-breathing bonuses switch off so they don't stack with the
Origins powers. The JSON is then the only source of those stats.

## Origins compatibility (optional)

| Mod | Mod id | Status |
|---|---|---|
| Origins (NeoForge) by IAFEnvoy | `origins` | supported, preferred if both are installed |
| NeoOrigins by CyberDay1 | `neoorigins` | supported |

Both are declared `type="optional"` in `neoforge.mods.toml`. There's **no Gradle dependency** on either: neither port
publishes a stable API for setting an origin. `compat/origins/OriginsCompat` never imports Origins classes. Instead:

- **Detection:** `ModList.isLoaded("origins")`, then `"neoorigins"`. If both are loaded, `origins` is used and a single
  log line says neoorigins was skipped.
- **Data:** each port's JSON ships as a built-in datapack inside the jar (`compat_packs/origins_iaf/` or
  `compat_packs/neoorigins/`). Only the pack for the detected port is registered, through `AddPackFindersEvent`. This
  matters because NeoOrigins also scans `data/<ns>/origins/**` for legacy packs and would misread the other port's files.
- **Setting the origin:** `SpeciesUtil.setSpecies` runs the port's own command on the server with suppressed output:
  `origin set <player> origins:origin avaliproject:<species>` (IAFEnvoy) or
  `neoorigins set <player> neoorigins:origin avaliproject:<species>` (NeoOrigins). The human serum and `/species reset`
  switch the player to the port's built-in Human race (`origins:human` / `neoorigins:human`). If the command fails, the
  failure is logged and the internal species change stays.
- **Reverse sync:** picking a race in the Origins picker (on join, with an Orb of Origin, or with `/origin set`)
  updates the internal species. Picking one of our races sets that species; picking any other race (Human, Merling,
  Avian, ...) sets the internal species to Human. This listens via reflection to `GrantOriginEvent` (IAFEnvoy) or the
  public `OriginChangedEvent` (NeoOrigins).
- **Source of truth:** with an Origins mod installed, the player's race is the species. A player whose species was set
  before Origins was installed picks a race on their next join like everyone else.
- **No Origins installed:** nothing above runs, and the serums and command only change the internal species.

### Our species as races

With an Origins mod installed, the seven species are **races in the main Origins picker**, listed after the built-in
races (Human, Merling, Avian, ...). There is no extra page or layer. Serums still work and switch your race. Without
an Origins mod, the serums and `/species` are the only way to change species.

| Port | How our races are added | Built-in races |
|---|---|---|
| IAFEnvoy | the additive origin tag `data/origins/tags/origins/origin/origin.json` (`"replace": false`), which the built-in `origins:origin` layer reads | kept |
| NeoOrigins | `data/neoorigins/origins/origin_layers/origin.json`, which NeoOrigins merges into its built-in `neoorigins:origin` layer by appending to `origins` | kept |

Remove an entry from those files to take a species out of the picker. It stays reachable through its serum.

### Origins and powers

| Origin | Impact | Order | Powers |
|---|---|---|---|
| `avaliproject:expie` | 1 low | 200 | `expie_nimble`: movement speed +5% |
| `avaliproject:avali` | 1 low | 210 | `avali_light_bones`: fall damage −25% |
| `avaliproject:sergal` | 2 medium | 220 | `sergal_strength`: attack damage +1 |
| `avaliproject:protogen` | 2 medium | 230 | `protogen_plating`: armor +2 |
| `avaliproject:wickerbeast` | 2 medium | 240 | `wickerbeast_sturdy`: knockback resistance +0.2. `wickerbeast_digger`: block break speed +10%. `wickerbeast_stride`: step height +0.5 |
| `avaliproject:felkin` | 1 low | 250 | `felkin_swift`: movement speed +15% |
| `avaliproject:manokit` | 1 low | 260 | `manokit_gills`: water breathing. `manokit_swimmer`: swim speed +20% |

Orders start at 200 so our races sort after the built-in ones. The Expie, Avali, Sergal and Protogen powers are
placeholders; there was no Java bonus to mirror for them. Names and descriptions are in `en_us.json`:
`origin.avaliproject.<id>.name|description` and `power.avaliproject.<id>.name|description`.

## How to edit stats

All paths are under `src/main/resources/compat_packs/`. Edit the folder for the port you use, or both. Then rebuild,
or override the same path from a world datapack (see the next paragraph). No Java changes are needed.

| What | IAFEnvoy Origins (`origins_iaf/`) | NeoOrigins (`neoorigins/`) |
|---|---|---|
| Origin | `data/avaliproject/origins/origin/<species>.json` | `data/avaliproject/origins/origins/<species>.json` |
| Power | `data/avaliproject/origins/power/<power>.json` | `data/avaliproject/origins/powers/<power>.json` |
| Race list (which species appear) | `data/origins/tags/origins/origin/origin.json` | `data/neoorigins/origins/origin_layers/origin.json` |
| Impact field | integer `0`–`3` | `"none" \| "low" \| "medium" \| "high"` |

To change numbers without rebuilding the jar, copy a file into a world datapack at the same path, for example
`<world>/datapacks/my_tweaks/data/avaliproject/origins/power/felkin_swift.json`, and run `/reload`. NeoOrigins also
loads packs from `config/originpacks/`.

Example: make Felkin 25% faster instead of 15%.

IAFEnvoy, `origins_iaf/data/avaliproject/origins/power/felkin_swift.json`:
```json
{
  "type": "origins:attribute",
  "modifier": {
    "attribute": "minecraft:generic.movement_speed",
    "value": 0.25,
    "operation": "add_multiplied_base"
  }
}
```

NeoOrigins, `neoorigins/data/avaliproject/origins/powers/felkin_swift.json`:
```json
{
  "type": "neoorigins:attribute_modifier",
  "attribute": "minecraft:generic.movement_speed",
  "amount": 0.25,
  "operation": "add_multiplied_base",
  "name": "power.avaliproject.felkin_swift.name",
  "description": "power.avaliproject.felkin_swift.description"
}
```

Operations: `add_value`, `add_multiplied_base`, `add_multiplied_total`. To add a power, create a new file and list
its id in the origin's `powers` array. To change the Java-side bonuses used without Origins, edit the constants in
`event/SpeciesContentEvents`.

## How to change the origin icon

Both ports only support an **item** icon:

- IAFEnvoy: `"icon": {"id": "avaliproject:wickerbeast_serum"}` (an item stack)
- NeoOrigins: `"icon": "avaliproject:wickerbeast_serum"` (an item id)

Each origin points at its serum, so the easiest way to re-skin a picker icon is to replace
`assets/avaliproject/textures/item/<species>_serum.png`. Pointing `icon` at any other item also works.

`assets/avaliproject/textures/origin/<species>.png` (32×32 coloured placeholders) are also included, but **neither
port reads them**: neither has a texture-icon field. They're there for a future port that supports texture icons, or
for a custom badge. Delete them if you won't need them.

## Wardrobe

Press **G** (rebindable as *Open Species Wardrobe* under *The Galaxy Comes Alive*) to open the species wardrobe.

- **Cosmetic only.** It changes the texture of the species model you already have. It never touches species,
  origin, subspecies, health, speed or any other stat.
- **Filtered by your current species.** The list is built from `SpeciesCosmetics.forModel(species, subspecies)`
  for the species you are right now, so an Avali only ever sees Avali skins, never Protogen or Sergal ones. A Manokit
  only sees skins for its own subspecies model, because the three Manokit models have different UV layouts. Humans
  get the normal player preview and "No species skins".
- The server rejects a selection whose species doesn't match yours, or whose skin isn't registered for your
  species and model.
- Your choice is saved per species in the `species_cosmetics` attachment, which survives death. Switching species
  restores that species' last choice. An unknown or removed skin id falls back to the species default.
- Other players see your chosen skin. Species and skins are synced to everyone tracking you on login, respawn,
  dimension change and every species change. Wild mobs always keep their own texture.
- Click a row to preview it, then use **Apply** or double-click to wear it. **Reset** goes back to the default.

### Adding a skin

1. Add one line to the species' block in `species/SpeciesCosmetics`, for example
   `add(Species.SERGAL, null, "midnight", "sergal/sergal_midnight", SpeciesCosmetics.NO_VARIANT);`
   The arguments are: species, Manokit model (`null` unless it's a Manokit skin), id, texture path, and the wild-mob
   variant id it mirrors (`NO_VARIANT` if none).
2. Drop the png at `assets/avaliproject/textures/entity/sergal/sergal_midnight.png`. It must use the same UV layout
   as that species' model.
3. Add the lang key `"wardrobe.avaliproject.sergal.midnight": "Midnight"`.

If the png is missing, the row is still listed with "(texture missing)" and renders the species default instead.

Currently registered: Expie normal/white, Avali Cynavali (the old penguin/blank textures don't fit the new model),
all 12 Sergal colours, Protogen blue/purple/green, Wickerbeast default, and one texture per Manokit subspecies.
**Protogen purple and green have no png yet** (`protogen/protogen_purple.png`, `protogen/protogen_green.png`). The
wild Protogen renderer also maps all three variants to `protogen.png` today.

## Wickerbeast / Felkin / Manokit content

### Creative filter tags

`GalaxyCreativeScreen` filters on item tags generated by `ModItemTagProvider.addGalaxyCategoryTags()`. Every new item
is in exactly one of these:

- `src/generated/resources/data/avaliproject/tags/item/wickerbeasts.json`: ores, hoard pile, crystalithium crystal,
  horn fragment, claw shard, mane tuft, wicker egg, crystalithium trinket, spawn egg, serum
- `src/generated/resources/data/avaliproject/tags/item/felkin.json`: saddlebags, hide, spawn egg, serum
- `src/generated/resources/data/avaliproject/tags/item/manokit.json`: coral cultivation block, altar, sea glass,
  coconut, passion fruit and papaya logs and leaves, moonstone, sunstone and larimar ores and gems, fruits, shell, coral
  piece, driftwood, pearls, bone jewelry, pendant, manokit trinket, abalone, sea opal, jadeite, carnelian, ammolite,
  both spawn eggs, serum

`/src/generated` is in `.gitignore`. New generated files need `git add -f` to be committed.

### Blocks

- **Ores**: crystalithium, moonstone, sunstone and larimar, each with stone and deepslate variants. Pickaxe, iron
  tier. Silk Touch drops the ore, otherwise gems with Fortune. Ores also smelt into gems.
- **Shell cover** (`ShellCoverBlock`): `type=clam|conch|cowrie` plus `waterlogged`. No collision, needs a sturdy face
  below. The Shell item places it and breaking it drops Shells.
- **Fruiting leaves** (`FruitingLeavesBlock`): `age=0|1`. Ripen on random tick with sky light. Right-click ripe
  leaves to pick a fruit (server-side); the leaves stay. Breaking ripe leaves adds a fruit unless you use shears or
  Silk Touch.
- **Sprouts** (`FruitSaplingBlock`): planted by the coconut, passion fruit and papaya items. The coconut sprout also
  grows on sand.
- **Logs**: coconut log and wood, passion fruit log, papaya log.
- **Coral Cultivation Block**: active when waterlogged or touching water. Every 2 s it gives Regeneration I,
  Resistance I and Water Breathing within 5 blocks (7 for Manokit players).
- **Moonlit Offering Altar**: 3 slots, works at night under open sky, twice as fast on a full moon.
  Pearl + sea glass + moonstone → pendant. Shell + bone → bone jewelry. Fruit + gem → XP.
- **Hoard Pile**: 54-slot container. Non-Wickerbeast players see the first 27 slots.
- **Sea Glass**: translucent glass that drops itself.

### Items

Gems and materials: crystalithium crystal, wicker horn fragment, claw shard, mane tuft, wicker egg, felkin hide,
shell, coral piece, driftwood, pearl, black pearl, moonstone, sunstone, larimar, abalone shard, sea opal, jadeite,
carnelian, ammolite, bone jewelry.

Food: coconut (short saturation), papaya (Regeneration 5 s), passion fruit (Speed 10 s). Each fruit also plants
its sprout.

Gear: Felkin Saddlebags (18-slot bag that can be strapped onto a tamed Felkin), Pearl Pendant, Manokit Trinket (the
gem is stored on the item), Crystalithium Trinket.

### Entities

| Entity | Tamed with | Notes |
|---|---|---|
| Wickerbeast | crystalithium crystal | melee, guards Wicker Enclave Nests, spawns in dark forest and old-growth spruce taiga |
| Felkin | `avaliproject:felkin_food` | the owner can ride it (no jump), saddlebags, herds in savanna, plains and snowy plains |
| Manokit | tropical fruit | Three variants (see Manokit variants). Shearing an Arcane drops gems, with a 5-minute cooldown. Has a regular and an Arcane spawn egg. Beaches and jungles |

### Manokit variants

One entity, `avaliproject:manokit`, with a synced `Variant`, saved as `"Variant": "beachdweller" | "tribal" | "arcane"`.
Old saves with `"Arcan": true` load as arcane. Code: `entity/client/ManokitVariant`, `ManokitModel`, `ManokitRenderer`.

| Variant | Model | Texture | Animation | Hitbox | Render scale |
|---|---|---|---|---|---|
| beachdweller | `geo/manokit_beachdweller.geo.json` | `textures/entity/manokit/beachdweller/manokit_beachdweller.png` | `animations/manokit_beachdweller.animation.json` | 0.7 x 1.5 | 1.0 |
| tribal | `geo/manokit_tribal.geo.json` | `textures/entity/manokit/tribal/manokit_tribal.png` | none: player-style animation in code | 0.6 x 1.8 | 0.9375 |
| arcane | `geo/manokit_arcane.geo.json` | `textures/entity/manokit/arcane/manokit_arcane.png` | `animations/manokit_arcane.animation.json` | 0.7 x 1.75 | 1.0 |

- **Animated variants** play `Idle`, `Walking`, `Running` (fast movement), `Swimming` (in water) and `Crouching`
  (sitting). `NoPunch` is in the files but unused. The head follows where the mob looks, except while swimming or sitting.
- **Tribal** has no animation file. `ManokitModel` animates it like a vanilla player: arms and legs swing opposite each
  other while walking, the arms sway gently at rest, and the head follows its gaze. When sitting, the legs go forward,
  the arms come up and the body drops 10 px so it sits on the ground. The tail sways. Bones used: `Head`, `LeftArm`,
  `RightArm`, `LeftLeg`, `RightLeg`, `Tail`, `root`.
- **Textures** are 256x256 while the models declare 128x128. That's fine: they're double-resolution versions of the
  same layout, and GeckoLib scales UVs by the size declared in the model.
- **Natural spawns:** 15% arcane. Otherwise beach and ocean give beachdweller, and jungle and forest give tribal. The
  regular spawn egg gives beachdweller or tribal. Babies take one parent's variant.
- **Playing as a Manokit** (`/species set manokit` or the serum) shows the tribal model, since it uses player
  animations.

### Worldgen

| Feature | Where |
|---|---|
| Crystalithium ore | all overworld, a bit rarer than gold |
| Moonstone ore | overworld mid-depth, extra in coastal and ocean biomes |
| Sunstone ore | overworld, extra in savanna, desert and plains |
| Larimar ore | rare, mid-depth |
| Shell patches | dry and underwater, in beach, stony shore, lukewarm ocean and warm ocean |
| Coconut palms | beach, jungle, sparse jungle |
| Papaya and passion fruit trees | jungle, sparse jungle, bamboo jungle |
| Wicker Enclave Nest | dripstone and lush caves, dark forest. Loot table `chests/wicker_enclave_nest` |
| Beach Burrow | beach. Loot table `chests/beach_burrow` |
| Tropical Den | jungles. Loot table `chests/tropical_den` |

The three structures are placeholder layouts built in code (`worldgen/*Feature.java`), not NBT structures. Artists can
replace them later and keep the same loot tables.

## Curios (optional)

Maven `https://maven.theillusivec4.top/`, `curios-neoforge:9.4.0+1.21.1`, declared optional in `neoforge.mods.toml`.
All Curios API use is isolated in `compat/curios/CuriosCompat`.

- Slots: `data/avaliproject/curios/slots/{back,necklace,charm}.json` and
  `data/avaliproject/curios/entities/species_trinkets.json`.
- Tags: `curios:back` (saddlebags), `curios:necklace` (pendant, manokit trinket), `curios:charm` (crystalithium
  trinket).
- Effects (`item/custom/TrinketEffects`):
  - Pearl Pendant: water breathing.
  - Manokit Trinket, by gem: moonstone gives night vision at night; sunstone gives speed in daylight; larimar and sea
    opal give swim speed; the other gems give luck.
  - Crystalithium Trinket: mining speed.

## Placeholder art still outstanding

- Item textures for every new item and all 8 serums are copies of existing mod textures.
- Block textures: the new ores, hoard pile, coral cultivation block, altar and three shell covers are copies. All three
  shell types share one texture.
- Models that use vanilla textures: sea glass uses light blue stained glass. Logs use jungle and stripped jungle logs.
  All fruit leaves use azalea and flowering azalea leaves. Change these in `ModBlockStateProvider`.
- Entities: `geo/` and `animations/` for wickerbeast (from talxwolf) and felkin (from talxdog) are still copies, with
  copied textures. Replacement models must keep `Idle` and `Walk` animations and a `head` bone.
- Manokit has its own models now (see Manokit variants). Its three textures look like Blockbench colour UV templates.
- `textures/origin/*.png` are generated colour discs.
- The hoard pile, coral block and altar use full-cube models.

## Datagen

```
./gradlew runData
```

This runs `--mod avaliproject --all --output src/generated/resources --existing src/main/resources`. Serum item
models, tags and recipes come from datagen. `en_us.json` is hand-maintained in `src/main/resources`, like the rest of
the mod's lang. The Origins JSON in `compat_packs/` is hand-written data, not datagen output.
