package SpellDuel.spells.discspells;
import SpellDuel.spells.*;
import SpellDuel.effects.*;
import SpellDuel.entities.*;
//import SpellDuel.entities.Discordant;

public class ProteanShift extends Spell{
	public ProteanShift(){
		name="Protean Shift";
		manacost=300;
		basecooldown=3;
		onlySelfCast=true;
		description="Take a new form, randomizing your stats and removing all effects.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		caster.drainMana(actualmc);

		caster.statRandom();
		
		for(int i=caster.buffs.size()-1;i>=0;i--){
			caster.buffs.get(i).end();
//			caster.buffs.remove(i);
		}

		cooldown=basecooldown;

		System.out.println("\n"+getDialog(caster, target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String cst=nameToBold(caster);
		String str="";

		str=cst+" is coated with chaotic light of a million indescribable colors before it is shed, revealing a new form. Their stats have been randomized and all effects have been cleansed.";
		return str;
	}
}
