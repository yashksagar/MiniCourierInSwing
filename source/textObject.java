import java.awt.Color;
import java.awt.Point;
import java.util.List;


public class textObject {

	Point pt;
	String text;
	Color textColor;
	
	public textObject(Point pt){
		
	    this.pt = pt;
		this.text = "";
		
	}

    public void setText(char txt){
    	
    	this.text += txt;
    }
    public String getText(){
    	
    	return this.text;
    }
    
    public void setColor(Color cl){
    	
    	this.textColor = cl;
    }
}
