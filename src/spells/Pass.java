package SpellDuel.spells;
import SpellDuel.entities.*;
public class Pass extends Spell{
	public Pass(){
		super(0,0,0,"Pass");
		onlySelfCast=true;
		description="Concede your turn, restoring mana equal to your MPR.";
	}

	@Override
	public void cast(Entity caster, Entity target){
		int mRestored = caster.getMPR();
		System.out.println("\n"+caster.getName()+" passes, restoring "+mRestored+" mana.\n");
		caster.restoreMana(caster.getMPR());
		//caster.update();
	}
}
