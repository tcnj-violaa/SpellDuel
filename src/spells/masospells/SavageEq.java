package SpellDuel.spells.masospells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class SavageEq extends Spell{
	protected boolean isDamaging;
	public SavageEq(){
		super(325,175,210,200,275,"Savage Equilibrium");
		description="If you are above 20% health, deal moderate damage to your target. If below, heal your target.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		actualdamage=(int)(calculateActualDamage(caster)*target.getAFactor());
		actualheal=(calculateActualHeal(caster));
		caster.drainMana(actualmc);

		if(caster.getHealth()<=(int)(caster.getMaxHealth()*0.2)){
			//target.heal(actualheal);
			isDamaging=false;
		}

		else{
			//target.damage(actualdamage);
			isDamaging=true;
		}

		if(isDamaging && blockCheck(caster,target)){ //Only block if it's dealing damage. We don't want to prevent the masochist from being able to heal at any point.
			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}

		if(isDamaging)
			target.damage(actualdamage);
		if(!isDamaging)
			target.heal(actualheal);

		System.out.println("\n"+getDialog(caster,target)+"\n");
		

	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String heal=healToGreen();
		String dmg=dmgToRed();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster == target)
			tgt="themselves";

		if(isDamaging){
			str=cst+" uses "+mana+" mana to fire a sinister bolt at "+tgt+", dealing "+dmg+" damage.";
		}

		if(!isDamaging){
			str="Using "+mana+" mana, "+cst+" summons a red, glowing orb over "+tgt+". It hovers there for a while before bursting down into a wide beam, healing for "+heal+ " points.";
		}

	return str;
	}

	public String getBlockDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String heal=healToGreen();
		String dmg=dmgToRed();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		str="Using "+mana+" mana, "+cst+" flings a dark bolt at "+tgt+", but it is blocked, dissipating harmlessly.";
		blocked=false;

		return str;

	}




}



