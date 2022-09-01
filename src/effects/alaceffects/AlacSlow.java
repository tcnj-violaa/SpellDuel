package SpellDuel.effects.alaceffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
//import SpellDuel.spells.alaceffects.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class AlacSlow extends Effect{
	protected int slowamt=75;

	public AlacSlow(Entity caster, Entity target){
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
		//System.out.println("Ending!"); //Debug
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
			str=tgtstr+" seems a little faster again.";
		}
		else
			str=tgtstr+" remains slowed by "+slowamt+".";

		return str;
	}
}
