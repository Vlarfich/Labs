package ttt;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * MessageUserResult class
 * 
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessagePlayResult extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean answer = false;
	public String nick1, nick2;

	public MessageResult.Data data = new MessageResult.Data();
	protected MessageResult.Data getData() {
		return data;
	}

	public MessagePlayResult() {}
	
	public MessagePlayResult(String errorMessage) { // Error
		super.setup(Protocol.CMD_PLAY, errorMessage);
	}

	public MessagePlayResult(boolean answer, String nick1, String nick2) { // No errors
		super.setup(Protocol.CMD_PLAY);
		this.answer = answer;
		this.nick1 = nick1;
		this.nick2 = nick2;
	}

}
