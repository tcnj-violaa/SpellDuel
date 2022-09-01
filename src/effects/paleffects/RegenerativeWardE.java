package SpellDuel.effects.paleffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.palspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class RegenerativeWardE extends Effect{
	private double armorincrease=0.15;
	//private double initialarmor;

	public RegenerativeWardE(Entity caster, Entity target){
		super("Regenerative Ward",4);
		heal=50;
		//initialarmor=getInitArmor(target);
		actualheal=calculateActualHeal(caster);
		abbreviation="RW";
		duration=4;
		buffcst=caster;
		bufftgt=target;
	}

	@Override
	public void tick(){
		//System.out.println(actualheal);
		//System.out.println(bufftgt);
		//System.out.println(duration);
		if(duration>0){
		bufftgt.unRHeal(actualheal);
		duration--;
		System.out.println(getPulseText());
		}
		

		if(duration==0){
			end();
		}
	}

	public void initEffect(){
		bufftgt.changeBonusAF(-armorincrease);
	}
		

	/*public double getInitArmor(Entity target){
		initialarmor=target.getAFactor();
		return initialarmor;
	}*/

	@Override
	public void end(){
		bufftgt.changeBonusAF(armorincrease);
		isActive=false;
		duration=0;
		bufftgt.removeBuff(this);
	}

	public double getArmorIncrease(){return armorincrease;}

	@Override
	public String getPulseText(){
		String nm=name;
		String tgtstr=nameToBold(bufftgt);
		String heal=healToGreen();
		String rounds=""+duration;
		String str="";
		str=name+" pulses, healing "+tgtstr+" for "+heal+" points. It will last for "+rounds+" more rounds.";
		return str;
	}
	/*public void setTarget(Entity tgt){target1=tgt;}
	public Entity getTarget(){return target1;}*/
}
