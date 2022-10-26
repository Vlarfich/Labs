package ttt.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

import ttt.*;

/**
 * <p>
 * Main class of server application
 * <p>
 * Realized in console
 * 
 * @author Alexander Litvinchik
 * @version 1.0
 */
public class ServerLitvinchik {

	private static int MAX_USERS = 100;

	public static void main(String[] args) {

		try (ServerSocket serv = new ServerSocket(Protocol.PORT)) {
			System.err.println("initialized");
			ServerStopThread tester = new ServerStopThread();
			tester.start();
			while (true) {
				Socket sock = accept(serv);
				if (sock != null) {
					if (ServerLitvinchik.getNumUsers() < ServerLitvinchik.MAX_USERS) {
						System.err.println(sock.getInetAddress().getHostName() + " connected");
						ServerThread server = new ServerThread(sock);
						server.start();
					} else {
						System.err.println(sock.getInetAddress().getHostName() + " connection rejected");
						sock.close();
					}
				}
				if (ServerLitvinchik.getStopFlag()) {
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

	public static Socket accept(ServerSocket serv) {
		assert (serv != null);
		try {
			serv.setSoTimeout(1000);
			Socket sock = serv.accept();
			return sock;
		} catch (SocketException e) {
		} catch (IOException e) {
		}
		return null;
	}

	private static void stopAllUsers() {
		String[] nic = getUsers();
		for (String user : nic) {
			ServerThread ut = getUser(user);
			if (ut != null) {
				ut.disconnect();
			}
		}
	}

	private static Object syncFlags = new Object();
	private static boolean stopFlag = false;

	public static boolean getStopFlag() {
		synchronized (ServerLitvinchik.syncFlags) {
			return stopFlag;
		}
	}

	public static void setStopFlag(boolean value) {
		synchronized (ServerLitvinchik.syncFlags) {
			stopFlag = value;
		}
	}

	private static Object syncUsers = new Object();
	private static TreeMap<String, ServerThread> users = new TreeMap<String, ServerThread>();

	public static ServerThread getUser(String userNic) {
		synchronized (ServerLitvinchik.syncUsers) {
			return ServerLitvinchik.users.get(userNic);
		}
	}

	public static ServerThread registerUser(String userNic, ServerThread user) {
		synchronized (ServerLitvinchik.syncUsers) {
			ServerThread old = ServerLitvinchik.users.get(userNic);
			if (old == null) {
				ServerLitvinchik.users.put(userNic, user);
			}
			return old;
		}
	}

	public static ServerThread setUser(String userNic, ServerThread user) {
		synchronized (ServerLitvinchik.syncUsers) {
			ServerThread res = ServerLitvinchik.users.put(userNic, user);
			if (user == null) {
				ServerLitvinchik.users.remove(userNic);
			}
			return res;
		}
	}

	public static String[] getUsers() {
		synchronized (ServerLitvinchik.syncUsers) {
			return ServerLitvinchik.users.keySet().toArray(new String[0]);
		}
	}

	public static int getNumUsers() {
		synchronized (ServerLitvinchik.syncUsers) {
			return ServerLitvinchik.users.keySet().size();
		}
	}
}

class ServerStopThread extends CommandThread {

	static final String cmd = "q";
	static final String cmdL = "quit";
	Scanner fin;

	public ServerStopThread() {
		fin = new Scanner(System.in);
		ServerLitvinchik.setStopFlag(false);
		putHandler(cmd, cmdL, new CmdHandler() {
			@Override
			public boolean onCommand(int[] errorCode) {
				return onCmdQuit();
			}
		});
		this.setDaemon(true);
		System.err.println("Enter \'" + cmd + "\' or \'" + cmdL + "\' to stop server\n");
	}

	public void run() {

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
			if (fin.hasNextLine() == false)
				continue;
			String str = fin.nextLine();
			if (command(str)) {
				break;
			}
		}
	}

	public boolean onCmdQuit() {
		System.err.print("stop server...");
		fin.close();
		ServerLitvinchik.setStopFlag(true);
		return true;
	}
}

class ServerThread extends Thread {

	private Socket sock;
	ObjectOutputStream os;
	ObjectInputStream is;
	private InetAddress addr;
	static Vector<Game> games = new Vector<Game>();

	String userNic = null;
	private String userFullName;
	public boolean connectedToGame;

	private static Object syncGame = new Object();

	public ServerThread(Socket s) throws IOException {
		sock = s;
		s.setSoTimeout(1000);
		os = new ObjectOutputStream(s.getOutputStream());
		is = new ObjectInputStream(s.getInputStream());
		addr = s.getInetAddress();
		this.setDaemon(true);
	}

	public void run() {
		try {
			while (true) {
				Message msg = null;
				try {
					msg = (Message) is.readObject();
				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
				}
				if (msg != null)
					switch (msg.getID()) {

					case Protocol.CMD_CONNECT:
						if (!connect((MessageConnect) msg))
							return;
						break;

					case Protocol.CMD_DISCONNECT:
						return;

					case Protocol.CMD_USER:
						user((MessageUser) msg);
						break;

					case Protocol.CMD_ANSWER:
						checkAnswer((MessageAnswer) msg);
						break;

					case Protocol.CMD_CHECK_PLAY:
						func((MessageCheckPlay) msg);
						break;

					case Protocol.CMD_PLAY:
						check((MessagePlay) msg);
						break;

					case Protocol.CMD_CHECK_CHOISE:
						CheckChoise((MessageChoise) msg);
						break;

					case Protocol.CMD_MATRIX:
						Matrix((MessageMatrix) msg);
						break;
					case Protocol.CMD_GAME:
						DelGame((MessageGame) msg);
						break;
					}
			}
		} catch (IOException e) {
			System.err.print("Disconnect...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	void DelGame(MessageGame msg) throws IOException {
		synchronized (syncGame) {
			String nick1, nick2;
			for (int i = 0; i < games.size(); i++) {
				nick1 = games.get(i).players[0].userNic;
				nick2 = games.get(i).players[1].userNic;
				if (nick1.equals(msg.nick1)) {
					ServerThread user1 = ServerLitvinchik.getUser(nick1);
					ServerThread user2 = ServerLitvinchik.getUser(nick2);
					user1.connectedToGame = false;
					user2.connectedToGame = false;
					games.remove(i);
					break;
				}
			}
		}
	}

	void CheckChoise(MessageChoise msg) throws Exception {
		synchronized (syncGame) {
			String nick1, nick2;
			for (int i = 0; i < games.size(); i++) {
				nick1 = games.get(i).players[0].userNic;
				nick2 = games.get(i).players[1].userNic;
				if (msg.nick.equals(nick1) || msg.nick.equals(nick2)) {
					games.get(i).add_el(msg.row, msg.column);
					break;
				}
			}
		}
	}

	void Matrix(MessageMatrix msg) throws IOException {
		os.writeObject(new MessageMatrixResult(msg.table));
	}

	void func(MessageCheckPlay msg) throws IOException {
	}

	void checkAnswer(MessageAnswer msg) throws IOException {
		ServerThread user1 = ServerLitvinchik.getUser(msg.nick1);
		user1.os.writeObject(new MessagePlayResult(msg.answer, msg.nick1, msg.nick2));
		ServerThread user2 = ServerLitvinchik.getUser(msg.nick2);

		synchronized (syncGame) {
			if (msg.answer == true) {
				user1.connectedToGame = true;
				user2.connectedToGame = true;
				games.add(new Game(user1, user2));
			}
		}
	}

	void check(MessagePlay msg) throws IOException {
		ServerThread user = ServerLitvinchik.getUser(msg.nick2);
		if (user == null) {
			os.writeObject(new MessagePlayResult("User " + msg.nick2 + " is not found"));
		} else if (user.userNic == this.userNic) {
			os.writeObject(new MessagePlayResult("you can't play with yourself"));
		} else if (user.connectedToGame) {
			os.writeObject(new MessagePlayResult("User " + msg.nick2 + " connected to other game session"));
		} else {
			user.os.writeObject(new MessageAnswerResult(msg.nick1, msg.nick2));
		}
	}

	boolean connect(MessageConnect msg) throws IOException {

		ServerThread old = register(msg.userNic, msg.userFullName);
		if (old == null) {
			os.writeObject(new MessageConnectResult());
			return true;
		} else {
			os.writeObject(new MessageConnectResult("User " + old.userFullName + " already connected as " + userNic));
			return false;
		}
	}

	void user(MessageUser msg) throws IOException {

		String[] nics = ServerLitvinchik.getUsers();
		if (nics != null)
			os.writeObject(new MessageUserResult(nics));
		else
			os.writeObject(new MessageUserResult("Unable to get users list"));
	}

	public boolean disconnected = false;

	public void disconnect() {
		if (!disconnected)
			try {
				System.err.println(addr.getHostName() + " disconnected");
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
		if (userNic != null) {
			ServerLitvinchik.setUser(userNic, null);
			userNic = null;
		}
	}

	private ServerThread register(String nic, String name) {
		ServerThread old = ServerLitvinchik.registerUser(nic, this);
		if (old == null) {
			if (userNic == null) {
				userNic = nic;
				userFullName = name;
				System.err.println("User \'" + name + "\' registered as \'" + nic + "\'");
			}
		}
		return old;
	}
}
