package SpellDuel.spells.masospells;
import SpellDuel.effects.masoeffects.*;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class Hemorrhage extends Spell{
	private HemorrhageE activeEffect;
	public Hemorrhage(){
		super(250,50,50,"Hemorrhage");
		basecooldown=3;
		description="Inflict a curse on your target that will tick for two turns, initially dealing light damage that doubles per tick.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		int actualmc=calculateActualMC(caster);
		caster.drainMana(actualmc);

		if(blockCheck(caster,target)){
			cooldown=basecooldown;
			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return;
		}


		actualdamage=(int)(calculateActualDamage(caster)*target.getAFactor());
		HemorrhageE hemo=new HemorrhageE(caster,target);
		setEffect(hemo);
		target.addBuff(hemo);
		target.damage(actualdamage);
		cooldown=basecooldown;
		System.out.println("\n"+getDialog(caster,target)+"\n");
		
	}
		

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String dmg=dmgToRed();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String edmg="\033[31;1m"+2*(int)(getEffect().calculateActualDamage(caster)*target.getAFactor())+"\033[0m";
		String turns=""+(getEffect().getDuration()-1);
		String str="";

		if(caster==target)
			tgt="themselves";

			str=cst+" inflicts a hemorrhaging curse on "+tgt+", dealing "+dmg+" damage and using "+mana+" mana to do so. The curse will last for "+turns+" rounds, dealing initial damage of "+edmg+". The damage will double per tick.";

		return str;
	}

	@Override
	public String getBlockDialog(Entity caster, Entity target){
		String cst=nameToBold(caster);
		String mana=manaToBlue();
		String str="";

		str=cst+"'s curse is blocked, dissipating and dealing no damage. "+mana+" mana was used.";
		blocked=false;

		return str;
	}


	public void setEffect(HemorrhageE active){activeEffect=active;}
	public HemorrhageE getEffect(){return activeEffect;}
}
