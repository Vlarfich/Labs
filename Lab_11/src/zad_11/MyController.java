package zad_11;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MyController {
	private String filename;
	private String filenameBak;
	private String idxname;
	private String idxnameBak;
	private MyFrame frame;

	public MyController() {
		filename = "Airlines.dat";
		filenameBak = "Airlines.~dat";
		idxname = "Airlines.idx";
		idxnameBak = "Airlines.~idx";
		frame = new MyFrame(this);
	}

	void open(String file) {
		filename = file + ".dat";
		idxname = file + ".idx";
		filenameBak = file + ".~dat";
		idxnameBak = file + "~.idx";
		frame.update(filename);
	}

	void appendFile(boolean zipped, String Departure, String Destination, String number, String type, String time, String day)
			throws FileNotFoundException, IOException, ClassNotFoundException, KeyNotUniqueException, ParseException {
		try (Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			Airlines air = new Airlines(Departure, Destination, number, type, time, day);
			idx.test(air);
			long pos = Buffer.writeObject(raf, air, zipped);
			idx.put(air, pos);
		}
	}

	private WriteRecord printRecord(RandomAccessFile raf, long pos) throws ClassNotFoundException, IOException {
		boolean[] wasZipped = new boolean[] { false };
		Airlines air = (Airlines) Buffer.readObject(raf, pos, wasZipped);
		return new WriteRecord(air, wasZipped[0]);
	}

	private List<WriteRecord> printRecord(RandomAccessFile raf, String key, IndexBase pidx)
			throws ClassNotFoundException, IOException {
		long[] poss = pidx.get(key);
		List<WriteRecord> records = new ArrayList<>();
		for (Long pos : poss) {
			records.add(printRecord(raf, pos));
		}
		return records;
	}

	void printFile() throws IOException, ClassNotFoundException {
		try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			long pos = raf.getFilePointer();
			List<WriteRecord> records = new ArrayList<>();
			while (pos < raf.length()) {
				records.add(printRecord(raf, pos));
				pos = raf.getFilePointer();
			}
			frame.showData(records);
		}
	}

	void printSorted(boolean reverse, int index) throws ClassNotFoundException, IOException {
		try (Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			IndexBase pidx = indexByName(idx, index);
			 String[] keys = null;
	            
	            if ( index==2) {
	               Comparator<Integer> comp = reverse ? new KeyCompReverseInt() : new KeyCompInt();
	               keys = pidx.getKeys( comp );
	            } 
	            else if (index == 0 || index == 1) {
	            	Comparator<String> comp = reverse ? new KeyCompReverse() : new KeyComp();
	            	keys = pidx.getKeys( comp );
	            } 
	            
	            List<WriteRecord> records = new ArrayList<>();
				for (String key : keys) {
					records.addAll(printRecord(raf, key, pidx));
				}
			frame.showData(records);
		}
	}

	private IndexBase indexByName(Index index, int number) {
		switch (number) {
		case 0: // departure
			return index.Departures;
		case 1: // destination
			return index.Destinations;
		case 2: // numbers
			return index.nums;
		default:
			throw new IllegalArgumentException("Invalid index: " + number);
		}
	}

	void printByKey(int number, String key) throws ClassNotFoundException, IOException {
		try (Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			IndexBase pidx = indexByName(idx, number);
			if (!pidx.contains(key)) {
				throw new IllegalArgumentException("Key not found: " + key);
			}
			frame.showData(printRecord(raf, key, pidx));
		}
	}

	void printByKey(boolean reverse, int index, String key) throws ClassNotFoundException, IOException {
		try (Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			IndexBase pidx = indexByName(idx, index);
			if (!pidx.contains(key)) {
				throw new IllegalArgumentException("Key not found: " + key);
			}
			String[] keys = null;
			 if ( index == 2) {
	               Comparator<Integer> comp = reverse ? new KeyCompReverseInt() : new KeyCompInt();
	               keys = pidx.getKeys( comp );
	            } 
	            else if (index == 1 || index == 0) {
	            	Comparator<String> comp = reverse ? new KeyCompReverse() : new KeyComp();
	            	keys = pidx.getKeys( comp );
	            } 

			
			List<WriteRecord> records = new ArrayList<>();
			for (String cur : keys) {
				if (cur.equals(key)) {
					break;
				}
				records.addAll(printRecord(raf, cur, pidx));
			}
			frame.showData(records);
		}
	}

	void delete(int number, String key)
			throws FileNotFoundException, IOException, ClassNotFoundException, KeyNotUniqueException {
		List<Long> poss = new ArrayList<Long>();
		try (Index idx = Index.load(idxname)) {
			IndexBase pidx = indexByName(idx, number);
			if (!pidx.contains(key)) {
				throw new IllegalArgumentException("Key not found: " + key);
			}
			long[] pos = pidx.get(key);
			for (int i = 0; i < pos.length;i++)
				poss.add(pos[i]);
		}
		Collections.sort(poss);
		File data = new File(filename), index = new File(idxname), dataBak = new File(filenameBak),
				indexBak = new File(idxnameBak);
		if ((!dataBak.exists() || dataBak.delete()) && (!indexBak.exists() || indexBak.delete())
				&& data.renameTo(dataBak) && index.renameTo(indexBak)) {
			try (Index idx = Index.load(idxname);
					RandomAccessFile fileBak = new RandomAccessFile(filenameBak, "rw");
					RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
				boolean[] wasZipped = new boolean[] { false };
				long pos;
				while ((pos = fileBak.getFilePointer()) < fileBak.length()) {
					Airlines air = (Airlines) Buffer.readObject(fileBak, pos, wasZipped);
					if (Collections.binarySearch(poss, pos) < 0) { // if not found in deleted
						long ptr = Buffer.writeObject(file, air, wasZipped[0]);
						idx.put(air, ptr);
					}
				}
			}
		}
	}
}
