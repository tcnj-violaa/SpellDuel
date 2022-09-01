package SpellDuel.effects;
import SpellDuel.spells.*;
import SpellDuel.spells.masospells.*;
import SpellDuel.spells.palspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
import SpellDuel.effects.*;

abstract public class ToggleEffect extends Effect{
	protected int upkeep;
	protected int actualUpkeep=0;
	protected boolean fizzled=false;
	protected Toggle superToggle;

	public ToggleEffect(String call, int upk){ //Make sure to include Toggle in actual implemented effect constructor, passing name and upkeep to this constructor with super.
		super(call, 999);
		upkeep=upk;
		//name=call;
	}

	public void tick(){
		System.out.println(getPulseText()); //Don't decrease duration.
	} 

	public String getAbbr(){
		return abbreviation;
	}

	public void upkeep(){
		actualUpkeep = (int)(upkeep*buffcst.getMFactor());
		if(actualUpkeep > buffcst.getMana()){
			actualUpkeep=buffcst.getMana();
			buffcst.drainMana(actualUpkeep); //Use what it can, then die.
			fizzled = true;
			end();
		}

		if(!(actualUpkeep > buffcst.getMana())){
			buffcst.drainMana(actualUpkeep);
		}
	}



}
