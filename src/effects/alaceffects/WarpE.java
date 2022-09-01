package SpellDuel.effects.alaceffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
//import SpellDuel.spells.alaceffects.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class WarpE extends ToggleEffect{
	protected int warpAmt=500;
	protected int baseDamage;
	protected int haltingStop=0;
	public WarpE(Entity caster, Entity target, Toggle over){
		super("Warp", 75);
		damage=25; //Deal this per turn?
		baseDamage=calculateActualDamage(caster);
		abbreviation="Warp";
		buffcst=caster;
		bufftgt=target;
		superToggle=over;
	}

	public WarpE(){ //Dummy constructor for Toggle
		super("Warp", 75);
		damage=25;
		abbreviation="Warp";
	}

	public void initEffect(){
		bufftgt.changeBonusSPD(warpAmt);
	}

	public void end(){
		bufftgt.changeBonusSPD(-warpAmt);
		haltingStop = (int)((warpAmt*0.2)*bufftgt.getAFactor());
		bufftgt.damage(haltingStop); //Come to a crashing stop

		//bufftgt.removeBuff(this);
		isActive=false;
		duration=0;
		superToggle.resetChainAmt(); //When this ends, it resets the chain -- this is fine when it's cleansed, but when it's ended manually we have a double reset, resulting in the chain being ticked right after it's casted.
//		System.out.println(superToggle+" reset by ending effect "+this);
		bufftgt.removeBuff(this);
	}

	public int calcHaltStop(){
		haltingStop = (int)((warpAmt*0.2)*bufftgt.getAFactor());
		return haltingStop;
	}

	@Override
	public void tick(){
		if(duration>0){
			actualdamage=(int)(baseDamage*bufftgt.getAFactor());
			bufftgt.damage(actualdamage);

			bufftgt.changeBonusSPD(-warpAmt);
			warpAmt+=100;
			bufftgt.changeBonusSPD(warpAmt); //Apply the new speed

			upkeep();

			duration--;
			System.out.println(getPulseText());
		}
		

	}

	@Override
	public String getPulseText(){
		String nm=name;
		String cststr=nameToBold(buffcst);
		String tgtstr=nameToBold(bufftgt);
		String dmg=dmgToRed(); 
		String finaldmg=toRed(haltingStop); //Actually make this red -- currently doesn't work.
		String upk=toBlue(actualUpkeep);
		String rounds=""+duration;
		String str="";

		if(bufftgt == buffcst)
			cststr="them";

		str=tgtstr+" remains at warp speed, taking "+dmg+"; it requires "+upk+" mana of "+cststr+" to upkeep it.";

		if(fizzled)
			str=nameToBold(buffcst)+" can no longer upkeep the spell -- "+tgtstr+" comes to an abrupt stop, taking "+finaldmg+" damage.";
		else{
			if(duration==0)
			str=tgtstr+" comes to an abrupt stop, taking "+finaldmg+" damage.";
		}
		return str;
	}

	public int haltingStop(){return haltingStop;}
}



