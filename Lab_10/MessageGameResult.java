package ttt;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageUserResult class
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageGameResult extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;
	public String result;
	
	public MessageResult.Data data = new MessageResult.Data();
	protected MessageResult.Data getData() {
		return data;
	}
	
	public MessageGameResult() {}
	
	public MessageGameResult(String result) {
		super.setup( Protocol.CMD_GAME );
			this.result = result;
	}
} 