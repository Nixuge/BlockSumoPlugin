# BlockSumoPlugin
### Beta replica of the BlockSumo mingame from the bedwarspractice server.  

# What is this?
BlockSumo is a Sumo but with blocks. You basically have to stay alive while everyone gets killed 5x (or more if bonuses were collected).  
Unlike sumo, you can place blocks (that by default disappear after a while), and you have bonuses, both at the middle of the map (OP Bonuses) and spawning randomly in your inventory.  
Altho you only have 2 items (shears & blocks), you can edit your kit so that you have them placed correctly in your inventory.  

# Configuration
[See the configuration documentation here](CONFIG.md)  
[See the example configuration here](config.yml)

# Build
Compile using Java 8 for Minecraft 1.8 - 1.12
- Git clone this repo  
- Open it in your editor of choice (personally using VSCode)  
- Import the Paper patcher jar "cache/patched_1.x.x.jar" as a referenced library  
    - Note that you can build with the 1.8 jar for 1.12 and vice versa, as long as you stay within the version range
- Export the Jar (no build tools, do it manually)  
- Done !  


# Questionnable decisions
Game-Wise, this plugin was made to be one I could enjoy however i wanted (hence the extended config). However, some coding decisions may seem a bit weird:   
## Using names instead of UUIDs
This plugin should be able to work with cracked accounts (untested but it should), which isn't possible with UUIDs
## The config
The config has a really weird system, but all of that's explained in [the Config class](me/nixuge/config/Config.java)
## The libraries
This project was mostly made for training for me, so pretty much no libs were used.  
If/When this project gets serious, I'll **maybe** (maybe) switch to existing libs for things like particles.  
## No build tools?
PM me if you want to teach me Graddle or Maven, for now i'm litterally just exporting jar/hot reloading to use the plugin, and you may want to do the same.

# Additional note, version support:
This is the 1.8 -> 1.12 (inclusive) branch.
Support for those versions is now finished, they work exactly the same way.

1.13+ still doesn't have it's own branch, as i'd need to actually play on those versions
and rewrite quite a lot of things, which i'm not really hyped to do. For this minigame, please use 1.8 or 1.7 anyways.