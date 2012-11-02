// Main Courier class

/* Author:   Yash Kshirsagar  
 *           MS CS (HCI)
             Georgia Institute of Technology
  Date:     4th Oct 2012
*/


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class Courier {
	
	final JFrame courierFrame;
	JPanel courierPanel = new JPanel();    // container for entire application
    JPanel browserPanel,statusPanel;
	JLabel statusLabel1 = new JLabel("Status: ");
	final JLabel statusLabel2 = new JLabel("");     // actual status of system
    JTabbedPane courierTabbed = new JTabbedPane();
	JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	myAddressBook addbook = new myAddressBook();
	inkPane inkPanel;
	JScrollPane inkScroll;
	
	public static void main(String[] args){
		
		// Spawns a new thread for handling Courier events
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				new Courier();
					
			}
		});
	
	}

	
	
	public Courier(){
		
	    courierFrame = new JFrame("My Courier");
		courierFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		courierFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // set initial window size to max  	
		
		myEditorPane htmlEditor =  new myEditorPane("http://www.cc.gatech.edu/classes/AY2013/cs4470_fall/hw2.html");		
				
		JPanel browserPanel = htmlEditor.getHtmlEditor();
		browserPanel.setFocusable(false);
		
		JPanel addbookPanel = addbook.getAddressBookPanel();		
		addbookPanel.setFocusable(false);
		
		courierTabbed.addTab("WEB BROWSER", browserPanel);
		courierTabbed.addTab("ADDRESS BOOK", addbookPanel);
		
		courierTabbed.setMinimumSize(new Dimension(650,650));		
		courierTabbed.setFocusable(false);
		
		splitH.resetToPreferredSizes();
	    
		splitH.setLeftComponent(courierTabbed);
		
		inkPanel = new inkPane();		
			
	    inkScroll = new JScrollPane(inkPanel.getinkPanel());
		
		splitH.setRightComponent(inkScroll); 
				
		splitH.setDividerSize(8);
        splitH.setFocusable(false);
       
        
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setFocusable(false);
		
	    statusPanel.add(statusLabel1,BorderLayout.WEST);
	    statusPanel.add(statusLabel2,BorderLayout.CENTER);
	    
		courierPanel.setLayout(new BorderLayout());
		courierPanel.add(splitH,BorderLayout.CENTER);
		courierPanel.add(statusPanel,BorderLayout.SOUTH);
		
		
		courierFrame.add(courierPanel);
	    		
	
		// Set a component listener to restrict resizing frame beyond minimum size
		
		courierFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent event) {
			    courierFrame.setSize(
			      Math.max(400, courierFrame.getWidth()),
			      Math.max(400, courierFrame.getHeight()));
			      
			      if(courierFrame.getWidth()==400 || courierFrame.getHeight()==400){  // add status info
			         statusLabel2.setText(" Window resized to minimum size.");
			         splitH.setDividerLocation((courierFrame.getWidth())/2);
			      }   
			      else
			    	 statusLabel2.setText("");  
			  }
		});
		
				
		courierFrame.setVisible(true);
	    
	}
}
