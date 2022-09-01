package SpellDuel.spells;
import SpellDuel.entities.*;
import SpellDuel.effects.*;
import SpellDuel.spells.*;

import java.lang.reflect.*;

public abstract class ToggleMember extends Spell{
	protected Toggle togg;
	protected ToggleEffect te;

	public ToggleMember(Toggle init){
		super();
		togg = init;
		
	}

	public void setTE(ToggleEffect effect){
		te = effect;
	}

	public ToggleEffect getTE(){
		return te;
	}

	/*public void findEffect(){
		for(Effect e : togg.initTgt.getBuffs()){
			if(e.getAbbr().equals(togg.getEffect().getAbbr()) && e.getIndex() == togg.getActiveEffect().getIndex()) // || te.getAbbr() causes npe
					te = (ToggleEffect)e;
		}
	} Unneccessary; likely more prone to error than just having the toggle know the spell it corresponds to.*/
}
