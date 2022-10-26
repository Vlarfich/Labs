package zadanie_8;

import java.io.*;
import java.util.*;


public class Test {

	public static void main(String[] args) {
		try {
			if( args.length >= 1) {
				if( args[0].equals("-?") || args[0].equals("-h")) {
					System.out.println(
							"Syntax:\n" +
							"\t-a  [file [encoding]] - append data\n" +
							"\t-az [file [encoding]] - append data, compress every record\n" +
							"\t-d                    - clear all data\n" +
							"\t-dk  {dep|des|n} key  - clear data by key\n" +
							"\t-p                    - print data unsorted\n" +
							"\t-ps  {dep|des|n}      - print data sorted by Depart/Destin/num\n" +
							"\t-psr {dep|des|n}      - print data reverse sorted by Depart/Destin/num\n" +
							"\t-f   {dep|des|n} key  - find record by key\n"+
							"\t-fr  {dep|des|n} key  - find records > key\n"+
							"\t-fl  {dep|des|n} key  - find records < key\n"+
							"\t-?, -h                - command line syntax\n"
								);
					}
					else if ( args[0].equals( "-a" )) {
		                // Append file with new object from System.in
	                    // -a [file [encoding]]
						appendFile( args, false );
					}
					else if( args[0].equals("-az")) {
						// Append file with compressed new object from System.in
	                    // -az [file [encoding]]
						appendFile( args, true );
					}
					else if( args[0].equals("-p")) {
						// Prints data file
						print_file();
					}
					else if( args[0].equals("-ps")) {
						// Prints data file sorted by key
						if ( printFile( args, false ) == false ) {
							System.exit(1);
						}
					}
					else if ( args[0].equals("-psr")) {
						// Prints data file reverse-sorted by key
						if( printFile( args, true ) == false ) {
							System.exit(1);
						}
					}
					else if ( args[0].equals("-d")) {
						// delete files
						if( args.length != 1) {
							System.err.println("Invalid number of arguments");
							System.exit(1);
						}
						delete_file();
					}
					else if ( args[0].equals("-dk")) {
						// delete records by key
						if ( deleteFile( args ) == false) {
							System.exit(1);
						}
					}
					else if (args[0].equals("-f")) {
						// find record(s) by key
						if( findByKey( args ) == false) {
							System.exit(1);
						}
					}
	                else if ( args[0].equals( "-fr" )) {
	                    // Find record(s) by key large then key 
	                    if ( findByKey( args, true )== false ) {
	                        System.exit(1);
	                    }
	                }
					else if (args[0].equals("-fl")) {
						// Find record(s) by key less then key
	                    if ( findByKey( args, false )== false ) {
	                        System.exit(1);
	                    }
					}
					else {
						System.err.println("Option is not realised: " + args[0]);
						System.exit(1);
					}
			}
			else {
				System.err.println("Airlines: Nothing to do! Enter -? for options");
			}
			
		}
		catch ( Exception e ) {
			System.err.println("Run/time error: " + e);
			System.exit(1);
		}
		System.err.println("Airlines finished...");
		System.exit(0);
		
	}
	
	static final String filename = "Airlines.dat";
	static final String filenameBak = "Airlines.~dat";
    static final String idxname     = "Airlines.idx";
    static final String idxnameBak  = "Airlines.~idx";
	private static PrintStream airOut = System.out;
		
	   private static String encoding = "Cp866";
	
	   
	static Airlines read_air( Scanner fin ) throws IOException {
		if( Airlines.nextRead( fin, airOut )) {
			return Airlines.read( fin, airOut );
		}
		return null;
	}
	
	
	private static void deleteBackup() {
		new File( filenameBak ).delete();
		new File( idxnameBak ).delete();
	}
	
	static void delete_file() {
		deleteBackup();
		File f = new File( filename );
		f.delete();
		new File( idxname ).delete();
	}
	
	private static void backup() {
		deleteBackup();
		new File( filename ).renameTo( new File( filenameBak ));
		new File( idxname ).renameTo( new File( idxnameBak ));
	}
	
	
	
	
    private static IndexBase indexByArg( String arg, Index idx ) {
        IndexBase pidx = null;
        if ( arg.equals("dep")) {
            pidx = idx.Departures;
        } 
        else if ( arg.equals("des")) {
            pidx = idx.Destinations;
        } 
        else if ( arg.equals("n")) {
            pidx = idx.nums;
        } 
        else {
            System.err.println( "Invalid index specified: " + arg );
        }
        return pidx;
    }
	
	
	
	
	
	
	
	
	
	
	
	static boolean deleteFile( String[] args )
			throws ClassNotFoundException, IOException, KeyNotUniqueException {
		// -dk  {dep|des|n} key		- clear data by key
		if( args.length != 3) {
			System.err.println("Invalid number of arguments");
			return false;
		}
		long[] poss = null;
		try( Index idx = Index.load(idxname)) {
			IndexBase pidx = indexByArg( args[1], idx);
			if( pidx == null ) {
				return false;
			}
			if( pidx.contains(args[2]) == false) {
				System.err.println("Key not found: " + args[2]);
				return false;
			}
			poss = pidx.get(args[2]);
		}
		backup();
		Arrays.sort(poss);
		try( Index idx = Index.load(idxname);
				RandomAccessFile fileBak= new RandomAccessFile(filenameBak, "rw");
				RandomAccessFile file = new RandomAccessFile( filename, "rw" )) {
			boolean[] wasZipped = new boolean[] {false};
			long pos;
			while ((pos = fileBak.getFilePointer()) < fileBak.length()) {
				Airlines book=(Airlines)Buffer.readObject(fileBak, pos,wasZipped);
				if( Arrays.binarySearch(poss, pos) < 0) {
					long ptr = Buffer.writeObject(file, book, wasZipped[0]);
					idx.put(book, ptr);
				}
			}
		}
		return true;
	}
	

	
	static void appendFile( String[] args, Boolean zipped )
			throws FileNotFoundException, IOException, ClassNotFoundException,
				KeyNotUniqueException {
		if( args.length >= 2) {
			FileInputStream stdin = new FileInputStream( args[1] );
			System.setIn(stdin);
			if( args.length == 3) {
				encoding = args[2];
			}
			airOut = new PrintStream("nul");
		}
		append_file( zipped );
	}
		
	static void append_file( Boolean zipped) 
			throws FileNotFoundException, IOException, ClassNotFoundException,
				KeyNotUniqueException {
		Scanner fin = new Scanner( System.in, encoding );
		airOut.println( "Enter Airline data: " );
		try( Index idx = Index.load(idxname);
			 RandomAccessFile raf = new RandomAccessFile( filename, "rw")) {
			for(;;) {
				Airlines air = read_air( fin );
				if( air == null )
					break;
				idx.test(air);
				long pos = Buffer.writeObject(raf, air, zipped);
				idx.put(air, pos);
			}
		}
	}
	
	private static void printRecord( RandomAccessFile raf, long pos )
			throws ClassNotFoundException, IOException {
		boolean[] wasZipped = new boolean[] {false};
		Airlines air = (Airlines) Buffer.readObject(raf, pos, wasZipped);
		if( wasZipped[0] == true ) {
			System.out.println(" compressed");
		}
		System.out.println(" record at position " + pos + ": \n" + air.toString());
	}
	
   private static void printRecord(RandomAccessFile raf,String key,IndexBase pidx)
		throws ClassNotFoundException, IOException {
		long[] poss = pidx.get(key);
		for( long pos : poss) {
			System.out.println("*** Key: " + key + " points to" );
			printRecord( raf, pos );
		}
	}
	
	static void print_file() 
			throws FileNotFoundException, IOException, ClassNotFoundException {
		int rec = 0;
		try( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
			long pos;
			while(( pos = raf.getFilePointer()) < raf.length() ) {
				System.out.println("#" + (++rec ));
				printRecord( raf, pos);
			}
			System.out.flush();
		}
	}
	
	static boolean printFile( String[] args, boolean reverse)
			throws ClassNotFoundException, IOException {
		if( args.length != 2 ) {
			System.err.println("Invalid number of arguments");
			return false;
		}
		try( Index idx = Index.load(idxname);
				RandomAccessFile raf = new RandomAccessFile( filename, "rw")) {
			IndexBase pidx = indexByArg( args[1], idx );
			if ( pidx == null ) {
				return false;
			}
			String[] keys= null;
			
	        if ( args[1].equals("dep")) {
	        	Comparator<String> comp = reverse ? new KeyCompReverse() : new KeyComp();
	            keys = pidx.getKeys( comp );
	        } 
	        else if ( args[1].equals("des")) {
	        	Comparator<String> comp = reverse ? new KeyCompReverse() : new KeyComp();
	            keys = pidx.getKeys( comp );
	        } 
	        else if ( args[1].equals("n")) {
	        	Comparator<Integer> comp = reverse ? new KeyCompReverseInt() : new KeyCompInt();
	               keys = pidx.getKeys( comp );
	        } 
	        else {
	            System.err.println( "Invalid index specified: " + args[1] );
	        }
			
			
			
			for( String key : keys) {
				printRecord( raf, key, pidx );
			}
		}
		return true;
	}
	
	static boolean findByKey( String[] args )
			throws ClassNotFoundException, IOException {
		if( args.length != 3) {
			System.err.println("Invalid number of arguments");
			return false;
		}
		try( Index idx = Index.load(idxname);
				RandomAccessFile raf = new RandomAccessFile( filename, "rw")) {
			IndexBase pidx = indexByArg( args[1], idx);
			if( pidx.contains(args[2]) == false) {
				System.err.println("Key not found: " + args[2]);
				return false;
			}
			printRecord( raf, args[2], pidx );
		}
		return true;
	}
	
	static boolean findByKey( String[] args, boolean reverse )
			throws ClassNotFoundException, IOException {
		if( args.length != 3 ) {
			System.err.println("Invalid number of arguments");
			return false;
		}
		try( Index idx = Index.load(idxname);
				RandomAccessFile raf = new RandomAccessFile( filename, "rw")) {
			IndexBase pidx = indexByArg( args[1], idx);
			if( pidx.contains(args[2]) == false ) {
				System.err.println("Key not found: " + args[2] );
				return false;
			}
			String[] keys= null;
			
	        if ( args[1].equals("dep")) {
	        	Comparator<String> comp = reverse ? new KeyCompReverse() : new KeyComp();
	            keys = pidx.getKeys( comp );
	        } 
	        else if ( args[1].equals("des")) {
	        	Comparator<String> comp = reverse ? new KeyCompReverse() : new KeyComp();
	            keys = pidx.getKeys( comp );
	        } 
	        else if ( args[1].equals("n")) {
	        	Comparator<Integer> comp = reverse ? new KeyCompReverseInt() : new KeyCompInt();
	               keys = pidx.getKeys( comp );
	        } 
	        else {
	            System.err.println( "Invalid index specified: " + args[1] );
	        }
			
			for( int i = 0; i < keys.length; i++ ) {
				String key = keys[i];
				if( key.equals(args[2])) {
					break;
				}
				printRecord( raf, key, pidx );
			}
		}
		return true;
	}

}









