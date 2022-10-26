package z_4;

import java.util.*;
import java.util.Random;
import java.util.Date;

public class test {

    public static void main(String[] args) {
    	
        vect a = new vect(4);
        System.out.print("a = ");
        a.println();
        
        vect b = new vect(2);
        System.out.print("b = ");
        b.println();
        
        System.out.print("a + b = ");
        (a.sum(b)).println();
        
        System.out.print("[ a, b ] = ");
        (a.vectProduct(b)).println();
        
        System.out.print("( a, b ) = ");
        System.out.println(a.scalarProduct(b));
        
        System.out.println("a == b?");
        System.out.println(a.Equals(b) );
        b = a;
        System.out.println("a = b\nb == a?");
        System.out.println(b.Equals(a));
        
        System.out.print("b * 1.5 = ");
        b = b.mult( 1.5 );
        b.println();
        System.out.println("a == b?");
        System.out.println(a.Equals(b) + "  " + b.Equals(a));
        System.out.print("b - b = ");
        (b.sum(b.neg())).print();
        
        System.out.println("\n_______________________________________________\n");
        
        int m = 5;
        
        
        System.out.println("m = " + m);
        vect[] mas = new vect[m];
        
        Random rand = new Random((new Date()).getTime());
        for( int i = 0; i < m; i++ ) {
        	mas[i] = new vect(rand.nextInt(5) + 22);
        	System.out.println("mas[" + i + "] = " + mas[i].toString());
        }
        
        System.out.println("Тройки комплонарных векторов: ");
        boolean f =false;
        for(int i = 0; i < m - 2; i++) {
        	for(int j = i + 1; j < m - 1; j++) {
        		for(int e = j + 1; e < m; e++) {
        			if( mas[i].complonar( mas[j], mas[e]) ) {
        				System.out.println("mas[" + i + "]  " + "mas[" + j + "]  " + "mas[" + e + "]  ");
        				f = true;
        			}
        		}
        	}
        }
        
        if( !f ) {
        	System.out.println("  нету уомплонарных векторов");
        }
        
    }
    
}
