package SpellDuel.effects.disceffects;
import SpellDuel.effects.*;
import SpellDuel.spells.*;
import SpellDuel.spells.discspells.*;
import SpellDuel.entities.*;
import SpellDuel.*;

public class ImposeE extends Effect{
	protected int casterHealth;
	protected int targetHealth;
	protected double percentHealth=0.0;

	protected int casterMana;
	protected int targetMana;
	protected double percentMana=0.0;

	protected int casterMPR;
	protected int targetMPR;

	protected double casterDF;
	protected double targetDF;

	protected double casterHF;
	protected double targetHF;

	protected double casterMF;
	protected double targetMF;

	protected double casterAF;
	protected double targetAF;

	protected int casterSpeed;
	protected int targetSpeed;

	public ImposeE(Entity caster, Entity target){
		super("Impose",6);
		duration=6;
		abbreviation="Im";
		buffcst=caster;
		bufftgt=target;

		casterHealth=buffcst.getMaxHealth();
		targetHealth=bufftgt.getMaxHealth();

		casterMana=buffcst.getMaxMana();
		targetMana=bufftgt.getMaxMana();

		casterMPR=buffcst.getBaseMPR();
		targetMPR=bufftgt.getBaseMPR();

		casterDF=buffcst.getBaseDF();
		targetDF=bufftgt.getBaseDF();

		casterHF=buffcst.getBaseHF();
		targetHF=bufftgt.getBaseHF();

		casterMF=buffcst.getBaseMF();
		targetMF=bufftgt.getBaseMF();

		casterAF=buffcst.getBaseAF();
		targetAF=bufftgt.getBaseAF();

		casterSpeed=buffcst.getSpeed();
		targetSpeed=bufftgt.getSpeed();
	}

	@Override
	public void tick(){
		if(duration>0){
			duration--;
			System.out.println(getPulseText());
		}

		if(duration==0){
			end();
		}
	}

	public void initEffect(){
		percentHealth=((double)bufftgt.getHealth()/bufftgt.getMaxHealth());
		percentMana=((double)bufftgt.getMana()/bufftgt.getMaxMana());

		bufftgt.setMaxHealth(casterHealth);
		bufftgt.setHealth((int)(casterHealth*percentHealth));

		bufftgt.setMaxMana(casterMana);
		bufftgt.setMana((int)(casterMana*percentMana));

		bufftgt.setBaseMPR(casterMPR);

		bufftgt.setBaseDF(casterDF);
		
		bufftgt.setBaseHF(casterHF);
		
		bufftgt.setBaseMF(casterMF);
		
		bufftgt.setBaseAF(casterAF);

		bufftgt.setBaseSPD(casterSpeed);
	}

	@Override
	public void end(){
		percentHealth=((int)bufftgt.getHealth()/bufftgt.getMaxHealth());
		percentMana=((int)bufftgt.getMana()/bufftgt.getMaxMana());

		bufftgt.setMaxHealth(targetHealth);
		bufftgt.setHealth((int)(targetHealth*percentHealth));

		bufftgt.setMaxMana(targetMana);
		bufftgt.setMana((int)(casterMana*percentMana));
		
		bufftgt.setBaseMPR(targetMPR);

		bufftgt.setBaseDF(targetDF);
		
		bufftgt.setBaseHF(targetHF);
		
		bufftgt.setBaseMF(targetMF);
		
		bufftgt.setBaseAF(targetAF);

		bufftgt.setBaseSPD(targetSpeed);

		bufftgt.removeBuff(this);
	}

	@Override
	public String getPulseText(){
		String nm=name;
		String cststr=nameToBold(buffcst);
		String tgtstr=nameToBold(bufftgt);
		String rounds=""+duration;
		String str="";
		if(duration==0){
			str=tgtstr+" shifts back to their standard form, adjusting health and mana based on a percent left of each.";
		}
		else
			str=tgtstr+" keeps "+cststr+"'s stats; this will last for "+duration+" more rounds.";

		return str;
	}
}
		

		

		






