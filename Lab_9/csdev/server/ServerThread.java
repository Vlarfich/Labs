package csdev.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import csdev.Battle;
import csdev.Message;
import csdev.MessageCheckMail;
import csdev.MessageCheckMailResult;
import csdev.MessageConnect;
import csdev.MessageConnectResult;
import csdev.MessageLetter;
import csdev.MessageLetterResult;
import csdev.MessageMove;
import csdev.MessageMoveResult;
import csdev.MessageUser;
import csdev.MessageUserResult;
import csdev.MessageWait;
import csdev.MessageWaitResult;
import csdev.Protocol;
import csdev.client.ClientMain;

public class ServerThread extends Thread {
	
	private Socket              sock;
	private ObjectOutputStream 	os;
	private ObjectInputStream 	is;
	private InetAddress 		addr;
	
	private String userNic = null;
	private String userFullName;
	
	private Object syncLetters = new Object();
	private Vector<String> letters = null;
	public void addLetter( String letter ) {	
		synchronized ( syncLetters ) {				
			if ( letters == null ) {
				letters = new Vector<String> ();
			}
			letters.add( letter );
		}
	}
	public String[] getLetters() {
		synchronized ( syncLetters ) {				
			String[] lts = new String[0];
			synchronized ( syncLetters ) {			
				if ( letters != null ) {
					lts = letters.toArray( lts );
					letters = null;
				}
			}
			return lts;
		}		
	}
	
	
	public ServerThread(Socket s) throws IOException {
		sock = s;
		s.setSoTimeout(1000);
		os = new ObjectOutputStream( s.getOutputStream() );
		is = new ObjectInputStream( s.getInputStream());
		addr = s.getInetAddress();
		this.setDaemon(true);
	}
	
	public void run() {
		try {
			while ( true ) {
				Message msg = null;
				try {
					msg = ( Message ) is.readObject();
				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
				}
				if (msg != null) switch ( msg.getID() ) {
			
					case Protocol.CMD_CONNECT:
						if ( !connect( (MessageConnect) msg )) 
							return;
						break;
						
					case Protocol.CMD_DISCONNECT:
						delWaiting(userNic);
						return;
						
					case Protocol.CMD_USER:
						user(( MessageUser ) msg);
						break;
						
					case Protocol.CMD_CHECK_MAIL:
						checkMail(( MessageCheckMail ) msg );
						//letter(( MessageCheckMail ) msg );
						break;
						
					case Protocol.CMD_LETTER:
						letter(( MessageLetter ) msg );
						
						break;
						
					case Protocol.CMD_MOVE:
						Move(( MessageMove ) msg );
						
						break;
						
					case Protocol.CMD_WAIT:
						wait(( MessageWait ) msg );
						
						break;
				}
			}	
		} catch (IOException | InterruptedException e) {
			System.err.print("Disconnect...");
		} finally {
			disconnect();
		}
	}
	
	int aa, bb;
	char charA;
	
	boolean checkMove( String str ) {
		if( str.length() <= 1)
			return false;
		String letters = "abcdefghij";
		String bigLetters = "ABCDEFGHIJ";
		String nums = "0123456789";
		for( int i = 0; i < letters.length(); i++ ) {
			if( str.charAt(0) == letters.charAt(i)   ||  str.charAt(0) == bigLetters.charAt(i) ) {
				aa = i + 1;
				charA = str.charAt(0);
				str = str.substring(1);
				str = str.trim();
				for(int j = 0; j < nums.length(); j++) {
					if( str.charAt(0) == nums.charAt(j) ) {
						bb = j + 1;
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	
	void Move( MessageMove msg) throws IOException, InterruptedException {
		Battle bat = ServerMain.getLobby(msg.battle - 1);
		
		String result = "";
		int a = 0, b = 0, c = 0;
		String m = msg.move.trim();
		if( ! checkMove( m )  ) {
			result = " Invalid move";
			charA = '-';
			bb = 1;
		}
		else {
			c = bat.move(bb, aa);
			if(c == 1) 
				result = "        HIT ";
			if(c == 0)
				result = "        MISS ";
			if(c == 2)
				result = "        KILL ";
		}
		//bat.changeHod();
		bat.setMove("*" + charA + (bb - 1) + "*");
		bat.setLastRes(result);
		
//		msg.os.writeObject( new MessageWaitResult(ServerMain.getLobby(msg.battle - 1).getMove(),    ServerMain.getLobby(msg.battle - 1).getPH(),
//					ServerMain.getBattle(msg.battle - 1,  bat.getOpponentNic(userNic)),  ServerMain.getLobby(msg.battle - 1).getStatus()) );
		ServerMain.interrupt(msg.battle - 1, this.userNic);	
		if(c == 0)
			bat.changeHod();
		
		os.writeObject( new MessageMoveResult( bat.getPH(), bat.getStr(this.userNic),    result , bat.getStatus()  ));
		//ServerMain.interrupt(msg.battle - 1);	
		
		
	}
	
	
	
	
	
	
	void wait( MessageWait msg) throws IOException, InterruptedException {
		while(true) {
			try {
				this.sleep(1000000);
			} catch (InterruptedException e) {
				//w = false;
				break;
			}
		}
		os.writeObject(new MessageWaitResult( ServerMain.getLobby(msg.battle - 1).getMove(),    ServerMain.getLobby(msg.battle - 1).getPH(),
					ServerMain.getBattle(msg.battle - 1,  this.userNic),  ServerMain.getLobby(msg.battle - 1).getStatus(), 
					ServerMain.getLobby(msg.battle - 1).getLastRes()  ));
	}
	
	
	
	boolean connect( MessageConnect msg ) throws IOException {
		
		ServerThread old = register( msg.userNic, msg.userFullName );
		if ( old == null )
		{
			os.writeObject( new MessageConnectResult());
			return true;
		} else {
			os.writeObject( new MessageConnectResult( 
				"User " + old.userFullName + " already connected as " + userNic ));
			return false;
		}
	}
	
	public int b = 0;
	
	@SuppressWarnings("static-access")
	void letter( MessageLetter msg ) throws IOException {
		
		ServerThread user = ServerMain.getUser( msg.usrNic );
		if ( user == null )
		{
			os.writeObject( new MessageLetterResult( ));
		} else {
			
			int k;
			while((k = ServerMain.registerWaiting( userNic, user)) == 0) {
				try {
					this.sleep(10000);
				} catch (InterruptedException e) {
					//w = false;
					break;
				}
			}
			if( k != 0)
				b = k;
			
			
			//os.writeObject(new MessageLetterResult(new String[]{" Opponent found!!  " + ServerMain.getBattle(b - 1)}, b));
			os.writeObject(new MessageLetterResult(  ServerMain.getLobby(b - 1).getPH(), 
						b,     ServerMain.getBattle(b - 1, this.userNic  ),  ServerMain.getLobby(b - 1).getStatus()
						        ));
			
			
			//ServerMain.registerBattle(userNic, user);      /////////////////////
			
		}
	}
	
	void letter( MessageCheckMail msg ) throws IOException {
		
		ServerThread user = ServerMain.getUser( msg.usrNic );
		if ( user == null )
		{
			os.writeObject( new MessageLetterResult() );
		} else {
			
			
			user.addLetter( this.userNic);
			os.writeObject( new MessageLetterResult());
			
			
			ServerMain.delWaiting(msg.usrNic);
			
		}
	}
	
	
	void user( MessageUser msg ) throws IOException {
		
		//String[] battles = ServerMain.getUsers();
		String[] battles = ServerMain.getWaiting(); 
		if ( battles != null ) 
			os.writeObject( new MessageUserResult( battles ));
		else
			os.writeObject( new MessageUserResult( "Unable to get battles list" ));
	}
	
	void checkMail( MessageCheckMail msg ) throws IOException {

//		String[] lts = getLetters(); 
//		if ( lts != null )
//			os.writeObject( new MessageCheckMailResult( lts ));
//		else
//			os.writeObject( new MessageCheckMailResult( "Unable to get mail" ));		
		//String[] battles = ServerMain.getUsers();
		String[] battles = ServerMain.getBattles(); 
		if ( battles != null ) 
			os.writeObject( new MessageUserResult( battles ));
		else
			os.writeObject( new MessageUserResult( "Unable to get battles list" ));
	}
	
	private boolean disconnected = false;
	public void disconnect() {
		if ( ! disconnected )
		try {			
			System.err.println( addr.getHostName() + " disconnected" );
			unregister();
			os.close();
			is.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.interrupt();
			disconnected = true;
		}
	}
	
	private void unregister() {
		if ( userNic != null ) {
			ServerMain.setUser( userNic, null );			
			userNic = null;
		}		
	}
	
	private ServerThread register( String nic, String name ) {
		ServerThread old = ServerMain.registerUser( nic, this );
		if ( old == null ) {
			if ( userNic == null ) {
				userNic = nic;
				userFullName = name;
				System.err.println("User \'"+ name+ "\' registered as \'"+ nic + "\'");
			}
		}
		return old;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void delWaiting( String st ) {
		ServerMain.delWaiting(st);
	}
	
	
	
	
	
	
	
	
	
	
	
}

