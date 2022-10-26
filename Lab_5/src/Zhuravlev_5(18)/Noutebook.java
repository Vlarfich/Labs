package zadanie_5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import zadanie_5.Noutebook.ArgExcept;

public class Noutebook extends Computer 
implements
	Comparable<Noutebook>, 
	Iterable<String>, 
	Iterator<String> 
{


	
	
	private String Battery;
	private String Case;
	
	
	
	
	///////////////////////////////////////////////////
	// argument exception
	public static class ArgExcept extends Exception {
		private static final long serialVersionUID = 1L;

		ArgExcept( String arg ) {
			super( "Invalid argument: " + arg ); 
		}
	}
	///////////////////////////////////////////////////
	
	
	
	
	public static final String[] names =
	    {
	      "Cost($)",
	      "CPU",
	      "Graphics Card (GPU)",
	      "Hard drive",
	      "Motherboard",
	      "USB Ports",
	      "Battery",
	      "Case"
	    };
	    
	    //format strings for area printout
	    public static String[] formatStr =  {
	    	"%-9s",   // 
	    	"%-9s",   //
	    	"%-22s",   //
	    	"%-17s",   // 
	    	"%-17s",  //
	    	"%-12s",  //
	    	"%-9s",  //
	    	"%-9s",  //
	    };
	    
	    
	    
	    protected boolean validCost(String str) {
	    	return str != null && str.length() > 0;
	    }
	    protected boolean validCPU( String str ) {
	    	return str != null && str.length() > 0;
	    }
	    protected boolean validGPU(String str) {
			return str != null && str.length() > 0;
		}
	    protected boolean validHard(String str) {
			return str != null && str.length() > 0;
		}
	    protected boolean validMother(String str) {
			return str != null && str.length() > 0;
		}
	    protected boolean validUSB(String str) {
			return str != null && str.length() > 0;
		}
	    protected boolean validBattery(String str) {
			return str != null && str.length() > 0;
		}
	    protected boolean validCase(String str) {
			return str != null && str.length() > 0;
		}
	    
	    
	    private String[] areas = null;
	    
	    
	    public int length() {
            return areas.length;
        }
	    
	    public String getArea( int idx ) {
            if ( idx >= length() || idx < 0 ) {
                throw new IndexOutOfBoundsException();
            }
            return areas[idx];
        }
	    
	    private void areaToData (int idx) {
			switch (idx) {
				case 0: cost = Integer.parseInt(areas[idx]); break;
				case 1: CPU = areas[idx]; break;
				case 2: GPU = areas[idx]; break;
				case 3: hard = areas[idx]; break;
				case 4: moth = areas[idx]; break;
				case 5: usb = Integer.parseInt(areas[idx]); break;
				case 6: Battery = areas[idx]; break;
				case 7: Case = areas[idx]; break;
			}
		}
	    
	    
	    
	    
	    
	    public void setArea( int idx, String value ) throws ArgExcept {
            if ( idx >= length() || idx < 0 ) {
                throw new IndexOutOfBoundsException();
            }            
            if (( idx == 0 && validCost( value )== false ) 			||
                ( idx == 1 && validCPU( value )== false ) 			||
                ( idx == 2 && validGPU( value )== false ) 			||
                ( idx == 3 && validHard( value )== false ) 			||
                ( idx == 4 && validMother( value )== false ) 		||
                ( idx == 5 && validUSB( value )== false )			||
                ( idx == 6 && validBattery( value )== false )		||
                ( idx == 7 && validCase( value )== false )	
            		
            		) {
                throw new ArgExcept( value );
            }
            areas[idx] = value;
            
            areaToData(idx);
            
        }
    
	    
	    
	    public String toString() {
	        if ( areas == null ) {
	            return " | | | | | | ";
	        }
	        String res = areas[0];
	        for ( int i = 1; i < areas.length; i++ ) {
	            res += " " + areas[i].toString();
	        }
	        return res;
	    }
	    // constructors:
	    //public Contact() {}
	    private void setup( String[] args ) throws ArgExcept {
	    	if ( args == null ) {
	    		throw new ArgExcept( "null pointer passed for args" );
	    	}
	        if ( args.length < 2 || args.length > names.length ) {
	            throw new ArgExcept( Arrays.toString( args ));
	        }
	        areas = new String[names.length];
	        int i = 0;
	        for (; i < args.length; i++ ) {
	            areas[i] = args[i];
	        }
	        while ( i < names.length ) {
	            areas[i++] = "";
	        }
	    }
	    public Noutebook( String str ) throws ArgExcept {
	    	if ( str == null ) {
	    		throw new ArgExcept( "null pointer passed for str" );
	    	}
	    	// count tokens:
	    	int num = 1, idx = 0, idxFrom = 0;
	        while (( idx = str.indexOf( '|', idxFrom ))!= -1 )
	        {
	            idxFrom = idx + 1;
	            num++;
	        }
	        // allocate array
	        String[] args = new String[num];   	
	        // put all tokens to array
	        idx = 0; idxFrom = 0; num = 0;
	        while (( idx = str.indexOf( '|', idxFrom ))!= -1 )
	        {
	        	args[num++] = str.substring( idxFrom, idx );
	            idxFrom = idx + 1;
	        }
	        args[num] = str.substring( idxFrom );
	        setup( args );
	    }
	    
	    public Noutebook( String...args ) throws ArgExcept {
	    	setup( args );
	    }
	    
	    private static String format( Iterable<String> what ) {
	    	String result = "";
	    	int idx = 0;
	        for( String str : what ) {
	       		result += String.format( formatStr[idx++], str );
	        }    	
	        return result;
	    }
	    
	    public static String format() {
	    	return Noutebook.format( Arrays.asList(Noutebook.names ));
	    }
	    
	    public static String format( Noutebook cn ) {
	    	return Noutebook.format(((Iterable<String>) cn ));
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	
	    public static String getSortByName(int sortBy) {
	    	return Noutebook.names[sortBy];
	    }
	    
	    public static Comparator<Noutebook> getComparator(int sortBy) {
	        if ( sortBy >= names.length || sortBy < 0 ) {
	            throw new IndexOutOfBoundsException();
	        }
	    	return new Comparator<Noutebook> () {
				@Override
				public int compare(Noutebook c0, Noutebook c1) {
					if(sortBy == 0) {
						int a = Integer.parseInt(c0.areas[sortBy]);
						int b = Integer.parseInt(c1.areas[sortBy]);
						if(a < b) 
							return -1;
						else { 
							if(a == b) 
								return 0; 
							else 
								return 1;
							} 
					}
			        return c0.areas[sortBy].compareTo( c1.areas[sortBy]);
				}
	    	};
	    }
	    
	    
	    
	    
	    
	    
	    private int iterator_idx = 0;
	    public void reset() {
	        iterator_idx = 0;
	    }
	
	    public boolean hasNext() {
	    	return iterator_idx >= areas.length ? false: true;
	    }
	    public void remove() {
	    	
	    }

	    public String next() {
	    	if ( iterator_idx < areas.length ) {
	    		return areas[iterator_idx++];
	        	}
	    	reset();
	    	return null;
	    }

	    public Iterator<String> iterator() {
	    	reset();
	    	return this;
	    }


	    public int compareTo(Noutebook o) {
	    	return areas[0].compareTo(o.areas[0]);
	    }
	    

}
