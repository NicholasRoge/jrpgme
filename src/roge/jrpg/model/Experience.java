package roge.jrpg.model;

import java.util.List;

public class Experience{
	private String __level_equation="sqrt({player_experience})";
	
	
	/*Begin Other Essential Methods*/
	public int getPlayerLevel(int player_experience){
		String expression=null;
		String variable=null;
		int    variable_start=0;
		int    variable_end=0;
		
		
		expression=this.__level_equation;
		while(true){
			//Get the location of the left brace
			variable_start=expression.indexOf('{');
			//Get the location of the right brace
			variable_end=expression.indexOf('}');
					
			//Check for errors
			if(variable_start>=0){
				if(variable_end==-1){
					throw new RuntimeException("Unmatched opening brace found.");
				}
			}else{
				if(variable_end>=0){
					throw new RuntimeException("Unmatched closing brace found.");
				}else{
					break;  //Exit the loop.  We've found and replaced all the variables
				}
			}
			
			//Make the replacement
			variable=expression.substring(variable_start+1,variable_end);
			
			if(variable.equals("player_experience")){
				variable=Integer.toString(player_experience);
			}else{
				throw new RuntimeException("Unrecognized variable \""+variable+"\" found in experience equation.");
			}
			expression=this._replaceVariable(expression,variable,variable_start,variable_end);
		}
		
		return (int)3;
	}
	
	public int getExperienceGain(int player_level,int opponent_level){
		return 0;
	}
	
	public String _replaceVariable(String old_string,String replacement,int variable_start_index,int variable_end_index){
		return old_string.substring(0,variable_start_index)+replacement+old_string.substring(variable_end_index+1,old_string.length());
	}
	/*End Other Essential Methods*/
	
	/*Begin Abstract Methods*/

	/*End Abstract Methods*/
}
