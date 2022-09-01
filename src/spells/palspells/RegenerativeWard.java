package SpellDuel.spells.palspells;
import SpellDuel.effects.paleffects.*;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class RegenerativeWard extends Spell{
	private RegenerativeWardE activeEffect;
	public RegenerativeWard(){
		super("Regenerative Ward",200);
//		Effect rward=new RegenerativeWardE(caster,target);
		description="Bless your target, decreasing the damage they take by 15% and healing them slightly per turn for 3 turns. The heal can increase health beyond maximum.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		int actualmc=calculateActualMC(caster);
		RegenerativeWardE rward=new RegenerativeWardE(caster,target);
		setEffect(rward);
		target.addBuff(rward);
		rward.initEffect();
		caster.drainMana(actualmc);

		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String heal="\033[32;1m"+getEffect().getActualHeal()+"\033[0m";
		String turns=""+(getEffect().getDuration()-1);
		String armor=""+(int)(getEffect().getArmorIncrease()*100);
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(cst.equals(tgt))
			tgt="themselves";

		str="Using "+mana+" mana, "+cst+" seals "+tgt+" with a Protective Ward. They will heal "+heal+" points for "+turns+" turns. They will take "+armor+"% less damage during this time.";
		return str;
	}

	public void setEffect(RegenerativeWardE active){activeEffect=active;}
	public RegenerativeWardE getEffect(){return activeEffect;}
	//public Entity returnTarget(){return target;}
}



