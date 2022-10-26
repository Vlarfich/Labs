package zadanie_6_2;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
public class Account implements Serializable {
	private static int start = 0;
	private int ID = start;
	private CC card;
	private int money;
	
	
	public final Date creationDate = new Date();
	public String getCreationDate() {
		DateFormat dateFormatter = DateFormat.getDateTimeInstance(
				DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
	    String dateOut = dateFormatter.format(creationDate);
		return dateOut;
	}
	
	
	
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
		return new String(AppLocale.getString(AppLocale.ID) + " - " + this.ID + "\n " +
						  AppLocale.getString(AppLocale.money) + ": " + this.money + 
						  AppLocale.getString(AppLocale.currency) + "\n " +
						  AppLocale.getString(AppLocale.card) + ": " + this.card.toString() + "\n " +
						  AppLocale.getString(AppLocale.creation) + " " + getCreationDate());
	}
	
	protected int getCardID() {
		return this.card.GetID();
	}
	
}