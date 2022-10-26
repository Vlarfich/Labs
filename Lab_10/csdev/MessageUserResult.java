package csdev;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * <p>MessageUserResult class
 * @author Sergey Gutnikov
 * @version 1.0
 */
@XmlRootElement
public class MessageUserResult extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String[] userNics = null;
	
	public MessageResult.Data data = new MessageResult.Data(); 
	protected MessageResult.Data getData() {
		return data;
	}
	
	public MessageUserResult( ) { // Error
		super.setup( Protocol.CMD_USER );
	}
	
	public MessageUserResult( String errorMessage ) { // Error
		super.setup( Protocol.CMD_USER, errorMessage );
	}
	
	public MessageUserResult( String[] userNics ) { // No errors
		super.setup( Protocol.CMD_USER );
		this.userNics = userNics;
	}
}