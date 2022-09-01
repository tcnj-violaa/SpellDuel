package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class VelocitousRestoration extends Spell{
	private double bonusFactor;
	public VelocitousRestoration(){
		super("Velocitous Restoration", 245);
		healmin=400;
		healmax=600;
		basecooldown=0;
		description="Heal your target significantly; the amount scales based on the target's speed.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		actualheal=calculateActualHeal(caster);

		bonusFactor = target.getSpeed() / 6500.0; //For every 1000 speed, ~15% bonus heal.

		actualheal=actualheal+(int)(actualheal*bonusFactor);

		target.heal(actualheal);
		
		caster.drainMana(actualmc);

		System.out.println(bonusFactor); //Remember to remove.
		System.out.println("\n"+getDialog(caster, target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String heal=healToGreen();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster==target)
			tgt="themselves";

		str=cst+" casts a rapidly surging, restorative spell upon "+tgt+", healing them for a total of "+heal+" health.";
		return str;
	}
}
