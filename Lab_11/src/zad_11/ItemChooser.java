package zad_11;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ItemChooser extends JPanel {
    // These fields hold property values for this component
    String name;           // The overall name of the choice
    String[] labels;       // The text for each choice option
    Object[] values;       // Arbitrary values associated with each option
    int selection;         // The selected choice
    int presentation;      // How the choice is presented

    // These are the legal values for the presentation field
    public static final int LIST = 1;
    public static final int COMBOBOX = 2;
    public static final int RADIOBUTTONS = 3;

    // These components are used for each of the 3 possible presentations
    JList<String> list;                     // One type of presentation
    JComboBox<String> combobox;             // Another type of presentation
    JRadioButton[] radiobuttons;    // Yet another type

    // The list of objects that are interested in our state
    ArrayList<ItemChooser.Listener> listeners = new ArrayList<ItemChooser.Listener>();

    // The constructor method sets everything up
    public ItemChooser(String name, String[] labels, Object[] values,
		       int defaultSelection, int presentation)
    {
	// Copy the constructor arguments to instance fields
	this.name = name;
	this.labels = labels;
	this.values = values;
	this.selection = defaultSelection;
	this.presentation = presentation;

	// If no values were supplied, use the labels
	if (values == null) this.values = labels;

	// Now create content and event handlers based on presentation type
	switch(presentation) {
	case LIST: initList(); break;
	case COMBOBOX: initComboBox(); break;
	case RADIOBUTTONS: initRadioButtons(); break;
	}
    }

    // Initialization for JList presentation
    void initList() {
	list = new JList<String>(labels);          // Create the list
	list.setSelectedIndex(selection);  // Set initial state
	
	// Handle state changes
	list.addListSelectionListener(new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    ItemChooser.this.select(list.getSelectedIndex());
		}
	    });
	
	// Lay out list and name label vertically
	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // vertical
	this.add(new JLabel(name));        // Display choice name
	this.add(new JScrollPane(list));   // Add the JList
    }
    
    // Initialization for JComboBox presentation
    void initComboBox() {
	combobox = new JComboBox<String>(labels);         // Create the combo box
	combobox.setSelectedIndex(selection);     // Set initial state
	
	// Handle changes to the state
	combobox.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    ItemChooser.this.select(combobox.getSelectedIndex());
		}
	    });
	
	// Lay out combo box and name label horizontally
	this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	this.add(new JLabel(name));
	this.add(combobox);
    }
    
    // Initialization for JRadioButton presentation
    void initRadioButtons() {
	// Create an array of mutually exclusive radio buttons
	radiobuttons = new JRadioButton[labels.length];   // the array
	ButtonGroup radioButtonGroup = new ButtonGroup(); // used for exclusion
	ChangeListener listener = new ChangeListener() {  // A shared listener
		public void stateChanged(ChangeEvent e) {
		    JRadioButton b = (JRadioButton)e.getSource();
		    if (b.isSelected()) {
			// If we received this event because a button was
			// selected, then loop through the list of buttons to
			// figure out the index of the selected one.
			for(int i = 0; i < radiobuttons.length; i++) {
			    if (radiobuttons[i] == b) {
				ItemChooser.this.select(i);
				return;
			    }
			}
		    }
		}
	    };
	
	// Display the choice name in a border around the buttons
	this.setBorder(new TitledBorder(new EtchedBorder(), name));
	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
	// Create the buttons, add them to the button group, and specify
	// the event listener for each one.
	for(int i = 0; i < labels.length; i++) {
	    radiobuttons[i] = new JRadioButton(labels[i]);
	    if (i == selection) radiobuttons[i].setSelected(true);
	    radiobuttons[i].addChangeListener(listener);
	    radioButtonGroup.add(radiobuttons[i]);
	    this.add(radiobuttons[i]);
	}
    }
    
    // These simple property accessor methods just return field values
    // These are read-only properties.  The values are set by the constructor
    // and may not be changed.
    public String getName() { return name; }
    public int getPresentation() { return presentation; }
    public String[] getLabels() { return labels; }
    public Object[] getValues() { return values; }
    
    /** Return the index of the selected item */
    public int getSelectedIndex() { return selection; }
    
    /** Return the object associated with the selected item */
    public Object getSelectedValue() { return values[selection]; }
    
    /**
     * Set the selected item by specifying its index.  Calling this
     * method changes the on-screen display but does not generate events.
     **/
    public void setSelectedIndex(int selection) {
	switch(presentation) {
	case LIST: list.setSelectedIndex(selection); break;
	case COMBOBOX: combobox.setSelectedIndex(selection); break;
	case RADIOBUTTONS: radiobuttons[selection].setSelected(true); break;
	}
	this.selection = selection;
    }

    /**
     * This internal method is called when the selection changes.  It stores
     * the new selected index, and fires events to any registered listeners.
     * The event listeners registered on the JList, JComboBox, or JRadioButtons
     * all call this method.
     **/
    protected void select(int selection) {
	this.selection = selection;  // Store the new selected index
	if (!listeners.isEmpty()) {  // If there are any listeners registered
	    // Create an event object to describe the selection
	    ItemChooser.Event e =
		new ItemChooser.Event(this, selection, values[selection]);
	    // Loop through the listeners using an Iterator
	    for(Iterator i = listeners.iterator(); i.hasNext();) {
		ItemChooser.Listener l = (ItemChooser.Listener)i.next();
		l.itemChosen(e);  // Notify each listener of the selection
	    }
	}
    }

    // These methods are for event listener registration and deregistration
    public void addItemChooserListener(ItemChooser.Listener l) {
	listeners.add(l);
    }
    public void removeItemChooserListener(ItemChooser.Listener l) {
	listeners.remove(l);
    }

    /**
     * This inner class defines the event type generated by ItemChooser objects
     * The inner class name is Event, so the full name is ItemChooser.Event
     **/
    public static class Event extends java.util.EventObject {
	int selectedIndex;      // index of the selected item
	Object selectedValue;   // the value associated with it
	public Event(ItemChooser source,
		     int selectedIndex, Object selectedValue) {
	    super(source);
	    this.selectedIndex = selectedIndex;
	    this.selectedValue = selectedValue;
	}

	public ItemChooser getItemChooser() { return (ItemChooser)getSource();}
	public int getSelectedIndex() { return selectedIndex; }
	public Object getSelectedValue() { return selectedValue; }
    }

    /**
     * This inner interface must be implemented by any object that wants to be
     * notified when the current selection in a ItemChooser component changes.
     **/
    public interface Listener extends java.util.EventListener {
	public void itemChosen(ItemChooser.Event e);
    }

}
