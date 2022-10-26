package ttt;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageUser class: Get answer of player
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageCheckPlay extends Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}
	
	public MessageCheckPlay() {
		super.setup(Protocol.CMD_CHECK_PLAY);

	}
}
 