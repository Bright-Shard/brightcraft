# Table of Contents
This file explains pretty much all the files in BrightCraft. I recommend using control/command + f to find what
you're looking for - it's a little long, lol.



# The Main Files
- [`/Main`](src/main/java/dev/brightshard/brightcraft/Main.java): The core of BrightCraft. It's the main file, hence the name (woah, really??? O.o). It also bypasses the "
<player> kicked for floating too long" by moving the player down every 40 ticks.
- [`/InitHacks`](src/main/java/dev/brightshard/brightcraft/InitHacks.java): This initiates all the hacks in BrightCraft, ensuring that only one of each is started and that
  all of them actually get started.
- [`/hacks`](src/main/java/dev/brightshard/brightcraft/hacks): All of the cheats. They all extend the `Hack` class in `/lib`. I'm not going to bother listing the files,
because the names are pretty self-explanatory.



# BrightCraft's Tools/Wrappers
- [`/lib`](src/main/java/dev/brightshard/brightcraft/lib): Resources for BrightCraft.
    - `/lib/Chat`: Tool for sending chat messages (to the player, not everyone on the server)
    - `/lib/Config`: This manages configuration (e.g. what cheats are enabled) and saves them to a config file.
    - `/lib/KeyBindManager`: A class for registering all of BrightCraft's keybinds, and checking if they were pressed
      once per tick.
    - `/lib/MathTools`: Math tools. Currently, it only has a `map` function that I use with Minecraft's slider widget.
    - `/lib/PlayerManager`: Pretty much core to BrightCraft - this handles moving the player and the player's various
      properties.



# BrightCraft's Mixins
- [`/mixin`](src/main/java/dev/brightshard/brightcraft/mixin): All of the mixins in BrightCraft. Each file is named `<class_that_it_injects_into>Mixin`.
    - `/mixin/BlockMixin`: (When XRay is on) Makes XRay work by setting `shouldDrawSide` to false for blocks that
      shouldn't be visible (e.g. dirt and stone).
    - `/mixin/ClientPlayerInteractionManagerMixin`: Used for the InstantBreak hack. Also has a stupidly long name.
    - `/mixin/ClientPlayerNetworkHandlerMixin`: Makes the message showing what cheats are enabled send when the
      player joins a world.
    - `/mixin/GameMenuScreenMixin`: Adds BrightCraft's settings button the game's menu screen.
    - `/mixin/GameRendererMixin`: Grants nightvision when FullBright is on.
    - `/mixin/MinecraftClienMixin`: Turns off Ambient Occlusion when XRay is on (Otherwise shadows get shown on the ore
      blocks, so the player can't see them even with FullBright).
    - `/mixin/PlayerEntityMixin`: Turns on NoClip for the player.



# BrightCraft's UI
- [`/ui`](src/main/java/dev/brightshard/brightcraft/ui): The UI for BrightCraft.
    - `/ui/widgets`: The widgets for BrightCraft's screens.
        - `/ui/widgets/BrightScreen`: A wrapper for Minecraft's `Screen` class, with additional features I needed.
        - `/ui/widgets/Button`: A wrapper for adding buttons to a `BrightScreen`. It calculates the size and placement
          for the widget automagically.
        - `/ui/widgets/Slider`: Basically the same thing as above, but for adding sliders. It automatically maps the
          slider's value to the min/max value you provide.
    - `/ui/AnticheatMenu`: A few (actually 1 lol) (VERY BASIC) anticheat bypasses.
    - `/ui/SettingsMenu`: BrightCraft's settings.