/**
 * File:  MapEditor.java
 * Date of Creation:  Jul 2, 2012
 */
package roge.jrpgme.gui;

import java.awt.Font;
import java.awt.event.WindowEvent;

import roge.gui.RWindow;
import roge.jrpgme.gui.menu.MainMenu;
import roge.jrpgme.gui.panel.MainMenuPanel;
import roge.jrpgme.gui.panel.NewMapPanel;

/**
 * @author Nicholas Rogé
 */
public class MapEditor extends RWindow{
    private class __OpenAboutWindowAction implements WindowAction{
        /*Begin Overridden Methods*/
        @Override public boolean performAction(Object data){
            new AboutWindow(MapEditor.this).setVisible(true);
            
            return true;
        }
        /*End Overridden Methods*/
    }
    
    private class __DisplayNewMapAction implements WindowAction{
        /*Begin Overridden Methods*/
        @Override public boolean performAction(Object data){
            MapEditor.this.setActivePanel(new NewMapPanel(MapEditor.this));
            MapEditor.this.pack();
            
            return true;
        }
        /*End Overridden Methods*/
    }
    
    public static class Actions{
        public static final int DISPLAY_NEW_MAP=101;
        public static final int OPEN_ABOUT_WINDOW=102;
    }
    
    
    /** Font which should be used for headings. */
    public static final Font HEADING_FONT = new Font("Arial",Font.BOLD,18);
    
    
    /*Begin Constructors*/
    /**
     * Constructs the window.
     */
    public MapEditor(){       
        /*Add the Actions*/
        this.registerWindowAction(Actions.DISPLAY_NEW_MAP,new __DisplayNewMapAction());
        this.registerWindowAction(Actions.OPEN_ABOUT_WINDOW,new __OpenAboutWindowAction());
        
        this.setWindowTitle("RPG Map Editor");
        
        this.setActiveContent(new MainMenu(this),new MainMenuPanel(this));
        this.pack();
    }
    /*End Constructors*/
}
