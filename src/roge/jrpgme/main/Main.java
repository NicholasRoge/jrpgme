/**
 * File:  Main.java
 * Date of Creation:  Jul 2, 2012
 */
package roge.jrpgme.main;


import roge.utils.Math.Expression;
import roge.utils.Math.Expression.InvalidExpressionException;

/**
 * @author Nicholas Rogé
 */
public class Main{
    /**
     * @param args
     */
    public static void main(String[] args){
    	try{
    		System.setProperty("com.apple.mrj.application.apple.menu.about.name","RPG Map Editor");
    	}catch(Exception e){
    		//Nothing really to do here.  This just means that we aren't using the Mac OS.
    	}


    	try{
    		System.out.print(Expression.fromString("sin([pi]/2)(5-cos(0)4)").evaluate());
    	}catch(InvalidExpressionException e){
    		System.out.print(e.getMessage());
    	}
        //new MapEditor().setVisible(true);
    }

}
