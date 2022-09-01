package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class AidChain extends Chain{
	public AidChain(){
		chain= new Spell[3];

		chain[0]=new Aid();
		chain[1]=new MutualVigor();
		chain[2]=new VelocitousRestoration(); //Maybe make this spella little more interesting...
	}
}
