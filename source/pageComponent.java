
public class pageComponent extends testCanvas {

	int id;
	
	public pageComponent(){
		
	setFocusable(true);	
	  drawStuff();
	}
	
   public void addNotify(){
		
		super.addNotify();
		requestFocus();
	}
}
