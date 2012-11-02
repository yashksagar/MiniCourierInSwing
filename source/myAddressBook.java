// Address Book Class

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

public class myAddressBook {
	
	JPanel addressBookPanel;
	JTable addressBook;       // address book
	JScrollPane bookScroller;  // scroller for table
	JTextArea infoText;  // display more info 
	int index;           // to get row index from table
   
	 // store values for table entries
    final String[] columnHeaders = {"Name","Age","E-mail"};
    
    final String[][] values = {{"Yash","22","yashksagar@gatech.edu"},
    		             {"Gaurav","22","gauravkanitkar@gatech.edu"},
    		             {"Salil","22","salilpai@gatech.edu"},
    		             {"Alex","22","alexm@gatech.edu"},
    		             {"Shashank","22","srraghu@gatech.edu"},
    		             {"Arindam","23","adas@gatech.edu"},
    		             {"The Dark Knight","34","brucew@wayne.com"},
    		             {"The Joker","30","joker@whysoserious.com"},
    		             {"George P. Burdell","130","gburdell@gatech.edu"}
    
    };
    
    // Other info about each table entry
    
    final String[] info = {"MS CS (HCI)","MS CS (NW)","MS CS (HCI)","MS CS (HCI)","MS CS (HCI)",
    		         "MS CS(HCI)","Gotham's Reckoning","Wicked sense of humor","The Alumni that got away" 	    		
    };
	
    public myAddressBook(){
		
			
	    addressBookPanel = new JPanel();
	    
	    addressBookPanel.setLayout(new BorderLayout());
	    
	         // align cells to center
	    
	    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
	    tcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	   
	  	    
	    addressBook = new JTable(values,columnHeaders);
	    
	       // for each column, align all cells to properties defined by CellRenderer
	    
	    for(int i=0;i<columnHeaders.length;i++){
	       addressBook.getColumnModel().getColumn(i).setCellRenderer(tcr);
	    }
	   
	       // add mouse listener to table to handle click event
	    
	    addressBook.addMouseListener(new MouseAdapter() {
	    	 public void mouseClicked(MouseEvent e){
	    		 
	    		 index = addressBook.getSelectedRow();
	    		
	    		 getInfo(index);   // get more info for the index
	    	 }
	    	 
		});
	    
	   	   
	    infoText = new JTextArea();
	    infoText.setEditable(false);    
	    
	    bookScroller = new JScrollPane(addressBook);
	  	 
	   	addressBookPanel.add(bookScroller,BorderLayout.NORTH);
	   	addressBookPanel.add(infoText,BorderLayout.CENTER);
	    
	}
	
    // return the entire address book panel
    
	public JPanel getAddressBookPanel(){
		
		return addressBookPanel;
	}

	// get more info for an index
	
	public void getInfo(int rowindex){
		
		int i=0;
		String infotext="";
	
		// append all info to the string 
		
		for(i=0;i<addressBook.getColumnCount();i++){
		
			 infotext = infotext +"\n   "+columnHeaders[i]+" :  "+"  "+values[rowindex][i] +"\n";
			 
		}	
		
		infotext = infotext + "\n   Information:   "+info[rowindex];
			
		infoText.setText(infotext);  // add info to the text area
				
	}
}
