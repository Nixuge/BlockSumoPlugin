# Types of elements in the config
| name             | description                               | examples                                                   |
| ---------------- | ----------------------------------------- | ---------------------------------------------------------- |
| bool             | Just a normal boolean                     | `true` \| `false`                                          |
| integer          | Just a normal integer                     | `1` \| `120` \| `999457`                                   |
| string           | Just a normal string                      | `Hello` \| `"Hi"` \| `world`                               |
| coordinates      | A string in the format `x y z`            | `100 80 100`  \|  `1 15 12`                                |
| coordinatesPlus  | A string in the format `x y z, yaw pitch` | `100 80 100, 90 0`  \|  `1 15 12, 90 6`                    |
| list (type)      | A list of items in the specified type     | `- 100 80 100` \| `- WOOL`<br> `- 120 80 120` \| `- STONE` |

# Categories
## game
| name                | type    | default                 | description                                                                                      |
| ------------------- | ------- | ----------------------- | ------------------------------------------------------------------------------------------------ |
| minplayercount      | integer | `2`                     | minimum playercount to start a game                                                              |
| maxplayercount      | integer | `8`                     | maximum playercount in a game                                                                    |
| countaskilldelay    | integer | `2`                     | delay until which the last player to hit someone gets the kill if he dies in that period of time |
| fireworkmaxtimetick | integer | `300`                   | delay in ticks until which fireworks stop being spawned at the game end                          |

## map
| name                | type                   | default                            | description                                                     |
| ------------------- | ---------------------- | ---------------------------------- | --------------------------------------------------------------- |
| world               | string                 | `world`                            | the name of the world in which the game is taking place         |
| centerblock         | coordinates            | `2 1 2`                            | coordinates of the center block itself                          |
| spawns              | list (coordinatesPlus) | `- 0 1 0, 90 6`<br>`- 5 1 5, 90 6` | coordinates of the blocks you can respawn on                    |
| destroyableblocks   | list (string)          | `- WOOL`<br>`- STONE`              | list of blocks that can be destroyed by a player and explosions |

## expiringblock
| name                      | type    | default | description                                                              |
| ------------------------- | ------- | ------- | ------------------------------------------------------------------------ |
| defaulttickbreaktime      | integer | `1200`  | time it takes in ticks for a block to break itself (set to 0 to disable) |
| defaulttickbreakstarttime | integer | `900`   | time it takes in ticks for a block to start showing it's breaking itself |
| mincolorchangecount       | integer | `1`     | minimum of times a wool is going to change color when you place it       |
| maxcolorchangecount       | integer | `6`     | maximum of times a wool is going to change color when you place it       |

## expiringblock
| name                         | type    | default | description                                                                                           |
| ---------------------------- | ------- | ------- | ----------------------------------------------------------------------------------------------------- |
| capturedelaytick             | integer | `100`   | time in ticks between the grab of players heights                                                     |
| minimumvaluesneeded          | integer | `7`     | minimum value count of height needed to enable the targetter                                          |
| maximumvaluesstored          | integer | `9`     | maximum value count stored for each player                                                            |
| minyaveragefortarget         | integer | `15`    | minimum Y average to get marked as a target (if multiple players match, the highest will be selected) |
| allowsametargetmultipletimes | bool    | `true`  | allow a player to be set as the target multiple times in a row                                        |
| minimumtimebetweentargets    | integer | `60`    | minimum time between the selection of two targets                                                     |

## general
| name     | type   | default | description                    |
| -------- | ------ | ------- | ------------------------------ |
| language | string | `en`    | language to use for the plugin |

## Additional config: custom areas
You can add custom areas to make it so that blocks break faster/slower depending on the region they're in  
(eg. break blocks faster if up in the sky or if close to mid)  
For that, you need to add a node to the `map/areas` key in the config. Here's an example:
```yml
    closemid:
      corner1: 0 0 0
      corner2: 4 4 4
      tickbreaktime: 60
      tickbreakstarttime: 0
```
Replace closemid with how you want to name the area, and see below for the individual args.  
| name               | type        | description                                                                                                   |
| ------------------ | ----------- | ------------------------------------------------------------------------------------------------------------- |
| corner1            | coordinates | first corner of the area                                                                                      |
| corner2            | coordinates | second corner of the area                                                                                     |
| tickbreaktime      | integer     | time it takes in ticks for a block to break itself in the area (overrides the expiringblock config)           |
| tickbreakstarttime | integer     | time it takes in ticks for a block to start showing it's breaking itself (overrides the expiringblock config) |

Also note that priority in areas is from top to bottom, meaning you can add multiple areas overlapping 
but the one to get choosen will be the one on top in the config.