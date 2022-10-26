package os2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JPanel {
	public boolean contest = false;
	private static final int CX = 580, CY = 500;
	private JTextField extension, stringTextField;
	private JButton directoryButton, Start;
	private String substr;
	private JTextArea resultArea;
	private JCheckBox extensionCheck, substrCheck, directoryCheck, subdirectoryCheck;
	private JComboBox<String> comboBox;
	private Timer timer;
	private SearchThread t = null;
	public int index = -1;

	public SearchThread getT() {
		return t;
	}

	public GUI(Color back, ArrayList<String> com, JTextArea TAT, int ind) {
		index = ind;
		setSize(CX, CY);
		setBorder(new EmptyBorder(2, 2, 2, 2));
		setBackground(back);
		setLayout(null);
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
		Start = new JButton("Start");
		getStart().setForeground(new Color(255, 255, 255));
		getStart().setBackground(new Color(0, 155, 0));
		getStart().setBounds(390, 20, 160, 100);
		getStart().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getStart().setEnabled(false);

				if (t == null) {
					resultArea.setText("");
					try {
						String regX = null;
						String searchStr = null;
						if (substrCheck.isSelected() || directoryCheck.isSelected() || subdirectoryCheck.isSelected()) {
							searchStr = stringTextField.getText();
						}
						if (extensionCheck.isSelected()) {
							regX = extension.getText();
						}
						contest = true;
						t = new SearchThread(regX, substr, searchStr, directoryCheck.isSelected(),
								subdirectoryCheck.isSelected(), substrCheck.isSelected(),
								comboBox.getSelectedIndex() + 1, com, TAT, index);
					} catch (Exception except) {
						JOptionPane.showMessageDialog(null, "НЕ ВЫБРАНО НАЧАЛО ПОИСКА", "Exception",
								JOptionPane.ERROR_MESSAGE);
						getStart().setEnabled(true);
						return;
					}
					setFieldsEnabled(false);
					t.start();

				} else {
					t.myResume();
					contest = true;
				}
				timer.start();

			}
		});
		add(getStart());

		directoryButton = new JButton("ВЫБЕРИТЕ ДИРЕКТОРИЮ");
		directoryButton.setFont(new Font("TimesRoman", Font.BOLD, 17));
		directoryButton.setBounds(30, 20, 340, 40);
		directoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					substr = chooser.getSelectedFile().getAbsolutePath();
					directoryButton.setText(substr);
				}
			}
		});
		add(directoryButton);
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
		extension = new JTextField();
		extension.setBounds(220, 70, 150, 20);
		add(extension);
		extension.setColumns(10);
		extension.setEnabled(false);
		stringTextField = new JTextField();
		stringTextField.setBounds(220, 100, 150, 20);
		add(stringTextField);
		stringTextField.setColumns(10);
		stringTextField.setEnabled(false);
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
		extensionCheck = new JCheckBox("Шаблон:");
		extensionCheck.setFont(new Font("TimesRoman", Font.BOLD, 17));
		extensionCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extension.setEnabled(extensionCheck.isSelected());
			}
		});
		extensionCheck.setBounds(30, 70, 100, 20);
		extensionCheck.setBackground(getBackground());
		add(extensionCheck);

		substrCheck = new JCheckBox("Подстрока:");
		substrCheck.setFont(new Font("TimesRoman", Font.BOLD, 17));
		substrCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stringTextField.setEnabled(substrCheck.isSelected());
			}
		});
		substrCheck.setBounds(30, 100, 175, 20);
		substrCheck.setBackground(getBackground());
		add(substrCheck);

		directoryCheck = new JCheckBox("Поиск в поддиректориях:");
		directoryCheck.setFont(new Font("TimesRoman", Font.BOLD, 17));
		directoryCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stringTextField.setEnabled(directoryCheck.isSelected());
			}
		});
		directoryCheck.setBounds(30, 130, 280, 20);
		directoryCheck.setBackground(getBackground());
		add(directoryCheck);

		subdirectoryCheck = new JCheckBox("Поиск директория");
		subdirectoryCheck.setFont(new Font("TimesRoman", Font.BOLD, 17));

		subdirectoryCheck.setBounds(30, 160, 180, 20);
		subdirectoryCheck.setBackground(getBackground());
		subdirectoryCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stringTextField.setEnabled(subdirectoryCheck.isSelected());
			}
		});
		add(subdirectoryCheck);

//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
		comboBox = new JComboBox<>();
		comboBox.setModel(
				new DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		comboBox.setSelectedIndex(0);
		comboBox.setMaximumRowCount(10);
		comboBox.setBounds(330, 130, 40, 20);
		add(comboBox);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 210, 520, 250);
		add(scrollPane);
		resultArea = new JTextArea();
		resultArea.setBackground(new Color(150, 150, 250));
		resultArea.setDisabledTextColor(Color.black);
		resultArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		resultArea.setEditable(false);
		resultArea.setBounds(30, 210, 500, 250);
		scrollPane.setViewportView(resultArea);
		timer = new Timer(5, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (t != null) {
					resultArea.setText("");
					for (int i = 0; i < t.foundDirs.size(); ++i)
						resultArea.append(t.foundDirs.get(i));
					if (!t.isAlive()) {
						t.Interrupt();
						contest = false;
						t = null;
						timer.stop();
						setFieldsEnabled(true);
						getStart().setEnabled(true);
					}
				}
			}
		});
	}

	public Dimension getPreferredSize() {
		return new Dimension(CX, CY);
	}

	private void setFieldsEnabled(boolean flag) {
		directoryButton.setEnabled(flag);
		substrCheck.setEnabled(flag);
		extensionCheck.setEnabled(flag);
		if (substrCheck.isSelected())
			stringTextField.setEnabled(flag);
		if (extensionCheck.isSelected())
			extension.setEnabled(flag);
		directoryCheck.setEnabled(flag);
		comboBox.setEnabled(flag);
	}

	public JButton getStart() {
		return Start;
	}

}