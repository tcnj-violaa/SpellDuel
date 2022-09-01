package SpellDuel.effects.ironeffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class BarrierE extends ToggleEffect{
	protected int numBlocks;
	protected int numPlayers; //The effectiveness of the ending heal decreases as it blocks more, but keep it relative to the number of players.
	protected int baseHeal=1000;
	protected int endHeal=0;
	protected double decFactor;
	protected double totalDecFactor;

	public BarrierE(Entity caster, Entity target, Toggle over){
		super("Barrier",65);
		abbreviation = "B";

		heal=baseHeal;

		superToggle=over;
		actualheal=calculateActualHeal(caster);
		
		buffcst=caster;
		bufftgt=target;

	}

	public BarrierE(){ //Dummy constructor
		super("Barrier",65);
		abbreviation="B";
	}

	public void initEffect(){
		bufftgt.setBlocking(true);
	}

	public void tick(){
		upkeep();

		duration--;

		System.out.println(getPulseText());
	}

	public void end(){
		numBlocks=bufftgt.getBlockCounter();

		numPlayers = Tester.players.size()-1; //Don't include the caster; we can safely assume they will not attack themselves while they are barriered.

		decFactor=(1.0/numPlayers)/2.0; //Divide by two, so two turns of everybody attacking negates it completely.

		System.out.println("decFactor = "+decFactor);
		System.out.println("numBlocks = "+numBlocks);

		totalDecFactor=(1.0-(decFactor*numBlocks)); //Calculate the actual factor to multiply by based on how many attacks were blocked.

		System.out.println("totalDecFactor = "+totalDecFactor);

		actualheal-=(actualheal-(actualheal*totalDecFactor));

		if(actualheal < 0) //Don't allow for negative values
			actualheal=0;

		bufftgt.heal(actualheal);

		bufftgt.setBlocking(false);
		System.out.println("\n"+endDialog()+"\n");

		superToggle.resetChainAmt();

		bufftgt.removeBuff(this);
	}

	public String endDialog(){
		String cst=nameToBold(buffcst);
		String tgt=nameToBold(bufftgt);
		String healamt=healToGreen();
		String str="";

		str="The barrier on "+tgt+" bursts inward, healing them for "+healamt+" health.";

		return str;
	}

	@Override
	public String getPulseText(){
		String cst=nameToBold(buffcst);
		String tgt=nameToBold(bufftgt);
		String upk=toBlue(actualUpkeep);
		String str="";

		if(buffcst == bufftgt)
			tgt="themselves";

		str=cst+" maintains the barrier on "+tgt+", using "+upk+" to do so.";
		return str;
	}



		

}
