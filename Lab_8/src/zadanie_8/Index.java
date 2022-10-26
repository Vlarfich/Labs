package zadanie_8;

import java.io.*;
import java.util.*;
import java.util.zip.*;

class KeyComp implements Comparator<String> {
	
	@Override
	public int compare(String o1, String o2) {        // right order:	
		return o1.compareTo(o2);
	}
	
}

class KeyCompReverse implements Comparator<String> {        // reverse order:

	@Override
	public int compare(String o1, String o2) {
		return o2.compareTo(o1);
	}
	
}

class KeyCompInt implements Comparator<Integer> {
	public int compare(Integer o1, Integer o2) {
		// right order:
		return o1.compareTo(o2);
	}
}

class KeyCompReverseInt implements Comparator<Integer> {
	public int compare(Integer o1, Integer o2) {
		// reverse order:
		return o2.compareTo(o1);
	}
}



interface IndexBase {
	String[] getKeys( @SuppressWarnings("rawtypes") Comparator comp);
	void put( String key, long value );
	boolean contains( String key );
	long[] get( String key );
}


class IndexOne2One implements Serializable, IndexBase {
	private static final long serialVersionUID = 1L;
	
	private TreeMap<String, Long> map;
	
	public IndexOne2One() {
		map = new TreeMap<String, Long> ();
	}
	
	public String[] getKeys( Comparator comp) {
		String[] res = map.keySet().toArray(new String[0]);
		Integer[] result = new Integer[res.length];
		for (int i = 0; i < res.length; i++)
			result[i] = Integer.parseInt(res[i]);
		Arrays.sort(result, comp);
		for (int i = 0; i < res.length; i++)
			res[i] = result[i].toString();
		return res;
	}
	
	public void put( String key, long value ) {
		map.put( key, new Long(value));
	}
	
	public boolean contains( String key ) {
		return map.containsKey(key);
	}
	
	public long[] get( String key ) {
		long pos = map.get(key).longValue();
		return new long[] {pos};
	}
}

class IndexOne2N implements Serializable, IndexBase {
	private static final long serialVersionUID = 1L;
	
	private TreeMap<String, long[]> map;
	
	public IndexOne2N() {
		map = new TreeMap<String, long[]> ();
	}
	
	public String[] getKeys( Comparator comp) {
		String[] result = map.keySet().toArray( new String[0] );
		Arrays.sort(result, comp);
		return result;
	}
	
	public void put( String key, long value ) {
		long[] arr = map.get(key);
		arr = ( arr != null ) ?
				Index.InsertValue(arr, value):
					new long[] {value};
		map.put(key, arr);
	}
	
	public void put( String keys,
					 String keyDel,
					 long value ) {
		StringTokenizer st = new StringTokenizer( keys, keyDel );
		int num = st.countTokens();
		for( int i = 0; i < num; i++) {
			String key = st.nextToken();
			key = key.trim();
			put(key, value);
		}
	}
	
	public boolean contains( String key ) {
		return map.containsKey(key);
	}
	
	public long[] get( String key ) {
		return map.get(key);
	}
}



class KeyNotUniqueException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public KeyNotUniqueException( String key ) {
		super( new String( "Key is not unique: " + key ));
	}
}

public class Index implements Serializable, Closeable {
	private static final long serialVersionUID = 1L;
	
	public static long[] InsertValue( long[] arr, long value) {
		int length = ( arr == null ) ? 0: arr.length;
		long[] result = new long[length + 1];
		for( int i = 0; i < length; i++)
			result[i] = arr[i];
		result[length] = value;
		return result;
	}
	
	IndexOne2N Departures;
	IndexOne2N Destinations;
	IndexOne2One nums;
	
	public void test( Airlines air ) throws KeyNotUniqueException {
		assert( air != null );
		//if( nums.contains( Integer.toString(air.num) )) {
		//	throw new KeyNotUniqueException( Integer.toString(air.num) );
		//}
		if( nums.contains( air.num.toString() )) {
			throw new KeyNotUniqueException( air.num.toString() );
		}
	}
	
	public void put( Airlines air, long value ) throws KeyNotUniqueException {
		test( air );
		Departures.put(air.Departure, value);
		Destinations.put(air.Destination, value);
		//nums.put(Integer.toString(air.num), value);
		nums.put(air.num.toString(), value);
	}
	
	public Index() {
		Departures = new IndexOne2N();
		Destinations = new IndexOne2N();
		nums = new IndexOne2One();
	}
	
	
    private transient String filename = null; 
	
    public void save( String name ) {
        filename = name;
    }
	
	
	public static Index load( String name )
		throws IOException, ClassNotFoundException {
		Index obj = null;
		try {
			FileInputStream file = new FileInputStream( name );
			try( ZipInputStream zis = new ZipInputStream( file )){
				ZipEntry zen = zis.getNextEntry();
				if( zen.getName().equals(Buffer.zipEntryName) == false) {
					throw new IOException("Invalid block format");
				}
				try( ObjectInputStream ois = new ObjectInputStream( zis )) {
					obj = (Index) ois.readObject();
				}
			}
		} 
		catch( FileNotFoundException e) {
			obj = new Index();
		}
		if( obj != null) {
			obj.save(name);
		}
		return obj;
	}
	
	public void saveAs( String name ) throws IOException {
		FileOutputStream file = new FileOutputStream( name );
		try( ZipOutputStream zos = new ZipOutputStream( file )){
			zos.putNextEntry( new ZipEntry( Buffer.zipEntryName ));
			zos.setLevel( ZipOutputStream.DEFLATED);
			try( ObjectOutputStream oos = new ObjectOutputStream( zos )) {
				oos.writeObject( this );
				oos.flush();
				zos.closeEntry();
				zos.flush();
			}
		}
	}
	
	public void close() throws IOException {
		saveAs( filename );
	}
	
}

















