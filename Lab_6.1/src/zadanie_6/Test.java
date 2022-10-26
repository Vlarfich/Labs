package zadanie_6;

public class Test {
	
	public static void Syso(Client[] mas) {
		for(Client i: mas)
			System.out.println(i);
	}
	
	public static void main(String[] args) {
		
		/*Order o = new Order(20);
		Account ac = new Account(4577, new CC(25154, 122), 1000);
		
		Client cl = new Client("Client", ac);
		
		System.out.println(cl);
		System.out.println();
		System.out.println("Clien trying to buy order o: " + o);
		boolean buy = cl.Buy(o);
		if(buy) System.out.println(" Successful try\n");
		else System.out.println(" Unsuccessful try\n");
		
		System.out.println(cl);
		
		cl.Block();
		System.out.println("\nClient " + cl.GetName() + " blocked the card " + cl.getCardID());
		System.out.println(cl);
		

		
		System.out.println("Clien trying to buy order o: " + o);
		buy = cl.Buy(o);
		if(buy) System.out.println(" Successful try\n");
		else System.out.println(" Unsuccessful try\n");
		
		System.out.println("\n________________________________________\n");
		Client cl2 = new Client("Anthony", new Account(211, new CC(513)));
		System.out.println(cl2);
		int amo = 150;
		System.out.println("Clinet trying to transfer " + amo + "$ to Anthony:");
		if(cl.Transfer(cl2, amo)) System.out.println(" Success");
		else System.out.println(" NO Success");
		
		
		Admin admin = new Admin("admin");
		admin.UnBlock(cl);
		System.out.println("\nNew Admin " + admin + " is unblocking Client\n");
		admin.UnBlock(cl);
		System.out.println(cl);
		
		System.out.println("Clinet trying to transfer " + amo + "$ to Anthony:");
		if(cl.Transfer(cl2, amo)) System.out.println(" Success");
		else System.out.println(" NO Success");
		
		System.out.println(cl + "\n\n" + cl2);
		System.out.println(" \nAnullating Client account:\n");
		cl.Annulate();
		System.out.println(cl);
		*/
		
		Client base[] = new Client[3];
		base[0] = new Client("Gorge", new Account(new CC(500), 1000));
		base[1] = new Client("James", new Account(new CC(), 202));
		base[2] = new Client("Gregory", new Account(new CC(750), 1500));
		//Syso(base);
		
		Admin adm = new Admin("Jonny");
		//System.out.println("\nAdmin - " + adm);
		
		Payments pay = new Payments(adm, base);
		System.out.println(pay);
		System.out.println();
		Order o = new Order(200);
		Order O = new Order(2000);
		
		if(pay.Buy(2, o)) 
			System.out.println("Client " + base[2].GetName() + " successfuly bought order o for " + o);
		
		System.out.println(base[2]);
		if(pay.Transfer(1, pay.getCl(0), 22))
			System.out.println("Client " + base[1].GetName() + " transfered 22$ to client " + base[0].GetName());
		if(!pay.Buy(0, O))
			System.out.println("\nClient " + base[0].GetName() + " trying to buy order O for " + O + " - No Success -> Client got blocked");
		
		System.out.println(base[0]);
		pay.UnBlok(pay.getCl(0));
		


		if(pay.Buy(2, O))
			System.out.println("Client " + base[2].GetName() + " successfuly bought order O for " + O);
		System.out.println(base[2]);
		System.out.println("\n\nBank system in result: \n\n" + pay);
		System.out.println("______________________________________________________________\n");
		try {
			Connector con = new Connector("Payments.dat");	
			con.write(pay);
			Payments P = con.read();
			System.out.println(P);
		}	
		catch ( Exception e ) {
			System.err.println(e);
		}
		
	}

}
