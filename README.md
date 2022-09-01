# spellduel
A text-based magical dueling game written in Java. Last updated in 2019, uploaded in 2022 for archival purposes. 

SpellDuel is a text-mode turn-based dueling/battle arena with a variety of features and combat systems, including:

* 5 playable classes
* Speed-based turn order system
* Mana and ability cooldown systems
* Damage over time and multi-turn effects
* Combo and 'double-turn' system
* Team-based or FFA gameplay
* Flexible ability targeting, with effects changing based on target's allied or enemy status
* Flavor text
* In-game explanations of player classes and ability effects

## Setup:

SpellDuel uses the [Ant](https://ant.apache.org/) Java build tool. 

Before first compilation, ensure the `bin` directory exists:

`$ mkdir bin`

and to compile, simply run 

`$ ant`

and compiled .class and .run files should appear under `bin/SpellDuel`; a .java file should appear under `build/`

To run, you can avail yourself of the `run.sh` script or, alternately, run:

`$ java -jar build/SpellDuel.java`

or

`$ java -cp bin SpellDuel.run`

## Screenshots

![First round screen, showing characters ordered by speed and the core game UI.](https://github.com/tcnj-violaa/spellduel/blob/main/screens/gamestart.png) First round screen, showing characters ordered by speed and the core game UI.

![Demonstration of multi-turn effects and UI.](https://github.com/tcnj-violaa/spellduel/blob/main/screens/effects.png)
Demonstration of multi-turn effects and UI.

![Demonstration of the Masochist's Maim ability and player being slain.](https://github.com/tcnj-violaa/spellduel/blob/main/screens/maim.png)
Demonstration of the Masochist's Maim ability and player being slain.

![Demonstration of the Paladin's Purge ability, removing current buffs from the target and healing/dealing damage.](https://github.com/tcnj-violaa/spellduel/blob/main/screens/purge.png)
Demonstration of the Paladin's Purge ability, removing current buffs from the target and healing/dealing damage.
`
