package SpellDuel.spells.savspells;
import SpellDuel.effects.saveffects.*;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class BlessingOfMana extends Spell{
	private BlessingOfManaE activeEffect;
	int manarestore=200;

	public BlessingOfMana(){
		super("Blessing of Mana", 0, 600, 800);
		basecooldown=10;
		description="On cast, restore a significant amount of health and a moderate amount of mana to the target. Also apply an effect that lasts for 4 rounds that increases their MPR by 150 and decreases their mana costs by 20%.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		actualheal=calculateActualHeal(caster);
		cooldown=basecooldown;
		BlessingOfManaE bom=new BlessingOfManaE(caster,target);
		setEffect(bom);
		target.addBuff(bom);
		bom.initEffect();
		target.restoreMana(manarestore);
		target.heal(actualheal);
		
		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String heal=healToGreen();
		String mrestore="\033[34;1m"+manarestore+"\033[0m";
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		//String mpr=""+bom.getMPR();
		//String mfs=""+bom.getMFB();
		String turns=""+(getEffect().getDuration()-1);
		String str="";

		if(tgt.equals(caster))
			tgt="themselves";
		str=cst+" blesses "+tgt+" with a gift of mana, restoring "+mrestore+" mana and "+heal+" health. For "+turns+" rounds, they will restore 150 mana per round and have spells cost 0.8x their original values.";
		return str;
	}
}

