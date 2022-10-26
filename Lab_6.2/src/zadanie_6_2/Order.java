package zadanie_6_2;

import java.io.Serializable;
public class Order implements Serializable {
	public int cost;
	public Order(int c){
		this.cost = c;
	}
	
	public void setCost(int c) {
		this.cost = c;
	}
	
	public String toString() {
		return new String(AppLocale.getString(AppLocale.cost) + " - " + this.cost + AppLocale.getString(AppLocale.currency));
	}
}
