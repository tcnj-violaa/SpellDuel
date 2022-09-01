package SpellDuel.entities;
import SpellDuel.spells.*;
import SpellDuel.spells.discspells.*;
import SpellDuel.effects.*;
import java.util.ArrayList;
public class Discordant extends Entity{
	public Discordant(String callme){
		super(callme, 100, 100, 100, 1.0, 1.0, 1.0, 1.0, 600);

		className="Discordant";

		int randHP=(int)((40*Math.random())+10)*100; //1000 min, 5000 max
		int randMP=(int)((15*Math.random())+6)*100; //600 min, 2100 max
		int randMPR=(int)(160*Math.random())+40; //40 min, 200 max
		double randDF=(1.5*Math.random())+0.5; //0.5 min, 2.0 max
		double randHF=(1.5*Math.random())+0.5; //0.5 min, 2.0 max
		double randMF=(1.5*Math.random())+0.25; //0.25 min, 1.75 max
		double randAF=(1.4*Math.random())+0.6; //0.6 min, 2.0 max
		int randSpeed=(int)(((125*Math.random())+5)*10); //50 min, 1300 max.
		
		maxhealth=randHP;
		health=randHP;
		maxmana=randMP;
		mana=randMP;
		mpregen=randMPR;
		damagefactor=randDF;
		healfactor=randHF;
		manafactor=randMF;
		armorfactor=randAF;
		speed=randSpeed;

		description="With chaos by their side, the Discordant manipulates the random to contend as an unpredictable combatant."+
			"\n\n\t\tStrengths: \n\t\t- Effect cleanse\n\t\t- Powerful stat manipulation\n\t\t- Access to high damage and powerful healing"+ 
			"\n\n\t\tWeaknesses: \n\t\t- Potential for low stats\n\t\t- High mana costs\n\t\t- Limited sources of damage";

		spells=new Spell[5];
		spells[0]=new ProteanShift();
		spells[1]=new Impose();
		spells[2]=new BoltOfDisorder();
		spells[3]=new HealOfProportion();
		spells[4]=new Pass();
	}

	@Override
	public void statRandom(){
		double percentHealth=(double)health/maxhealth;
		double percentMana=(double)mana/maxmana;
		int randHP=(int)((40*Math.random())+10)*100;
		int randMP=(int)((15*Math.random())+6)*100;
		int randMPR=(int)(160*Math.random())+40;
		double randDF=(1.5*Math.random())+0.5;
		double randHF=(1.5*Math.random())+0.5;
		double randMF=(1.5*Math.random())+0.25;
		double randAF=(1.4*Math.random())+0.6;
		int randSpeed=(int)(((125*Math.random())+5)*10);
		
		System.out.println(percentHealth);
		System.out.println(percentMana);
		maxhealth=randHP;
		health=(int)(randHP*percentHealth);
		maxmana=randMP;
		mana=(int)(randMP*percentMana);
		mpregen=randMPR;
		damagefactor=randDF;
		healfactor=randHF;
		manafactor=randMF;
		armorfactor=randAF;
		speed=randSpeed;
	}


}
