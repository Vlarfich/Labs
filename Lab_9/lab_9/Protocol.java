package lab_9;

interface CMD 
{
	static final byte CMD_FIELD = 1;
	static final byte CMD_SHOT  = 2;
	static final byte CMD_CONNECT  = 3;
	static final byte CMD_DISCONNECT  = 4;
//	static final byte CMD_USER 	= 5;
//	static final byte CMD_CHECK_MAIL= 4;
//	static final byte CMD_LETTER	= 5; //CHANGE!!!!!!!!!!!
}

/**
 * <p>RESULT interface: Result codes 
*/
interface RESULT 
{
	static final int RESULT_CODE_OK 	= 0;
	static final int RESULT_CODE_ERROR 	= -1;
}	

/**
 * <p>PORT interface: Port # 
*/
interface PORT 
{
	static final int PORT = 8071;
}

/**
 * <p>Protocol class: Protocol constants 
 */
public class Protocol implements CMD, RESULT, PORT 
{
	private static final byte CMD_MIN = CMD_FIELD;
	private static final byte CMD_MAX = CMD_DISCONNECT;
	public static boolean validID( byte id ) {
		return id >= CMD_MIN && id <= CMD_MAX; 
	}
}
