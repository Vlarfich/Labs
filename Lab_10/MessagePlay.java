package ttt;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * MessageUser class: Get answer of player
 * 
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessagePlay extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public String nick1, nick2;

	public Message.Data data = new Message.Data();

	protected Message.Data getData() {
		return data;
	}

	public MessagePlay() {}
	
	public MessagePlay(String nick1, String nick2) {
		super.setup(Protocol.CMD_PLAY);
		this.nick1 = nick1;
		this.nick2 = nick2;
	}

}
