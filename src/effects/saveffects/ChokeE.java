package SpellDuel.effects.saveffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.savspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class ChokeE extends Effect{
	private double manaFIncrease=1.0;

	public ChokeE(Entity caster, Entity target){
		super("Choke",4);
		duration=4;
		abbreviation="Ch";
		buffcst=caster;
		bufftgt=target;
	}

	@Override
	public void tick(){
		if(duration>0){
			duration--;
			System.out.println(getPulseText());
		}


		if(duration==0){
			end();
		}
	}

	public void initEffect(){
		bufftgt.changeBonusMF(manaFIncrease);
		bufftgt.changeBonusMPR(-bufftgt.getBaseMPR());
	}
	
	public double getMFIncrease(){return manaFIncrease;}

	@Override
	public void end(){
		bufftgt.changeBonusMPR(bufftgt.getBaseMPR());
		bufftgt.changeBonusMF(-manaFIncrease);
		isActive=false;
		duration=0;
		bufftgt.removeBuff(this);
	}

	@Override
	public String getPulseText(){
		String nm=name;
		String tgtstr=nameToBold(bufftgt);
		String increase=""+manaFIncrease+"x";
		String str="";
		str=name+" keeps its grasp on "+tgtstr+". It will last for "+duration+" more rounds.";

		if(duration==0)
			str=name+" releases its grasp from "+tgtstr+".";

	       return str;
	}
}	
		


