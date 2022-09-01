package SpellDuel.entities;
import SpellDuel.spells.*;
import SpellDuel.spells.masospells.*;
import java.util.ArrayList;
public class Masochist extends Entity{
	private double minhealfactor;
	private double maxhealfactor;
	private double mindamagefactor;
	private double maxdamagefactor;
	private double maxmanafactor;
	private double minmanafactor;

	public Masochist(String callme){
		super(callme,3000,800,75,0.8,0.6,1.0,1.25,600);
		className="Masochist";

		description="Feral and blood-hungry, the Masochist is fueled by their own pain to deal high damage to themselves and enemies."+
			"\n\n\t\tStrengths: \n\t\t- Very high damage\n\t\t- High kill potential\n\t\t- Damage and healing factors scale up as health decreases"+
			"\n\n\t\tWeaknesses: \n\t\t- Poor defense\n\t\t- Limited healing\n\t\t- Low max HP\n\t\t- Mediocre max MP\n\t\t- Mediocre mana regen\n\t\t- Risky";
		
		spells=new Spell[5];
		spells[0]=new Maim();
		spells[1]=new Sunder();
		spells[2]=new SavageEq();
		spells[3]=new Hemorrhage();
		spells[4]=new Pass();
	}

	@Override
	public void damage(int hurt){
		super.damage(hurt);
		setfactors();
	}

	public void heal(int rej){
		super.heal(rej);
		setfactors();
	}

	public void setfactors(){
		double factor=0;
		if(health<600)
			factor=Math.log(((double)maxhealth/health))+5;
		else
			factor=((double)maxhealth/health);
		damagefactor=factor;
		healfactor=factor/2.0;
	}

}
