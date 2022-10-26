package csdev;

import java.io.Serializable;

/**
 * <p> MessageCheckMail class
 * @author Sergey Gutnikov
 * @version 1.0
 */
public class MessageCheckMail extends Message implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	public String usrNic;
	public String txt;
	
	public MessageCheckMail() {
		super( Protocol.CMD_CHECK_MAIL );
	}
	
	public MessageCheckMail( String usrNic, String txt ) {
		
		super( Protocol.CMD_CHECK_MAIL );
		this.usrNic = usrNic;
		this.txt = txt;
	}
	
}