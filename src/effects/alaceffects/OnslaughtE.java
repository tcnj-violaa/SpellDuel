package SpellDuel.effects.alaceffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.alacspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;
public class OnslaughtE extends Effect{
	private int speedwarp=1000;
	private int actualWarp=speedwarp;

	public OnslaughtE(Entity caster, Entity target){
		super("Rapid Onslaught", 3);
		duration=3;
		abbreviation="RO";
		buffcst=caster;
		bufftgt=target;
	}

	@Override
	public void tick(){ //Nested else/if statements here are ~important!~
		if(duration==3){
			duration--;
		}
		
		else{
			if(duration==2){
				duration--;
				actualWarp=2*speedwarp;
				bufftgt.changeBonusSPD(actualWarp);
				actualWarp= -1000; //It's important that we change it here. Otherwise, cleanses wouldn't set the bonus speed correctly.
			}
			
			else{
				if(duration==1){
					//bufftgt.changeBonusSPD(actualWarp);
					end();
				}
			}
		}

		
	}

	public void initEffect(){
		bufftgt.changeBonusSPD(-speedwarp);
	}

	public void end(){
		//bufftgt.changeBonusSPD(-speedwarp);
		bufftgt.changeBonusSPD(actualWarp);
		System.out.println();
		isActive=false;
		duration=0;
		bufftgt.removeBuff(this);
	}

	@Override
	public String getPulseText(){
		String nm=name;
		String tgt=nameToBold(bufftgt);
		String str="";

		if(duration==1){
			str=tgt+" accelerates to a blinding quickness.";
		}
		
		if(duration==0){
			str=tgt+" returns to normal speed.";
		}

		return str;
	}
}

