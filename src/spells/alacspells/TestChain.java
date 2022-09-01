package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class TestChain extends Chain{
	public TestChain(){
		chain= new Spell[3];

		chain[0]=new RapidOnslaught();
		chain[1]=new Jab();
		chain[2]=new Pass();
	}
}
