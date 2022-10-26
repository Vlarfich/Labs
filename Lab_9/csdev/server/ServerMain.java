package csdev.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

import csdev.*;

import csdev.Battle;

/**
 * <p>Main class of server application
 * <p>Realized in console 
 * @author Sergey Gutnikov
 * @version 1.0
 */
public class ServerMain {
	
	private static int MAX_USERS = 100;

	public static void main(String[] args) {

		try ( ServerSocket serv = new ServerSocket( Protocol.PORT  )) {
			System.err.println("initialized");
			ServerStopThread tester = new ServerStopThread();
			tester.start();
			while (true) {
				Socket sock = accept( serv );
				if ( sock != null ) {
					if ( ServerMain.getNumUsers() < ServerMain.MAX_USERS )
					{
						System.err.println( sock.getInetAddress().getHostName() + " connected" );
						ServerThread server = new ServerThread(sock);

						server.start();
					}
					else
					{
						System.err.println( sock.getInetAddress().getHostName() + " connection rejected" );
						sock.close();
					}
				} 
				if ( ServerMain.getStopFlag() ) {
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			stopAllUsers();
			System.err.println("stopped");	
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {		
		}
	}
	
	public static Socket accept( ServerSocket serv ) {	
		assert( serv != null );
		try {
			serv.setSoTimeout( 1000 );
			Socket sock = serv.accept();
			return sock;
		} catch (SocketException e) {
		} catch (IOException e) {
		}		
		return null;
	}
	
	private static void stopAllUsers() {
		String[] nic = getUsers();
		for (String user : nic ) {
			ServerThread ut = getUser( user );
			if ( ut != null ) {
				ut.disconnect();
			}
		}
	}
	
	private static Object syncFlags = new Object();
	private static boolean stopFlag = false;
	public static boolean getStopFlag() {
		synchronized ( ServerMain.syncFlags ) {
			return stopFlag;
		}
	}
	public static void setStopFlag( boolean value ) {
		synchronized ( ServerMain.syncFlags ) {
			stopFlag = value;
		}
	}
	
	private static Object syncUsers = new Object();
	private static TreeMap<String, ServerThread> users = 
			new TreeMap<String, ServerThread> ();
	
	public static ServerThread getUser( String userNic ) {
		synchronized (ServerMain.syncUsers) {
			return ServerMain.users.get( userNic );
		}		
	}

	public static ServerThread registerUser( String userNic, ServerThread user ) {
		synchronized (ServerMain.syncUsers) {
			ServerThread old = ServerMain.users.get( userNic );
			if ( old == null ) {
				ServerMain.users.put( userNic, user );
			}
			return old;
		}		
	}

	public static ServerThread setUser( String userNic, ServerThread user ) {
		synchronized (ServerMain.syncUsers) {
			ServerThread res = ServerMain.users.put( userNic, user );
			if ( user == null ) {
				ServerMain.users.remove(userNic);
			}
			return res;
		}		
	}
	
	public static String[] getUsers() {
		synchronized (ServerMain.syncUsers) {
			return ServerMain.users.keySet().toArray( new String[0] );
		}		
	}
	
	public static int getNumUsers() {
		synchronized (ServerMain.syncUsers) {
			return ServerMain.users.keySet().size();
		}		
	}
	
	
	
	
	
	
	
	
	private static Object syncWaiting = new Object();
	private static TreeMap<String, ServerThread> waiting = 
			new TreeMap<String, ServerThread> ();
	
	public static ServerThread getWait( String userNic ) {
		synchronized (ServerMain.syncWaiting) {
			return ServerMain.waiting.get( userNic );
		}		
	}

//	public static ServerThread registerWaiting( String userNic, ServerThread user ) {
//		synchronized (ServerMain.syncWaiting) {
//			ServerThread old = ServerMain.waiting.get( userNic );
//			if ( old == null ) {
//				ServerMain.waiting.put( userNic, user );
//			}
//			return old;
//		}		
//	}

	public static ServerThread setWaiting( String userNic, ServerThread user ) {
		synchronized (ServerMain.syncWaiting) {
			ServerThread res = ServerMain.waiting.put( userNic, user );
			if ( user == null ) {
				ServerMain.waiting.remove(userNic);
			}
			return res;
		}		
	}
	
	public static String[] getWaiting() {
		synchronized (ServerMain.syncWaiting) {
			String[] res = ServerMain.waiting.keySet().toArray( new String[0] );
			for(int i = 0; i < res.length; i++) {
				res[i] = " * Player " + res[i] + " is waiting for battle";
			}
			
			return res;
		}		
	}
	
	public static int getNumWaiting() {
		synchronized (ServerMain.syncWaiting) {
			return ServerMain.waiting.keySet().size();
		}		
	}
	
	protected static void delWaiting( String st ) {
		synchronized (ServerMain.syncWaiting) {
			ServerMain.waiting.remove(st);
		}	
	}

	
	
	private static Vector<Battle> battles = new Vector<Battle>();
	private static Object syncBattles = new Object();
	
	//////////////////////////////////////////////////////////////////////////////////
	public static int registerWaiting( String userNic, ServerThread user ) {
		synchronized (ServerMain.syncWaiting) {
			if(waiting.size() == 0) {
				waiting.put(userNic, user);
				return 0;
			}
			ServerThread old = waiting.get( userNic );
			if ( old == null ) {
				if(waiting.size() > 0) {
					for(String i : waiting.keySet()) {
						battles.add(new Battle(i, waiting.get(i),     userNic, user));
						waiting.get(i).b = battles.size();
						
						
						waiting.get(i).interrupt();
						waiting.remove(i);
						return battles.size();
					}
				}
			}
			return 0;
		}		
	}
	
	public static String[] getBattles() {
		synchronized (ServerMain.syncBattles) {
			String[] result = new String[battles.size()];
			for(int i = 0; i < battles.size(); i++) {
				result[i] = (battles.get(i)).toString();
			}
			
			return result;
		}		
	}
	
	public static String getBattle(int i, String s) {
		synchronized (ServerMain.syncBattles) {
			return battles.get(i).getStr(s);
		}
	}
	
	public static Battle getLobby(int i) {
		synchronized (ServerMain.syncBattles) {
			return battles.get(i);
		}
	}
	
	public static void interrupt( int i, String pl) {
		synchronized (ServerMain.syncBattles) {
			battles.get(i).getOppThr( pl ).interrupt();
		}
	}
	//////////////////////////////////////////////////////////////////////////////////	
//	
//	public static ServerThread registerBattle( String userNic, ServerThread user ) {
//		synchronized (ServerMain.syncBattles) {
//			ServerThread old = ServerMain.waiting.get( userNic );
//			if ( old != null ) {
//				Battle b = new Battle(userNic, user);
//				ServerMain.battles.add( b );
//			}
//			return old;
//		}		
//	}
//	

//	
//	protected static void delBattle(String st) {
//		ServerMain.waiting.remove(st);
//		for(int i = 0; i < battles.size(); i++) {
//			if( (battles.get(i).getStr()).equals(st) ) {
//				battles.remove(i);
//				break;
//			}
//		}
//	}
	
	
	
	
	
	
	
}

class ServerStopThread extends CommandThread {
	
	static final String cmd  = "q";
	static final String cmdL = "quit";
	
	Scanner fin; 
	
	public ServerStopThread() {		
		fin = new Scanner( System.in );
		ServerMain.setStopFlag( false );
		putHandler( cmd, cmdL, new CmdHandler() {
			@Override
			public boolean onCommand(int[] errorCode) {	return onCmdQuit(); }				
		});
		this.setDaemon(true);
		System.err.println( "Enter \'" + cmd + "\' or \'" + cmdL + "\' to stop server\n" );
	}
	
	public void run() {
		
		while (true) {			
			try {
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				break;
			}
			if ( fin.hasNextLine()== false )
				continue;
			String str = fin.nextLine();
			if ( command( str )) {
				break;
			}
		}
	}
	
	public boolean onCmdQuit() {
		System.err.print("stop server...");
		fin.close();
		ServerMain.setStopFlag( true );
		return true;
	}
}

