/**
 * File:  NewMapPanel.java
 * Date of Creation:  Jul 2, 2012
 */
package roge.jrpgme.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import roge.gui.RWindow;
import roge.gui.panel.RPanel;
import roge.gui.widget.ETextArea;

/**
 * TODO:  Write Class Description
 *
 * @author Nicholas Rogé
 */
public class NewMapPanel extends RPanel{
	private class __MapTitleFocus implements FocusListener{
		public void focusGained(FocusEvent event) {
			NewMapPanel.this.setInfoData(
				"Map Title",
				"This is the title that your map will have in game.<br />" +
		        "<div style='padding-left:10px;color:#FF0000;'>" +
		        "Your title must not exceed 60 characters." +
		        "</div>"
		    );
		}
		public void focusLost(FocusEvent event) {
			NewMapPanel.this.setInfoData("Map Title","lol");
		}
	}
	
	/*Begin Member Variables*/
	private JTextPane __info_pane;
	/*End Member Variables*/
	
	
    /*Begin Constructors*/
    /**
     * Constructs the NewMapPanel object.
     * 
     * @param parent Parent window
     */
    public NewMapPanel(RWindow parent){
        super(parent);
        
        this._applyGUI();
    }
    /*End Constructors*/
    
    /*Begin Getter/Setter Methods*/
    public void setInfoData(String title,String information){
    	this.__info_pane.setText(
    		"<html>"+
    	    "<center><strong>"+title+"</strong></center><br />"+
    	    "<br />"+
    	    information+
    	    "</html>"
    	);
    }
    /*End Getter/Setter Methods*/
    
    /*Begin Other Essential Methods*/
    protected void _applyGUI(){
    	final GridBagConstraints constraints = new GridBagConstraints();
    	final SpringLayout       layout = new SpringLayout();
    	
    	JPanel      data_panel = null;
    	JLabel      label = null;
    	JPanel      info_panel = null;
    	JScrollPane scroll_pane = null;
    	ETextArea   text_area = null;
    	
    	
    	this.setBackground(Color.WHITE);
    	
    	this.setBorder(new EmptyBorder(20,20,20,20));
    	this.setLayout(layout);
    	{
    		constraints.fill = GridBagConstraints.HORIZONTAL;
    		constraints.anchor = GridBagConstraints.NORTH;
    		
    		data_panel = new JPanel();
    		data_panel.setOpaque(false);
	    	data_panel.setLayout(new GridBagLayout());
	    	{
	    		label = new JLabel("Map Title");
	    		label.setFont(new Font("Arial",Font.BOLD,12));
	    		
	    		constraints.insets=new Insets(0,0,0,5);
	    		constraints.gridx=0;
	    		constraints.gridy=0;
	    		constraints.weightx=0;
	    		
	    		data_panel.add(label,constraints);
	    	}
	    	{
	    		text_area = new ETextArea();
	    		text_area.setMaximumAllowedCharacters(60);
	    		text_area.setFont(new Font("Arial",Font.PLAIN,12));
	    		text_area.addFocusListener(new __MapTitleFocus());
	    		
	    		scroll_pane = new JScrollPane(text_area);
	    		scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    		scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	    		
	    		constraints.insets=new Insets(0,5,0,0);
	    		constraints.gridx=1;
	    		constraints.gridy=0;
	    		constraints.weightx=1;
	    		
	    		data_panel.add(scroll_pane,constraints);
	    	}
	    	layout.putConstraint(SpringLayout.NORTH,data_panel,0,SpringLayout.NORTH,this);
	    	layout.putConstraint(SpringLayout.SOUTH,data_panel,0,SpringLayout.SOUTH,this);
	    	layout.putConstraint(SpringLayout.WEST,data_panel,0,SpringLayout.WEST,this);
	    	
	    	this.add(data_panel);
    	}
    	{
    		info_panel = new JPanel();
    		info_panel.setOpaque(false);
    		info_panel.setBorder(new LineBorder(Color.GRAY,1));
    		info_panel.setLayout(new GridLayout(1,1));
    		{    			
    			this.__info_pane = new JTextPane();
    			this.__info_pane.setContentType("text/html");
    			this.__info_pane.setFont(new Font("Arial",Font.PLAIN,12));
    			this.__info_pane.setOpaque(false);
    			this.__info_pane.setEditable(false);
    			
    			this.setInfoData("Info","Select an input field at the left to begin.");
    			
    			scroll_pane = new JScrollPane(this.__info_pane);
    			scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    			scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    			
    			info_panel.add(scroll_pane);
    		}
    		layout.putConstraint(SpringLayout.NORTH,info_panel,0,SpringLayout.NORTH,this);
	    	layout.putConstraint(SpringLayout.SOUTH,info_panel,0,SpringLayout.SOUTH,this);
	    	layout.putConstraint(SpringLayout.EAST,info_panel,0,SpringLayout.EAST,this);
	    	layout.putConstraint(SpringLayout.WEST,info_panel,-200,SpringLayout.EAST,this);
    		
    		this.add(info_panel);
    	}
    	
    	//Add another constraint.
    	layout.putConstraint(SpringLayout.EAST,data_panel,-10,SpringLayout.WEST,info_panel);

    	this.setPreferredSize(new Dimension(640,200));
    }
    /*End Other Essential Methods*/
}

