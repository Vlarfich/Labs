package ttt;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageUserResult class
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageAnswerResult extends MessageResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String nick1, nick2;
	
	public MessageResult.Data data = new MessageResult.Data();
	protected MessageResult.Data getData() {
		return data;
	}
	
	public MessageAnswerResult() {}
	
	public MessageAnswerResult( String errorMessage ) { // Error
		super.setup( Protocol.CMD_ANSWER, errorMessage );
	}
	
	public MessageAnswerResult(String nick1, String nick2) {
		super.setup( Protocol.CMD_ANSWER);
		this.nick1 = nick1;
		this.nick2 = nick2;
	}
}
 