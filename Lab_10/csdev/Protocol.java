package csdev;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBException;


/**
 * <p>CMD interface: Client message IDs 
 * @author Sergey Gutnikov
 * @version 1.0
 */
interface CMD {
	static final byte CMD_CONNECT 	= 1;
	static final byte CMD_DISCONNECT= 2;
	static final byte CMD_USER 	= 3;
	static final byte CMD_CHECK_MAIL= 4;
	static final byte CMD_LETTER	= 5;
	static final byte CMD_MOVE	= 6;
	static final byte CMD_WAIT	= 7;
	static final byte CMD_CONTEXT = 8;
}

/**
 * <p>RESULT interface: Result codes 
 * @author Sergey Gutnikov
 * @version 1.0
 */
interface RESULT {
	static final int RESULT_CODE_OK 	= 0;
	static final int RESULT_CODE_ERROR 	= -1;
}	

/**
 * <p>PORT interface: Port # 
 * @author Sergey Gutnikov
 * @version 1.0
 */
interface PORT {
	static final int PORT = 8071;
}

/**
 * <p>Protocol class: Protocol constants 
 * @author Sergey Gutnikov
 * @version 1.0
 */
public class Protocol implements CMD, RESULT, PORT {
	private static final byte CMD_MIN = CMD_CONNECT;
	private static final byte CMD_MAX = CMD_CONTEXT;
	public static boolean validID( byte id ) {
		return id >= CMD_MIN && id <= CMD_MAX; 
	}
	
	
	
	
	
	static String makeFileName(Object obj) {
        String fileName;
        StringTokenizer st = new
            StringTokenizer(obj.getClass().getName(), ".");
        int n = st.countTokens();
        // skip package name(s):
        if ( n > 1 )
            while ( --n > 0 ) {
                st.nextToken();
            }
        fileName = st.nextToken();
        if (( obj instanceof MessageResult ) &&
             ((MessageResult) obj).Error()== true ) {
            return fileName + "Err.xml";
        }
        return fileName + ".xml";
    }

    public static void testMessage( MessageXml msg )
                        throws IOException, JAXBException {
        String file = makeFileName(msg);
        OutputStream os = new FileOutputStream( file );
        MessageXml.toXml( msg, os );
        os.close();
        //InputStream is = new FileInputStream( file );
        //MessageXml msgx = (MessageXml)
           //MessageXml.fromXml( msg.getClass(), is );
        //is.close();
        //System.out.println( "Write object: " + msg );
        //System.out.println( "Read object: " + msgx );
    }
}

