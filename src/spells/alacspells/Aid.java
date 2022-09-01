package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class Aid extends Spell{
	public Aid(){
		super("Aid", 65);
		healmin=75;
		healmax=150;
		basecooldown=0;
		description="Heal your target for a small amount."; //Elaborate.
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		actualheal=calculateActualHeal(caster);

		target.heal(actualheal);
		
		target.drainMana(actualmc);

		System.out.println("\n"+getDialog(caster, target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String dmg=dmgToRed();
		String heal=healToGreen();
		String mana=manaToBlue();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster==target)
			tgt="themselves";

		str=cst+" casts a small blessing upon "+tgt+", healing them for "+heal+".";

		return str;
	}
}



