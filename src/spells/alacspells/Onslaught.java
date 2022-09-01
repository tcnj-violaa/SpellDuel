package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;
public class Onslaught extends Spell{
	private int bonusdmg;
	public Onslaught(){
		super(150, 350, 400, "Onslaught");
		basecooldown=0;
		description="Finish the combo, dealing heavy damage with bonus damage based on the difference between your speed and your target's speed.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		actualdamage=calculateDealtDamage(caster, target);
		caster.drainMana(actualmc);

		int speedDiff = caster.getSpeed()-target.getSpeed();
		bonusdmg = (int)(speedDiff*0.20); //Scale by 20%.

		if(blockCheck(caster,target)){
			target.damage(bonusdmg); //Still pierce.
			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}

		target.damage(actualdamage);
		target.damage(bonusdmg); //Ignore armor factor.


		System.out.println("\n"+getDialog(caster, target)+"\n");
	}
	
	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String dmg=dmgToRed();
		String bonusdmgS=toBold(""+bonusdmg);
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster==target)
			tgt="themselves";

		str=cst+" unleashes a final onslaught on "+tgt+", dealing "+dmg+" base damage and "+bonusdmg+" bonus damage based on their speed difference. They use "+mana+" mana.";

		return str;
	}

	@Override
	public String getBlockDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String bonusdmgS=toBold(""+bonusdmg);
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster==target)
			tgt="themselves";

		str=cst+" unleashes an onslaught on "+tgt+"; while the first flurry is blocked, the last strike pierces and deals "+bonusdmgS+" damage, using "+mana+" mana.";

		return str;
	}
}
		


