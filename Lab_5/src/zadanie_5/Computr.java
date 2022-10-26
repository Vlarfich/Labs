package zadanie_5;

import java.util.*;
import zadanie_5.Desktop.ArgException;
import zadanie_5.Noutebook.ArgExcept;

public class Computr {
    static void sortAndPrint( Desktop[] pl, int sortBy )  {
        // printout in table
    	System.out.println( "----- Sorted by: " + Desktop.getSortByName(sortBy));
    	Arrays.sort( pl, Desktop.getComparator(sortBy));
    	System.out.printf( Desktop.format() );
    	System.out.println();
    	for( Desktop cnt : pl ) {
    		System.out.println( Desktop.format( cnt ) );
    	}
    }
    static void sortAndPrint( Noutebook[] pl, int sortBy )  {
        // printout in table
    	System.out.println( "----- Sorted by: " + Noutebook.getSortByName(sortBy));
    	Arrays.sort( pl, Noutebook.getComparator(sortBy));
    	System.out.printf( Noutebook.format() );
    	System.out.println();
    	for( Noutebook cnt : pl ) {
    		System.out.println( Noutebook.format( cnt ) );
    	}
    }
	
	public static void main(String[] args) {
		try {
			Desktop[] d = new Desktop[4];
			d[0] = new Desktop("1000|intel|NVidea|256|ASUS|4|144 Hz|Yes");
			d[1] = new Desktop("600|amd|amd|128|Gigabyte|6|60 Hz|No");
			d[2] = new Desktop("850","intel","","512","MSI","3");
			d[3] = new Desktop("0000000|empty||");
			
			sortAndPrint(d, 0);
			sortAndPrint(d, 1);
			sortAndPrint(d, 3);
			sortAndPrint(d, 5);
			
			System.out.println("___________________________________________________________________________________________________\n");
			
			Noutebook[] n = new Noutebook[4];
			n[0] = new Noutebook("1800|intel|amd|512|MSI|4|66 Wt'h|Yes");
			n[1] = new Noutebook("3000|amd|NVidea|1024|ASUS|5|90 Wt'h|No");
			n[2] = new Noutebook("700", "intel", "", "256", "Gigabyte", "3");
			n[3] = new Noutebook("0000000|empty||");
			
			sortAndPrint(n, 0);
			sortAndPrint(n, 1);
			sortAndPrint(n, 4);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
