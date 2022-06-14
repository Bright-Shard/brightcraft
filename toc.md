# Table of Contents
This file explains pretty much all the files in BrightCraft. I recommend using control/command + f to find what
you're looking for - it's a little long, lol.



# The Main Files
- [`/Main`](src/main/java/dev/brightshard/brightcraft/Main.java): The core of BrightCraft. It's the main file, hence the name (woah, really??? O.o). It also bypasses the "
<player> kicked for floating too long" by moving the player down every 40 ticks.



# BrightCraft's Events
- [`/events/`](src/main/java/dev/brightshard/brightcraft/events): BrightCraft uses an event system for its cheats. For
example, the XRay cheat has an event that is triggered every time a block should be rendered; then, if it is enabled,
the XRay cheat will handle that event and only render a block if it's selected in one of the XRay groups.
  - `EventData`: A class for sharing data between event triggers and the event handlers.
  - `EventHandler`: A class for code that handles events when they're triggered.
  - `EventManager`: The manager for binding and triggering events.



# BrightCraft's Cheats
- [`/hacks/`](src/main/java/dev/brightshard/brightcraft/hacks): Where all of the cheats are stored.
  - `Fly`: The fly cheat.
  - `Fullbright`: The fullbright cheat.
  - `Instabreak`: The instant mine cheat.
  - `Jetpack`: The jetpack cheat.
  - `NoClip`: The noclip cheat.
  - `NoFallDamage`: The cheat that blocks fall damage.
  - `Speed`: The speed cheat.
  - `XRay`: The xray cheat.



# BrightCraft's Libraries
- [`/lib/`](src/main/java/dev/brightshard/brightcraft/lib): Libraries for BrightCraft. These just save me time while
programming.
    - `Chat`: Tool for sending chat messages (to the player, not everyone on the server)
    - `Config`: This manages configuration (e.g. what cheats are enabled) and saves them to a config file.
    - `Hack`: The core Hack class that all the cheats extend.
    - `MathTools`: Useful math functions.
    - `XRayBlockGroup`: A class for storing a group of blocks in a group - e.g. all ore blocks in the ore group.
    - `Keybinds`: Manages keybinds.



# BrightCraft's Managers
- [`/managers/`](src/main/java/dev/brightshard/brightcraft/managers): These each manage specific parts of BrightCraft.
They're almost all interfaces that are then implemented by the mixins so that the changes apply directly to Minecraft.
  - `ClientManager`: Makes parts of the client easier to modify.
  - `GameOptionsManager`: Makes the gamma option modifiable.
  - `InteractionManager`: Makes it easier to send action packets.
  - `PlayerManager`: Heavily modifies to player, to add functions for moving it and changing its properties.



# BrightCraft's Mixins
- [`/mixin`](src/main/java/dev/brightshard/brightcraft/mixin): All of the mixins in BrightCraft. These mostly trigger
events for the cheats to handle.
    - `BlockMixin`: (When XRay is on) Makes XRay work by setting `shouldDrawSide` to false for blocks that
      shouldn't be visible (e.g. dirt and stone).
    - `ClientPlayerEntityMixin`: Implements the PlayerManager interface.
    - `ClientPlayerInteractionManagerMixin`: Implements the InteractionManager interface.
    - `ClientPlayerNetworkHandlerMixin`: Tells BrightCraft when the player joins a world.
    - `ClientWorldMixin`: Tells BrightCraft when the player leaves a world.
    - `GameMenuScreenMixin`: Adds BrightCraft's settings button the game's menu screen.
    - `GameOptionsMixin`: Implements the GameOptionsManager interface.
    - `MinecraftClientMixin`: Implements the ClientManager interface and disables ambient occlusion when XRay is on.
    - `PlayerEntityMixin`: Implements the PlayerManager interface.
    - `SimpleOptionMixin`: Allows SimpleOptions in Minecraft to be turned up past their max value (like Gamma past 1
  for Fullbright to work).



# BrightCraft's UI
- [`/ui/`](src/main/java/dev/brightshard/brightcraft/ui): The UI for BrightCraft.
    - `/ui/widgets/`: The widgets for BrightCraft's screens.
        - `BrightScreen`: A wrapper for Minecraft's `Screen` class, with additional features I needed.
        - `Button`: A wrapper for adding buttons to a `BrightScreen`. It calculates the size and placement
          for the widget automagically.
        - `Slider`: Basically the same thing as above, but for adding sliders. It automatically maps the
          slider's value to the min/max value you provide.
    - `AnticheatMenu`: A few (actually 1 lol) (VERY BASIC) anticheat bypasses.
    - `SettingsMenu`: BrightCraft's settings.
    - `XRayMenu`: For changing what XRay block groups will be visible when XRay is on.