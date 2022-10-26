package ttt;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageUserResult class
 * @author Sergey Gutnikov
 * @version 1.0
 */
@XmlRootElement
public class MessageChoiseResult extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;

	public String Message;

	public MessageResult.Data data = new MessageResult.Data();
	protected MessageResult.Data getData() {
		return data;
	}
	
	public MessageChoiseResult() {}
	
	public MessageChoiseResult(String Message) {
		super.setup( Protocol.CMD_CHECK_CHOISE);
		this.Message = Message;
	}
} 