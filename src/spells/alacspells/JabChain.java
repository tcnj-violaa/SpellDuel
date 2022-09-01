package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class JabChain extends Chain{
	public JabChain(){
		chain= new Spell[3];

		chain[0]=new Jab();
		chain[1]=new CripplingKick();
		chain[2]=new Onslaught();
	}
}
