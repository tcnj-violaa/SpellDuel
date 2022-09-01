package SpellDuel.spells.discspells;
import SpellDuel.spells.*;
import SpellDuel.effects.*;
import SpellDuel.effects.disceffects.*;
import SpellDuel.entities.*;

public class Impose extends Spell{
	private ImposeE activeEffect;
	public Impose(){
		super("Impose",700);
		basecooldown=4;
		description="Mold your target into your likeness, temporarily changing their base stats to yours for 6 turns. Health and mana will be adjusted to an appropriate value based on each of their respective percentages. If cast on yourself, make current stat bonuses permanent.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		int actualmc=calculateActualMC(caster);
		if(target.equals(caster)){
			caster.setBaseMPR(caster.getMPR());
			
			caster.setBaseHF(caster.getHFactor());
			
			caster.setBaseDF(caster.getDmgFactor());

			caster.setBaseMF(caster.getMFactor());

			caster.setBaseAF(caster.getAFactor());

			caster.setBaseSPD(caster.getSpeed());
		}

		else{
			ImposeE impos = new ImposeE(caster,target);
			setEffect(impos);
			target.addBuff(impos);
			impos.initEffect();
		}
		
		caster.drainMana(actualmc);

		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(target.equals(caster))
			str=cst+" seals themselves with pressing magic, making all current stat adjustments permanent.";
		else{
			String rounds=""+(getEffect().getDuration()-1);
			str=cst+" creates an image of themselves, to superimpose it upon "+tgt+". "+tgt+" gains "+cst+"'s stats for "+rounds+".";
		}
			return str;
	}
}




			
