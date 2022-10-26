package zadanie_6;
import java.io.Serializable;
public class Payments implements Serializable {
	private static int start = -1;
	private int ID = start;
	protected Admin adm;
	
	protected Client [] clientbase = null;
	
	Payments(Admin a, Client[] base){
		ID = ++start;
		adm = a;
		clientbase = base;
	}
	
	protected int getID() {return ID;}
	protected void setID(int id) {
		ID = id;
	}
	
	public boolean Buy(int id, Order o) {
		boolean f = clientbase[id].Buy(o);
		if(!Check(clientbase[id]))
			return f;
		else return false;	
	}
	
	public boolean Transfer(int id, Client c, int m) {
		/*for(Client i: clientbase) {
			if(i.getID() == id) {
				return i.Transfer(c, m);
			}
		}*/
		
		return clientbase[id].Transfer(c, m);
		//return false;
	}
	
	private boolean Check(Client c) {
		if(c.getOver() < 0) {
			adm.Block(c);
			return true;
		}
		return false;
	}
	
	protected void UnBlok(Client c) {
		adm.UnBlock(c);
	}
	
	
	public String toString() {
		String str = new String("ID: " + ID + "\n Admin - " + adm + "\n Client base:\n\n");
		for(Client i: clientbase) {
			str += i + "\n";
		}
		return str;
	}
	
	
	protected int getClID(int index) {
		if(index >= clientbase.length)
			return -1;
		return clientbase[index].getID();
	}
	
	protected Client getCl(int index) {
		if(index >= clientbase.length)
			return null;
		return clientbase[index];
	}
	
	
}
