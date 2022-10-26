package csdev;

import java.io.Serializable;



public class MessageWaitResult extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String move = "";
	public String player = "";
	public String field = "";
	public int status;
	public String result = "";
	
	public MessageResult.Data data = new MessageResult.Data(); 
	protected MessageResult.Data getData() {
		return data;
	}
	
	public MessageWaitResult( String l, String pl, String b, int s, String r ) { // No errors
		super.setup( Protocol.CMD_WAIT );
		this.move = l;
		this.player = pl;
		this.field = b;
		this.status = s;
		this.result = r;
	}
	
	
}