
/* Zhuravlev 10 gr.
 * 
 * 1. e^x
 * arguments:
 *  [0]   x, (-inf,+inf)
 *  [1]   k, k > 1
 *
*/
package Zadanie_1;

import java.util.*;

public class Zadanie_1 {

	public static void main(String[] args) {
		if ( args.length != 2 ) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }
        double x = Double.parseDouble( args[0] );
        
        int k = Integer.parseInt( args[1] );
        
        if ( k <= 1 ) {
            System.err.println("Invalid argument: " + k );
            System.exit(1);
        }
     
     double Eps = 1 / Math.pow( 10, k + 1 );
     double result = 1;
     double step = x; 
     int n=1;
     while( Math.abs( step ) >= Eps ) {
    	 result += step; 
    	 step=(step*x)/(n+1);
    	 n++;
     }
     String fmt = "%." + args[1] + "f\n";
     System.out.printf( fmt, result );
     System.out.printf( fmt, Math.pow(Math.E,x));
     System.exit(0);
     
     
	}
}
