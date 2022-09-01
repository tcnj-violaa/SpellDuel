package SpellDuel.effects.disceffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.discspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class DrainE extends Effect{
	private int basedamage;
	private int heal;
	public DrainE(Entity caster, Entity target){
		super("Drain", 4);
		duration=4;
		abbreviation="Dr";
		damage=75;
		basedamage=calculateActualDamage(caster);
		actualheal=(int)(basedamage*0.5);
		buffcst=caster;
		bufftgt=target;
	}

	@Override
	public void tick(){
		if(duration>0){
		actualdamage=(int)(basedamage*bufftgt.getAFactor());
		//actualheal=(int)(actualdamage*0.5);
		bufftgt.damage(actualdamage);
		buffcst.unRHeal(actualheal);
		duration--;
		System.out.println(getPulseText());
		}

		if(duration==0){
			end();
		}
	}

	public void end(){
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
		String dmg=dmgToRed();
		String heal=healToGreen(); //Not working?
		String healamt=""+heal;

		if(duration==0){
			str="The tether between "+tgtstr+" and "+cststr+" breaks.";
		}

		else
			str="The tether pulses, dealing "+dmg+" damage to "+tgtstr+" and transferring "+healamt+" health to "+cststr+".";
		return str;
	}
}

