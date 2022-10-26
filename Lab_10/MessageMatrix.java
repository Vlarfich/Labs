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
public class MessageMatrix extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public String [][] table = new String[3][3];

	public Message.Data data = new Message.Data();
	protected Message.Data getData() {
		return data;
	}
	
	public  MessageMatrix() {}
	public  MessageMatrix(String[][] table) {
		super.setup(Protocol.CMD_MATRIX);
		for(int i = 0;i<3;i++)
			for(int j=0;j<3;j++)
				this.table[i][j]= table[i][j];
	}
}
 