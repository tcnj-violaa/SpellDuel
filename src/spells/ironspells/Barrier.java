package SpellDuel.spells.ironspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.ironeffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class Barrier extends ToggleMember{
	private BarrierE barr;

	public Barrier(Toggle tog){
		super(tog);
		name="Barrier";
		manacost=50;
		description=""; //Write.
		te=null;
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		caster.drainMana(actualmc);
		caster.resetBlockCounter();

		setTE(togg.on(caster,target));
		setEffect(te);
		target.addBuff(te);
		te.initEffect();

		togg.chain[1].setCooldown(2);
		System.out.println("togg.chain[1] cooldown = "+togg.chain[1].getCooldown());
		togg.chainamt++;
		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster == target)
			tgt="themselves";

		str=cst+" uses "+mana+" mana to summon a protective barrier upon "+tgt+". It will end when "+cst+" wills it to, or if it is cleansed.";

		return str;
	}


}

