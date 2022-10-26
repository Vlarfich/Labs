package zadanie_6;
import java.io.*;

public class Connector {

	private String filename;

	public Connector( String filename ) {
		this.filename = filename;
	}

	public void write(Payments pay)throws IOException {
		FileOutputStream fos = new FileOutputStream (filename);
		try ( ObjectOutputStream oos = new ObjectOutputStream( fos )){
			oos.writeInt(pay.getID());
			oos.writeObject(pay.adm);
			oos.writeInt(pay.clientbase.length);
			for ( int i = 0; i < pay.clientbase.length; i++) {
				oos.writeObject( pay.clientbase[i] );		
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public Payments read() throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(filename);
		try ( ObjectInputStream oin = new ObjectInputStream(fis)){
			int id = oin.readInt();
			Admin adm = (Admin) oin.readObject();
			int length = oin.readInt();
			Client base[] = new Client[length];
			for(int i = 0; i < length; i++) {
				base[i] = (Client) oin.readObject();
			}
			Payments result = new Payments(adm, base);
			result.setID(id);
			return result;
		}
	}

}
