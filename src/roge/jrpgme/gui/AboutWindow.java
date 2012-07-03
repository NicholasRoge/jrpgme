/**
 * File:  AboutWindow.java
 * Date of Creation:  Jul 2, 2012
 */
package roge.jrpgme.gui;

import java.awt.event.WindowEvent;

import roge.gui.RWindow;
import roge.jrpgme.gui.panel.AboutPanel;

/**
 * TODO:  Write Class Description
 *
 * @author Nicholas Rogé
 */
public class AboutWindow extends RWindow{
    private RWindow __parent;
    
    
    /*End Constructors*/
    public AboutWindow(RWindow parent){
        if(parent==null){
            throw new NullPointerException("Argument 'parent' must not be null.");
        }
        
        this.setActivePanel(new AboutPanel(this));
        this.pack();
        
        this.__parent=parent;
        this.__parent.setEnabled(false);       
    }
    /*Begin Constructors*/
    
    /*Begin Overridden Methods*/
    public void onWindowClosing(WindowEvent event){
        this.__parent.setEnabled(true);
    }
    /*End Overridden Methods*/
}
