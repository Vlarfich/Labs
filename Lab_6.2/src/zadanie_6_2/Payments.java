package zadanie_6_2;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;


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
	
	
	
	public final Date creationDate = new Date();
	public String getCreationDate() {
		DateFormat dateFormatter = DateFormat.getDateTimeInstance(
				DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
	    String dateOut = dateFormatter.format(creationDate);
		return dateOut;
	}
	
	///////////////////////////////////////////////////
	// argument exception
	public static class ArgException extends Exception {
		private static final long serialVersionUID = 1L;

		ArgException( String arg ) {
			super( "Invalid argument: " + arg ); 
		}
	}
	///////////////////////////////////////////////////
	
	
	
	public boolean Buy(int id, Order o) throws ArgException{
		if(id > clientbase.length || id < 0)
			throw new IndexOutOfBoundsException();
		
		boolean f = clientbase[id].Buy(o);
		if(!Check(clientbase[id]))
			return f;
		else return false;	
	}
	
	public boolean Transfer(int id, Client c, int m) throws ArgException{
		if(id > clientbase.length || id < 0)
			throw new IndexOutOfBoundsException();
		
		if(m <= 0)
			throw new ArgException("The ammount of money is negative");
		
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
		String str = new String(AppLocale.getString(AppLocale.ID) + ": " + ID + "\n " +
							    AppLocale.getString(AppLocale.adm) + " - " + adm + "\n " +
							    AppLocale.getString(AppLocale.clientbase) + ":\n" + 
							    AppLocale.getString(AppLocale.creation) + " " + getCreationDate() + "\n\n");
		for(Client i: clientbase) {
			str += "* " + i + "\n";
			//str += i + "\n";
		}
		return str;
	}
	
	
	protected int getClID(int index) {
		if(index >= clientbase.length || index < 0)
			return -1;
		return clientbase[index].getID();
	}
	
	protected Client getCl(int index) {
		if(index >= clientbase.length || index < 0)
			return null;
		return clientbase[index];
	}
	
	
}