package SpellDuel.teams;

import SpellDuel.entities.*;
import SpellDuel.Tester;

import java.util.ArrayList;

public class Team{
	private String name;
	private String color; //Should be an ANSI escape sequence for coloring.
	protected ArrayList<Entity> members = new ArrayList<Entity>();


	public Team(String call, String spect){
		name=call;
		color=spect;
	}

	public String getColor(){
		return color;
	}

	public String getName(){
		return name;
	}

	public ArrayList<Entity> getMembers(){
		return members;
	}

	public void addMember(Entity ent){
		members.add(ent);
	}

	public void removeMember(Entity ent){
		members.remove(ent);
		members.trimToSize();
	}

}

