package SpellDuel;
import SpellDuel.spells.*;
import SpellDuel.spells.palspells.*;
import SpellDuel.entities.*;
import SpellDuel.teams.*;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.reflect.*;
import java.util.Collection;
public class Tester{
	public static int round=1;
//	protected Entity classList[]={new Paladin(""),new Masochist(""),new Savant(""),new Discordant(""),new Alacriter(""), new Ironskin("")};
	protected Entity classList[]={new Paladin(""),new Masochist(""),new Savant(""),new Discordant(""),new Alacriter("")}; //Excludes Ironskin, for a 'production' release.

	public static ArrayList<Entity> players= new ArrayList<>();
	protected ArrayList<Entity> speedSortedPlayers= new ArrayList<>();
	protected int spResponse;
	protected int tgtResponse;
	static public Spell prevSpell=null;
	protected Team teamList[]={new Azurites(), new Pyres()};
	public Mode modeList[]={new FFA(), new TeamPlay()};
	public Mode runningMode;
	static public boolean teamMode=false; //Implement mode class, potentially?

	public void test(){
		playerPrompt();
		for(Entity i : players){
			System.out.println(i.getName());
			System.out.println(i.getMaxHealth());

		}

		//speedSortedPlayers=sortPlayersBySpeed();
		System.out.println("Sorted by speed");
		for(Entity i : speedSortedPlayers){
			System.out.println(i.getName());
			System.out.println(i.getSpeed());
		}
	}



	public void run(){
		System.out.println("~| Welcome to SpellDuel! |~");
		System.out.println("---------------------------\n");

		//FFA.doSomething();
		modePrompt();
		playerPrompt();

		/*for(Entity ent : players){
			speedSortedPlayers.add(ent);
		}*/
	
		runningMode.runMode();

	}

/*	public void run(){ Backup of legacy run mode
		System.out.println("~| Welcome to SpellDuel! |~");
		System.out.println("---------------------------\n");

		modePrompt();
		playerPrompt();

		/*for(Entity ent : players){
			speedSortedPlayers.add(ent);
		}
		
		while(numAlive()>1){
			System.out.println("\n~Round "+round+"~\n");
			updateAll();
			classPrompt());
			}
			System.out.println(numplayers);
			return numplayers;
	}*/

	protected Mode modePrompt(){
		Scanner scan=new Scanner(System.in);
		int response;
		String str="";

		for(int i=0; i<modeList.length; i++){
			str+=(i+1)+" for "+modeList[i].getName()+" || ";

		}

		System.out.println(str);
		System.out.println("Choose a mode to play.");

		try{response=scan.nextInt();}

			catch(InputMismatchException e){
				System.out.println("I don't think that's a mode.");
				return modePrompt();
			}
		

			if(response > modeList.length || response < 0){
				System.out.println("There aren't that many modes, unfortunately.");
				return modePrompt();
			}

			response-=1;

			runningMode=modeList[response];

			if(runningMode == modeList[1])
				teamMode = true;

			System.out.println("");

			return modeList[response];
	}



	protected Spell spellPrompt(Entity caster){
		Scanner scan=new Scanner(System.in);
		String choices="";
		for(int i=0;i<caster.spells.length;i++){
			choices+=i+1+" for "+caster.spells[i].getName()+" (\033[34;1m"+caster.spells[i].calculateActualMC(caster)+"\033[0m MP) ";
			if(caster.spells[i].offCooldown())
				choices+="(\033[1mReady!\033[0m)";
			else
				choices+="("+caster.spells[i].getCooldown()+" turns left)";
			
			choices+=" || ";
		}
		choices+=" 404 for a spell description.";
		System.out.println(choices);
		spResponse=0;

		try{spResponse=scan.nextInt();}


		catch(InputMismatchException e){
			System.out.println("I don't think you have that spell...");
			return spellPrompt(caster);
		}
		
		if(spResponse>caster.spells.length && spResponse!=404 || spResponse<1){
			System.out.println("You don't have \033[1mthat\033[0m many spells.");
			return spellPrompt(caster); 
		}

		else if(spResponse==404){
			System.out.println(descPrompt(caster));
			return spellPrompt(caster);
		}

		/*else if(spResponse==caster.spells.length){
			tgtResponse=-1;
		}*/

		prevSpell=caster.spells[spResponse-1];
		return caster.spells[spResponse-1];
	}
	
	protected String descPrompt(Entity caster){
		Scanner scan=new Scanner(System.in);

		int response=0;
		String desc="";

		System.out.println("\nPick a spell to describe!");

		try{response=scan.nextInt();}

		catch(InputMismatchException e){
			System.out.println("I don't think you have that spell...");
			return descPrompt(caster);
		}

		if(response>caster.spells.length){
			System.out.println("I think you're just asking too much of me.");
			return descPrompt(caster);
		}

		else
			desc=caster.spells[response-1].getDescription();

		return desc;
	}
					
	protected Entity targetPrompt(Entity caster){

		if(prevSpell.selfOnly()) //Don't prompt if pass was used prior. Probably should implement this a little more cleanly.
			return null; //Pass doesn't need a target, so this is valid.
		
		String str="";
		Entity tgt=null;
		int tgtIncrementor=0;
		Scanner scan=new Scanner(System.in);
		for(Entity i : players){
			if(i.isAlive())
				str+=(tgtIncrementor+1)+" for "+i.getColor()+i.getName()+"\033[0m || ";
			tgtIncrementor++;
		}
		str+="909 to go back.";

		System.out.println("\n"+str);
		System.out.println("Choose a target.");
		tgtResponse=0;
		try{tgtResponse=scan.nextInt();}
		catch(InputMismatchException e){
			System.out.println("Something bad happened.");
			return targetPrompt(caster);
		}

		if(tgtResponse>players.size() && tgtResponse!=909){
			System.out.println("There aren't that many players.\n");
			return targetPrompt(caster);
		}

		if(tgtResponse == 909){
			System.out.println("");
			caster.cast(spellPrompt(caster), targetPrompt(caster));
			return null;
		}

		tgt=players.get(tgtResponse-1);
		return tgt;
	}

	protected void updateAll(){
		for(int i=0;i<players.size();i++){
			players.get(i).update();
		}

		runningMode.checkForDeath();

		System.out.println("");
	}

	protected int numAlive(){
		int numAlive=0;
		for(int i=0;i<players.size();i++){
			if(players.get(i).isAlive()){
				numAlive++;
			}
		}
		return numAlive;
	}

	protected void getStatuses(){
		for(int i=0;i<players.size();i++){
			if(players.get(i).isAlive())
				players.get(i).printStatus();
		}
		System.out.println("");
	}

	protected void cleanBuffs(){
		for(int i=0;i<players.size();i++){
			players.get(i).cleanUpBuffs();
		}
	}

	protected void castLoop(){
		for(int i=0;i<players.size();i++){
			if(players.get(i).isAlive()){
				System.out.println(players.get(i).getName()+"'s turn!");

				if(i==0 && players.get(i).doubleCasted()) //If they're the first player and took the last turn, keep double-casted status.
					players.get(i).setDCasted(true);

				else
					players.get(i).setDCasted(false);

				players.get(i).cast(spellPrompt(players.get(i)), targetPrompt(players.get(i)));


				if(i==players.size()-1)//If the player takes the last turn, deem them eligible for a double-cast bonus if they're first next turn.
					players.get(i).setDCasted(true);
				else
					players.get(i).setDCasted(false);

				getStatuses();

				if(players.get(i).getSpeed()>1000){ //Check for threshold so we don't need to calculate bonus chances for people who are too slow anyway.
					checkDoubleTurn(players.get(i));
				}

				if(runningMode.checkForDeath())
					i--; //Necessary? Is this having excess effects?

			/* Double-cast check operates as a loop with safeguards: 
			 * Apply a double-casted eligibility status if they're last.	
			 * Next turn, if the player is first and they had dc bonus eligibility status, they keep it. Otherwise, drop it.*/
			}
		}

		//players.trimToSize(); //Move this to the end so players after a player that dies within a turn don't get gypped of a turn!



	}

	protected void checkDoubleTurn(Entity caster){
		double bonusDoubleChance=((caster.getSpeed()/1000.0)-1.0)/2;
		//System.out.println(bonusDoubleChance);

		if(Math.random()<=0.25+bonusDoubleChance){
			System.out.println(caster.getName()+" manages to find an opening to take another turn!\n");
			caster.setDCasted(true); //Ensure double-cast bonuses are applied.
			caster.cast(spellPrompt(caster), targetPrompt(caster));
			caster.setDCasted(false); //An in-round double-turn makes you ineligible for out-of-round double-cast bonuses.
			getStatuses();
			runningMode.checkForDeath();
		}

		else
			System.out.print("");
	}




	protected boolean checkForDeath(){
		boolean died = false;
		for(int j=players.size()-1;j>=0;j--){
			
			if(!players.get(j).isAlive()){	

				for(int i=players.get(j).buffs.size()-1;i>=0;i--){ //Clean up effects
					players.get(j).buffs.get(i).end();
					//players.get(j).buffs.remove(i);
				}

				System.out.println("\t~"+players.get(j).getName()+" has been killed!~\n");
				players.remove(j);
				died=true;

			}
		}

		players.trimToSize();
		return died;
	}

		


/*	protected void p2Init(){
		System.out.println("\n\033[1m"+p2.getName()+"'s\033[0m turn.");
		p2.cast(spellPrompt(p2),targetPrompt(p2));
		p1.printStatus();
		p2.printStatus();
			if(p2.isAlive()){
				System.out.println("\n\033[1m"+p1.getName()+"'s\033[0m turn.");
				p1.cast(spellPrompt(p1),targetPrompt(p1));
			}
			else System.out.println(p2.getName()+" has been slain!");
	}


	protected void p1Init(){
		System.out.println("\n\033[1m"+p1.getName()+"'s\033[0m turn.");
		p1.cast(spellPrompt(p1),targetPrompt(p1));
		p1.printStatus();
		p2.printStatus();
			if(p1.isAlive()){
				System.out.println("\n\033[1m"+p2.getName()+"'s\033[0m turn.");
				p2.cast(spellPrompt(p2),targetPrompt(p2));
			}
			else System.out.println(p1.getName()+" has been slain!");
	}
	*/




	protected int playerPrompt(){
		int resp;
		int numplayers;
		Scanner scan=new Scanner(System.in);
		System.out.print("How many players do you want? ");
			try{resp=scan.nextInt();}
			catch(InputMismatchException e){
				System.out.println("I can't do that.\n");
				return playerPrompt();
				 
			}

			numplayers=resp;


			for(int i=0;i<numplayers;i++){
				System.out.println("\nPlayer "+(i+1));
				players.add(classPrompt());
			}
			System.out.println(numplayers);
			return numplayers;
	}

	protected Entity classPrompt(){
		Scanner scan=new Scanner(System.in);
		int classResp;
		Entity ent=null;
		String nameResp="";
		String str="";
		Constructor entConst=null;

		System.out.println("Enter your name: ");
		nameResp=scan.nextLine();
		System.out.println("");

		for(int i=0;i<classList.length;i++){
			str+=(i+1)+" for "+classList[i].getClass().getSimpleName()+" || ";
		}

		str+="404 for more information about a class.";

		System.out.println(str);
		System.out.println("Choose a class: ");

		try{classResp=scan.nextInt();}
		catch(InputMismatchException e){
			System.out.println("Enter a number representing an actual class, please.\n");
			return classPrompt();
		}

		if(classResp>classList.length && classResp!=404){
			System.out.println("I hate to break it to you, but this game doesn't have that many classes yet.\n");
			return classPrompt();
		}

		else if(classResp==404){
			System.out.println(classDescPrompt());
			return classPrompt();
		}
		
		classResp-=1;


		//System.out.println("Enter a name, too: ");
		//nameResp=scan.nextLine();

		Class entType=classList[classResp].getClass();
		try{entConst=entType.getConstructor(String.class);}
		catch(NoSuchMethodException ne){
			System.out.println("String.class failed to pass.");}
		
		/*try{ent=entType.newInstance();}
		catch(InstantiationException ie){
			System.out.println("nameResp failed to pass.");
		}*/

		//ent.setName("Test");
			

		try{ent = (Entity)
			entConst.newInstance(nameResp);}

			catch(InstantiationException | IllegalAccessException | InvocationTargetException ie){
				System.out.println("Didn't instantiate properly");
			}
//		ent.setName(nameResp);
			
		System.out.println("");

		if(runningMode == modeList[1])
			ent.setTeam(teamPrompt());	

		return ent;
	}

	public Team teamPrompt(){
		Scanner scan = new Scanner(System.in);
		String list="";
		int response;

		for(int i=0; i<teamList.length ; i++){
			list+=(i+1)+" for "+teamList[i].getColor()+teamList[i].getName()+"\033[0m || ";
		}

		System.out.println(list);
		System.out.println("Choose a team!");

		try{response=scan.nextInt();}

			catch(InputMismatchException e){
				System.out.println("That team would love to have you if it existed.");
				return teamPrompt();
			}
		

			if(response > teamList.length || response < 0){
				System.out.println("There aren't that many teams, unfortunately.");
				return teamPrompt();
			}

			response-=1;

			System.out.println("");

			return teamList[response];
	}

	public String classDescPrompt(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose a class to describe.");
		int response=0;
		String str="";

		try{response=scan.nextInt();}
		catch(InputMismatchException e){
			return classDescPrompt();
		}

		if(response>classList.length)
			return classDescPrompt();

		str=classList[response-1].getDescription();
		return str;
	}

	public int getTurn(){return round;}


	/*public ArrayList<Entity> sortPlayersBySpeed(){
		ArrayList<Entity> sortedP = new ArrayList<>();
		Collections.copy(sortedP,players);
		Database.sortedP((o2, o1) -> o1.getSpeed().compareTo(o2.getSpeed()));
		return sortedP;
	}*/


//Start of modes.

	public abstract class Mode{
		protected String name;

		public abstract void runMode();

		public boolean checkForDeath(){
			boolean died = false;
			for(int j=players.size()-1;j>=0;j--){
			
				if(!players.get(j).isAlive()){	

					for(int i=players.get(j).buffs.size()-1;i>=0;i--){ //Clean up effects
					players.get(j).buffs.get(i).end();
					//players.get(j).buffs.remove(i);
					}

					System.out.println("\t~"+players.get(j).getName()+" has been killed!~\n");
					players.remove(j);
					died=true;

				}
			}

			players.trimToSize();
			return died;
		}

		public String getName(){return name;}
	}

	public class FFA extends Mode{

		public FFA(){
			name="Free-for-All";
		}

		public void runMode(){

			while(numAlive()>1){
				System.out.println("\n~Round "+round+"~\n");
				updateAll();
				players.sort((Entity o1, Entity o2) -> o2.getSpeed() - o1.getSpeed()); //Works, but also reorders status output; consider cloning array and traversing a sorted one only for casts.
				//System.out.println("\tNumber alive: "+numAlive());
				getStatuses();
				castLoop();
				round++;
				checkForDeath();

			
			}
		
		
			if(numAlive()==1){
				for(int i=0;i<players.size();i++){
					if(players.get(i).isAlive())
						System.out.println(players.get(i).getName()+" wins!");
				}

			}
		}
	}

	public class TeamPlay extends Mode{

		public TeamPlay(){
			name = "Teams";
		}

		public void runMode(){

			while(aliveTeams() > 1){
				System.out.println("\n~Round "+round+"~\n");
				updateAll();
				players.sort((Entity o1, Entity o2) -> o2.getSpeed() - o1.getSpeed()); //Works, but also reorders status output; consider cloning array and traversing a sorted one only for casts.
				//System.out.println("\tNumber alive: "+numAlive());
				getStatuses();
				castLoop();
				round++;
				checkForDeath();
			}

			if(aliveTeams() == 1){
				String win = "";
				for(int i=0; i < teamList.length; i++){
					if(teamList[i].getMembers().size() > 0){
						win=teamList[i].getColor()+teamList[i].getName()+"\033[0m win!";
					}
				}

			System.out.println(win);
			}
		}


		public int aliveTeams(){
			int numFighting = 0;
			for(int i=0; i < teamList.length; i++){
//				System.out.println(teamList[i].getMembers().size());

				if(teamList[i].getMembers().size() > 0)
					numFighting++;
			}

			return numFighting;
		}

		@Override
		public boolean checkForDeath(){
			//System.out.println("This is a team-mode death check.");
			boolean died = false;

			for(int j=players.size()-1;j>=0;j--){
			
				if(!players.get(j).isAlive()){	

					for(int i=players.get(j).buffs.size()-1;i>=0;i--){ //Clean up effects
						players.get(j).buffs.get(i).end();
					//players.get(j).buffs.remove(i);
					}

				System.out.println("\t~"+players.get(j).getName()+" has been killed!~\n");

				ArrayList<Entity> jsTeam = players.get(j).getTeam().getMembers();
				//System.out.println("printing members: "+ jsTeam);
				
			//	System.out.println("Removing from team...");
				jsTeam.remove(players.get(j)); //It's important that it removes the player, not just the index; otherwise, we get OOB errors and other messy stuff.
				jsTeam.trimToSize();

			//	System.out.println("Removing from player list...");
				players.remove(j);
				died=true;

			}
		}

		players.trimToSize();
		return died;
	}


		
	}
}



