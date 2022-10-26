package ttt;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageConnectResult class
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageConnectResult extends MessageResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public MessageResult.Data data = new MessageResult.Data(); 
	protected MessageResult.Data getData() {
		return data;
	}
	
	public MessageConnectResult( String errorMessage ) { // Error
		super.setup( Protocol.CMD_CONNECT, errorMessage );
	}
	
	public MessageConnectResult() { // No error
		super.setup( Protocol.CMD_CONNECT );
	}
}