package SpellDuel.effects.alaceffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class MutualBen extends Effect{
	protected double healBuff = 0.1;
	protected int speedB = 100;

	public MutualBen(Entity caster, Entity target){
		super("Mutual Vigor", 3);
		duration = 3;
		abbreviation = "MV";

		buffcst=caster;
		bufftgt=target;
	}

	public void initEffect(){
		bufftgt.changeBonusSPD(speedB);
		buffcst.changeBonusSPD(speedB);

		bufftgt.changeBonusHF(healBuff);
		buffcst.changeBonusHF(healBuff);
	}

	public void end(){
		bufftgt.changeBonusSPD(-speedB);
		buffcst.changeBonusSPD(-speedB);

		bufftgt.changeBonusHF(-healBuff);
		buffcst.changeBonusHF(-healBuff);

		isActive=false;
		duration=0;

		bufftgt.removeBuff(this);
	}

	@Override
	public String getPulseText(){
		String nm=name;
		String tgtstr=nameToBold(bufftgt);
		String cststr=nameToBold(buffcst);
		String rounds=""+duration;
		String str="";
		String speed=""+speedB;

		if(duration==0){
			str="The invigorating link between "+tgtstr+" and "+cststr+" breaks.";
		}

		else
			str="The vigored bond between "+tgtstr+" and "+cststr+" remains put.";

		return str;
	}
}


