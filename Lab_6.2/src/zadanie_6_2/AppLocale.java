package zadanie_6_2;
import java.util.*;
public class AppLocale {
	private static final String strMsg = "Msg";
	private static Locale loc = Locale.getDefault();
	private static ResourceBundle res = 
			ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );
	
	static Locale get() {
		return AppLocale.loc;
	}
	
	static void set( Locale loc ) {
		AppLocale.loc = loc;
		res = ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );
	}
	
	static ResourceBundle getBundle() {
		return AppLocale.res;
	}
	
	static String getString( String key ) {
		return AppLocale.res.getString(key);
	}
	
	// Resource keys:
	
	public static final String ID="ID";
	public static final String overdraft="overdraft";
	public static final String currency="currency";
	public static final String block="Block";
	public static final String money="money";
	public static final String card="card";
	public static final String name="name";
	public static final String account="account";
	public static final String cost="cost";
	public static final String adm="adm";
	public static final String clientbase="clientbase";
	public static final String client="client";
	public static final String clientu="clientu";
	public static final String transf="transf";
	public static final String sucbuy="sucbuy";
	public static final String banksysres="banksysres";
	
	public static final String creation="creation";
}