package SpellDuel.spells.discspells;
import SpellDuel.spells.*;
import SpellDuel.effects.*;
import SpellDuel.effects.disceffects.*;
import SpellDuel.entities.*;

public class HealOfProportion extends Spell{
	int actualmprest;
	boolean damaging;
	double hpjitterfactor;
	double mpjitterfactor;
	public HealOfProportion(){
		super(600,100,200,"Heal of Proportion");
		basecooldown=4;
		description="Restore health and mana to the target based on a percentage of your maximum health and mana; things have a small chance for things to go... a little wrong."; //Restore hp and mana to target based on your max hp and mp
	}

	@Override
	public void cast(Entity caster, Entity target){
		int actualmc=calculateActualMC(caster);
		int basescaledheal=(int)(caster.getMaxHealth()*0.15); //25% of max hp
		int basescaledmprest=(int)(caster.getMaxMana()*0.20); //20% of max mana

		hpjitterfactor=(Math.random()/2);
		mpjitterfactor=(Math.random()/2);

		actualheal=basescaledheal+(int)(basescaledheal*hpjitterfactor); //Add a random percentage of the base heal to the full heal value.
		actualmprest=basescaledmprest+(int)(basescaledmprest*mpjitterfactor); //Same as above.

		if(Math.random()<=0.05){ //Small chance (5%) to damage and drain mana instead.
			actualheal=-actualheal;
			actualmprest=-actualmprest;
			damaging=true;
		}


		target.heal(actualheal);
		target.restoreMana(actualmprest);

		caster.drainMana(actualmc);
		cooldown=basecooldown;

		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String heal=healToGreen();
		String mrestore="\033[34;1m"+actualmprest+"\033[0m";
		String hj=""+hpjitterfactor;
		String mj=""+mpjitterfactor;
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(target == caster)
			tgt="themselves";

		str+="Upon "+tgt+", "+cst+" summons a bolt that seems to appraise its caster before bursting into energy that restores "+heal+" health ("+hj+" bonus) and "+mrestore+" mana ("+mj+" bonus).";

		if(damaging)
			str="Upon "+tgt+", "+cst+" summons a bolt that seems to appraise its caster before bursting into energy that... er... 'restores' "+heal+" health ("+hj+" bonus) and "+mrestore+" mana ("+mj+" bonus).";

		return str;
	}
}

		



