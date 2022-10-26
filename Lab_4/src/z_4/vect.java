package z_4;

import java.util.Random;
import java.util.Date;

class vect {
	
	static final int MAX_A = 10;
	
	static final int N = 3;             //   R^N   ---->   R^3
	// int N;
	double [] mas = new double [3];
	
	//////////////////////////////////////////////
	//   конструкторы
	//////////////////////////////////////////////
	public vect() {
		assert( N >= 3);
		for( int i = 0; i < N; i++ )
			mas[i] = 0;
	}
	
	public vect( int M ) {
		assert( N >= 3);
		assert( M > 0 );
		Init( M );
	}
	
	public vect( int [] n) {
		assert( N >= 3);
		assert( n.length >= N);
		for( int i = 0; i < N; i++ )
			mas[i] = n[i];
	}
	
	public vect( vect b) {
		assert( N >= 3);
		for( int i = 0; i < N; i++ ) {
			mas[i] = b.mas[i];
		}
	}
	
	
	
	private void Init( int max_val ) {
        Random rand = new Random( (new Date()).getTime() );
        for( int i = 0; i < N; i++ ) {
        		mas[i] = rand.nextInt( Math.abs(max_val) + 1 );
        }
    }
	//////////////////////////////////////////////
	//   вывод
	//////////////////////////////////////////////
	public void print() {
		assert( N > 0 );
		System.out.print("( ");
		for( int i = 0; i < N; i++ ) {
			System.out.print( mas[i] + " ");
		}
		System.out.print(")");
	}
	public void println() {
		assert( N > 0 );
		System.out.print("( ");
		for( int i = 0; i < N; i++ ) {
			System.out.print( mas[i] + " ");
		}
		System.out.println(")");
	}
	
	public String toString() {
		assert( N > 0 );
		String s = "( ";
		for( int i = 0; i < N; i++ ) {
			s += mas[i] + " ";
		}
		s += ")";
		return s;
	}
	//////////////////////////////////////////////
	//  Арифметические операции
	//////////////////////////////////////////////
	//  Сумма
	public vect	sum( vect b) {
		vect c = new vect( this );
		for( int i = 0; i < N; i++ ) {
			c.mas[i] += b.mas[i];
		}
		return c;
	}
	//  умножение на число
	public vect mult( double x ) {
		vect b = new vect(this);
		for( int i = 0; i < N; i++ ) {
			b.mas[i] = b.mas[i]*x;
		}
		return b;
	}
	//  протривоположное
	public vect neg() {
		vect b = new vect( this );
		for( int i = 0; i < N; i++) {
			b.mas[i] = -b.mas[i];
		}
		return b;
	}
	//   разность
	public vect minus( vect c) {
		vect b = new vect( this.sum(c.neg()) );
		return b;
	}
	
	//  Скалярное произведение
	public double scalarProduct( vect b ) {
		double s = 0;
		for( int i = 0; i < N; i++ ) {
			s += mas[i] * b.mas[i];
		}
		return s;
	}
	
	public vect vectProduct( vect c ) {
		vect b = new vect();
		
		b.mas[0] = mas[1] * c.mas[2] - mas[2] * c.mas[1];
		b.mas[1] = mas[2] * c.mas[0] - mas[0] * c.mas[2];       //b.mas[1] = -( mas[0] * c.mas[2] - mas[2] * c.mas[0] );
		b.mas[2] = mas[0] * c.mas[1] - mas[1] * c.mas[0];
		
		return b;
	}
	
	//////////////////////////////////////////////
	//   Операции сравнения       ==   !=
	//////////////////////////////////////////////
	//   ==
	public boolean Equals( vect b) {
		for( int i = 0; i < N; i++ ) {
			if( mas[i] != b.mas[i])
				return false;
		}
		return true;
	}
	//   !=
	public boolean notEquals( vect b) {
		return !(this.Equals(b));
	}
	//////////////////////////////////////////////
	//   Комплонарность
	//////////////////////////////////////////////
	
	public boolean complonar( vect a, vect b ) {
		return ( (this.vectProduct( a )).scalarProduct(b) == 0);
	}

	
}
