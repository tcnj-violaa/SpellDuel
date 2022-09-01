package SpellDuel.spells.ironspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.ironeffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class BarrierToggle extends Toggle{
	public BarrierToggle(){
		toggle = new BarrierE();

		chain[0]=new Barrier(this);
		chain[1]=new RestorativeBreak(this);
	}
}
