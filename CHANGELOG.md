# BoneSkeleton 1.3.0 Changelog

## New: configurable vanilla / modded skeleton chance

Skeletons now roll their type when they spawn:

- **Modded type**: skeletons hold bones, strays hold fish, bogged hold poisoned arrows, parched hold dead bushes. They always use melee attacks and the modded loot tables.
- **Vanilla type**: bows, vanilla ranged AI, and vanilla loot tables — completely unchanged vanilla behavior.
- **Wither skeletons are unaffected.**

Chance values:

| Chance | Result |
|--------|--------|
| 0% | All skeletons spawn as vanilla type |
| 50% | Roughly half vanilla, half modded |
| 100% | All skeletons spawn as modded type (default; preserves previous behavior) |

The chance is rolled **once at spawn** and stored on the entity. Skeletons that already exist are not affected by later changes.

## How to configure

- **In game**: with Cloth Config installed, press **K** (default key, rebindable in Controls) and drag the chance slider.
- **Mod Menu**: with Mod Menu installed, a **Config** button appears in the mod list.
- **Config file**: settings are stored in `config/boneskeleton.json` and can be edited manually.
- **Server command** (requires OP / permission level 2):
  - `/boneskeleton chance` — show the current chance
  - `/boneskeleton chance <0-100>` — set and save the chance; applies to newly spawned skeletons immediately

**Cloth Config and Mod Menu are optional.** Without them, the game still starts normally; the chance can be changed via the server command or the config file. **Fabric API is required.**

On a dedicated server, edit the server's config file or use the command. Pressing K on a client only changes that client's local config and does not affect the server.
