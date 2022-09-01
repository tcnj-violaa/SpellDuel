package SpellDuel.spells.alacspells;
import SpellDuel.spells.*;
import SpellDuel.entities.*;
import SpellDuel.effects.alaceffects.*;
import SpellDuel.effects.*;
import SpellDuel.*;
public class JabC extends Spell{
	private int basedamage=100;
	private int combocount=0;
	private int turncounter=0;
	private int combo3bonus;
	private Effect activeEffect;

	public JabC(){ //Split into separate spells as to better account for displayed mana costs.
		super(75, 100, 100, "Quick Strikes");
		basecooldown=0;
		description="";
	}

	@Override
	public void cast(Entity caster, Entity target){
		
		if(turncounter==0){
			damagemin*=2;
			damagemax*=2;
			manacost+=35;
			combocount++;
		}

		else{
			damagemin=100;
			damagemax=100;
			combocount=0;
		}

		if(combocount==1){
			AlacSlow slow= new AlacSlow(caster, target);
			setEffect(slow);
			target.addBuff(slow);
			slow.initEffect();
		}

		if(combocount==2){
			int speeddiff=caster.getSpeed()-target.getSpeed();
			combo3bonus=(int)(speeddiff*0.20);
			target.damage(combo3bonus); //Ignores armor factors.
		}



		//actualdamage=(int)(calculateActualDamage(caster)*target.getAFactor());

		actualmc=calculateActualMC(caster);

		actualdamage=calculateDealtDamage(caster, target);
		target.damage(actualdamage);

		caster.drainMana(actualmc);
		turncounter=0;

		System.out.println("\n"+getDialog(caster,target)+"\n");

	}


	public void counterTick(){
		turncounter++;
	}

	@Override
	public String getDialog(Entity caster, Entity target){
		String nm=name;
		String mana=manaToBlue();
		String dmg=dmgToRed();
		String bonusdmg=toBold(""+combo3bonus);
		String cst=nameToBold(caster);
		String tgt=nameToBold(target);
		String str="";

		if(caster==target)
			tgt="themselves";

		if(combocount==0)
			str=cst+" jabs "+tgt+" with a quick, contusing blow, dealing "+dmg+". They use "+mana+" mana to do so.";

		if(combocount==1)
			str=cst+" strikes "+tgt+" with a sweeping kick, foot infused with a magical sigil, applying a slowing effect and dealing "+dmg+" damage. They use "+mana+" mana to do this.";

		if(combocount==2)
			str=cst+" unleashes a final onslaught on "+tgt+", dealing "+dmg+" base damage and "+bonusdmg+" bonus damage based on their speed difference. They use "+mana+" mana.";


		return str;
	}




}
