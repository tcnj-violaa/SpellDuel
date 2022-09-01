package SpellDuel.entities;
import SpellDuel.Tester;
import SpellDuel.teams.*;
import SpellDuel.spells.*;
import SpellDuel.effects.*;
import SpellDuel.effects.paleffects.*;
import SpellDuel.effects.masoeffects.*;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.NumberFormat;
abstract public class Entity{
	protected String name;
	protected String className;
	protected Team team;
	protected int maxhealth;
	protected int health;
	protected int maxmana;
	protected int mana;
	protected int mpregen;
	protected int bonusMPR=0;
	protected double damagefactor;
	protected double bonusDF=0.0; //Positive increases damage
	protected double healfactor;
	protected double bonusHF=0.0; //Positive increases healing
	protected double manafactor;
	protected double bonusMF=0.0; //Positive increases MP costs
	protected double armorfactor; 
	protected double bonusAF=0.0; //Positive increases damage taken
	protected int speed;
	protected int bonusSPD=0;
	protected boolean doubleCasted=false;
	protected boolean isAlive;
	protected boolean isActive;
	protected boolean isBlocking;
	public int blockCounter = 0;
	protected String description="No description found.";
	public Spell spells[];
	public ArrayList<Effect> buffs=new ArrayList<>();
	
public Entity(String callme, int maxhp, int maxmp, int mpr, double dfactor, double hfactor, double mfactor, double afactor, int spd){
	name=callme;
	maxhealth=maxhp;
	health=maxhp;
	maxmana=maxmp;
	mana=maxmp;
	mpregen=mpr;
	damagefactor=dfactor;
	healfactor=hfactor;
	manafactor=mfactor;
	armorfactor=afactor;
	speed=spd;
	isAlive=true;
	isActive=false;
}



public int getHealth(){return health;}
public void setHealth(int newhp){health=newhp;}
public void damage(int hurt){health-=hurt;}
public void heal(int rej){
	if(rej+health>maxhealth)
		rej=maxhealth-health;
	health+=rej;
}

public void unRHeal(int rej){health+=rej;}


public int getMaxHealth(){return maxhealth;}
public void setMaxHealth(int newhp){maxhealth=newhp;}

public String getHPMeter(){
	String meter="\033[31;1mHP:\033[0m "+health+"/"+maxhealth; return meter;
}

public int getMana(){return mana;}
public void setMana(int newmana){mana=newmana;}
public void drainMana(int drain){mana-=drain;}
public void unrRestoreMana(int rest){mana+=rest;}
public void restoreMana(int rest){
	if(rest+mana>maxmana)
		rest=maxmana-mana;
	mana+=rest;
}

public int getMaxMana(){return maxmana;}
public void setMaxMana(int newmana){maxmana=newmana;}

public String getMPMeter(){
	String meter="\033[34;1mMP:\033[0m "+mana+"/"+maxmana; return meter;
}

public void printMeters(){
	//System.out.print("\033[1m"+getName()+")\033[1m "+getHPMeter()+" | "+getMPMeter());
	System.out.print(getColor()+getName()+")\033[0m "+getHPMeter()+" | "+getMPMeter());

}

public String getEffect(int i){
	String str="";
	String left=""+(buffs.get(i).getDuration()-1);

	if(buffs.get(i) instanceof ToggleEffect)
		left="?";

	str+=buffs.get(i).getAbbr(); str+=" (\033[1m"+left+"\033[0m turns)";
	return str;
}

public void printEffects(){
	String str="Effects | ";

	for(int i=0; i<buffs.size(); i++){
		if(i==buffs.size()-1){
			str+=getEffect(i);

		}

		else{
		str+=getEffect(i);
		str+=", ";
		}
	}

	str+=" |||";
	System.out.println(str);
}


public String getName(){return name;}
public void setName(String newname){name=newname;}

public String getDescription(){
	String str="\t"+description+"\n";
	return str;}

public double getHFactor(){
	double factor=getBaseHF()+getBonusHF();
	return factor;}
public double getBaseHF(){return healfactor;}
public void setBaseHF(double factor){healfactor=factor;}
public double getBonusHF(){return bonusHF;}
public void setBonusHF(double factor){bonusHF=factor;}
public void changeBonusHF(double factor){bonusHF+=factor;}

public double getMFactor(){
	double factor=getBaseMF()+getBonusMF();	
	return factor;}
public double getBaseMF(){return manafactor;}
public void setBaseMF(double factor){manafactor=factor;}
public double getBonusMF(){return bonusMF;}
public void setBonusMF(double factor){bonusMF=factor;}
public void changeBonusMF(double factor){bonusMF+=factor;}

public double getDmgFactor(){
	double factor=getBaseDF()+getBonusDF();
	return factor;}
public double getBaseDF(){return damagefactor;}
public void setBaseDF(double factor){damagefactor=factor;}
public double getBonusDF(){return bonusDF;}
public void setBonusDF(double factor){bonusDF=factor;}
public void changeBonusDF(double factor){bonusDF+=factor;}

public double getAFactor(){
	double factor=getBaseAF()+getBonusAF();
	return factor;}
public double getBaseAF(){return armorfactor;}
public void setBaseAF(double factor){armorfactor=factor;}
public double getBonusAF(){return bonusAF;}
public void setBonusAF(double factor){bonusAF=factor;}
public void changeBonusAF(double factor){bonusAF+=factor;}

public int getSpeed(){
	int spd=getBaseSPD()+getBonusSPD();	
	return spd;}
public int getBaseSPD(){return speed;}
public void setBaseSPD(int spd){speed=spd;}
public int getBonusSPD(){return bonusSPD;}
public void setBonusSPD(int spd){bonusSPD=spd;}
public void changeBonusSPD(int spd){bonusSPD+=spd;}

public int getMPR(){
	int regen=getBaseMPR()+getBonusMPR();	
	return regen;}
public int getBaseMPR(){return mpregen;}
public void setBaseMPR(int mpr){mpregen=mpr;}
public int getBonusMPR(){return bonusMPR;}
public void setBonusMPR(int mpr){bonusMPR=mpr;}
public void changeBonusMPR(int mpr){bonusMPR+=mpr;}

public void statRandom(){boolean useful=false;}

public void printFactors(){
	DecimalFormat trun=new DecimalFormat("#.##");
	//trun.setRoundingMode(RoundingMode.CEILING);

	String df=trun.format(getDmgFactor());
	String dfb=trun.format(getBonusDF());
	String hf=trun.format(getHFactor());
	String hfb=trun.format(getBonusHF());
	String mf=trun.format(getMFactor());
	String mfb=trun.format(getBonusMF());
	String af=trun.format(getAFactor());
	String afb=trun.format(getBonusAF());

	System.out.println(" ||| DF: "+df+"|"+dfb+" | HF: "+hf+"|"+hfb+" | MF: "+mf+"|"+mfb+" | AF: "+af+"|"+afb+" | SPD: "+getSpeed()+"|"+getBonusSPD()+" | MPR: "+getMPR()+"|"+getBonusMPR()+" |||"); 
}

public void printStatus(){
	System.out.print("\t");
	printMeters(); printFactors();
	System.out.print("\n\t\t"); printEffects(); 
	System.out.print("\n");
}


public boolean isAlive(){
	if(health>0)
		return true;
	else
		return false;
}

public boolean isBlocking(){
	return isBlocking;
	}

public void setBlocking(boolean blk){
	isBlocking=blk;
}

public void block(){
	blockCounter++;
	//System.out.println("blockCounter incremented to "+blockCounter);
	}

public void resetBlockCounter(){
	blockCounter=0;
}

public int getBlockCounter(){
	return blockCounter;
}

public void setActive(){isActive=true;}
public void setInactive(){isActive=false;}

public void cast(Spell spell, Entity target){
	if(target == null && !(Tester.prevSpell.selfOnly())){
		return;
	}

	if(!spell.offCooldown())
		System.out.println(spell.getName()+" isn't off cooldown!\n");
	 
		
	else if(!enoughMP(spell))
		System.out.println("Not enough mana! "+ spell.getName()+" fizzles. \n");
		
	else	
		spell.cast(this, target);
	
}

public boolean enoughMP(Spell spell){
	int actualmanacost=(int)(getMFactor()*spell.getManaCost());
	if(getMana()<actualmanacost)
		return false;
	else
		return true;
}

public void cleanUpBuffs(){ //Need this so dead buffs (particularly toggles) can't be cleansed for benefits
	for(int i=buffs.size()-1;i>=0;i--){
		if(buffs.get(i).getActivity()==false){
			buffs.get(i).end();
			System.out.println("Buff ended!"); //Debug
			buffs.remove(i);
		}
	}
	buffs.trimToSize();
}

public void update(){
	//System.out.println("Updating!");
	int restore=getMPR();
	if(mana+restore>maxmana){
		restore=maxmana-mana;
		mana+=restore;}
	else{
	mana+=restore;}

	// System.out.println(buffs.size());

	//cleanUpBuffs();

	for(int i=0;i<buffs.size();i++){
		//System.out.println(buffs.size());
	       	buffs.get(i).tick();	
	       	//System.out.println(buffs.get(i).getPulseText());
		
	}

	//cleanUpBuffs();

	for(int i=0;i<spells.length;i++){
		if(spells[i].getCooldown() > 0)
		spells[i].tickCooldown();
	}

	for(int i=0;i<spells.length;i++){ //Take care of chains.
		if(spells[i] instanceof Chain){
			/*if(!doubleCasted())
				spells[i].counterTick();*/
			if(spells[i].getCounter()>2) //If casts are not subsequent, prevent chain from progressing.
				spells[i].resetChainAmt();
		}
	}


}

public ArrayList<Effect> getBuffs(){
	return buffs;
}

public void addBuff(Effect effect){
	buffs.add(effect);
}

public void removeBuff(Effect effect){
	buffs.remove(effect);
}

public boolean doubleCasted(){
	return doubleCasted;
}

public void setDCasted(boolean dc){
	doubleCasted=dc;
}

public void setTeam(Team t){
	team=t;
	t.addMember(this);
}

public Team getTeam(){
	return team;
}

public String getColor(){
	String col="";
	if(Tester.teamMode){
		col=getTeam().getColor();
	}
	else{
	col="\033[1m";
	}

	return col;
}

	

/*public String getClass(){
	String str=className;
	return str;
}*/



}
 
