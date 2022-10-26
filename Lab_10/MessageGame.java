package ttt;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageUserResult class
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageGame extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public String nick1;
	
	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}
	
	public MessageGame() {}
	
	public MessageGame(String nick1) { // No errors
		super.setup( Protocol.CMD_GAME );
			this.nick1 = nick1;
	}
} 