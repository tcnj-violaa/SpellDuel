package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class MutualVigor extends Spell{
	private Effect activeEffect;
	public MutualVigor(){
		super("Mutual Vigor", 135);
		healmin=250;
		healmax=300;
		basecooldown=0;
		description="Heal your target for a moderate amount, increasing both your and their speed and healing factor for three turns.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		actualheal=calculateActualHeal(caster);

		MutualBen mb = new MutualBen(caster, target);

		setEffect(mb);
		target.addBuff(mb);
		mb.initEffect();

		target.heal(actualheal);

		caster.drainMana(actualmc);

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

		str="Casting a spiraling, glowing bolt at "+tgt+", "+cst+" heals them for "+heal+" and places a mutual blessing shared between them. This boon will last for three turns, slightly increasing speed and healing factor.";
		return str;
	}
}



