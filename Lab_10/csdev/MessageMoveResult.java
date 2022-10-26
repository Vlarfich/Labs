package csdev;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MessageMoveResult extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public MessageResult.Data data = new MessageResult.Data(); 
	protected MessageResult.Data getData() {
		return data;
	}
	public MessageMoveResult() { // No errors
		
		super.setup( Protocol.CMD_MOVE );
	}
	
	public String[] letters = null;
	public boolean fight = false;
	public String player = "";
	public String field = "";
	public String result;
	public int r = 0;
	public int status;
	
	
	public MessageMoveResult( String h, String f, String res, int s ) { // No errors
		super.setup( Protocol.CMD_MOVE );
		player = h;
		fight = true;
		field = f;
		result = res;
		if(res.equals(" Invalid move"))
			r = -1;
		else
			if( res.equals(" HIT "))
				r = 1;
		status = s;
	}

	
	
	
	
}