import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
  
public class testCanvas extends JPanel{


	private Image img;
	Dimension d;
	private int drawchoice=0;
	private Point rect1,rect2;
	private int rectchoice=0;
	private boolean mouseReleased=true;
	private Rectangle tempRect = new Rectangle();
	private Rectangle tempOval = new Rectangle();
	private textObject txtObj;
	private ArrayList<textObject> textObjList = new ArrayList<textObject>();
    
	private ArrayList<Point> ptList = new ArrayList<Point>();	
	private ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> ovalList = new ArrayList<Rectangle>();
	 
    private Color[] inkColor={Color.BLACK,Color.BLUE,Color.RED};	
    private int colorchooser=0;


	public testCanvas(){
		
		setFocusable(true);
		
		// use Legal Pad image for paper-like background
		
		URL myurl = this.getClass().getResource("legal_pad.png");
		Toolkit tk = this.getToolkit();
		img = tk.getImage(myurl);
		
		
		ptList.add(new Point(-1,-1));
		repaint();	
		
	}

  // to set the focus on the canvas 
	
	public void addNotify(){
		
		super.addNotify();
		requestFocus();
	}
	
	public void setChoice(int choice){
		
		drawchoice=choice;
		
	}
	
	
	// handle all mouse/keyboard clicks and handle all display lists
	
	public void drawStuff(){
		
		setFocusable(true);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				mouseReleased=true;
				
				rectList.add(tempRect);
				
				ovalList.add(tempOval);
				
				ptList.add(new Point(-1,-1));  // this point acts as separator between different strokes
				
				rectchoice=0;
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
               // right click to toggle between 3 colors
			    
			    if(arg0.getButton()==MouseEvent.BUTTON3)
			    {
			    	colorchooser++;
			    	if(colorchooser==3){
			    		colorchooser=0;
			    	}
			    }
			    
			
				txtObj = new textObject(arg0.getPoint());
				txtObj.setColor(inkColor[colorchooser]);
				textObjList.add(txtObj);
			
				 
			   // drawchoice=1;
			
		  }
		

		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mouseReleased = false;
				   
				if(drawchoice==2||drawchoice==3){
				drawRectangle(arg0.getPoint());
				}
				
				else{
				ptList.add(arg0.getPoint());
				
			   
			  }
				repaint();
			}
		});
		
	
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			
				
				     // BACKSPACE functionality 
				
				if(e.getKeyChar()== '\u0008'){
				
					String ch = textObjList.get(textObjList.size()-1).getText();
														
					textObjList.get(textObjList.size()-1).text = ch.substring(0, ch.length()-1);
					
				}
				
				   // DELETE key to undo previously drawn shape
				
				else if(e.getKeyChar()=='\u007F'){
					
					
					for(int i=ptList.size()-2;i>=0;i--){
						
						if(ptList.get(i).x==-1 && ptList.get(i).y==-1 ){
		
						 for(int j=i+1;j<ptList.size()-1;j++)	
						 {   
							 ptList.remove(j);
						 }
					
						 break;
						}
					}
					
				
					System.out.println("DELETE!!");
				}
				
				// append character to the text object
				
				else{
					
				txtObj.setText(e.getKeyChar());
				
				}
				
			
			//	System.out.println("\n textObj : "+txtObj.getText());
			   	
				 repaint();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	
	}


	public void drawRectangle(Point rectpoint){
		
			
		if(rectchoice==0){
		 rect1 = rect2=rectpoint;
		 rectchoice++;
		}
		else if(rectchoice==1){
			rect2=rectpoint;
		}
		Rectangle rect = new Rectangle();
    	
	
	    if(rect2.x-rect1.x >=0 && rect2.y-rect1.y>=0){
	  	    	rect.setBounds(rect1.x, rect1.y, (rect2.x-rect1.x), (rect2.y-rect1.y));
	     
	  	    }    
	  	    else if(rect2.x-rect1.x>=0 && rect1.y-rect2.y>=0){
	  	    	rect.setBounds(rect1.x, rect2.y, (rect2.x-rect1.x), (rect1.y-rect2.y));

	  	    	
	  	    }	
	  	    else if(rect1.x-rect2.x>=0 && rect2.y - rect1.y>=0){
	  	    	
	  	    	rect.setBounds(rect2.x, rect1.y, (rect1.x-rect2.x), (rect2.y-rect1.y));
	  	    	 
	  	    }
	  	    else
	  	    	rect.setBounds((rect2.x), (rect2.y),rect1.x-rect2.x, rect1.y-rect2.y);
  	    
	    if(drawchoice==2)
		 tempRect = rect;
	    else if(drawchoice==3)
	     tempOval = rect;	
	   	
		
		repaint();	
	}
	
   protected void paintComponent(Graphics g){
	   
	   super.paintComponents(g);
	   
	    d = getSize();
	   
	
	   Graphics2D g2d = (Graphics2D) g;
	   g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
	   // draw the background legal pad image
	   
	   g2d.drawImage(img, 0, 0, d.width, d.height, Color.WHITE, this);
       
     
       // Free form strokes
	   
	   int index=0;
	   
       for(Point tempPt:ptList){
    	
    	index = ptList.indexOf(tempPt);
    	
    	if(index>0){
    	
    		if(tempPt.x!=-1 && ptList.get(index-1).x!=-1){ 
    			
    			    		    
    	     	g2d.drawLine(ptList.get(index-1).x , ptList.get(index-1).y,tempPt.x, tempPt.y);
      	       
    	   }
         } 
       
         else
        	  g2d.fillOval(tempPt.x, tempPt.y, 2, 2);
       } 	
   

        // draw all Rectangles 
       
   	for(Rectangle r:rectList){
		g2d.draw(r);
	}
	
        // draw all Ovals
     	
   	for(Rectangle o:ovalList){
		g2d.drawOval(o.x,o.y,o.width,o.height);
	   }
	  
       // draw all text strings
   	
   	Font f;
   	
   	f= new Font("SansSerif",Font.BOLD,14);
  	
	 FontMetrics fm = g2d.getFontMetrics(f);
	 
	 g2d.setFont(f);
	 
	 int strheight=0;
	 
	 
    for(textObject to:textObjList){
    	
    	g2d.setColor(to.textColor);
    	
    	strheight = fm.getHeight();
    	
    	// text wrapping and displaying
        
    	
        int curX = to.pt.x;
        int curY = to.pt.y;
        int wordwidth=0;
        
        for(String word: to.getText().split(" ")){
        	
        	wordwidth = fm.stringWidth(word);
        	
          	
        	
        	  if(curX + wordwidth >= d.width){
        		
        		curX = 140;
        		curY = curY + strheight;
        		
        	}
        	
        	  
        	  g2d.drawString(word, curX, curY);
        		//g2d.drawString("|",curX+wordwidth,curY); 
        
        	
        	curX+=wordwidth+8;
        } 
        
                
		
    }
   	
    
   g2d.setColor(Color.BLACK); 
    
    
    // draw according to user choice
    
   	// draw Text 
   
 /*  else*/ if(drawchoice==1){
	   
	
   }
 
    // draw Rectangles
   
    else if(drawchoice==2){
	     
    	
    	if(!mouseReleased)
     	  g2d.draw(tempRect);
    }
   
     // draw Ovals
   
    else if(drawchoice==3){
              
    	g2d.drawOval(rect1.x, rect1.y, (rect2.x-rect1.x), (rect2.y-rect1.y));
    
    	
	
	   if(!mouseReleased)
 	      g2d.drawOval(tempOval.x,tempOval.y,tempOval.width,tempOval.height);
    
    }
   

        
 
  
   
   }



 
}
