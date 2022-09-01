package SpellDuel.effects.disceffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.discspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class RandSlow extends Effect{
	protected int slowamt=(int)(((Math.random()*40)+10)*10);

	public RandSlow(Entity caster, Entity target){
		super("Slow",3);
		duration=3;
		abbreviation="Sl";

		buffcst=caster;
		bufftgt=target;
	}

	public void initEffect(){
		bufftgt.changeBonusSPD(-slowamt);
	}

	public void end(){
		bufftgt.changeBonusSPD(slowamt);
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
		String slow=""+slowamt;

		if(duration==0){
			str=tgtstr+"'s limbs seem lighter; their speed returns to normal.";
		}
		else
			str=tgtstr+" remains slowed by "+slowamt+".";

		return str;
	}
}


	
