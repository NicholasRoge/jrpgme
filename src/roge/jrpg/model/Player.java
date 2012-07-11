package roge.jrpg.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player{	
	/*Begin Final Declarations*/
	public static final String HEADER="PMDS";
	/*End Final Declarations*/
	
	private String __player_name;
	
	
	/*Begin Constructor*/
	/**
	 * Constructs the Player object.
	 * 
	 * @param player_name Player's new name.  Must not be longer than 12 characters.
	 * 
	 * @throws NullPointerException Thrown if argument player_name is null.
	 * @throws IllegalArgumentException Thrown if argument player_name is longer than 12 characters.
	 */
	public Player(String player_name){
		this.setPlayerName(player_name);
	}
	/*End Constructor*/
	
	/*Begin Getter/Setter Methods*/
	public String getPlayerName(){
		return this.__player_name;
	}
	
	/**
	 * Sets the player's name.
	 * 
	 * @param player_name Player's new name.  Must not be longer than 12 characters.
	 * 
	 * @throws NullPointerException Thrown if argument player_name is null.
	 * @throws IllegalArgumentException Thrown if argument player_name is longer than 12 characters.
	 */
	public void setPlayerName(String player_name){
		if(player_name==null){
			throw new NullPointerException();
		}else if(player_name.length()>12){
			throw new IllegalArgumentException("Number of characters in argument 'player_name' must not exceed 12.");
		}
		
		this.__player_name=player_name;
	}
	/*End Getter/Setter Methods*/
	
	/*Begin Other Essential Methods*/
	/*End Other Essential Methods*/
}
