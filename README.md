# BlockSumoPlugin
### Beta replica of the BlockSumo mingame from the bedwarspractice server.  

# What is this?
BlockSumo is a Sumo but with blocks. You basically have to stay alive while everyone gets killed 5x (or more if bonuses were collected).  
Unlike sumo, you can place blocks (that by default disappear after a while), and you have bonuses, both at the middle of the map (OP Bonuses) and spawning randomly in your inventory.  
Altho you only have 2 items (shears & blocks), you can edit your kit so that you have them placed correctly in your inventory.  

# Configuration
This plugin was made to be as costumizable as possible, so you may get lost in the config.  
As of now, since the plugin is still in beta and that i'm still adding functionalities, I haven't yet made a proper wiki for what every field in the config does (*altho you should be able to figure it out yourself for most of them*).


# Build
Compile using Java 8 only for Minecraft 1.8.8 only.
- Git clone this repo  
- Open it in your editor of choice (personally using VSCode)  
- Import the Paper 1.8.8's "cache/patched_1.8.8.jar" as a references library  
- Export the Jar (no build tools, do it manually)  
- Done !  


# Questionnable decisions
Game-Wise, this plugin was made to be one I could enjoy however i wanted (hence the extended config). However, some coding decisions may seem a bit weird:   
## Using names instead of UUIDs
This plugin should be able to work with cracked accounts (untested but it should), which isn't possible with UUIDs
## The config
The config has a really weird system, but all of that's explained in the "me/nixuge/config/Config.java" file
## The libraries
This project was mostly made for training for me, so pretty much no libs were used.  
If/When this project gets serious, I'll **maybe** (maybe) switch to existing libs for things like particles.  
## No build tools?
PM me if you want to teach me Graddle or Maven, for now i'm litterally just exporting jar/hot reloading to use the plugin, and you may want to do the same.

# Additional note (1.8 only):
I'm currently adding other version support, for now focusing on 1.7. Will eventually add support for at least 1.12 & 1.16.