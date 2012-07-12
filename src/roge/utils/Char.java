package roge.utils;

public class Char{
	private char __raw=0x00;
	
	
	/*Begin Constructors*/
	Char(char c){
		this.__raw=c;
	}
	/*End Constructors*/
	
	/*Begin Overridden Methods*/
	public char toChar(){
		return this.__raw;
	}
	
	public String toString(){
		return ""+this.__raw;
	}
	/*End Overridden Methods*/
}
