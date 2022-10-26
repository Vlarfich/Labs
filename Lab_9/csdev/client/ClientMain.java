package csdev.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.TreeMap;

import csdev.*;
import csdev.server.ServerThread;

/**
 * <p>Main class of client application
 * <p>Realized in console 
 * <br>Use arguments: userNic userFullName [host]
 * @author Sergey Gutnikov
 * @version 1.0
 */
public class ClientMain {
	// arguments: userNic userFullName [host]
	public static void main(String[] args)  {
		if (args.length < 2 || args.length > 3) {
			System.err.println(	"Invalid number of arguments\n" + "Use: nic name [host]" );
			waitKeyToStop();
			return;
		}
		try ( Socket sock = ( args.length == 2 ? 
				new Socket( InetAddress.getLocalHost(), Protocol.PORT ):
				new Socket( args[2], Protocol.PORT ) )) { 		
			System.err.println("initialized");
			session(sock, args[0], args[1] );
		} catch ( Exception e) {
			System.err.println(e);
		} finally {
			//ServerThread.delBattle( args[0] );
			System.err.println("bye...");
		}
	}
	
	static void waitKeyToStop() {
		System.err.println("Press a key to stop...");
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
	
	static class Session {
		boolean connected = false;
		String userNic = null;
		String userName = null;
		Session( String nic, String name ) {
			userNic = nic;
			userName = name;
		}
	}
	static void session(Socket s, String nic, String name) {
		try ( Scanner in = new Scanner(System.in);
			  ObjectInputStream is = new ObjectInputStream(s.getInputStream());
			  ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream())) {
			Session ses = new Session(nic, name);
			if ( openSession( ses, is, os, in )) { 
				try {
					while (true) {
						Message msg = getCommand(ses, in);
						if (! processCommand(ses, msg, is, os)) {
							break;
						}				
					}			
				} finally {
					closeSession(ses, os);
				}
			}
		} catch ( Exception e) {
			System.err.println(e);
		}
	}
	
	static boolean openSession(Session ses, ObjectInputStream is, ObjectOutputStream os, Scanner in) 
			throws IOException, ClassNotFoundException {
		os.writeObject( new MessageConnect(ses.userNic, ses.userName));
		MessageConnectResult msg = (MessageConnectResult) is.readObject();
		if (msg.Error()== false ) {
			System.err.println("connected");
			ses.connected = true;
			return true;
		}
		System.err.println("Unable to connect: "+ msg.getErrorMessage());
		System.err.println("Press <Enter> to continue...");
		if( in.hasNextLine())
			in.nextLine();
		return false;
	}
	
	static void closeSession(Session ses, ObjectOutputStream os) throws IOException {
		if ( ses.connected ) {
			ses.connected = false;
			os.writeObject(new MessageDisconnect());
		}
	}
	static boolean f = true;

	static Message getCommand(Session ses, Scanner in) {	
		while (true) {
			printPrompt();
			if (in.hasNextLine()== false)
				break;
			String str = in.nextLine();
			byte cmd = translateCmd(str);
			switch ( cmd ) {
				case -1:
					return null;
				case Protocol.CMD_CHECK_MAIL:
				{
					MessageCheckMail mas = inputPlay(in);
					f = false;
					return mas;
				}
				case Protocol.CMD_USER:
					return new MessageUser();
				case Protocol.CMD_LETTER:
					{
						MessageLetter mas = inputLetter(ses.userNic);
						return mas;
					}
				case 0:
					continue;
				default: 
					System.err.println("Unknow command!");
					continue;
			}
		}
		return null;
	}
	
	
	static boolean invite = false;
	
	
	static MessageLetter inputLetter(String st) {
		String usrNic;
		usrNic = st;
		System.out.println("  Waiting for someone to connect ...");
		return new MessageLetter(usrNic, "lets play together");
	}
	
	static MessageCheckMail inputPlay(Scanner in) {
		String usrNic;
		System.out.print("Enter user NIC: ");
		usrNic = in.nextLine();
		//System.out.print("Enter message : ");
		//letter = in.nextLine();
		
		return new MessageCheckMail(usrNic, "lets play together");
	}
	
	static TreeMap<String,Byte> commands = new TreeMap<String,Byte>();
	static {
		commands.put("q", new Byte((byte) -1));
		commands.put("quit", new Byte((byte) -1));
		commands.put("u", new Byte(Protocol.CMD_USER));
		commands.put("users", new Byte(Protocol.CMD_USER));
		commands.put("s", new Byte(Protocol.CMD_LETTER));
		commands.put("start", new Byte(Protocol.CMD_LETTER));
	}
	
	static byte translateCmd(String str) {
		// returns -1-quit, 0-invalid cmd, Protocol.CMD_XXX
		str = str.trim();
		Byte r = commands.get(str);
		return (r == null ? 0 : r.byteValue());
	}
	
	static void printPrompt() {
		System.out.println();
		System.out.print("(q)uit/(u)sers/(s)start >");
		System.out.flush();
	}
	
	static boolean hod = false;
	static int bat = 0;
	
	static boolean processCommand(Session ses, Message msg, 
			                      ObjectInputStream is, ObjectOutputStream os) 
            throws IOException, ClassNotFoundException {
		if ( msg != null )
		{
			os.writeObject(msg);			
			MessageResult res = (MessageResult) is.readObject();
			if ( res.Error()) {
				System.err.println("ERROR");
				System.err.println(res.getErrorMessage());
			} else {
				switch (res.getID()) {
					case Protocol.CMD_CHECK_MAIL:
						printMail((MessageCheckMailResult) res);
						break;
					case Protocol.CMD_USER:
						printUsers(( MessageUserResult ) res);
						break;
					case Protocol.CMD_LETTER:
						//printLetter((MessageLetterResult) res);
						//   Battle started:
						
						bat = ((MessageLetterResult) res).bat;
						String pl = ((MessageLetterResult) res).player;						
						
						Scanner in = new Scanner(System.in);
						String field = ((MessageLetterResult) res).field;
						System.out.println();
						System.out.println(field);
						
						int status = ((MessageLetterResult) res).status;
						
						
						
						while( status != 0 ) {
							if ( !pl.equals(ses.userNic) ) {
								wait( ses, is, os);
								res = (MessageWaitResult) is.readObject();
								field = ((MessageWaitResult) res).field;
								status = ((MessageWaitResult) res).status;
								
								System.err.println(" Your opponent made a move: " + ((MessageWaitResult) res).move );
								System.err.println( ((MessageWaitResult) res).result );
								/////////////////////////
								System.out.println(field);
								/////////////////////////
								pl = ((MessageWaitResult) res).player;
//								if(!pl.equals(ses.userNic))
//									continue;
							}
							else{
								System.out.println("Your TURN!!!");
								move(is, os, in);
								
								res = (MessageMoveResult) is.readObject();
								
								pl = ((MessageMoveResult) res).player;
								field = ((MessageMoveResult) res).field;
								status = ((MessageMoveResult) res).status;
								
								System.err.println("  You made a move ");
								System.err.println(((MessageMoveResult) res).result);
								if(((MessageMoveResult) res).r == -1) {
									System.err.println("  It's now " + pl + "'s move   :(");
								}
								else {
									System.out.println(field);
								}
								
							}
						}
						System.err.println("  Battle ended\n  Player " + pl + " won" );
						break;
						
					default:
						assert(false);
						break;
				}
			}
			return true;
		}
		return false;
	}
	
	static String player = "";
	
	static int move(ObjectInputStream is, ObjectOutputStream os, Scanner in) throws IOException, ClassNotFoundException {
		String m;
		System.out.println(" What is your next move?\n...");
		m = in.nextLine();
		
		os.writeObject(new MessageMove(m, bat));
		
		
		return 0;
	}
	
	
	static void wait( Session ses, ObjectInputStream is, ObjectOutputStream os) throws IOException {
		System.out.println(" Waiting for your opponents move...");
		
		os.writeObject( new MessageWait( ses.userNic, bat));
	}
	
	
	
	static void printMail(MessageCheckMailResult m) {

		System.out.println(m.letters[0]);
		
	}
	
//	static void printLetter(MessageLetterResult res){
//		System.out.println(res.letters[0]);
//	}
	
	static void printUsers(MessageUserResult m) {
		if ( m.userNics != null ) {
			int i = 0;
			System.out.println("Users searching for battle {");
			for (String str: m.userNics) {
				System.out.println("\t" + str);
				i++;
			}	
			if(i == 0)
				System.out.println("\t*Sorry nobody is looking for battle right now");
			System.out.println("}");
		}
	}
}
