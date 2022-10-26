package zadanie_6_2;
import java.util.*;
import java.io.*;
import java.lang.*;

public class Test {
	
	static Locale createLocale( String[] args )	{
		if ( args.length == 2 ) {
			return new Locale( args[0], args[1] );
		} else if( args.length == 4 ) {
			return new Locale( args[2], args[3] );
		}
		return null;
	}
	
	static void setupConsole(String[] args) {
		if ( args.length >= 2 ) {
			if ( args[0].compareTo("-encoding")== 0 ) {
				try {
					System.setOut( new PrintStream( System.out, true, args[1] ));
				} catch ( UnsupportedEncodingException ex ) {
					System.err.println( "Unsupported encoding: " + args[1] );
					System.exit(1);
				}				
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		
		try {
			
			setupConsole( args );
			Locale loc = createLocale( args );
			if ( loc == null ) {
				System.err.println( 
						"Invalid argument(s)\n" +
				        "Syntax: [-encoding ENCODING_ID] language country\n" +
						"Example: -encoding Cp855 be BY" );
				System.exit(1);
			}
			AppLocale.set( loc );
			
			
			Client base[] = new Client[3];
			base[0] = new Client("Gorge", new Account(new CC(500), 1000));
			base[1] = new Client("James", new Account(new CC(), 202));
			base[2] = new Client("Gregory", new Account(new CC(750), 1500));
			//Syso(base);
			
			Admin adm = new Admin("Jonny");
			//System.out.println("\nAdmin - " + adm);
			Payments pay = new Payments(adm, base);	
			
			try {
				Connector con = new Connector("Payments.dat");	
				con.write(pay);
				Payments P = con.read();
				System.out.println(P);
			}	
			catch ( Exception e ) {
				System.err.println(e);
			}
			
			System.out.println("______________________________________________________________\n");
	
			
			
			System.out.println();
			Order o = new Order(200);
			Order O = new Order(2000);
			
			if(pay.Buy(2, o)) 
				System.out.println(AppLocale.getString(AppLocale.client) + " " + base[2].GetName() + " " + AppLocale.getString(AppLocale.sucbuy) + " o: " + o);
			
			System.out.println(base[2]);
			if(pay.Transfer(1, pay.getCl(0), 22))
				System.out.println(AppLocale.getString(AppLocale.client) + " " + base[1].GetName() + " " +
								   AppLocale.getString(AppLocale.transf) + " 22" + AppLocale.getString(AppLocale.currency) + " " +
								   AppLocale.getString(AppLocale.clientu) + " " + base[0].GetName());
			if(pay.Buy(0, O))
				System.out.println("\n " + AppLocale.getString(AppLocale.client) + base[0].GetName() + AppLocale.getString(AppLocale.sucbuy) + " O: " + O);
			
			System.out.println(base[0]);
			pay.UnBlok(pay.getCl(0));
			


			if(pay.Buy(2, O))
				System.out.println(AppLocale.getString(AppLocale.client) + " " + base[2].GetName() + 
								   " " + AppLocale.getString(AppLocale.sucbuy) + " O: " + O);
			System.out.println(base[2]);
			System.out.println("\n\n" + AppLocale.getString(AppLocale.banksysres) + ": \n\n" + pay);
			System.out.println(pay);
		
		}
		catch ( Exception e ) {
			System.err.println(e);
		}
	}

}