package SpellDuel.effects.saveffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.savspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class BlessingOfManaE extends Effect{
	private double manaFSet=0.2;
	private double initMF;
	private int setmpregen=150;

	public BlessingOfManaE(Entity caster, Entity target){
		super("Blessing of Mana",5);
		duration=5;
		abbreviation="BoM";
		buffcst=caster;
		bufftgt=target;
	}

	@Override
	public void tick(){
		if(duration>0){
			duration--;
			System.out.println(getPulseText());
		}
		if(duration==0)
			end();
	}

	public void initEffect(){
		bufftgt.changeBonusMF(-manaFSet);
		bufftgt.changeBonusMPR(setmpregen);
	}

	
	public double getMFSet(){return manaFSet;}
	public int getMPRBoost(){return setmpregen;}

	@Override
	public void end(){
		bufftgt.changeBonusMPR(-setmpregen);
		bufftgt.changeBonusMF(manaFSet);
		isActive=false;
		duration=0;
		bufftgt.removeBuff(this);
	}

	@Override
	public String getPulseText(){
		String nm=name;
		String tgtstr=nameToBold(bufftgt);
		String mpr=toBlue(setmpregen);
		String fset=""+manaFSet+"x";
		String rounds=""+duration;
		String str="";

		str+=name+" remains on "+tgtstr+". It will last for "+rounds+" more rounds before dispelling.";

		if(duration==0)
			str=name+" dispells "+" from "+tgtstr+".";

		return str;
	}

	public int getMPR(){return setmpregen;}
	public double getMFB(){return manaFSet;}
}
