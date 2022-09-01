package SpellDuel.effects;
import SpellDuel.spells.*;
import SpellDuel.spells.masospells.*;
import SpellDuel.spells.palspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
abstract public class Effect{
	protected int duration;
	protected String name;
	protected int heal;
	protected int actualheal=0;
	protected int damage;
	protected int actualdamage=0;
	protected Entity buffcst;
	protected Entity bufftgt;
	protected boolean isActive=true;
	protected String abbreviation="Abbr";
	protected static int numEffects=0;
	protected int index;
	public boolean ended; //Check if ready for clean-up; also prevent doubly ending, which presents issues with 

	public void tick(){
		if(duration>0){	
			duration--;
			System.out.println(getPulseText());

		}

		if(duration==0){
			end();
		}
		
	}
	
	public void end(){
	//Implement in actual class	
		isActive=false;
		duration=0;
		bufftgt.removeBuff(this); //It's actually much cleaner to have the buffs remove themselves than to have a cleaner that checks for dead buffs; eliminates the possibility of multiple endings, too.
	}

	public void initEffect(){
	//Implement per effect
	}

	public Effect(String call, int dur){
		name=call;
		duration=dur;
		numEffects++;
		index=numEffects;
	}

	public int calculateActualHeal(Entity caster){
		actualheal=(int)(heal*caster.getHFactor());
		return actualheal;}

	public int calculateActualDamage(Entity caster){
		actualdamage=(int)(damage*caster.getDmgFactor());
		return actualdamage;}

	public String toBlue(int num){
		String str="\033[34;1m"+num+"\033[0m";
		return str;
	}

	public String toRed(int num){
		String str="\033[32;1m"+num+"\033[0m";
		return str;
	}

	public String toGreen(int num){
		String str="\033[32;1m"+num+"\033[0m";
		return str;
	}

	public String healToGreen(){
		String str="\033[32;1m"+actualheal+"\033[0m";
		return str;
	}

	public String dmgToRed(){
		String str="\033[31;1m"+actualdamage+"\033[0m";
		return str;
	}

	public String nameToBold(Entity name){
		String str="\033[1m"+name.getName()+"\033[0m";
		return str;
	}

	public int getDuration(){return duration;}
	public int getActualHeal(){return actualheal;}

	public String getPulseText(){
		String str="";
		return str;
	}

	//public boolean getActivity(){return isActive;}
	public boolean getActivity(){
		if(getDuration() == 0)
			return false;
		else
			return true;
	}
	
	public String getAbbr(){return abbreviation;}
	
	public int getIndex(){return index;}


	//public Entity setRefTarget(Entity tgt){
	//public Entity getRefTarget(){return target;}

	/*public String manaToBlue(){
		String str="\033[34;1m"+mana+"\033[0m";
	}*/
}
