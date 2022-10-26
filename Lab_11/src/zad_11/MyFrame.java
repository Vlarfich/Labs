package zad_11;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Enumeration;
import java.util.List;

public class MyFrame extends JFrame implements ActionListener {
    private MyController controller;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu data;
    private JMenu help;
    private JMenu view;
    private JMenu look_and_feel;
    private JMenuItem font;
    private JMenuItem about;
    private JMenuItem append;
    private JMenuItem print;
    private JMenuItem find;
    private JMenuItem delete;
    private JMenuItem open;
    private JMenuItem exit;
    private JList<WriteRecord> list;
    private JLabel status;
    
    public static JMenuItem createMenuFont(final JFrame frame) {
    	
    	JMenuItem fontmenu = new JMenuItem("Font");
    	 final FontChooser chooser = new FontChooser(frame);
         fontmenu.addActionListener(new ActionListener() {
  		    public void actionPerformed(ActionEvent e) {
  			chooser.setVisible(true);
  			Font f = chooser.getSelectedFont();
  			Enumeration keys = UIManager.getDefaults().keys();
  	        while (keys.hasMoreElements()) {
  	            Object key = keys.nextElement();
  	            Object value = UIManager.get(key);
  	            if (value instanceof FontUIResource) {
  	                FontUIResource orig = (FontUIResource) value;
  	                UIManager.put(key, new FontUIResource(f));
  	              SwingUtilities.updateComponentTreeUI(frame);
  	            }
  	        }
  			}
  	    });
    	return fontmenu;
        }
    

    public static JMenu createPlafMenu(final JFrame frame) {
    	
    	JMenu plafmenu = new JMenu("Look and Feel");

    	ButtonGroup radiogroup = new ButtonGroup();  

    	UIManager.LookAndFeelInfo[] plafs =
    	    UIManager.getInstalledLookAndFeels();

    	for(int i = 0; i < plafs.length; i++) {
    	    String plafName = plafs[i].getName();
    	    final String plafClassName = plafs[i].getClassName();

    	    JMenuItem item = plafmenu.add(new JRadioButtonMenuItem(plafName));
    	    item.addActionListener(new ActionListener() {
    		    public void actionPerformed(ActionEvent e) {
    			try {
    			   
    			    UIManager.setLookAndFeel(plafClassName);
    			    SwingUtilities.updateComponentTreeUI(frame);
    			}
    			catch(Exception ex) { System.err.println(ex); }
    		    }

    		});
    	    radiogroup.add(item);  
    	}
    	return plafmenu;
        }
    
    public MyFrame(MyController controller) throws HeadlessException {
        super("MainWindow");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Do you want to close application?", "Close",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION ) {
                    setVisible(false);
                    dispose();
                }
            }
        });
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException ignored) {
        }
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        menuBar = new JMenuBar();

        file = new JMenu("File");
        open = new JMenuItem("Open");
        exit = new JMenuItem("Exit");
        file.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        file.addSeparator();
        file.add(exit);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));

        data = new JMenu("Data");
        append = new JMenuItem("Append");
        print = new JMenuItem("Print");
        delete = new JMenuItem("Delete");
        find = new JMenuItem("Find");
        
        help = new JMenu("Help");
        about = new JMenuItem("about");
        
        view = new JMenu("View");
        font = createMenuFont(this);
        look_and_feel = createPlafMenu(this);
        
        data.setEnabled(false);
        data.add(append);
        data.add(print);
        data.add(find);
        data.add(delete);
        
        view.add(font);
        view.addSeparator();
        view.add(look_and_feel);
        
        help.add(about);

        menuBar.add(file);
        menuBar.add(data);
        menuBar.add(help);
        menuBar.add(view);
        setJMenuBar(menuBar);

        open.addActionListener(this);
        exit.addActionListener(this);
        append.addActionListener(this);
        print.addActionListener(this);
        delete.addActionListener(this);
        find.addActionListener(this);
        font.addActionListener(this);
        look_and_feel.addActionListener(this);
        about.addActionListener(this);

        list = new JList<>();
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane);

        status = new JLabel("No file opened");
        Border border = BorderFactory.createEtchedBorder(Color.white, new Color(100, 100, 100));
        status.setBorder(border);

        status.setOpaque(true);
        add(status);

        pack();
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == open) {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Data file", "dat", "txt"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String file = fileChooser.getDescription(fileChooser.getSelectedFile()).replaceFirst("[.][^.]+$", "");
                controller.open(file);
                data.setEnabled(true);
            }
        } else if (event.getSource() == exit) {
            if (JOptionPane.showConfirmDialog(null, "Do you want to close application?", "Close",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION ) {
                setVisible(false);
                dispose();
            }
        } else if (event.getSource() == delete) {
            JPanel inputPanel = new JPanel();
            JComboBox<String> index = new JComboBox<>(new String[] {"By Departure", "By Destination", "By Number of flight"});
            index.setEnabled(true);
            index.setSelectedIndex(0);
            inputPanel.add(new JLabel("Index"));
            inputPanel.add(index);
            JTextField key = new JTextField("Minsk", 20);
            inputPanel.add(new JLabel("Key"));
            inputPanel.add(key);
            String[] hints = {"Minsk", "Tolochin", "123456"};
            index.addItemListener(e -> key.setText(hints[index.getSelectedIndex()]));
            if (JOptionPane.showConfirmDialog(this, inputPanel,
                    "Enter position", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    controller.delete(index.getSelectedIndex(), key.getText());
                } catch (ClassNotFoundException | IOException | KeyNotUniqueException | IllegalArgumentException e) {
                    showError(e);
                }
            }
        } else if (event.getSource() == append) {
            JTextField Departure = new JTextField("Minsk", 5);
            JTextField Destination = new JTextField("Tolochin", 20);
            JTextField Number = new JTextField("123456", 5);
            JTextField Type = new JTextField("Passenger", 5);
            JTextField Time = new JTextField("15:30", 10);
            JTextField Day = new JTextField("Friday", 10);
          

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(7, 2));
            inputPanel.add(new JLabel("Departure"));
            inputPanel.add(Departure);
            inputPanel.add(new JLabel("Destination"));
            inputPanel.add(Destination);
            inputPanel.add(new JLabel("Number"));
            inputPanel.add(Number);
            inputPanel.add(new JLabel("Type"));
            inputPanel.add(Type);
            inputPanel.add(new JLabel("Time"));
            inputPanel.add(Time);
            inputPanel.add(new JLabel("Day"));
            inputPanel.add(Day);

            JCheckBox zipped = new JCheckBox("Zipped", false);
            inputPanel.add(zipped);
            if (JOptionPane.showConfirmDialog(this, inputPanel,
                    "Append Airline", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    controller.appendFile(zipped.isSelected(), Departure.getText(), Destination.getText(),
                    		Number.getText().toString(), Type.getText(), Time.getText(), Day.getText());

                } catch (IOException | ClassNotFoundException |KeyNotUniqueException | DateTimeParseException | IllegalArgumentException e) {
                    showError(e);
                } catch (ParseException e) {
					e.printStackTrace();
				}
            }
        } else if (event.getSource() == print) {
            JRadioButton notSorted = new JRadioButton("Not sorted");
            JRadioButton sorted = new JRadioButton("Sorted");
            JRadioButton revSorted = new JRadioButton("Reverse sorted");
            ButtonGroup group = new ButtonGroup();
            group.add(notSorted);
            group.add(sorted);
            group.add(revSorted);
            notSorted.setSelected(true);

            JPanel inputPanel = new JPanel();
            inputPanel.add(notSorted);
            inputPanel.add(sorted);
            inputPanel.add(revSorted);

            JComboBox<String> index = new JComboBox<>(new String[] {"By Departure", "By Destination", "By number of flight"});
            index.setEnabled(false);
            index.setSelectedIndex(0);
            notSorted.addActionListener(e -> index.setEnabled(false));
            sorted.addActionListener(e -> index.setEnabled(true));
            revSorted.addActionListener(e -> index.setEnabled(true));
            inputPanel.add(new JLabel("Index"));
            inputPanel.add(index);
            if (JOptionPane.showConfirmDialog(this, inputPanel,
                    "Print flights", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    if (notSorted.isSelected()) {
                        controller.printFile();
                    } else {
                        controller.printSorted(revSorted.isSelected(), index.getSelectedIndex());
                    }
                } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
                    showError(e);
                }
            }
        } else if (event.getSource() == find) {
            JRadioButton byKey = new JRadioButton("By key");
            JRadioButton larger = new JRadioButton("Large");
            JRadioButton less = new JRadioButton("Less");
            JPanel inputPanel = new JPanel();
            inputPanel.add(byKey);
            inputPanel.add(larger);
            inputPanel.add(less);
            ButtonGroup group = new ButtonGroup();
            group.add(byKey);
            group.add(larger);
            group.add(less);
            byKey.setSelected(true);
            JComboBox<String> index = new JComboBox<>(new String[] {"By Departure", "By Destination", "By number of flight"});
            index.setEnabled(true);
            index.setSelectedIndex(0);
            inputPanel.add(new JLabel("Index"));
            inputPanel.add(index);
            JTextField key = new JTextField("Minsk", 5);
            inputPanel.add(new JLabel("Key"));
            inputPanel.add(key);
            String[] hints = {"Minsk", "Tolochin", "123456"};
            index.addItemListener(e -> key.setText(hints[index.getSelectedIndex()]));
            if (JOptionPane.showConfirmDialog(this, inputPanel,
                    "Find accounts", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    if (byKey.isSelected()) {
                        controller.printByKey(index.getSelectedIndex(), key.getText());
                    } else if (larger.isSelected()) {
                        controller.printByKey(true, index.getSelectedIndex(), key.getText());
                    } else {
                        controller.printByKey(false, index.getSelectedIndex(), key.getText());
                    }
                } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
                    showError(e);
                }
            }
        }else if(event.getSource() == about){
        	JOptionPane.showConfirmDialog(null, "Журавлёв Владислав, 10 группа, 2 курс, ФПМИ, БГУ", "About",
                    JOptionPane.DEFAULT_OPTION);
        	
        }else if(event.getSource() == font){
        	
        }
    }
    void showData(List<WriteRecord> data) {
        list.setListData(data.toArray(new WriteRecord[0]));
    }

    void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    void update(String string) {
        status.setText(string);
    }
}