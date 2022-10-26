package csdev;

import csdev.server.ServerThread;

public class Battle {
	private String player1;
	private String player2;
	
	private ServerThread st1;
	private ServerThread st2;
	
	private int status = 1;
	private int m1 = 21;
	private int m2 = 21;
	
	private char[][] pl1F =
		{
			{' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'},
			{'0', '#', '#', ' ', '#', ' ', ' ', '#', '#', '#', '#'},
			{'1', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' '},
			{'2', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#'},
			{'3', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
			{'4', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{'5', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' '},
			{'6', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{'7', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{'8', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', '#', ' '},
	        {'9', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' '}
		};
	private char[][] pl1Ef =
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
	
	private char[][] pl2F =
		{
			{' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'},
			{'0', '#', '#', ' ', '#', ' ', ' ', '#', '#', '#', '#'},
			{'1', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' '},
			{'2', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#'},
			{'3', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
			{'4', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{'5', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' '},
			{'6', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{'7', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{'8', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', '#', ' '},
	        {'9', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' '}
		};
	private char[][] pl2Ef =
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
	
	
	
	private int hod = 1;
	
	
	private String lastMove = "";
	private String lastRes = "";
	
	
	public Battle(String pl1, ServerThread s1, String pl2, ServerThread s2){
		player1 = pl1;
		st1 = s1;
		player2 = pl2;
		st2 = s2;
	}
	
	public void SetP2(String pl, ServerThread st) {
		player2 = pl;
		st2 = st;
	}
	
	public String toString() {
		return new String("Player " + player1 + " is waiting for battle");
	}
	
//	public ServerThread getThr1() {
//		return st1;
//	}
//	public ServerThread getThr2() {
//		return st2;
//	}
	public ServerThread getThr() {
		return (hod == 1) ? st1 : st2 ;
	}
	public ServerThread getOppThr( String str) {
		if(str.equals(player1))
			return st2;
		else
			return st1;
	}
	
	
	public String getStr(String s) {
		
		StringBuilder res = new StringBuilder("         YOU                                         OPPONENT\n");
		if(s.equals(player1)) {
			for(int i = 0; i < pl1F.length; i++) {
				for(int j = 0; j < pl1F[i].length; j++) {
					res.append(pl1F[i][j] + " ");
				}
				res.append("                      ");
				for(int j = 0; j < pl1Ef[i].length; j++) {
					res.append(pl1Ef[i][j] + " ");
				}
				res.append("\n");
			}
		}
		else {
			for(int i = 0; i < pl2F.length; i++) {
				for(int j = 0; j < pl2F[i].length; j++) {
					res.append(pl2F[i][j] + " ");
				}
				res.append("                      ");
				for(int j = 0; j < pl2Ef[i].length; j++) {
					res.append(pl2Ef[i][j] + " ");
				}
				res.append("\n");
			}
		}
		
		if(hod == 1) 
			res.append("\n            " + player1 + "'s turn\n");
		else
			res.append("\n            " + player2 + "'s turn\n");
		
		
		return new String(player1 + " vs " + player2 + "\n" + res);
	}
	
	
	public int getHod() {
		return hod;
	}
	
	public void changeHod() {
		hod = (hod == 1) ? 2 : 1;
	}
	
	public String getPH() {
		return (hod == 1) ? player1 : player2;
	}
	
	public boolean getHod(String str) {
		return (str.equals(getPH()));
	}
	
//	public ServerThread getOpponentThread( String s) {
//		if( s.equals(player1) )
//			return st2;
//		else
//			return st1;
//	}
	public String getOpponentNic( String s) {
		if( s.equals(player1) )
			return player2;
		else
			return player1;
	}
	
	public String getMove() {
		return lastMove;
	}
	public void setMove( String s ) {
		lastMove = s;
	}
	public String getLastRes() {
		return lastRes;
	}
	public void setLastRes( String s) {
		lastRes = s;
	}
	
	public int move(int a, int b) {
		if(hod == 1) {
			if( this.pl2F[a][b] == '#'  ||  this.pl2F[a][b] == 'x') {
				this.pl2F[a][b] = 'x';
				this.pl1Ef[a][b] = 'x';
				
				m2 --;
				if(procMove(a, b, this.pl2F, this.pl1Ef)) {
					if(m2 <= 0)
						this.status = 0;
					return 2;
				}
				if(m2 <= 0)
					this.status = 0;
				return 1;
			}
			else {
				this.pl2F[a][b] = 'o';
				this.pl1Ef[a][b] = 'o';
				return 0;
			}
		}
		else {
			if(this.pl1F[a][b] == '#'  ||  this.pl1F[a][b] == 'x') {
				this.pl1F[a][b] = 'x';
				this.pl2Ef[a][b] = 'x';
				m1 --;
				if(procMove(a, b, this.pl1F, this.pl2Ef)) {
					if( m1 <= 0 )
						this.status = 0;
					return 2;
				}
				if( m1 <= 0 )
					this.status = 0;
				return 1;
			}
			else {
				this.pl1F[a][b] = 'o';
				this.pl2Ef[a][b] = 'o';
				return 0;
			}
		}
	}
	
	public int getStatus() {
		return status;
	}
	
	
	static private boolean procMove(int a, int b, char[][] f, char[][] f2) {
		boolean vert = false;
		boolean horiz = false;
		
		if( a > 1 ) {
			if(f[a - 1][b] == 'x') vert = true;
			if(f[a - 1][b] == '#') return false;
		}
		if( a < f.length - 1) {
			if(f[a + 1][b] == 'x') vert = true;
			if(f[a + 1][b] == '#') return false;
		}
		if( b > 1 ) {
			if(f[a][b - 1] == 'x') horiz = true;
			if(f[a][b - 1] == '#') return false;
		}
		if( b < f.length - 1) {
			if(f[a][b + 1] == 'x') horiz = true;
			if(f[a][b + 1] == '#') return false;
		}
		
		if( !( horiz || vert )) {
			if( a - 1 > 0 ) {
				f[a - 1][b] = 'o';
				f2[a - 1][b] = 'o';
				if(b - 1 > 0) {
					f[a - 1][b - 1] = 'o';
					f2[a - 1][b - 1] = 'o';
					
					f[a][b - 1] = 'o';
					f2[a][b - 1] = 'o';
				}
				if(b + 1 < f.length ) {
					f[a - 1][b + 1] = 'o';
					f2[a - 1][b + 1] = 'o';
					
					f[a][b + 1] = 'o';
					f2[a][b + 1] = 'o';

				}
			}
			if( a + 1 < f.length ) {
				f[a + 1][b] = 'o';
				f2[a + 1][b] = 'o';
				if(b - 1 > 0) {
					f[a + 1][b - 1] = 'o';
					f2[a + 1][b - 1] = 'o';
				}
				if(b + 1 < f.length ) {
					f[a + 1][b + 1] = 'o';
					f2[a + 1][b + 1] = 'o';
				}
			}
			
		}
		
		
		
		if( vert ) {
			int l = 0;
			for( int i = a - 1; i > 0 ; i--, l++ ) {
				if( f[i][b] == '#' )
					return false;
				if( f[i][b] == ' ' ) break;
			}
			
			int r = 0;
			for( int i = a + 1; i < f.length ; i++, r++ ) {
				if( f[i][b] == '#' )
					return false;
				if( f[i][b] == ' ' ) break;
			}
			
			if(b > 1) {
				for( int i = a - l - 1; i <= a + r + 1; i++) {
					if( i > 0   &&  i < f.length ) {
						f[i][b - 1] = 'o';
						f2[i][b - 1] = 'o';
					}
				}
			}
			if(b < f.length - 1) {
				for( int i = a - l - 1; i <= a + r + 1; i++) {
					if( i > 0   &&  i < f.length ) {
						f[i][b + 1] = 'o';
						f2[i][b + 1] = 'o';
					}
				}
			}
			
			if( a - l - 1 > 0 ) {
				f[a - l - 1][b] = 'o';
				f2[a - l - 1][b] = 'o';
			}
			
			if( a + r + 1 < f.length ) {
				f[a + r + 1][b] = 'o';
				f2[a + r + 1][b] = 'o';
			}
			
//			if(l != 0) {
//				if( a - l > 0) {
//					f[a - l][b] = 'o';
//					f2[a - l][b] = 'o';
//				}
//				if( a - l - 1 > 0) {
//					f[a - l - 1][b] = 'o';
//					f2[a - l - 1][b] = 'o';
//					if(b > 1) {
//						f[a - l - 1][b - 1] = 'o';
//						f2[a - l - 1][b - 1] = 'o';
//					}
//					if(b + 1 < f.length) {
//						f[a - l - 1][b + 1] = 'o';
//						f2[a - l - 1][b + 1] = 'o';
//					}
//				}
//			}
//			
//			if(r > 0) {
//				if( a + r < f.length) {
//					f[a + r][b] = 'o';
//					f2[a + r][b] = 'o';
//				}
//				
//				
//				if( a + r + 1 < f.length) {
//					f[a + r + 1][b] = 'o';
//					f2[a + r + 1][b] = 'o';
//					if(b > 1) {
//						f[a + r + 1][b - 1] = 'o';
//						f2[a + r + 1][b - 1] = 'o';
//					}
//					if(b + 1 < f.length) {
//						f[a + r + 1][b + 1] = 'o';
//						f2[a + r + 1][b + 1] = 'o';
//					}
//				}
//				
//			}
			
			
			return true;
			
		}
		if( horiz ) {
			int l = 0;
			for( int i = b - 1; i > 0 ; i--, l++ ) {
				if( f[a][i] == '#' )
					return false;
				if( f[a][i] == ' ' ) break;
			}
			
			int r = 0;
			for( int i = b + 1; i < f[a].length ; i++, r++ ) {
				if( f[a][i] == '#' )
					return false;
				if( f[a][i] == ' ' ) break;
			}
			
			if(a > 1) {
				for( int i = b - l - 1; i <= b + r + 1; i++) {
					if( i > 0  &&  i < f.length ) {
						f[a - 1][i] = 'o';
						f2[a - 1][i] = 'o';
					}
				}
			}
			if(a < f.length - 1) {
				for( int i = b - l - 1; i <= b + r + 1; i++) {
					if(	i > 0  &&  i < f.length ) {
						f[a + 1][i] = 'o';
						f2[a + 1][i] = 'o';
					}
				}
			}
			
			if( b - l - 1 > 0 ) {
				f[a][b - l - 1] = 'o';
				f2[a][b - l - 1] = 'o';
			}
			if( b + r + 1 < f.length ) {
				f[a][b + r + 1] = 'o';
				f2[a][b + r + 1] = 'o';
			}
			
			
//			if( l > 0) {
//				if( a - l > 0) {
//					f[a][b - l] = 'o';
//					f2[a][b - l] = 'o';
//				}
//				if( b - l - 1 > 0) {
//					f[a][b - l - 1] = 'o';
//					f2[a][b - l - 1] = 'o';
//					if(a > 1) {
//						f[a - 1][b - l - 1] = 'o';
//						f2[a - 1][b - l - 1] = 'o';
//					}
//					if(a + 1 < f.length) {
//						f[a + 1][b - l - 1] = 'o';
//						f2[a + 1][b - l - 1] = 'o';
//					}
//				}
//			}
//			
//			if( r > 0) {
//				if( a + r < f.length) {
//					f[a][b + r] = 'o';
//					f2[a][b + r] = 'o';
//				}
//				
//				if( b + r + 1 < f.length) {
//					f[a][b + r + 1] = 'o';
//					f2[a][b + r + 1] = 'o';
//					if(a > 1) {
//						f[a - 1][b + r + 1] = 'o';
//						f2[a - 1][b + r + 1] = 'o';
//					}
//					if(a + 1 < f.length) {
//						f[a + 1][b + r + 1] = 'o';
//						f2[a + 1][b + r + 1] = 'o';
//					}
//				}
//			}
			
			
			
			return true;
			
		}
		
		
		return true;
	}
	
	
}
