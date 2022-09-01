package SpellDuel.spells.palspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class Smite extends Spell{
	private boolean reflected=false;
	public Smite(){
		super(80,200,260,"Smite");
		description="A simple incantation that deals light damage to the target.";}

	public void cast(Entity caster, Entity target){
		actualmc=calculateActualMC(caster);
		caster.drainMana(actualmc);

		if(blockCheck(caster,target)){
			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}

		actualdamage=calculateDealtDamage(caster,target);
		target.damage(actualdamage);
		System.out.println("\n"+getDialog(caster,target)+"\n");
		
	}

	
	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String dmg=dmgToRed();
		String mana=manaToBlue();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster == target)
			tgt="themselves";
		str=cst+" smites "+tgt+" with a pure beam of light, dealing "+dmg+" damage and using "+mana+" mana.";

		return str;
	}

	public String getBlockDialog(Entity caster, Entity target){
		String nm=name;
		String dmg=dmgToRed();
		String mana=manaToBlue();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		str=cst+" casts a powerful beam of light upon "+tgt+", but it bounces off. "+mana+" mana was used.";
		blocked=false;

		return str;
	}


}

