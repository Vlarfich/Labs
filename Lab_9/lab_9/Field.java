package lab_9;

import java.util.Scanner;

public class Field 
{
	// ÔÓ·ËÚËÂ - X
	// ÏËÏÓ .
	// clear - clear
	
	String nic;
	char[][] answer = new char[][] 
		{
		        {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'},
				{'0', '#', '#', ' ', '#', ' ', ' ', '#', '#', '#', '#'},
				{'1', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' '},
				{'2', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#'},
				{'3', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
				{'4', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'5', ' ', '#', ' ', '#', ' ', ' ', '#', ' ', ' ', ' '},
				{'6', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'7', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'8', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', '#', ' '},
		        {'9', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' '}
		};
	static char[][] current = new char[][]
			{
		        {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'},
				{'0', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'1', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'2', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'3', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'4', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'5', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'6', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'7', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'8', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
		        {'9', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
			};
	//
	public Field(String nic)
	{
		this.nic = nic;
	}
	//
	public void Shot(Scanner in)
	{
		String cr;
		int i, j;
		//
		System.out.print("Input coordinates: ");
		cr = in.nextLine();
		//
		j = (int) cr.charAt(0) - 64;
		i = Integer.parseInt(cr.substring(1)) + 1;
		
		try
		{
			if(i < 1 || i > 10 || j < 1 || i > 10)
			{ throw new Exception("Error coordinates! Check them..."); }
		
			if(answer[i][j] == '#')
			{
				System.out.println("Penetration!");
				current[i][j] = '#';
			}
			else
			{
				System.out.println("Away...");
				current[i][j] = '.';
			}
		}//ƒ¬Œ…ÕŒ≈ œŒœ¿ƒ¿Õ»≈
		catch(Exception e) {System.out.println(e);}
	}
	
	//
	public void PrintField()
	{
		for(int i = 0; i < current.length; i++)
		{
			for(int j = 0; j < current[i].length; j++)
				System.out.print(current[i][j] + "\t");
			System.out.println();
		}
	}
}
