package ttt;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageConnect class
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageConnect extends Message implements Serializable {

	private static final long serialVersionUID = 1L;

	public String userNic;
	public String userFullName;

	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}

	public MessageConnect() {
		//super.setup( Protocol.CMD_CONNECT );
	}

	public MessageConnect( String userNic, String userFullName ) {
		super.setup( Protocol.CMD_CONNECT );
		this.userNic = userNic;
		this.userFullName = userFullName;
	}

	public String toString() {
		return super.toString() + ", " + userNic + ", " + userFullName;
	}
}
