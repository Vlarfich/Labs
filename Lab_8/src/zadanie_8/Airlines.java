package zadanie_8;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.*;
import java.util.*;


public class Airlines implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String Departure;
	static final String P_Departure = "Departure";
	
	String Destination;
	static final String P_Destination = "Destination";
	
	Integer num;
	static final String P_num = "num";
	
	String Type;
	static final String P_Type = "Type";
	
	String time;
	static final String P_time = "time";
	
	String days;
	static final String P_days = "days";
	
	
	
	// validation:
	static Boolean validDep( String str ) {
		return str != null;
	}
	static Boolean validTime( String str ) {
		int i = 0, ndig = 0;
		for( ; i < str.length(); i++ ) {
			char ch = str.charAt(i);
			if( Character.isDigit(ch) ) {
				ndig++;
				continue;
			}
			if( ch != ':' )
				return false;
		}
		return ( ndig == 4 || ndig == 3 );
	}
	
	static Boolean validNum( int n ) {
		return n > 0 && n < 1000000;
	}
	
	
	static Boolean nextRead( final String prompt, Scanner fin, PrintStream out) {
		out.print( prompt );
		out.print( ": " );
		return fin.hasNextLine();
	}
	
	public static Boolean nextRead( Scanner fin, PrintStream out) {
		return nextRead( P_Departure, fin, out);
	}
	
	
	
	public static Airlines read(Scanner fin, PrintStream out) throws IOException {
		Airlines air = new Airlines();
		String str;
		
		air.Departure = fin.nextLine();
		if( Airlines.validDep( air.Departure ) == false ) 
			throw new IOException("");
		if( ! nextRead( P_Destination, fin, out ))	return null;
		air.Destination = fin.nextLine();
		
		if( ! nextRead( P_Type, fin, out )) 	return null;
		air.Type = fin.nextLine();
		
		if( ! nextRead( P_time, fin, out )) 	return null;
		air.time = fin.nextLine();
		if( Airlines.validTime( air.time ) == false ) {
			throw new IOException("Invalid Airline.time value");
		}
		
		if( ! nextRead( P_days, fin, out )) return null;
		air.days = fin.nextLine();
		
		if( ! nextRead( P_num, fin, out )) return null;
		str = fin.nextLine();
		
		if( Airlines.validNumber( str ) == false ) {
			throw new IOException("Invalid Airline.num value");
		}
		air.num = Integer.parseInt(str);
		if( !Airlines.validNum(air.num) ) 
			throw new IOException("Invalid Airline.num value");
		
		return air;
	}
	
	static Boolean validNumber(String str) {
		if(str.length()==0)
			return false;
		int i = 0;
		for (; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isDigit(ch)) {
				continue;
			}
			return false;
		}
		return true;

	}
	
	public Airlines() {
		
	}
	
	public String toString() {
		return //new String(
				"Departure: " + Departure + "\n" +
				"Destination: " + Destination + "\n" +
				"Type: " + Type + "\n" +
				"Time: " + time + "\n" +
				"Days: " + days + "\n" +
				"Num: " + num
				//)
;
	}
	
}
