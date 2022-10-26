package ttt.server;

import java.io.IOException;

import ttt.MessageChoiseResult;
import ttt.MessageGameResult;
import ttt.MessageMatrixResult;

/** 
 * <p>
 * Game class of application
 * <p>
 * Realized in console <br>
 *
 * @author Yuzefovich Kirill
 * @version 1.0
 */
public class Game {

	Game(ServerThread user1, ServerThread user2) throws IOException {
		players = new ServerThread[2];
		players[0] = user1;
		players[1] = user2;
		move1 = true;
		move2 = false;
		players[0].os.writeObject(new MessageChoiseResult(" "));
	}

	public String[][] table = { { "_", "_", "_" }, { "_", "_", "_" }, { "_", "_", "_" } };
	public ServerThread[] players;
	public int moves = 0;
	public int gameNumber;
	boolean move1;
	boolean move2;

	public void add_el(int row, int col) throws Exception {

		if (row > 0 && row < 4 && col > 0 && col < 4 && chek(row, col) == true) {
			moves++;

			if (move1 == true) {
				move1 = false;
				move2 = true;
				table[row - 1][col - 1] = "X";
				if (chek_win("X") == true) {
					players[0].os.writeObject(new MessageMatrixResult(table));
					players[1].os.writeObject(new MessageMatrixResult(table));
					players[0].os.writeObject(new MessageGameResult("You won"));
					players[1].os.writeObject(new MessageGameResult("You lose"));
				} else if (moves == 9) {
					players[0].os.writeObject(new MessageMatrixResult(table));
					players[1].os.writeObject(new MessageMatrixResult(table));
					players[0].os.writeObject(new MessageGameResult("You have a draw"));
					players[1].os.writeObject(new MessageGameResult("You have a draw"));
				} else {
					players[0].os.writeObject(new MessageMatrixResult(table));
					players[1].os.writeObject(new MessageMatrixResult(table));
					players[1].os.writeObject(new MessageChoiseResult(" "));
				}

			} else {
				move1 = true;
				move2 = false;
				table[row - 1][col - 1] = "O";
				if (chek_win("O") == true) {
					players[1].os.writeObject(new MessageMatrixResult(table));
					players[0].os.writeObject(new MessageMatrixResult(table));
					players[0].os.writeObject(new MessageGameResult("You lose"));
					players[1].os.writeObject(new MessageGameResult("You won"));
				} else if (moves == 9) {
					players[0].os.writeObject(new MessageMatrixResult(table));
					players[1].os.writeObject(new MessageMatrixResult(table));
					players[0].os.writeObject(new MessageGameResult("You have a draw"));
					players[1].os.writeObject(new MessageGameResult("You have a draw"));
				} else {
					players[1].os.writeObject(new MessageMatrixResult(table));
					players[0].os.writeObject(new MessageMatrixResult(table));
					players[0].os.writeObject(new MessageChoiseResult(" "));
				}
			}
		} else {
			if (move1)
				players[0].os.writeObject(new MessageChoiseResult("Invalid coordinates or cell filled, try again"));
			else
				players[1].os.writeObject(new MessageChoiseResult("Invalid coordinates or cell filled, try again"));
		}
	}
	

	public Boolean chek(int row, int col) {
		if (this.table[row - 1][col - 1] == "_")
			return true;
		else
			return false;

	}

	public Boolean chek_win(String symbol) {
		int k = 0;

		for (int i = 0; i < 3; i++) {
			k = 0;
			for (int j = 0; j < 3; j++) {
				if (this.table[i][j] == symbol) {
					k++;
					if (k == 3)
						return true;
				}
			}
		}

		for (int j = 0; j < 3; j++) {
			k = 0;
			for (int i = 0; i < 3; i++)//
			{
				if (this.table[i][j] == symbol) {
					k++;
					if (k == 3)
						return true;
				}
			}
		}

		k = 0;
		for (int i = 0; i < 3; i++) {
			if (this.table[i][i] == symbol)
				k++;
		}
		if (k == 3)
			return true;

		k = 0;
		for (int i = 0; i < 3; i++) {
			if (this.table[i][2 - i] == symbol)
				k++;
		}
		if (k == 3)
			return true;

		return false;
	}

}
