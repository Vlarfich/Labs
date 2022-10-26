package zadanie_6;
import java.io.Serializable;
public class Admin implements Serializable{
	private String name;
	
	
	Admin(String s){
		name = s;
	}
	
	protected boolean Block(Client c) {
		if(c.getOver() < 0) {
			c.Block();
			return true;
		}
		else return false;
	}
	
	
	protected void UnBlock(Client c) {
		c.UnBlock();
	}
	
	
	public String toString() {
		return name;
	}
	
}