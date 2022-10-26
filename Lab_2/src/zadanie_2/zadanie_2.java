package zadanie_2;
import java.util.*;

// Zhuravlev  10 gr.
// lab 2  ---  18 var.
// example string :  apple door housed trees retirementa environment shift fishing

public class zadanie_2 {
	
	
	public static void main(String[] args) {
		Scanner in = new Scanner( System.in );
        while(in.hasNextLine()) {
            String s = in.nextLine();
            if(s=="")
            	continue;
            StringTokenizer st = 
                	new StringTokenizer( s );
            if(st.countTokens()==0)
            	continue;
            
            
            if(st.countTokens()==1) {
            	System.out.println("\n Result:  " + st.nextToken() + 
            			"\n__________________________________________________________________________");
            	continue;
            }
            
            
            //ArrayList<String> al=new ArrayList<String>();
            LinkedList<String> al = new LinkedList<String>(); 
            
            while ( st.hasMoreTokens()) {
            	String k = st.nextToken();
            	al.add(k);
            }
            System.out.println();
            System.out.println(al);
            
            
            for(int i=0;i<al.size();i++) {
            	
            	String a=(String) al.get(i);
            	//System.out.println(a);
            	
            	for(int j=i+1;j<al.size();j++) {
            		String b=(String) al.get(j);
            		//System.out.println(b);
            		if(a.charAt(a.length()-1)==b.charAt(0)) {
            			
            			String tmp=(String) al.get(i+1);
            			al.remove(i+1);
            			al.add(i+1, b);
            			
            			al.remove(j);
            			al.add(j, tmp);
            			
            		}
            	}     	
            }
            
               	int pos=0;
            	
            	for(int i=1;i<al.size();i++) {
            		String a1=(String) al.get(i-1);
            		String a2=(String) al.get(i);
            		
            		if(a1.charAt(a1.length()-1)!=a2.charAt(0)) {
            			pos =i;
            			break;
            		}
            	}
            	
            	String e=(String) al.get(0);
            	//System.out.println(al);
            	
            	
            	for(int i=pos;i<al.size();i++) {
            		String b=(String) al.get(i);
            		
            		if(e.charAt(0)==b.charAt(b.length()-1)) {
            			al.remove(i);
            			al.add(0, b);
            			e=b;
            			i--;
            			pos++;
            		}
            		
            	}
            		String fin=(String) al.get(0) + " ";
            		for(int i=1;i<al.size();i++) {
            			String a1=(String) al.get(i-1);
            			String a2=(String) al.get(i);
            			if(a2.charAt(0)==a1.charAt(a1.length()-1)) {
            				fin+=a2+" ";
            			}
            			else break;
            		}
            		System.out.println("\n Result:  " + fin + 
            				"\n__________________________________________________________________________");
            	
            	
            	

	
	
        }
        
        in.close();
	}
}
