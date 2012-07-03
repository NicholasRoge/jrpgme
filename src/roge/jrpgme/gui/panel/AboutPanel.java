/**
 * File:  AboutPanel.java
 * Date of Creation:  Jul 2, 2012
 */
package roge.jrpgme.gui.panel;

import java.awt.Dimension;

import roge.gui.RWindow;
import roge.gui.panel.About;

/**
 * TODO:  Write Class Description
 *
 * @author Nicholas Rogé
 */
public class AboutPanel extends About{
    /*Begin Constructors*/
    /**
     * Constructs the AboutPanel object.
     * 
     * @param parent Parent window.
     */
    public AboutPanel(RWindow parent){
        super(parent);
    }
    /*End Constructors*/
    
    /*Begin Overridden Methods*/
    @Override public String getContents(){
        return "<html><center>"+
               "<strong>Author</strong><br />"+
               "Nicholas Rogé<br />"+
               "<br />"+
               "<strong>About this Application</strong><br />"+
               "This application was designed to create and manipulate map files for the RPG Creator.<br />"+
               "<br />"+
               "<br />"+
               "&copy;Nicholas Rogé - 2012"+
               "</center></html>";
    }
    /*End Overridden Methods*/
}
