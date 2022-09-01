package SpellDuel.spells;
import SpellDuel.entities.*;
import SpellDuel.effects.*;
public class Spell{
	protected int manacost;
	protected int damagemin;
	protected int damagemax;
	protected int healmin;
	protected int healmax;
	protected int actualdamage;
	protected int actualheal;
	protected int actualmc;
	protected int cooldown;
	protected int basecooldown;
	protected Effect activeEffect;
	protected String name;
	protected String description="No description found!";
	protected boolean onlySelfCast=false;
	protected boolean blockable=true;
	protected boolean blocked=false; //Necessary for individual per-block dialogs in getDialog(); remember to reset after dialog is retrieved!


public Spell(){
	manacost=0;
	damagemin=0;
	damagemax=0;
	healmin=0;
	healmax=0;
	name="";
	cooldown=0;
	basecooldown=0;
}

public Spell(String callme, int mc){
	basecooldown=0;
	cooldown=0;
	name=callme;
	manacost=mc;}


public Spell(int mc, int dmin, int dmax, int hmin, int hmax, String callspell){
	manacost=mc;
	damagemin=dmin;
	damagemax=dmax;
	healmin=hmin;
	healmax=hmax;
	name=callspell;
	actualdamage=0;
	actualheal=0;
	actualmc=0;
	cooldown=0;
	basecooldown=0;
}
			
public Spell(int mc, int dmin, int dmax, String callspell){
	manacost=mc;
	damagemin=dmin;
	damagemax=dmax;
	healmin=0;
	healmax=0;
	name=callspell;
	actualdamage=0;
	actualheal=0;
	actualmc=0;
	cooldown=0;
	basecooldown=0;
}

public Spell(String callspell, int mc, int hmin, int hmax){
	manacost=mc;
	damagemin=0;
	damagemax=0;
	healmin=hmin;
	healmax=hmax;
	name=callspell;
	actualdamage=0;
	actualheal=0;
	actualmc=0;
	cooldown=0;
	basecooldown=0;
}//Actual class, use this for healing spells.


public String getName(){return name;}
public String getDescription(){
	String str="\t"+description+"\n";
	return str;}
public int getManaCost(){return manacost;}
public boolean selfOnly(){return onlySelfCast;}

public boolean isBlockable(){return blockable;}

public int getCooldown(){return cooldown;}
public void tickCooldown(){cooldown--;}
public void reduceCooldown(int red){cooldown-=red;}
public void addCooldown(int add){cooldown+=add;}
public void setCooldown(int set){cooldown=set;}
public boolean offCooldown(){
	if(cooldown==0)
		return true;
	return false;
}

public int getBaseCooldown(){return basecooldown;}

public int calcRandomHealEfficacy(){
	int healE = (int)(Math.random()*(healmax-healmin)+healmin);
	return healE;}

public int calcRandomDamage(){
	int rDamage = (int)(Math.random()*(damagemax-damagemin)+damagemin);
	return rDamage;}

public int calculateActualHeal(Entity caster){
	int baseheal=calcRandomHealEfficacy();
	actualheal=(int)(baseheal*caster.getHFactor());
	return actualheal;}

public int calculateActualDamage(Entity caster){
	int basedamage=calcRandomDamage();
	actualdamage=(int)(basedamage*caster.getDmgFactor());
	return actualdamage;}

public int calculateDealtDamage(Entity caster, Entity target){
	int basedamage=calcRandomDamage();
	actualdamage=(int)(basedamage*caster.getDmgFactor()*target.getAFactor());
	return actualdamage;
}

public int calculateActualMC(Entity caster){
	actualmc=(int)(manacost*caster.getMFactor());
	return actualmc;}

/*(public boolean enoughMP(Entity caster){
	if(caster.getMana()<actualmc)
		return false;
	else
		return true;}*/

public String getDialog(Entity caster, Entity target){
	String nm=name;
	//int dmg=(int)(actualdamage*target.getAFactor());
	String dmg=dmgToRed();
	String heal=healToGreen();
	String mana=manaToBlue();
	String cst=nameToBold(caster);
	String tgt=nameToBold(target);

	if(cst.equals(tgt))
		tgt="\033[1mthemselves\033[0m";

	String str =cst+" casts "+name+" on "+tgt;
	if(actualdamage>0)
		str+=", dealing "+dmg+" points of damage,";
	if(actualheal>0)
		str+=", healing "+heal+" points,";
	str+=" using "+mana+" mana.";
	return str;}

public String getDialog(Entity caster){
	String nm=name;
	String cst=nameToBold(caster);
	String str=cst+" casts "+nm+" on themselves.";
	return str;
}

public String healToGreen(){
	String str="\033[32;1m"+actualheal+"\033[0m";
	return str;
}

public String toGreen(String inp){
	String str="\033[32;1m"+inp+"\033[0m";
	return str;
}

public String dmgToRed(){
	String str="\033[31;1m"+actualdamage+"\033[0m";
	return str;
}

public String toRed(String inp){
	String str="\033[31;1m"+inp+"\033[0m";
	return str;
}

public String manaToBlue(){
	String str="\033[34;1m"+actualmc+"\033[0m";
	return str;
}

public static String toBlue(String inp){
	String str="\033[34;1m"+inp+"\033[0m";
	return str;
}

public String nameToBold(Entity name){
	String str="\033[1m"+name.getName()+"\033[0m";
	return str;
}

public static String toBold(String inp){
	String str="\033[1m"+inp+"\033[0m";
	return str;
}

public void cast(Entity caster, Entity target){
        //int actualheal=0,actualdamage=0;
        actualmc=calculateActualMC(caster);

        actualheal=calculateActualHeal(caster);
        actualdamage=(int)(calculateActualDamage(caster)*target.getAFactor());

        if(actualheal+target.getHealth()>target.getMaxHealth())
                actualheal=target.getMaxHealth()-target.getHealth();

        target.heal(actualheal); //Heals the target if healmax > 0. $
        target.damage(actualdamage); //Damages the target if d$
        caster.drainMana(actualmc);
	target.isAlive();
	caster.isAlive();

        System.out.println("\n"+getDialog(caster, target)+"\n");

}

public boolean blockCheck(Entity caster, Entity target){ //Don't check this in entity-level cast; while it might be easier to have implemented at that level (requires none at each spell level), we want to be flexible enough to allow for unique effects upon block. If a spell is unblockable, just don't include it in cast implementation.

	String str;
	actualmc=calculateActualMC(caster);
	String cost = manaToBlue();

	if(selfOnly()) //Hopefully this doesn't screw anything up...
		return false;

	if(target.isBlocking()){
		//caster.drainMana(actualmc);
		target.block();
		return true;
	}

	return false;
}

public String getBlockDialog(Entity caster, Entity target){
	String nm=name;
	//int dmg=(int)(actualdamage*target.getAFactor());
	String dmg=dmgToRed();
	String heal=healToGreen();
	String mana=manaToBlue();
	String cst=nameToBold(caster);
	String tgt=nameToBold(target);
	String str="";

	str=name+" was blocked! "+mana+" mana was used.";
	return str;
}
		
public void cast(Entity caster){
	actualmc=calculateActualMC(caster);
	System.out.println("\n"+getDialog(caster)+"\n");
}

public void setEffect(Effect active){activeEffect=active;}
public Effect getEffect(){return activeEffect;}

public void counterTick(){boolean useful=false;}
public int getCounter(){return 0;}
public void resetChainAmt(){}

}
//public int getSpellDmg(){return damagemax;}
//public int getSpellHeal(){return healmax;}

