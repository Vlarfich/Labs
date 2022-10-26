package zadanie_6;
import java.io.Serializable;
public class Account implements Serializable {
	private static int start = 0;
	private int ID = start;
	private CC card;
	private int money;
	
	protected int GetID() {return ID;}
	
	protected int getMoney() { return this.money;}
	protected int getOver() { return this.card.getOver();}
	
	protected void setMoney(int m) { money = m;}
	protected void setOver(int o) { this.card.setOver(o);}
		
	public Account(CC c) {
		this.ID = ++start;
		this.card = c;
		this.money = 0;
	}
	public Account(CC c, int m) {
		this.ID = ++start;
		this.card = c;
		this.money = m;
	}
	
	
	protected void Block() {
		card.Block();
	}
	
	protected void UnBlock() {
		card.UnBlock();
	}
	
	
	protected boolean getBlock() {
		return card.block;
	}
	
	
	public String toString() {
		return new String("ID - " + this.ID + "\n Money: " + this.money + " $\n Card: " + this.card.toString());
	}
	
	protected int getCardID() {
		return this.card.GetID();
	}
	
}
