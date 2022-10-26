package csdev;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * <p> MessageCheckMail class
 * @author Sergey Gutnikov
 * @version 1.0
 */
@XmlRootElement
public class MessageCheckMail extends Message implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	public String usrNic;
	public String txt;
	
	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}
	
	public MessageCheckMail() {
		super.setup( Protocol.CMD_CHECK_MAIL );
	}
	
	public MessageCheckMail( String usrNic, String txt ) {
		
		super.setup( Protocol.CMD_CHECK_MAIL );
		this.usrNic = usrNic;
		this.txt = txt;
	}
	
}