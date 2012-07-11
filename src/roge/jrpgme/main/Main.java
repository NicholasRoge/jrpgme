/**
 * File:  Main.java
 * Date of Creation:  Jul 2, 2012
 */
package roge.jrpgme.main;

import roge.jrpg.model.Experience;
import roge.jrpgme.gui.MapEditor;
import roge.utils.Math.Expression;

/**
 * @author Nicholas Rog�
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

    	System.out.print(Expression.fromString("4(cos(0)+3)(4)").evaluate());
        //new MapEditor().setVisible(true);
    }

}
