package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.*;
public class RapidOnslaught extends Spell{
	private OnslaughtE activeEffect;
	private int manarestore;
	public RapidOnslaught(){
		super(150, 0, 0, "Rapid Onslaught");
		basecooldown=2;
		description="Apply a blessing on your target, bending their speed such that they will take the next turn last and the turn after first, allowing them to cast two times in a row. Restore mana to them as well.";
		manarestore=100;
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		OnslaughtE ons = new OnslaughtE(caster, target);
		setEffect(ons);
		target.addBuff(ons);
		ons.initEffect();
		caster.drainMana(actualmc);
		target.restoreMana(manarestore);
		cooldown=basecooldown;

		System.out.println("\n"+getDialog(caster,target)+"\n");

	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String manar="\033[34;1m"+manarestore+"\033[0m";
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);

		String str="";

		if(caster == target)
			tgt="themselves";
		str="Using "+mana+" mana, "+cst+" restores "+manar+" mana to and blesses "+tgt+" with a curious incantation, initially slowing them to a crawl. Next turn, they will accelerate to great speed.";

		
		return str;
	}
}
		

