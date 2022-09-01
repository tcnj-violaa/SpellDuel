package SpellDuel.spells.savspells;
import SpellDuel.effects.saveffects.*;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class Choke extends Spell{
	private ChokeE activeEffect;
	public Choke(){
		super("Choke", 500);
		basecooldown=6;
		description="Stifle your target for 3 turns, doubling their mana costs and negating their MPR.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		cooldown=basecooldown;
		ChokeE choke=new ChokeE(caster, target);
		setEffect(choke);
		target.addBuff(choke);
		caster.drainMana(actualmc);
		choke.initEffect();

		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String turns=""+(getEffect().getDuration()-1);
		String mincrease=""+(int)(getEffect().getMFIncrease()*100)+"%";
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(cst.equals(tgt))
			tgt="themselves";
		str=cst+" inflicts a curse on "+tgt+" that will increase their manacosts by "+mincrease+" of original values and negate their mana regen for "+turns+" rounds. To do so, "+cst+" used "+mana+" mana.";
		return str;
	}

	public void setEffect(ChokeE active){activeEffect=active;}
	public ChokeE getEffect(){return activeEffect;}
}


