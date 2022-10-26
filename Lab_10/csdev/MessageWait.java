package csdev;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageWait extends Message implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	public String usrNic;
	public int battle = 0;
	
	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}
	
	
	public MessageWait( String usrNic, int b ) {
		
		super.setup( Protocol.CMD_WAIT );
		this.usrNic = usrNic;
		battle = b;
	}
	
	public MessageWait( ) {
		super.setup( Protocol.CMD_WAIT );
	}
}