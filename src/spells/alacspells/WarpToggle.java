package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class WarpToggle extends Toggle{
	public WarpToggle(){
		toggle = new WarpE();

		chain[0]=new Warp(this); //Implement spell
		chain[1]=new CrashingHalt(this); //Implement spell
	}
}
