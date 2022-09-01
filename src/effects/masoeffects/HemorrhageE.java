package SpellDuel.effects.masoeffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.masospells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class HemorrhageE extends Effect{
	private	int basedamage;
	public HemorrhageE(Entity caster, Entity target){
		super("Hemorrhage",3);
		damage=50;
		basedamage=calculateActualDamage(caster);
		abbreviation="Hem";
		duration=3;
		buffcst=caster;
		bufftgt=target;
	}

	@Override
	public void tick(){
		if(duration>0){
			basedamage=basedamage*2; //Double damage per tick
			actualdamage=(int)(basedamage*bufftgt.getAFactor());
			bufftgt.damage(actualdamage);
			duration--;
			System.out.println(getPulseText());
		}


		if(duration==0)
			end();

	}

	@Override
	public String getPulseText(){
		String nm=name;
		String tgtstr=nameToBold(bufftgt);
		String dmg=dmgToRed();
		String rounds=""+duration;
		String str="";
		str=name+" intensifies, bleeding "+tgtstr+" for "+dmg+" points. It will last for "+rounds+" more rounds.";
		if(duration==0)
			str=name+" intensifies, finally bleeding "+tgtstr+" for "+dmg+" points.";
		return str;
	}
}

