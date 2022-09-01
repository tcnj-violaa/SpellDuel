package SpellDuel.entities;
import SpellDuel.spells.*;
import SpellDuel.spells.savspells.*;
import SpellDuel.entities.*;
import java.util.ArrayList;
public class Savant extends Entity{
	public Savant(String callme){
		super(callme, 2500, 1500, 0, 1.0, 1.0, 1.0, 1.5, 500);

		className="Savant";

		description="The Savant is a skilled mage specializing in the manipulation of mana among themselves and others, crippling enemies and chipping away at their focus to ultimately deal great amounts of damage."+
			"\n\n\t\tStrengths: \n\t\t- High damage\n\t\t- Disables enemies\n\t\t- Very high mana pool\n\t\t- Mana leech and restore"+
			"\n\n\t\tWeaknesses: \n\t\t- Very poor defense\n\t\t- Low max HP\n\t\t- No mana regen";

		spells=new Spell[5];
		spells[0]=new ManaVacuum();
		spells[1]=new ManaTheft();
		spells[2]=new Choke();
		spells[3]=new BlessingOfMana();
		spells[4]=new Pass();
	}
}
