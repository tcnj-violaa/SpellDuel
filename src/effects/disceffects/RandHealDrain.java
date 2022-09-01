package SpellDuel.effects.disceffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.discspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class RandHealDrain extends Effect{
	protected double drainpercent=Math.random();

	public RandHealDrain(Entity caster, Entity target){
		super("Wither",4);
		duration=4;
		abbreviation="Wi";

		buffcst=caster;
		bufftgt=target;
	}
	//Implement the rest.
	
	public void initEffect(){
		bufftgt.changeBonusHF(-drainpercent);
	}

	public void end(){
		bufftgt.changeBonusHF(drainpercent);
		isActive=false;
		duration=0;
		bufftgt.removeBuff(this);
	}

	@Override
	public String getPulseText(){
		String nm=name;
		String tgtstr=nameToBold(bufftgt);
		String rounds=""+duration;
		String str="";
		String wither=""+drainpercent;

		if(duration==0){
			str=tgtstr+"'s weakness fades, and they're up and ready to rejuventate.";

		}
		else
			str=tgtstr+"'s healing abilities remained weakened by "+wither+".";
		return str;
	}
}
