package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class Jab extends Spell{
	private int basedamage=100;

	public Jab(){
		super(75, 100, 100, "Jab");
		basecooldown=0;
		description="Deal light damage with a jab."; //Elaborate
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		actualdamage=calculateDealtDamage(caster, target);
		caster.drainMana(actualmc);
		
		if(blockCheck(caster,target)){
			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}

		target.damage(actualdamage);

		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String dmg=dmgToRed();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster==target)
			tgt="themselves";

		str=cst+" jabs "+tgt+" with a quick, contusing blow, dealing "+dmg+". They use "+mana+" mana to do so.";

		return str;

	}

	@Override
	public String getBlockDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String dmg=dmgToRed();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		str=cst+" tries to strike "+tgt+" with a fast jab, but it is blocked -- "+mana+" mana was used anyway.";
		return str;
	}

}
