package os2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JTextArea;

public class SearchThread extends Thread {

	ArrayList<String> foundDirs;
	String fileRegx, startDirectory, stringToFind = "";
	boolean searchInSubDirs = false;
	boolean findSubDir = false;
	boolean findSubstr = false;
	int depth = 1;
	int index = -1;

	ArrayList<String> arr;
	JTextArea jt;

	public SearchThread(String regx, String startDir, String searchStr, boolean inSubDir, boolean findSub,
			boolean findsubstr, int dep, ArrayList<String> com, JTextArea J, int ind) {
		super();
		// setPriority(2);
		if (!validDir(startDir))
			throw new IllegalArgumentException("Invalid directory \"" + startDir + "\".");
		foundDirs = new ArrayList<>();
		if (regx != null && !regx.equals("")) {
			regx = regx.replaceAll("[.]", "[.]");
			regx = regx.replaceAll("[*]", ".*");
			regx = regx.replaceAll("[?]", ".");
		}
		depth = dep;
		fileRegx = regx;
		startDirectory = startDir;
		stringToFind = searchStr;
		searchInSubDirs = inSubDir;
		findSubDir = findSub;
		findSubstr = findsubstr;
		// foundDirs.add(new Date() + "\n");
		foundDirs.add("Started searching\n*********\n");

		arr = com;
		jt = J;

		index = ind;
	}

	@Override
	public void run() {
		// foundDirs.add(new Date() + "\n*********\n");
		String dir = Paths.get(startDirectory).toString();
		if (dir.charAt(dir.length() - 1) != '\\')
			dir += '\\';
		if (checkPause() != -1) {
			try {
				mySearch(dir, depth);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Interrupt();
	}

	private void mySearch(String dir, int dep) throws IOException {
		// results.add(dir + "....\n");
		if (dep == 0)
			return;
		if (checkPause() == -1)
			return;
		File fileDir = new File(dir);
		String[] list = fileDir.list();
		if (list == null)
			return;
		String item;
		for (int i = 0; i < list.length; i++) {
			item = list[i];
			// System.out.println(item + " " + searchString);
			if (checkPause() == -1)
				return;
			File newDir = new File(dir + item);
			if (newDir.isDirectory() && searchInSubDirs) {
				String pathname2 = dir + item + "\\";
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
				mySearch(pathname2, depth - 1);
			}
			if (newDir.isFile()) {
				boolean isGood = false;
				if (fileRegx != null && newDir.getName().matches(fileRegx))
					isGood = true;
				if (fileRegx == null || isGood) {
					if (stringToFind != null) {
						if (newDir.canRead()) {
							try {
								if (findSubDir)
									isGood = readData(dir);
								if (findSubstr && !isGood)
									isGood = readData(item);
							} catch (FileNotFoundException e) {
								foundDirs.add("--- " + newDir + " --- FileNotFoundException  !!\n");
								continue;
							}
						} else
							isGood = false;
					} else
						isGood = true;
				}
				if (isGood) {
					if (index == 2) {
						String s = "";
						if (item.contains(".txt")) {
							BufferedReader in = new BufferedReader(
					                new FileReader(dir + item));
							String t;
							while((t = in.readLine()) != null) {
								s += t;
							}
							in.close();
							
							s += "HI " + Thread.currentThread().getId() + "\n";
							
							Files.write(Paths.get(dir + item), s.getBytes());
							s = dir + item + " wrote: HI" + index + "\n";
						}
						File file = new File(dir + item);
						int ran = (int) (10 * Math.random());
						int in = item.indexOf('.');
						String n = item.substring(in);
						synchronized (file) {
							File newFile = new File(dir + "ch" + ran + n);
							file.renameTo(newFile);
						}	
						s += "changed " + item + " to " + "ch" + ran + n + '\n';
						synchronized (jt) {
								jt.append(s);
							}
						foundDirs.add(s);
						return;
						
					} else {
						foundDirs.add(" +  FOUND + " + dir + item + "\n");
						// arr.add(" + FOUND + " + dir + item + "\n");
						String s = " +  FOUND + " + dir + item + "\n";
						synchronized (jt) {
							// jt.setText(arr.toString());
							// String s = arr.toString();
							jt.append(s);
						}
					}
				}
			}
		}
	}

	private int checkPause() {
		while (isSuspended) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				return -1;
			}
		}
		return 0;
	}

	private synchronized boolean readData(String newDir) throws FileNotFoundException {
		/*
		 * Scanner fr = new Scanner(newDir); while ( fr.hasNextLine() ) { if (
		 * fr.nextLine().contains(searchString) ) { fr.close(); return true; } }
		 * fr.close();
		 */
		return newDir.contains(stringToFind);
	}

	private boolean isSuspended = false;

	public void mySuspend() {
		foundDirs.add(" suspended\n");
		isSuspended = true;
	}

	public void myResume() {
		isSuspended = false;
	}

	public void Interrupt() {
		foundDirs.add("\n*********\n");
		interrupt();
	}

	private boolean validDir(String startDir) {
		return !startDir.equals("") && Files.isDirectory(Paths.get(startDir));
	}
}
