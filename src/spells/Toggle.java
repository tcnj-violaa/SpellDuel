package SpellDuel.spells;
import SpellDuel.entities.*;
import SpellDuel.effects.*;

import java.lang.reflect.*;

public class Toggle extends Chain{
	protected ToggleEffect toggle;
	protected ToggleEffect activeEffect;

	public Entity initTgt;
	public Entity initCst;

	public Toggle(){
		//Define toggle states and effect in-class
		chain = new Spell[2];
	}

	@Override
	public void cast(Entity caster, Entity target){
		//super.cast(caster, target);
		chain[chainamt].cast(caster,target);
/*			chainamt++; //This poses an issue for toggles; after it's reset to zero by the ending member, it'll still be incremented to one.
				Instead, just include togg.chainamt++ in the first member of the toggle 'chain'*/

		//No need for reset via cast; ending the effect will work fine.
//		System.out.println("chainamt: "+chainamt+" on "+this);
	}

	public ToggleEffect getEffect(){
		return toggle;
	}

	public ToggleEffect getActiveEffect(){
		return activeEffect;
	}

	public ToggleEffect on(Entity caster, Entity target){
		Class effType=toggle.getClass();
		Constructor effConst=null;
		ToggleEffect eff=null;

		try{effConst=effType.getConstructor(Entity.class, Entity.class, Toggle.class);}
		catch(NoSuchMethodException ne){
			System.out.println("Entity.class failed to pass.");}

		try{eff = (ToggleEffect)
			effConst.newInstance(caster, target, this);}

			catch(InstantiationException | IllegalAccessException | InvocationTargetException ie){
				System.out.println("Didn't instantiate properly.");
			}

		activeEffect=eff;

		initCst=caster;
		initTgt=target;

		return activeEffect; //Return aE or eff? Does it matter?

	}

	@Override //Toggles don't need turn counters; window for next spell never expires
	public void counterTick(){/*Do nothing*/}

	//@Override
	/*public void resetChainAmt(){
		chainamt=0;
		turncounter=0;
		System.out.println("Toggle reset");
	}*/



}
