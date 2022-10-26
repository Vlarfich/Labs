package lab_8;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

public class Bus implements Serializable 
{
	private static final long serialVersionUID = 1L;
	String Name, Mark, Number_R, Year, Mileage;
	Integer num;
	//
	static final String _strName = "Name";
	static final String _strMark = "Mark";
	static final String _strNumber = "Number";
	static final String _strNumber_R = "Number of route";
	static final String _strYear = "Year";
	static final String _strMileage = "Mileage";
	//
	Bus(){};
	
	public static Boolean nextRead(Scanner in, PrintStream out)
	{return nextRead(_strName, in, out); }
	
	public static Boolean nextRead(final String field, Scanner in, PrintStream out)
	{
		out.print(field + ": ");
		return in.hasNextLine();
	}
	
	public Bus (String Name, String Mark, String Number, String Number_R, String Year, String Mileage)
	{
		//Bus bus = new Bus();
		this.Name = Name;
		
		//if(!nextRead(_strNumber, in, out)) return null;
		this.num = Integer.parseInt(Number);
		
		//if(!nextRead(_strNumber_R, in, out)) return null;
		this.Number_R = Number_R;
		
		//if(!nextRead(_strMark, in, out)) return null;
		this.Mark = Mark;
		
		//if(!nextRead(_strYear, in, out)) return null;
		this.Year = Year;
		
		//if(!nextRead(_strMileage, in, out)) return null;
		this.Mileage = Mileage;
		
		//return bus;
	}
	
	public static Bus read(Scanner in, PrintStream out)
	{
		Bus bus = new Bus();
		bus.Name = in.nextLine();
		
		if(!nextRead(_strNumber, in, out)) return null;
		String str = in.nextLine();
		bus.num = Integer.parseInt(str);
		//bus.num = in.nextInt();
		
		if(!nextRead(_strNumber_R, in, out)) return null;
		bus.Number_R = in.nextLine();
		
		if(!nextRead(_strMark, in, out)) return null;
		bus.Mark = in.nextLine();
		
		if(!nextRead(_strYear, in, out)) return null;
		bus.Year = in.nextLine();
		
		if(!nextRead(_strMileage, in, out)) return null;
		bus.Mileage = in.nextLine();
		
		return bus;
	}
	
	public String toString()
	{ return new String(Name + " " + Mark + " " + Integer.toString(num) + " " + Number_R + " " + Year + " " + Mileage + " "); }
	
}

