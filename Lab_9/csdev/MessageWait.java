package csdev;

import java.io.Serializable;


public class MessageWait extends Message implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	public String usrNic;
	public int battle = 0;
	
	public MessageWait( String usrNic, int b ) {
		
		super( Protocol.CMD_WAIT );
		this.usrNic = usrNic;
		battle = b;
	}
	
}