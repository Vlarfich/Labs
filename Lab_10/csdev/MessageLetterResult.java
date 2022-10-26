package csdev;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import csdev.server.ServerThread;

/**
 * <p>MessageLetterResult class
 * @author Sergey Gutnikov
 * @version
 *  1.0
 */
@XmlRootElement
public class MessageLetterResult extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public MessageResult.Data data = new MessageResult.Data(); 
	protected MessageResult.Data getData() {
		return data;
	}
	
	public MessageLetterResult( String errorMessage ) { //Error
		
		super.setup( Protocol.CMD_LETTER, errorMessage );
	}
	
	
	//public String[] letters = null;
	public int bat = 0;
	public String player;
	public String field;
	public int status = 0;
	//public DataOutputStream os;
	
	public MessageLetterResult(  ) { // No errors
		super.setup( Protocol.CMD_LETTER );
		//this.letters = letters;
	}
	
	public MessageLetterResult( String h, int b ) { // No errors
		super.setup( Protocol.CMD_LETTER );
		//this.letters = letters;
		this.player = h;
		this.bat = b;
	}
	
	public MessageLetterResult( String h, int b, String f, int s ) { // No errors
		super.setup( Protocol.CMD_LETTER );
		//this.letters = letters;
		this.player = h;
		this.bat = b;
		this.field = f;
		this.status = s;
	}
	
//	public MessageLetterResult( String h, int b, String f, int s, DataOutputStream o ) { // No errors
//		super.setup( Protocol.CMD_LETTER );
//		//this.letters = letters;
//		this.player = h;
//		this.bat = b;
//		this.field = f;
//		this.status = s;
//		this.os = o;
//	}
}