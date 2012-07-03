/**
 * File:  LoadMapPanel.java
 * Date of Creation:  Jul 2, 2012
 */
package roge.jrpgme.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import roge.gui.RWindow;
import roge.gui.panel.RPanel;
import roge.jrpgme.gui.MapEditor;

/**
 * @author Nicholas Rogé
 *
 * TODO:  Write Class Description
 */
public class MainMenuPanel extends RPanel{
    /*Begin Constructors*/
    /**
     * Constructs the MainMenuPanel object.
     * 
     * @param parent Parent window
     */
    public MainMenuPanel(RWindow parent){
        super(parent);
        
        this._applyGUI();
    }
    /*End Constructors*/
    
    /*Begin Overridden Methods*/
    @Override public void onPanelClose(){
        this.getParentWindow().setResizable(true);
    }
    /*End Overridden Methods*/
    
    /*Begin Other Essential Methods*/
    protected void _applyGUI(){
        JLabel  label = null;
        
        
        this.setBackground(Color.WHITE);
        
        //Add the panel's contents
        this.setBorder(new EmptyBorder(20,20,20,20));
        this.setLayout(new GridLayout(1,1));
        {
            label = new JLabel("Choose an option from the menu...",JLabel.CENTER);
            label.setFont(MapEditor.HEADING_FONT);
            this.add(label);
        }
        
        this.setPreferredSize(new Dimension(600,400));
        this.getParentWindow().setResizable(false);
    }
    /*End Other Essential Methods*/
}
