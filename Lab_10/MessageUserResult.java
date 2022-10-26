package ttt;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MessageUserResult class
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
@XmlRootElement
public class MessageUserResult extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;

	public String[] userNics = null;

	public MessageResult.Data data = new MessageResult.Data();
	protected MessageResult.Data getData() {
		return data;
	}

	public MessageUserResult() {
	}

	public MessageUserResult( String errorMessage ) { // Error
		super.setup( Protocol.CMD_USER, errorMessage );
	}

	public MessageUserResult( String[] userNics ) { // No errors
		super.setup( Protocol.CMD_USER );
		this.userNics = userNics;
	}

	public String toString() {
		String res = super.toString();
		if (userNics != null)
			for (String str : userNics)
				res += ", " + str;
		return res;
	}
}
