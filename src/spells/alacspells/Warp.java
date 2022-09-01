package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class Warp extends ToggleMember{
	private WarpE warp;

	public Warp(Toggle tog){
		super(tog);
		name="Warp";
		manacost=75;
		description="Place a toggleable effect on your target that increasingly improves their speed, dealing light damage per turn.";
		//togg = tog;
		te=null;
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);

		setTE(togg.on(caster,target));
		setEffect(te);
		target.addBuff(te);
		caster.drainMana(actualmc);
		te.initEffect();

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

		str=cst+" enchants "+tgt+" with rapidly accelerating alacrity; it will end when they will it to.";
		return str;
	}
}



	


