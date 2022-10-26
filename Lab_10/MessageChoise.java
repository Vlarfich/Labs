package ttt;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * MessageUser class: Get choise in game
 * 
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageChoise extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public int row, column;
	public String nick;

	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}

	public MessageChoise() {}
	
	public MessageChoise(String nick, int i, int j) {
		super.setup(Protocol.CMD_CHECK_CHOISE);
		this.nick = nick;
		row = i;
		column = j;
	}
}
