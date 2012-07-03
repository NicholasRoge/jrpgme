/**
 * File:  MainMenu.java
 * Date of Creation:  Jul 2, 2012
 */
package roge.jrpgme.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import roge.gui.RWindow;
import roge.gui.menu.RMenu;
import roge.jrpgme.gui.MapEditor;
import roge.jrpgme.gui.panel.MainMenuPanel;

/**
 * TODO:  Write Class Description
 *
 * @author Nicholas Rogé
 */
public class MainMenu extends RMenu{
    /**
     * Action the About menu item should take.
     * 
     * @author Nicholas Rogé
     */
    private class __AboutAction implements ActionListener{
        /*Begin Overridden Methods*/
        public void actionPerformed(ActionEvent event){
            MainMenu.this.getParentWindow().performAction(MapEditor.Actions.OPEN_ABOUT_WINDOW);
        }
        /*End Overridden Methods*/
    }
    
    /**
     * Action the Load Map menu item should take.
     * 
     * @author Nicholas Rogé
     */
    private class __LoadMapAction implements ActionListener{
        /*Begin Overridden Methods*/
        public void actionPerformed(ActionEvent event){
            //TODO_HIGH:  Define Load Map action
        }
        /*End Overridden Methods*/
    }
    
    /**
     * Action the New Map menu item should take.
     * 
     * @author Nicholas Rogé
     */
    private class __NewMapAction implements ActionListener{
        /*Begin Overridden Methods*/
        public void actionPerformed(ActionEvent event){
            MainMenu.this.getParentWindow().performAction(MapEditor.Actions.DISPLAY_NEW_MAP);
        }
        /*End Overridden Methods*/
    }
    
    /**
     * Action the Exit menu item should take.
     * 
     * @author Nicholas Rogé
     */
    private class __ExitAction implements ActionListener{
        /*Begin Overridden Methods*/
        public void actionPerformed(ActionEvent event){
            MainMenu.this.getParentWindow().performAction(RWindow.Actions.CLOSE_WINDOW);
        }
        /*End Overridden Methods*/
    }
    
    
    /*Begin Constructors*/
    /**
     * Constructs the MainMenu object.
     * 
     * @param parent Parent window
     */
    public MainMenu(RWindow parent){
        super(parent);
        
        this._addChildren();
    }
    /*End Constructors*/
    
    /*Begin Other Essential Methods*/
    protected void _addChildren(){
        JMenu     menu = null;
        JMenuItem menu_item = null;
        
        
        {
            menu = new JMenu("File");
            menu.setMnemonic('f');
            {
                menu_item = new JMenuItem("New Map");
                menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));
                menu_item.addActionListener(new __NewMapAction());
                menu.add(menu_item);
            }
            {
                menu_item = new JMenuItem("Load Map");
                menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));
                menu_item.addActionListener(new __LoadMapAction());
                menu.add(menu_item);
            }
            {
                menu_item = new JMenuItem("Exit");
                menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,KeyEvent.ALT_MASK));
                menu_item.addActionListener(new __ExitAction());
                menu.add(menu_item);
            }
            this.add(menu);
        }
        {
            menu = new JMenu("Help");
            menu.setMnemonic('h');
            {
                menu_item = new JMenuItem("About");
                menu_item.addActionListener(new __AboutAction());
                menu.add(menu_item);
            }
            this.add(menu);
        }
    }
    /*End Other Essential Methods*/
}
