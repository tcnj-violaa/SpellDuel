package SpellDuel.spells.palspells;

import SpellDuel.teams.*;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class Purge extends Spell{
	private int purgenum;
	private double purgescale=1.50;
	public Purge(){
		name="Purge";
		manacost=300;
		damagemin=100;
		damagemax=125;
		healmin=300;
		healmax=350;
		basecooldown=3;
		description="Cleanse all effects from your target; if they are allied, heal them. Otherwise, deal damage. Both healing and damage scales by "+((int)(purgescale*100))+"% per effect removed.";
	}

	@Override
	public void cast(Entity caster, Entity target){ //Unblockable
		actualmc=calculateActualMC(caster);

		purgenum=0;

		for(int i=target.buffs.size()-1;i>=0;i--){
			target.buffs.get(i).end();
			purgenum++;
		}
		 
		target.buffs.trimToSize();

		if(caster.getTeam() == target.getTeam() && caster.getTeam() != null){
			int baseheal=calculateActualHeal(caster);
			actualheal=(int)(baseheal*((purgescale*purgenum)+1.0));

			target.heal(actualheal);
		}

		else{
			int basedmg=(int)(calculateActualDamage(caster));
			int factoredDmg=(int)(basedmg*(purgescale*purgenum+1.0));
			actualdamage=(int)(factoredDmg*target.getAFactor());
			target.damage(actualdamage);
		}

		cooldown=basecooldown;
		caster.drainMana(actualmc);
		System.out.println("\n"+getDialog(caster,target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String dmg=dmgToRed();
		String heal=healToGreen();
		String mana=manaToBlue();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String purged=""+getPurged();
		String totalbonus=""+(int)((purgescale*purgenum)*100)+"%";

		if(target.equals(caster))
			tgt="themselves";

		String str="Using "+mana+" mana, "+cst+" envelops "+tgt+" in a cloak of pure light, cleansing "+purged+" effects for a bonus "+totalbonus;

		if(target.getTeam() == caster.getTeam() && caster.getTeam() != null)
			str+=" healing. The light seeps into their bones, restoring "+heal+" health.";
		else
			str+=" damage. The light bursts, dealing "+dmg+" damage.";

		return str;
	}

	private int getPurged(){return purgenum;}
}









