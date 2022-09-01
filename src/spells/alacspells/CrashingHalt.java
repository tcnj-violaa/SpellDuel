package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;

public class CrashingHalt extends ToggleMember{
	int finaldmg;

	public CrashingHalt(Toggle tog){
		super(tog);
		name="Crashing Halt";
		manacost=0;
		description="End the Warp effect, dealing damage to your target based on the speed gained from the effect.";
		//togg=tog;
		//te = togg.getActiveEffect(); Refers to null -- there is no active effect at instantiation!
		onlySelfCast=true;

		

	}

	@Override
	public void cast(Entity caster, Entity target){
		//findEffect();
		
		te=togg.getActiveEffect();

		finaldmg=((WarpE) te).calcHaltStop();
		te.end();
		togg.initTgt.cleanUpBuffs(); //Needs to use initTgt; this spell isn't passed a target!

		//togg.resetChainAmt(); Unneccessary! The chain takes care of resetting itself, and this causes it always to increment back to one after reset
		
		System.out.println("\n"+getDialog(caster, target)+"\n");
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String cst=nameToBold(caster);
		String tgt=nameToBold(togg.initTgt);
		String dmg=toRed(""+finaldmg);
		String str="";

		if(caster == target)
			tgt="themselves";
		str+=cst+" causes "+tgt+" to suddenly decelerate, causing them to come to a crashing halt and dealing "+dmg+" damage.";

		return str;
	}
}
