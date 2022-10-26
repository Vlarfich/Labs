package zadanie_6;
import java.io.Serializable;
public class CC implements Serializable{
	private static int start = 0;
	private int ID = start;
	boolean block = false;
	private int overdraft = 0;
	
	protected int GetID() {return ID;}
	
	protected int getOver() {
		return this.overdraft;
	}
	
	protected void setOver(int o) {
		this.overdraft = o;
	}
	
	public CC(){
		this.ID = ++start;
		this.overdraft = 0;
	}
	
	public CC(int o){
		this.ID = ++start;
		this.overdraft = o;
	}
	
	protected void Block() {
		block = true;
	}
	
	protected void UnBlock() {
		block = false;
		setOver(0);
	}
	
	public String toString() {
		return new String( "ID - " + (this.ID) + "   Overdraft: " + this.overdraft + " $   Block: " + this.block );
	}
	
}
