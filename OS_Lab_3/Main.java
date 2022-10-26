package os2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JFrame {
	private JPanel MainPanel;
	int i = 2;
	ArrayList<GUI> arr = new ArrayList<GUI>();
	boolean going = false;

	public boolean T1 = false, T2 = false;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class thr extends Thread {
		GUI g1, g2;

		void changeArr(GUI a, GUI b) {
			g1 = a;
			g2 = b;
		}

		JLabel l;

		void setOwner(JLabel j) {
			l = j;
		}

		public thr(GUI a, GUI b, JLabel j) {
			g1 = a;
			g2 = b;
			l = j;
		}

		@Override
		public void run() {
			l.setText("");
			while (true) {
				if (!going) {
					if (g1.getT() != null || g2.getT() != null)
						going = true;
				}
				if (g1.getT() != null && g2.getT() != null) {
					l.setText("Поток 1: +, Поток 2: +");
					T1 = true;
					T2 = true;
				}
				if (g1.getT() != null && g2.getT() == null) {
					l.setText("Поток 1: +, Поток 2: -");
					T1 = true;
					T2 = false;
				}
				if (g1.getT() == null && g2.getT() != null) {
					l.setText("Поток 1: -, Поток 2: +");
					T1 = false;
					T2 = true;
				}
				if (g1.getT() == null && g2.getT() == null) {
					l.setText("Поток 1: -, Поток 2: -");
					T1 = false;
					T2 = false;
					if (going)
						;
					// dispose();
				}
				/*
				 * try { Thread.sleep(100); } catch (InterruptedException e) {
				 * System.err.println(e.getMessage()); }
				 */
			}
		}

	}

	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainPanel = new JPanel();
		MainPanel.setPreferredSize(new Dimension(1900, 1000));
		setContentPane(MainPanel);
		setTitle(" " + i + " panels opened");
		ArrayList<String> common = new ArrayList<String>();
		common.add("");

		// add(scrollPane);

		JTextArea jt = new JTextArea();
		jt.setBackground(new Color(255, 255, 0));
		jt.setFont(new Font("Monospaced", Font.PLAIN, 12));
		jt.setEditable(false);
		jt.setBounds(30, 30, 500, 500);
		
		jt.setMaximumSize(new Dimension(800, 750));

		// jt.setText(common.toString());
		String s = "        \n";
		jt.setText(s);
		//MainPanel.add(jt);
		JScrollPane scrollPane = new JScrollPane(jt);
		scrollPane.setPreferredSize(new Dimension(700, 700));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		MainPanel.add(scrollPane);
		
		

		scrollPane.setBounds(20, 50, 250, 500);
		jt.setMaximumSize(new Dimension(250, 500));
		scrollPane.setMaximumSize(new Dimension(250, 500));
		scrollPane.setViewportView(jt);

		/*
		 * JButton addButton = new JButton("Add"); addButton.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if (i != 5) { GUI g =
		 * new GUI(new Color(155, 0, 155), common, jt, i + 1); arr.add(g);
		 * MainPanel.add(g); pack(); i++; setTitle(" " + i + " panels opened"); } } });
		 */

		GUI g1 = new GUI(new Color(155, 0, 155), common, jt, 1);
		GUI g2 = new GUI(new Color(155, 0, 155), common, jt, 2);
		arr.add(g1);
		arr.add(g2);
		MainPanel.add(g1);
		MainPanel.add(g2);
		//MainPanel.add(addButton);
		JLabel contest = new JLabel("ни один поток не работает");
		contest.setFont(new Font("TimesRoman", Font.BOLD, 17));
		contest.setBounds(20, 10, 50, 50);
		MainPanel.add(contest);
		thr checker = new thr(g1, g2, contest);
		checker.start();

		JButton btnGeneralStart = new JButton("START ALL");
		btnGeneralStart.setBounds(500, 460, 40, 20);
		btnGeneralStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jt.setText("        \n");
				g1.getStart().doClick();
				g2.getStart().doClick();
			}
		});
		MainPanel.add(btnGeneralStart);

		pack();
		setResizable(true);
	}

}
