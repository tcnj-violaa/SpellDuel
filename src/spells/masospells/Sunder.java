package SpellDuel.spells.masospells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class Sunder extends Spell{
	protected double sunderamt=0.02;
	protected double selfdmgpercent=0.050;
	protected int selfdmg;
	protected int totaldmg;
	public Sunder(){
		super(90,75,125,"Sunder");
		description="Deal light damage to your target, decreasing their base armor by "+((int)(sunderamt*100))+"%. Deal "+((int)(selfdmgpercent*100))+"% of your current health to yourself as recoil.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		int actualmc=calculateActualMC(caster);
		caster.drainMana(actualmc);

		if(blockCheck(caster, target)){

			selfdmg=(int)(caster.getHealth()*selfdmgpercent);
			caster.damage(selfdmg); //Still recieve damage if blocked.
			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}
			
				

		target.setBaseAF(target.getBaseAF()+sunderamt); //Decrease armor prior to atk; higher factor=higher damage

		actualdamage=(int)(calculateActualDamage(caster)*target.getAFactor());

		target.damage(actualdamage);
		selfdmg=(int)(caster.getHealth()*selfdmgpercent); //Increase damage dealt by first hit, if cast on self
		caster.damage(selfdmg);

		totaldmg=selfdmg+actualdamage;
		System.out.println("\n"+getDialog(caster,target)+"\n");

	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String tdmg=dmgToRed();
		String cdmg="\033[031;1m"+selfdmg+"\033[0m";
		String totdmg="\033[031;1m"+totaldmg+"\033[0m";
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String sunderpercent=""+(int)(sunderamt*100);
		String str="";

		if(caster == target){
		str="Using "+mana+" mana, "+cst+" rends their own armor, permanently increasing the damage they recieve by "+sunderpercent+"% and dealing a total of "+totdmg+" damage to themselves.";

		return str;
		}
		
		str="Using "+mana+" mana, "+cst+" rends "+tgt+"'s armor, permanently increasing the damage they recieve by "+sunderpercent+"% and dealing "+tdmg+" damage to them. Out of recoil, "+cst+" deals "+cdmg+" damage  to themselves.";  	
	       return str;
	}

	@Override
	public String getBlockDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String cdmg="\033[031;1m"+selfdmg+"\033[0m";
		String cst=nameToBold(caster);
		String str="";

		str=cst+"'s wild strike is blocked -- they take "+cdmg+" damage in recoil. This required "+mana+" mana.";
		blocked=false;
		return str;
	}
}

