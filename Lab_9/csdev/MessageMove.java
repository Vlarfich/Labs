package csdev;

import java.io.ObjectOutputStream;
import java.io.Serializable;

import csdev.server.ServerThread;


public class MessageMove extends Message implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	public String move;
	public int battle = 0;
	public ObjectOutputStream os;
	
	public MessageMove( String m, int b ) {
		
		super( Protocol.CMD_MOVE );
		move = m;
		battle = b;
	}

	public MessageMove( String m, int b, ObjectOutputStream o ) {
		
		super( Protocol.CMD_MOVE );
		move = m;
		battle = b;
		os = o;
	}
}