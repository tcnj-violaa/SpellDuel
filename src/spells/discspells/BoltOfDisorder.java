package SpellDuel.spells.discspells;
import SpellDuel.spells.*;
import SpellDuel.effects.*;
import SpellDuel.effects.disceffects.*;
import SpellDuel.entities.*;

public class BoltOfDisorder extends Spell{
	private Effect activeEffect;
	private boolean crit=false;
	private boolean heal=false;
	private boolean slowing=false;
	private boolean wither=false;
	private boolean draining=false;
	private int clause=0;
	public BoltOfDisorder(){
		super(550,25,450,"Bolt of Disorder");
		basecooldown=0;
		description="Fire an unpredictable bolt of magic at the target with a chance of random effects, including:"
			+"\n\t\t- 20% chance to critically hit for twice the damage"
			+"\n\t\t- 10% chance to heal you for an amount equal to the damage dealt"
			+"\n\t\t- 33% chance to apply a slowing effect"
			+"\n\t\t- 33% chance to apply a health-draining effect"
			+"\n\t\t- 33% chance to apply a heal-weakening effect";
	}

	@Override
	public void cast(Entity caster, Entity target){ //Currently unable to apply multiple instances?
		int actualmc=calculateActualMC(caster);
		actualdamage=(int)(calculateActualDamage(caster)*target.getAFactor());
		caster.drainMana(actualmc);
		cooldown=basecooldown;

		if(blockCheck(caster, target)){
			if(Math.random()<0.15){

			String str ="\n"+nameToBold(caster)+"'s chaotic bolt ricochets off "+nameToBold(target)+", bouncing back to hit "+nameToBold(caster)+"!";
			System.out.println(str);

			cast(target,caster); //A chance to reflect!
				actualmc=calculateActualMC(target);
				target.drainMana(-actualmc);//Restore mana back to the target.
				//reflected=true;

			return;

			}

			System.out.println("\n"+getBlockDialog(caster,target)+"\n");
			return; //Don't bother to run the rest. 
		}

		
		if(Math.random()<0.2){ //Roll for crit; 20% chance
			actualdamage*=2; //Double damage
			crit=true;
		}

		if(Math.random()<0.1){ //Roll for heal; 10% chance
			actualheal=actualdamage;
			heal=true;
			clause++;
		}

		if(Math.random()<0.33){ //Roll for slow; 33% chance
			RandSlow slow = new RandSlow(caster,target);
			setEffect(slow);
			target.addBuff(slow);
			slow.initEffect();
			slowing=true;
			clause++;
		}

		if(Math.random()<0.33){ //Roll for heal wither; 33% chance
			RandHealDrain rhd = new RandHealDrain(caster,target);
			setEffect(rhd);
			target.addBuff(rhd);
			rhd.initEffect();
			wither=true;
			clause++;
		}

		if(Math.random()<0.33){//Roll for leech ticks; 33% chance
			DrainE drain = new DrainE(caster,target);
			setEffect(drain);
			target.addBuff(drain);
			//drain.initEffect();
			draining=true;
			clause++;
		}

		target.damage(actualdamage);

		if(heal)
			caster.heal(actualheal);


		System.out.println("\n"+getDialog(caster,target)+"\n");

		//Have to reset these; otherwise, if it occurs once, it appears to occur on every cast after.
		crit=false;
		heal=false;
		slowing=false;
		wither=false;
		draining=false;

	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String dmg=dmgToRed();
		String healamt=healToGreen();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		str+="Using "+mana+" mana, "+cst+" flings a chaotic bolt at "+tgt+",";

		if(crit){
			str+=" critically hitting for "+dmg+" damage";
			clause--;
		}

		else{
			str+=" dealing "+dmg+" damage";
			clause--;
		}

		if(heal){
			str+=",";
			if(clause==0)
				str+=" and";
			str+=" sapping to heal "+cst+" for "+healamt+" health";
			clause--;
		}

		if(slowing){
			str+=",";
			if(clause==0)
				str+=" and";
			str+=" applying a slowing effect"; //Add turns?
			clause--;
		}

		if(wither){
			str+=",";
			if(clause==0)
				str+=" and";
			str+=" applying a heal-withering effect";
			clause--;
		}

		if(draining){
			str+=",";
			if(clause==0)
				str+=" and";
			str+=" applying a health-sapping effect";
			clause--;
		}

		str+=".";

		return str;
	}

	@Override
	public String getBlockDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String dmg=dmgToRed();
		String healamt=healToGreen();
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		str=cst+"'s wild bolt is blocked; it required "+mana+" mana to fire.";
		

		return str;
	}




}



