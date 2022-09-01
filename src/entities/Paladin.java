package SpellDuel.entities;
import SpellDuel.spells.*;
import SpellDuel.spells.palspells.*;
import java.util.ArrayList;
public class Paladin extends Entity{
	private int hpregen=10;
public Paladin(String callme){
	super(callme, 4000, 1000, 70, 0.9, 1.5, 1.0, 0.90, 350);
	className="Paladin";
//	isBlocking=true;

	description="The Paladin is a beacon of virtue and a master of defensive magic, keeping watch over allies and fortifying themselves to withstand heavy damage."+
		"\n\n\t\tStrengths:\n\t\t- Strong healing and defensive abilities\n\t\t- High max HP\n\t\t- Good defense\n\t\t- Effect control"+
		"\n\n\t\tWeaknesses:\n\t\t- Limited damage\n\t\t- Low max MP\n\t\t- Mediocre mana regen\n\t\t- Slow";

	spells=new Spell[5];
	spells[0]=new Rejuvenate();
	spells[1]=new Smite();
	spells[2]=new RegenerativeWard();
	spells[3]=new Purge();
	spells[4]=new Pass();
}

/*@Override
public void update(){
	super.update();
	if(health+hpregen>maxhealth)
		hpregen=maxhealth-health;
	heal(hpregen);
}*/
}
