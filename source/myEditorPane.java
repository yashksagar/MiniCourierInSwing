//  HTML Editor Class

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


 // Editor pane for displaying html content

@SuppressWarnings("serial")
public class myEditorPane extends JFrame implements HyperlinkListener {

	/**
	 * 
	 */
		
        /**
	 * 
	 */
	
		String Url;
		JLabel urlLabel = new JLabel("URL:");
        JPanel addressBar = new JPanel();
		JTextField urlText = new JTextField(20);
		JEditorPane htmlPage;
		JButton buttonGo = new JButton("Go");
		JButton buttonBack = new JButton(" < ");
		JButton buttonForward = new JButton(" > ");
		JPanel panelLeft = new JPanel();
		JScrollPane scroller;
		
		 // Store page list of previous and next pages
		
		ArrayList<String> pageList = new ArrayList<String>();  
		
			
		public myEditorPane(String myURL){
			
				   
			// listen to keyboard event such as ENTER key hit on address bar
			
           	urlText.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					go(e);
					
				}
			});    
        
             	// GO button listener
           	
           	buttonGo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					go(e);
					
				}
			});    
           	
           	buttonGo.setFocusable(false);
           	
           	//urlText.setFocusable(false);
           	
        	urlText.setText(myURL);  // set address bar text to current page's URL
        	
        	buttonBack.setEnabled(false);  // initially disabled
        	
        	buttonBack.setToolTipText("Back");
        	buttonBack.setFocusable(false);
        	// listener for Back button click
        	
        	buttonBack.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					 goBack();
					
				}
			});
        	
        	buttonForward.setEnabled(false);
        	buttonForward.setFocusable(false);
        	buttonForward.setToolTipText("Forward");
        	
        	
        	buttonForward.addActionListener(new ActionListener() {
				
        	
				@Override
				public void actionPerformed(ActionEvent e) {
					goForward();
					
				}
			});
        	
        	// addressBar holds buttons and URL address bar
        	
        	addressBar.setLayout(new FlowLayout());
        	addressBar.add(buttonBack);
        	addressBar.add(buttonForward);
        	addressBar.add(urlLabel);
        	addressBar.add(urlText);
        	addressBar.add(buttonGo);
        	
        	
        	// HTML editor pane
        	
        	try {
				htmlPage = new JEditorPane(myURL);
				pageList.add(myURL);
	            htmlPage.addHyperlinkListener(this);
	            htmlPage.setEditable(false);
	            panelLeft.add(htmlPage, BorderLayout.CENTER);
 
	        // add to a scroller pane 
	            htmlPage.setFocusable(false);
	            
	            scroller = new JScrollPane(htmlPage);
	           scroller.setFocusable(false);

			} catch(Exception ce){
        		
				JOptionPane.showMessageDialog(this, "Oops! Internet not working. Please connect to internet" +
						"and try again.... Exiting program...");
				System.exit(0);
				
				try {
					htmlPage = new JEditorPane("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
        	}/*catch (IOException e) {
				
				e.printStackTrace();
			}
        	*/
        	// panelLeft holds the addressBar and scrollerpane
        	
        	panelLeft.setLayout(new BorderLayout());
        	panelLeft.add(addressBar,BorderLayout.NORTH);
        	panelLeft.add(scroller,BorderLayout.CENTER);
            
        	panelLeft.setFocusable(false);
        }

		
		//  Return the panel containing the entire web browser
		
		public JPanel getHtmlEditor() {
			
			return panelLeft;
		}
		
		public void goBack(){
			
			
			URL currentUrl = htmlPage.getPage();
			
			// get index of the current page
			
			final int index = pageList.indexOf(currentUrl.toString());
			
			if(index > 0 ){
					
					if(index==1){   // disable Back button when 1st page is about to reach
						buttonBack.setEnabled(false);
					}
					
					buttonForward.setEnabled(true);
					
					// to go to previous page, move index back by 1
					
					try {
						  // call to method that displays new page
						
							showPage(new URL((String)pageList.get(index-1)));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(this, "Oops! There seems to be some problem.");
						e.printStackTrace();
					}
					
							
								
						
			}

				
		}

		
	public void goForward(){
			
			
			URL currentUrl = htmlPage.getPage();
			
			int index = pageList.indexOf(currentUrl.toString());
			
			if(index <  (pageList.size()-1)){
		
				try {
				
				    if(index == pageList.size()-2){
				    	buttonForward.setEnabled(false);
				    }
				    
					buttonBack.setEnabled(true);
					showPage(new URL((String)pageList.get(index+1)));
					
				} catch (MalformedURLException e) {
			
					e.printStackTrace();
				}
			}
			else{
				buttonForward.setEnabled(false);
			}
		
}	
		
	
		@Override
		public void hyperlinkUpdate(final HyperlinkEvent he) {
	
			// handle hyperlink events
			
			if(he.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
				
				
					 buttonBack.setEnabled(true);
					 buttonForward.setEnabled(false);	
					 
					try{
						htmlPage.setPage(he.getURL());
					} catch (IOException e) {
					
						JOptionPane.showMessageDialog(this, "Oops! There seems to be some " +
									"problem with the page.");

						e.printStackTrace();
				    }
					   
					// add page to the page list
					
				    pageList.add(htmlPage.getPage().toString());
					
	                urlText.setText(he.getURL().toExternalForm());
				
			}
			
		}

        // listener for Go button
		
		public void go(ActionEvent e2){
			
		//handle events from URL address bar or GO button
			
			if(e2.getSource() == urlText || e2.getSource() == buttonGo){
				
				final URL url;
			
				try {
					url = new URL(urlText.getText());
					
					pageList.add(url.toString());
					
					buttonBack.setEnabled(true);
					
					
			// separate thread to handle web requests
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
						buttonForward.setEnabled(false);	
						showPage(url);	
							
								
						}
					});
					
					
				} catch (MalformedURLException e) {
					JOptionPane.showMessageDialog(this, "Incorrect URL format, please attach \"http://\"");
					e.printStackTrace();
				}
			
			}	
		}

   // handle page display for Go,Back and Forward button events
		
   public void showPage(URL url){
	   
	   try {
		   
		    htmlPage.setPage(url);
			
			urlText.setText(url.toString());
			
		} catch (MalformedURLException e) {
			
			JOptionPane.showMessageDialog(this, "Incorrect URL format, please attach \"http://\"");
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

   }
   
}


