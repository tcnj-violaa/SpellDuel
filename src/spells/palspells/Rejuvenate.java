package SpellDuel.spells.palspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class Rejuvenate extends Spell{
	public Rejuvenate(){
		//super("Rejuvenate",300);
		name="Rejuvenate";
		manacost=100;
		description="Heal your target for 20% of their missing health.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualheal=100;
		int actualmanacost=calculateActualMC(caster);

		int missinghp=target.getMaxHealth()-target.getHealth();
		actualheal=(int)(missinghp*0.2); //Heal for 20% of missing hp.

		target.heal(actualheal);
		caster.drainMana(actualmanacost);
		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String heal=healToGreen();
		String mana=manaToBlue();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);

		if(cst.equals(tgt))
			tgt="themselves";
		String str="Using "+mana+" mana, "+cst+" summons a brilliant light upon "+tgt+", healing them for "+heal+" points.";
		return str;
	}
}

