package ttt;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageUserResult class
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageCheckPlayResult extends MessageResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public boolean answer;
	public String nick1, nick2;
	
	public MessageResult.Data data = new MessageResult.Data();
	protected MessageResult.Data getData() {
		return data;
	}
	
	public MessageCheckPlayResult( String errorMessage ) { // Error
		super.setup( Protocol.CMD_CHECK_PLAY, errorMessage );
	}

	public MessageCheckPlayResult() {
		super.setup(Protocol.CMD_CHECK_PLAY);
	}
}

