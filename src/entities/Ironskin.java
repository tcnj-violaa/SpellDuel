package SpellDuel.entities;
import SpellDuel.spells.*;
import SpellDuel.spells.ironspells.*;
import SpellDuel.spells.alacspells.*;
import SpellDuel.effects.*;
import java.util.ArrayList;

public class Ironskin extends Entity{
//	int blocktally;
	public Ironskin(String callme){
		super(callme, 1800, 600, 45, 1.0, 1.0, 1.0, 0.65, 250);

		description="";

		spells=new Spell[5];
		spells[0]=new BarrierToggle();
		spells[1]=new WarpToggle();
		spells[2]=new Pass();
		spells[3]=new Pass();
		spells[4]=new Pass();

	}

	/*@Override
	public void block(){
		blocktally--;
	}*/
}
		
