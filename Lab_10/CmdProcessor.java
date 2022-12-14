package ttt;

/**
 * <p>Command processor interface
 * @author Alexander Litvinchik & Yuzefovich Kirill
 * @version 1.0
 */
public interface CmdProcessor {
	void putHandler( String shortName, String fullName, CmdHandler handler );

	int lastError();
	boolean command( String cmd );
	boolean command( String cmd, int[] err );
} 