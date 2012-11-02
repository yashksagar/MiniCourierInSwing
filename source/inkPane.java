// Ink Pane Class - right side of Split Pane

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class inkPane implements ActionListener{

	
	final JPanel buttonBar,toolBar,radioBar,mainPanel;
    JButton newButton,deleteButton,forwardButton,backButton;
    JRadioButton freeformRadio,rectangleRadio,ovalRadio;
    
    pageComponent inkPanel;
    int curpg=0;
    int index=0;
    
    ArrayList<pageComponent> pageList;
    
	public inkPane(){
		
		
	//	inkPanel = new pageComponent();  // ink drawable area
		
		mainPanel = new JPanel();   // holds the ink pane + buttons
		mainPanel.setLayout(new BorderLayout());
		
		buttonBar = new JPanel();    // add JButtons to a panel
		FlowLayout fl1 = new FlowLayout();
		fl1.setHgap(40);             // set Horizontal gap of 40px between buttons
		buttonBar.setLayout(fl1);
		
		radioBar = new JPanel();      // add Radio buttons to separate panel
		FlowLayout fl2 = new FlowLayout();
		fl2.setHgap(70);          // gap of 70px between radiobuttons
		radioBar.setLayout(fl2);
		radioBar.setFocusable(false);
		
		toolBar = new JPanel();   // holds both the panels in a grid layout
	
		toolBar.setFocusable(false);
		GridLayout gl = new GridLayout(2,1); //  a row for Jbuttons and RadioButtons each
		
		toolBar.setLayout(gl);
		gl.setVgap(10);         // set vertical gap between the buttons
				
		newButton = new JButton(" NEW ");
		deleteButton = new JButton(" DELETE ");
		forwardButton = new JButton(" FORWARD ");
		backButton = new JButton(" BACK ");
		
		newButton.setFocusable(false);
		deleteButton.setFocusable(false);
		deleteButton.setEnabled(false);
		
		forwardButton.setFocusable(false);
		backButton.setEnabled(false);
		backButton.setFocusable(false);
		
		newButton.addActionListener(this);
		deleteButton.addActionListener(this);
		forwardButton.addActionListener(this);
		backButton.addActionListener(this);
		
		buttonBar.add(newButton);
		buttonBar.add(deleteButton);
		buttonBar.add(backButton);
		buttonBar.add(forwardButton);
		
		freeformRadio = new JRadioButton("Free Form Ink");
		rectangleRadio = new JRadioButton("Rectangle");
		ovalRadio = new JRadioButton("Oval");
		
		
		freeformRadio.setFocusable(false);
		rectangleRadio.setFocusable(false);
		ovalRadio.setFocusable(false);
		
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(freeformRadio);
		radioGroup.add(rectangleRadio);
		radioGroup.add(ovalRadio);
	
		freeformRadio.addActionListener(this);
		rectangleRadio.addActionListener(this);
		ovalRadio.addActionListener(this);
		
		radioBar.add(freeformRadio);
		radioBar.add(rectangleRadio);
		radioBar.add(ovalRadio);

		toolBar.add(buttonBar);
		toolBar.add(radioBar);
		
		pageList = new ArrayList<pageComponent>();
		
		newpage();
		
		inkPanel.setLayout(new BorderLayout());
		
		// add both panels to main panel
		
		mainPanel.add(toolBar,BorderLayout.SOUTH);
		mainPanel.add(inkPanel,BorderLayout.CENTER);
						
	}
	
	// return the entire panel
	
	public JPanel getinkPanel(){
		
		return mainPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==newButton){
			newpage();
			backButton.setEnabled(true);
		}	
		else if(e.getSource()==backButton){
			
			prevpage();
			forwardButton.setEnabled(true);
		}
		else if(e.getSource()==forwardButton){
			
			nextpage();
			backButton.setEnabled(true);
		}
		else if(e.getSource() == deleteButton){
			
			deletepage();
		
		}
		else if(e.getSource()==freeformRadio){
		
			 for(pageComponent pg:pageList){
		    	 
		    	 if(pg.id==curpg){
		    		 
		    		pg.setChoice(0); 
		    	 }
			 }	 
			
		}
		else if(e.getSource()==rectangleRadio){
			
			for(pageComponent pg:pageList){
		    	 
		    	 if(pg.id==curpg){
		    		 
		    		pg.setChoice(2); 
		    	 }
			 }
		     
		}
		else if(e.getSource()==ovalRadio){
			
			for(pageComponent pg:pageList){
		    	 
		    	 if(pg.id==curpg){
		    		 
		    		pg.setChoice(3); 
		    	 }
			 }
		    
		}
	}

		

		public void newpage(){
		
		forwardButton.setEnabled(false);
		
				
		inkPanel = new pageComponent();
		
		for(pageComponent pg:pageList){
			
			pg.setVisible(false);
			pg.setEnabled(false);
		}
		
		pageList.add(inkPanel);
		inkPanel.setEnabled(true);
		inkPanel.setVisible(true);
	    inkPanel.setFocusable(true);
	   freeformRadio.setSelected(true);
		
	    inkPanel.requestFocus();
	    
	    
	 	mainPanel.add(inkPanel,BorderLayout.CENTER);
		
		index = pageList.indexOf(inkPanel);
		curpg = index+1;
		
		inkPanel.id = curpg;
	 
		System.out.println("\n After new page, Current page:  "+curpg);
		if(pageList.size()>1)
			 deleteButton.setEnabled(true);
	}

	

	public void prevpage() {
		// TODO Auto-generated method stub
		 if(pageList.size()==1)
			 deleteButton.setEnabled(false);
	  
	  if(this.index>0){	
		
		
		for(pageComponent pg:pageList){
			
			if(pg.isVisible() && pg.isEnabled()){
				pg.setVisible(false);
			    pg.setEnabled(false);
			}	
		}
		
		
		
		pageList.get(this.index-1).setVisible(true);
		pageList.get(this.index-1).setEnabled(true);
		pageList.get(this.index-1).requestFocus();
		
		setChoice(pageList.get(this.index-1));
		//freeformRadio.setSelected(true);
		
		this.index--;
		curpg = pageList.get(this.index).id;
	  }
	 if(this.index<1 || pageList.size()==1){
		  backButton.setEnabled(false);
	  }
	
	 
	  System.out.println("\n After prevpage, current page: "+curpg);
	}
	
	

	public void nextpage() {
		// TODO Auto-generated method stub
		 if(pageList.size()==1)
			 deleteButton.setEnabled(false);
		
		 if(this.index<pageList.size()-1){	
				
				
				for(pageComponent pg:pageList){
					
					if(pg.isVisible() && pg.isEnabled()){
						pg.setVisible(false);
					    pg.setEnabled(false);
					}	
				}
				
				pageList.get(this.index+1).setVisible(true);
				pageList.get(this.index+1).setEnabled(true);
				pageList.get(this.index+1).requestFocus();
				setChoice(pageList.get(this.index+1));
				
				this.index++;
				curpg = pageList.get(this.index).id;
		}
		 if(this.index>=pageList.size()-1){
			 forwardButton.setEnabled(false);
		}
		
		 System.out.println("\n After next page, current page:  "+curpg);
	}

	public void deletepage() {
		// TODO Auto-generated method stub
			
	   if(pageList.size()>1){
	
		 for(pageComponent pg:pageList){
	    	 
	    	 if(pg.id==curpg){
	    		 pg.setVisible(false);
	    		 pg.setEnabled(false);
	    		 this.index = pageList.indexOf(pg);
	    		 pageList.remove(pg);
	    		 	    		    		 
	    		 if(this.index==0)
	    			 nextpage();
	    		 else
	    		     prevpage();
	    	 }
	     }
	   }	
	}
	
	private void setChoice(pageComponent pc) {
		// TODO Auto-generated method stub
		
		if(freeformRadio.isSelected())
			pc.setChoice(0);
		else if(rectangleRadio.isSelected())
			pc.setChoice(2);
		else if(ovalRadio.isSelected())
			pc.setChoice(3);
		
	}

}
