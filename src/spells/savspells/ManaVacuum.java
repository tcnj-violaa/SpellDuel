package SpellDuel.spells.savspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class ManaVacuum extends Spell{
	protected double manascaledfactor;
	protected double targetmanaperc;
	protected int basedamage;
	public ManaVacuum(){
		name="Mana Vacuum";
		manacost=850;
		damagemin=70;
		damagemax=70;
		basecooldown=3;
		description="Deal damage that scales up as the target's mana is closer to zero; restore mana to yourself equal to the damage dealt.";

	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);

		actualdamage=calculateActualDamage(caster);

		targetmanaperc=((double)(target.getMana())/target.getMaxMana()); //Percentage of target's mana

		manascaledfactor=(-8*(Math.pow((targetmanaperc),2))+9); //Scale by -8x^2+8

		basedamage=(int)(actualdamage*manascaledfactor); //Scale damage

		actualdamage=(int)(basedamage*target.getAFactor()); //Scale damage based on tgt's armor

		caster.drainMana(actualmc);
		target.damage(actualdamage);
		caster.restoreMana(actualdamage); //Restore mana 1:1 based on damage.
		cooldown=basecooldown;

		System.out.println(targetmanaperc);
		System.out.println(manascaledfactor);
		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String manac=manaToBlue();
		String manar="\033[34;1m"+actualdamage+"\033[0m";
		String dmg=dmgToRed();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(cst.equals(tgt))
			tgt="themselves";

		str="With "+manac+" mana, "+cst+" summons a vortex upon "+tgt+", dealing "+dmg+" damage and absorbing the damage dealt as "+manar+" mana.";

		return str;
	}
}



