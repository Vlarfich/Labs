
/*	Zhuravlev 10 gr.
 * 	zadanie 3
 * 	variant 1
 * 	
 * 	args[1] == 2  --->  working with rows 
 * 	else          --->  columns
 * 
 */
package zadanie_3;


import java.util.*;
public class zadanie_3 {

	public static void main(String[] args) {
		
		boolean f=true;
		if ( args.length < 1 ) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }
		else {
			if(args.length!=1) {
				int c=Integer.parseInt(args[1]);
				if(c==2) {
					f=false;
			
				}
			}
		}
	
		
		int k=Integer.parseInt( args[0] );
		
		
		Scanner in=new Scanner(System.in);
		System.out.println("Matrix size n: ");
		int n=in.nextInt();
		
		if(n<2) {
			System.err.println("Invalid argument: "+n);
            System.exit(2);
		}
		
		if(k>n-1) {
			System.err.println("Invalid arguments: k > n");
            System.exit(3);
		}
		
		int mas[][]=new int[n][n];
		
		
		
		Random rnd = new Random();
        rnd.setSeed( System.currentTimeMillis() );
        
        System.out.println("Source values: \n");
        
        for(int i=0;i<n;i++) {
        	
        	for(int j=0;j<n;j++) {
        		int temp = rnd.nextInt();
                mas[i][j] = temp % (n + 1);
        		System.out.print(mas[i][j]+"\t");
        		
        	}
        	System.out.println();
        }
        
        System.out.println("\nk = "+k);
        
        int srt[][] = new int[n][2];
        if(f)
        	System.out.println("\nColumn "+k+":");
        else
        	System.out.println("\nRow "+k+":");
        
        for(int i=0;i<n;i++) {
        	if(f) {
        		srt[i][0]=mas[i][k];
        		srt[i][1]=i;
        	}
        	else {
        		srt[i][0]=mas[k][i];
        		srt[i][1]=i;
        	}
        	
        	System.out.print(srt[i][0]+" ");
        	
        	//System.out.println("\t"+srt[i][0]+" \t "+srt[i][1]);
        }
        
        
        
        for(int i=0;i<n-1;i++) {
        	
        	for(int j=i+1;j<n;j++) {
        		if(srt[j][0]<srt[i][0]) {
        			int tmp=srt[i][0];
        			srt[i][0]=srt[j][0];
        			srt[j][0]=tmp;
        			
        			tmp = srt[i][1];
        			srt[i][1]=srt[j][1];
        			srt[j][1]=tmp;
        			
        			continue;
        		}
        	}
        	
        	
        }
        
        System.out.println("\nSorted:");
        for(int i=0;i<n;i++){
        	System.out.print(srt[i][0]+" ");
        }
        System.out.println();
        {
        	int tmp[][] = new int[n][n];
        	if(f) {
        		for(int i=0;i<n;i++) {
        			tmp[i]=mas[srt[i][1]];	
        		}
        	}
        	else
        		for(int i=0;i<n;i++) {
        			for(int j=0;j<n;j++) {
        				tmp[j][i]=mas[j][srt[i][1]];
        			}
        		}
        	mas = tmp;
        }
        // Source array changes
        
        System.out.println("\n____________________________________________________________________________\n");
        System.out.println("Result:\n");
        for(int i=0;i<n;i++) {
        	
        	for(int j=0;j<n;j++) {
        		System.out.print(mas[i][j]+"\t");
        		//System.out.print(mas[srt[i][1]][j]+"\t");
        		
        	}
        	System.out.println();
        }
        
		
        in.close();
        System.exit(0);
	}
}
