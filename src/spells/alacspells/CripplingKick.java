package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class CripplingKick extends Spell{
	private Effect activeEffect;
	public CripplingKick(){
		super(110, 200, 250, "Crippling Kick");
		basecooldown=0;
		description="Infuse your foot with a magical sigil, kicking your target to detonate it and slow them.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		caster.drainMana(actualmc);

		if(blockCheck(caster,target)){ //Have sigil detonate on self, maybe?
			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}

		AlacSlow slow = new AlacSlow(caster, target);
		setEffect(slow);
		target.addBuff(slow);
		slow.initEffect();

		actualdamage=calculateDealtDamage(caster, target);
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

		str=cst+" strikes "+tgt+" with a sweeping kick, foot infused with a magical sigil, applying a slowing effect and dealing "+dmg+" damage. They use "+mana+" mana to do this.";

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

		str=cst+" leaps at "+tgt+", trying to strike them with a flying kick, but it is blocked -- "+mana+" mana was used anyway.";
		return str;
	}
}
