package SpellDuel.entities;
import SpellDuel.spells.*;
import SpellDuel.spells.alacspells.*;
import SpellDuel.spells.ironspells.*;
import SpellDuel.effects.*;
import java.util.ArrayList;
public class Alacriter extends Entity{
	public Alacriter(String callme){
		super(callme, 3100, 950, 50, 1.0, 1.0, 1.0, 1.33, 950);

		description="The Alacriter is a blindingly quick contender; they manipulate their speed and others' to roll out rapid-fire combos and double turns left and right."+
		"\n\n\t\tStrengths: \n\t\t- Fast\n\t\t- Low mana costs\n\t\t- Moderate-high damage\n\t\t- Extensive comboing\n\t\t- Exceptional speed manipulation"+
		"\n\n\t\tWeaknesses: \n\t\t- High armor factor \n\t\t- Low mana regen\n\t\t- Inflexible combos\n\t\t- Combos can be mana-intensive";
		spells=new Spell[5];
		spells[0]=new RapidOnslaught();
		spells[1]=new JabChain();
		spells[2]=new AidChain();
		spells[3]=new WarpToggle();
		spells[4]=new Pass();
	}

	@Override //Heals and damages to the Alacriter increase their speed.
	public void damage(int hurt){
		super.damage(hurt);
		speed+=10;
		//changeBonusSPD(10);
	}

	@Override //Heals moreso
	public void heal(int rej){
		super.heal(rej);
		speed+=20;
		//changeBonusSPD(20);
	}

	@Override
	public void update(){
		super.update();
		//Check for double-casting for chain eligibility

		for(int i=0;i<spells.length;i++){
			if(spells[i] instanceof Chain){
				if(!doubleCasted() && !(spells[i] instanceof Toggle)) //Must check for alacriter, otherwise the toggle always resets!
					spells[i].resetChainAmt();
			}
		}

	}


	//Somehow implement a mechanic such that subsequent casts of a spell have progressively stronger effects.

}
