package SpellDuel.spells.ironspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.ironeffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class RestorativeBreak extends ToggleMember{

	public RestorativeBreak(Toggle tog){
		super(tog);
		name="Restorative Break";
		manacost=0;
		description="Dissipate your barrier on your target; it bursts, healing them based on how few attacks it blocked.";
		onlySelfCast=true;

	}

	@Override
	public void cast(Entity caster, Entity target){
		te=togg.getActiveEffect();

		te.end();
		
		togg.initTgt.cleanUpBuffs();
		//togg.resetChainAmt();

		System.out.println("\n"+getDialog(caster, target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String cst=nameToBold(caster);
		String tgt=nameToBold(togg.initTgt);
		String str="";

		if(caster == target)
			tgt="themselves";
		str+=cst+" wills the barrier on "+tgt+" to dissipate.";

		return str;
	}
}



