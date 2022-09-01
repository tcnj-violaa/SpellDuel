package SpellDuel.spells;
import SpellDuel.entities.*;
import SpellDuel.effects.*;

public class Chain extends Spell{
	protected Spell form;
	protected int length;
	//protected int chainamt;
	public int chainamt;
	protected int turncounter=0;
	public Spell[] chain;

	public Chain(Spell[] spells){
		chain = spells.clone();
		length = chain.length;
		form = chain[0];
	}

	public Chain(){
		//Define members of the chain in-constructor
	}

	public Spell getForm(){
		return form;
	}

	//Overrides are to ensure that chains operate based on spells' costs, not the chain itself.
	@Override
	public void cast(Entity caster, Entity target){
		//System.out.println("Initial chainamt of "+this+": "+chainamt);
		chain[chainamt].cast(caster, target);
			chainamt++;

		//System.out.println("ChainAMT increased: "+this);

		if(chainamt>chain.length-1){
			chainamt=0;
			//System.out.println("Chain reset via cast(): "+this);
		}
}


	@Override
	public String getDialog(Entity caster, Entity target){
		String str = chain[chainamt].getDialog(caster, target);
		return str;
	}


	@Override
	public String getName(){
		return chain[chainamt].getName();
	}

	@Override
	public int calculateActualMC(Entity caster){
		int mana = chain[chainamt].calculateActualMC(caster);
		return mana;
	}

	/*@Override
	public int getCooldown(){
		int cd = chain[chainamt].getCooldown();
		return cd;
	}*/

	@Override
	public void tickCooldown(){
		chain[chainamt].reduceCooldown(1);
	}

	@Override
	public String getDescription(){
		String str = chain[chainamt].getDescription();
		return str;
	}

	@Override 
	public boolean selfOnly(){ //Ensure individual spells are respected
		boolean self = chain[chainamt].selfOnly();
		return self;
	}

	@Override
	public int getManaCost(){
		int mc = chain[chainamt].getManaCost();
		return mc;
	}

	public void counterTick(){
		turncounter++;
		System.out.println("Counter ticked: "+this);
	}

	public int getCounter(){
		return turncounter;
	}

	public void resetChainAmt(){
		chainamt=0;
		//turncounter=0;
		//System.out.println("Chain reset: "+this); //Debug
	}
}



