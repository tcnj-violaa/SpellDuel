package SpellDuel.spells.masospells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class Maim extends Spell{
	protected int masodmg;
	protected int tgtdmg;
	protected int totaldmg;
	public Maim(){
		super(425,300,400,"Maim");
		basecooldown=3;
		description="Deal heavy damage to your target and yourself. If you kill your target with Maim, the self-damage cannot kill you."; 
	}
	

	@Override
	public void cast(Entity caster, Entity target){
		int actualmc=calculateActualMC(caster);
		int baseshareddmg=(calculateActualDamage(caster));
		caster.drainMana(actualmc);

		masodmg=(int)(baseshareddmg*caster.getAFactor());
		tgtdmg=(int)(baseshareddmg*target.getAFactor());
		totaldmg=masodmg+tgtdmg;


		if(blockCheck(caster,target)){
			tgtdmg=0;
			totaldmg=masodmg+tgtdmg;
			caster.damage(masodmg); //The supplemental self-damage is still dealt, but not the full. Can be used to decrease the amount of damage the masochist does to themselves.

			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}

		
		
		if(target.getHealth()-tgtdmg<=0 && masodmg>caster.getHealth()){
			masodmg=(caster.getHealth()-1);}
			// ^^ If Maim kills the target, the maso doesn't die

		caster.damage(masodmg);
		target.damage(tgtdmg);

		cooldown=basecooldown;
		caster.isAlive();
		target.isAlive();

		System.out.println("\n"+getDialog(caster,target)+"\n");
	
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String cdmg=toRed(masodmg);
		String tdmg=toRed(tgtdmg);
		String totdmg=toRed(totaldmg);
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(!blocked && caster == target){
			str=cst+" summons a cyclone of knives around them, maiming themselves for "+totdmg+" damage, using "+mana+" mana.";
			return str;
		}

		str=cst+" summons whirling, gouging blades, dealing "+tdmg+" damage to "+tgt+" and "+cdmg+" to themselves, using "+mana+" mana.";
		return str;
	}

	@Override
	public String getBlockDialog(Entity caster, Entity target){
		String nm=name;
		String cdmg=toRed(masodmg);
		String mana=manaToBlue();
		String str="";
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);

		str=cst+" summons whirling, gouging blades, but they are blocked by "+tgt+". "+cst+" still takes "+cdmg+" and uses "+mana+" mana.";

		if(caster == target){
		str=cst+ " summons a whirling storm of knives around themselves; however, some are blocked. Still, a few strike and deal "+cdmg+". This required "+mana+" mana.";
		}

		blocked=false;
		return str;
	}





	public String toRed(int damage){
		String str="\033[031;1m"+damage+"\033[0m";
		return str;
	}
}
