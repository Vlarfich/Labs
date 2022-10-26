package ttt;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageUserResult class
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageAnswer extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public boolean answer = false;
	public String nick1, nick2;
	
	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}
	
	public MessageAnswer() {}
	
	public MessageAnswer( boolean ans, String nick1, String nick2) {
		super.setup( Protocol.CMD_ANSWER);
		this.nick1 = nick1;
		this.nick2 = nick2;
		this.answer = ans;
	}
}
 