package ttt.client;

import ttt.Protocol;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;
/*import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;*/

import ttt.*;

/**
 * <p>
 * Main class of client application
 * <p>
 * Realized in console <br>
 * Use arguments: userNic userFullName [host]
 * 
 * @author Yuzefovich Kirill
 * @version 1.0
 */
public class ClientYuzefovich {
	public static String userNic = null;
	public static String userName = null;
	public static boolean connected = false;

	// arguments: userNic userFullName [host]
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 3) {
			System.err.println("Invalid number of arguments\n" + "Use: nic name [host]");
			waitKeyToStop();
			return;
		}
		try (Socket sock = (args.length == 2 ? new Socket(InetAddress.getLocalHost(), Protocol.PORT)
				: new Socket(args[2], Protocol.PORT))) {
			System.err.println("initialized");
			session(sock, args[0], args[1]);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
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
		Session(String nic, String name) {
			userNic = new String(nic);
			userName = name;
		}
	}

	static void session(Socket s, String nic, String name) {
		try (Scanner in = new Scanner(System.in);
				ObjectInputStream is = new ObjectInputStream(s.getInputStream());
				ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream())) {
			Session ses = new Session(nic, name);
			if (openSession(ses, is, os, in)) {
				try {
					while (true) {
						Message msg = getCommand(ses, in);
						if (msg != null)
							Protocol.testMessage(msg);
						if (!processCommand(ses, in, msg, is, os)) {
							break;
						}
					}
				} finally {
					closeSession(ses, os);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	static boolean openSession(Session ses, ObjectInputStream is, ObjectOutputStream os, Scanner in)
			throws IOException, ClassNotFoundException {
		os.writeObject(new MessageConnect(userNic, userName));
		MessageConnectResult msg = (MessageConnectResult) is.readObject();
		if (msg.Error() == false) {
			System.err.println("connected");
			connected = true;
			return true;
		}
		System.err.println("Unable to connect: " + msg.getErrorMessage());
		System.err.println("Press <Enter> to continue...");
		if (in.hasNextLine())
			in.nextLine();
		return false;
	}

	static void closeSession(Session ses, ObjectOutputStream os) throws IOException {
		if (connected) {
			connected = false;
			os.writeObject(new MessageDisconnect());
		}
	}

	static Message getCommand(Session ses, Scanner in) throws IOException {
		while (true) {
			printPrompt();
			if (in.hasNextLine() == false)
				break;
			String str = in.nextLine();
			byte cmd = translateCmd(str);
			switch (cmd) {
			case -1:
				return null;
			case Protocol.CMD_USER:
				return new MessageUser();
			case Protocol.CMD_PLAY:
				return inputToPlay(in);
			case Protocol.CMD_CHECK_PLAY:
				System.out.println("Приглашения в игру:");
				return new MessageCheckPlay();
			case 0:
				continue;
			default:
				System.err.println("Unknown command!");
				continue;
			}
		}
		return null;
	}

	static MessagePlay inputToPlay(Scanner in) {
		String usrNic;
		System.out.print("Enter user NIC: ");
		usrNic = in.nextLine();
		return new MessagePlay(userNic, usrNic);
	}

	static TreeMap<String, Byte> commands = new TreeMap<String, Byte>();
	static {
		commands.put("q", new Byte((byte) -1));
		commands.put("quit", new Byte((byte) -1));
		commands.put("p", new Byte(Protocol.CMD_PLAY));
		commands.put("play", new Byte(Protocol.CMD_PLAY));
		commands.put("u", new Byte(Protocol.CMD_USER));
		commands.put("users", new Byte(Protocol.CMD_USER));
		commands.put("c", new Byte(Protocol.CMD_CHECK_PLAY));
		commands.put("check", new Byte(Protocol.CMD_CHECK_PLAY));
	}

	static byte translateCmd(String str) {
		// returns -1-quit, 0-invalid cmd, Protocol.CMD_XXX
		str = str.trim();
		Byte r = commands.get(str);
		return (r == null ? 0 : r.byteValue());
	}

	static void printPrompt() {
		System.out.println();
		System.out.print(userNic + " (q)uit/(p)lay/(u)sers/(c)heck[Invitation] >");
		System.out.flush();
	}

	static boolean processCommand(Session ses, Scanner in, Message msg, ObjectInputStream is, ObjectOutputStream os)
			throws IOException, ClassNotFoundException, InterruptedException, JAXBException {
		if (msg != null) {
			os.writeObject(msg);
			MessageResult res = (MessageResult) is.readObject();
			if (res != null)
				Protocol.testMessage(res);
			if (res.Error()) {
				System.err.println(res.getErrorMessage());
			} else {
				switch (res.getID()) {
				case Protocol.CMD_CHECK_PLAY:
					break;
				case Protocol.CMD_PLAY:
					takeAnswer((MessagePlayResult) res, in, ses, is, os);
					break;
				case Protocol.CMD_USER:
					printUsers((MessageUserResult) res);
					break;
				case Protocol.CMD_ANSWER:
					invitationToPlay((MessageAnswerResult) res, in, ses, is, os);
					break;
				default:
					assert (false);
					break;
				}
			}
			return true;
		}
		return false;
	}

	private static void takeAnswer(MessagePlayResult m, Scanner in, Session ses, ObjectInputStream is,
			ObjectOutputStream os) throws ClassNotFoundException, IOException, InterruptedException, JAXBException {
		if (m.answer == true) {
			System.out.println("Player " + m.nick2 + " accepted your invitation");
			System.out.println("Connecting to game...");
			connectToGame(userNic, m.nick2, in, ses, is, os);
			System.out.println("Game ended");
		} else {
			System.out.println("Player " + m.nick2 + " rejected your invitation");
		}
	}

	static void invitationToPlay(MessageAnswerResult m, Scanner in, Session ses, ObjectInputStream is,
			ObjectOutputStream os) throws IOException, ClassNotFoundException, InterruptedException, JAXBException {

		System.out.println("Do you want to play with " + m.nick1 + "?\n 1-yes, 0-no");
		int answer;
		do {
			answer = in.nextInt();
			if (answer != 1 && answer != 0)
				System.out.println("Invalid value, try again");
		} while (answer != 1 && answer != 0);

		boolean res = answer == 1 ? true : false;
		os.writeObject(new MessageAnswer(res, m.nick1, m.nick2));
		if (res == true) {
			System.out.println("Connecting to game...");
			connectToGame(m.nick1, userNic, in, ses, is, os);
			System.out.println("Game ended");
		}
		// }
	}

	static void printUsers(MessageUserResult m) {
		if (m.userNics != null) {
			System.out.println("Users {");
			for (String str : m.userNics) {
				System.out.println("\t" + str);
			}
			System.out.println("}");
		}
	}

	/////////////////////// GAME
	private static void connectToGame(String userNic, String opponentNic, Scanner in, Session ses, ObjectInputStream is,
			ObjectOutputStream os) throws IOException, ClassNotFoundException, InterruptedException, JAXBException {
		System.out.println("Connected to game!");
		while (true) {
			if (!processGameCommand(ses, in, is, os)) {
				break;
			}
		}
	}

	static boolean processGameCommand(Session ses, Scanner in, ObjectInputStream is, ObjectOutputStream os)
			throws IOException, ClassNotFoundException, InterruptedException, JAXBException {

		MessageResult msg = (MessageResult) is.readObject();
		Protocol.testMessage(msg);
		switch (msg.getID()) {
		case Protocol.CMD_CHECK_CHOISE:
			CheckRes((MessageChoiseResult) msg, in, os);
			// получение координат точки
			break;

		case Protocol.CMD_MATRIX:
			OutMatrix((MessageMatrixResult) msg);
			// вывод матрицы
			break;

		case Protocol.CMD_GAME:
			GameResult((MessageGameResult) msg, os);
			return false;
		default:
			assert (false);
			break;
		}
		return true;
	}

	static void CheckRes(MessageChoiseResult res, Scanner in, ObjectOutputStream os) throws IOException {
		String err = res.Message;
		if (!err.equals(" "))
			System.out.println(err);

		System.out.print("Write row and column: ");
		int i = 0, j = 0;

		i = in.nextInt();
		j = in.nextInt();

		os.writeObject(new MessageChoise(userNic, i, j));
	}

	static void OutMatrix(MessageMatrixResult res) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(res.table[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	static void GameResult(MessageGameResult res, ObjectOutputStream os) throws IOException {
		System.out.println(res.result);
		os.writeObject(new MessageGame(userNic));

	}
}
