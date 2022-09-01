package SpellDuel.spells.savspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class ManaTheft extends Spell{
	protected double theftperc=0.4;
	protected int theftamt;
	protected double tgtmcincrease=0.05;
	protected int basedamage;
	public ManaTheft(){
		name="Mana Theft";
		manacost=300;
		damagemin=200;
		damagemax=275;
		description="Blast your target, dealing moderate damage and stealing "+((int)(theftperc*100))+" percent of the damage dealt as mana.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		caster.drainMana(actualmc);

		if(blockCheck(caster,target)){
			int manarestore= (int)(actualmc*0.5);
			caster.restoreMana(manarestore); //Refund half the mana cost.
			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}

		basedamage=calculateActualDamage(caster);
		actualdamage=(int)(basedamage*target.getAFactor());

		theftamt=(int)(actualdamage*theftperc);

		if(theftamt>target.getMana())
			theftamt=target.getMana();


		target.damage(actualdamage);
		target.drainMana(theftamt);
		target.setBaseMF(target.getBaseMF()+tgtmcincrease);
		caster.restoreMana(theftamt);

		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String theft="\033[34;1m"+theftamt+"\033[0m";
		String increase=""+(int)(tgtmcincrease*100)+"%";
		String dmg=dmgToRed();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster == target){
			tgt="themselves";
			str=cst+" summons a snaking spell around their own head, dealing "+dmg+" damage. Their spells will now cost "+increase+" more mana from now on.";
		}

		str=cst+" uses "+mana+" mana to fire a spell -- snaking and hungry for mana -- at "+tgt+"'s head, dealing "+dmg+" damage and sapping "+theft+" mana. "+tgt+"'s spells will now cost "+increase+" more mana from now on.";

		return str;
	}

	@Override
	public String getBlockDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String refund = toBlue(""+(int)(actualmc*0.5));
		String theft="\033[34;1m"+theftamt+"\033[0m";
		String increase=""+(int)(tgtmcincrease*100)+"%";
		String dmg=dmgToRed();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster == target)
			tgt="themselves";

		str=cst+" fires a snaking bolt at "+tgt+", but it is blocked -- it returns to "+cst+", refunding half of the mana cost ("+refund+").";

		return str;
	}	




}
