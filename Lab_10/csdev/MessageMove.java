package csdev;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import csdev.server.ServerThread;

@XmlRootElement
public class MessageMove extends Message implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	public String move;
	public int battle = 0;
	//public DataOutputStream os;
	
	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}
	
	public MessageMove( ) {
		super.setup( Protocol.CMD_MOVE );
	}
	
	public MessageMove( String m, int b ) {
		
		super.setup( Protocol.CMD_MOVE );
		move = m;
		battle = b;
	}

//	public MessageMove( String m, int b, DataOutputStream o ) {
//		
//		super.setup( Protocol.CMD_MOVE );
//		move = m;
//		battle = b;
//		os = o;
//	}
}