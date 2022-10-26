package zadanie_6_2;
import java.io.Serializable;
public class Client implements Serializable {
	private String name;
	private Account ac;
	private static int start = 0;
	
	private int ID = start;
	
	
	protected String GetName() {return name;}
	
	protected int getMoney() {
		return ac.getMoney();
	}
	
	protected int getOver() {
		return ac.getOver();
	}
	
	protected int getID() {return ID;}
	
	Client(){name = "";};
	
	public Client(String n, Account a) {
		this.name = n;
		this.ac = a;
		ID = ++start;
	}
	
	public boolean Buy(Order o) {
		if(this.ac.getBlock())
			return false;
		
		if(o.cost > this.ac.getMoney()) {
			/*if(o.cost > this.ac.getMoney() + this.ac.getOver()) {
				return false;
			}
			else {
				this.ac.setOver(this.ac.getOver() - o.cost + this.ac.getMoney());
				this.ac.setMoney(0);
				return true;
			}*/
			
			this.ac.setOver(this.ac.getOver() - o.cost + this.ac.getMoney());
			this.ac.setMoney(0);
			return true;
		}
		else {
			this.ac.setMoney(this.ac.getMoney() - o.cost);
			return true;
		}
		
	}
	
	public void Block() {
		ac.Block();
	}
	
	
	protected void UnBlock() {
		ac.UnBlock();
	}
	
	
	protected boolean Transfer(Client c, int a) {
		if(c.ac.getBlock() || this.ac.getBlock())
			return false;
		if(a > this.ac.getMoney())
			return false;
		
		c.ac.setMoney(c.ac.getMoney() + a);
		this.ac.setMoney(this.ac.getMoney() - a);
		return true;
	}
	
	
	protected void Annulate() {
		this.ac.setMoney(0);
	}
	
	
	
	
	public String toString() {
		return new String(AppLocale.getString(AppLocale.ID) + " " + this.ID + "\n" +
						  AppLocale.getString(AppLocale.name) + ": " + this.name + "\n" +
						  AppLocale.getString(AppLocale.account) + ": " + this.ac.toString());
	}
	
	public String getAcID() {
		return new String(this.ac.GetID() + "");
	}
	
	public int getCardID() {
		return this.ac.getCardID();
	}
	
}
