package net.src_dev.sendnc;

import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class MachinesDialog extends JDialog {

	public JList<String> machinesList = new JList<String>();
	public JButton newButton;
	public JButton deleteButton;
	public JButton closeButton;
	public JButton saveButton;
	public JTextField nameField;
	public JTextField portField;
	public JComboBox<String> baudBox;
	public JComboBox<String> parityBox;
	public JRadioButton oneStopbitsRadio;
	public JRadioButton twoStopbitsRadio;
	public JRadioButton sevenDatabitsRadio;
	public JRadioButton eightDatabitsRadio;
	public ButtonGroup databitsGroup;
	public ButtonGroup stopbitsGroup;
	public JButton moveUpButton;
	public JButton moveDownButton;
		
	public MachinesDialog() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		setBackground(SystemColor.controlHighlight);
		getContentPane().setBackground(SystemColor.controlHighlight);		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MachinesDialog.class.getResource("/toolbarButtonGraphics/development/Host16.gif")));
		setTitle("Machines");
		setBounds(0, 0, 332, 395);
		setResizable(false);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e) {
			
		}
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		newButton = new JButton("New");
		newButton.setBackground(SystemColor.controlHighlight);
		newButton.setBounds(5, 5, 120, 20);
		getContentPane().add(newButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		deleteButton.setBackground(SystemColor.controlHighlight);
		deleteButton.setBounds(5, 30, 120, 20);
		getContentPane().add(deleteButton);
		
		nameField = new JTextField();
		nameField.setEnabled(false);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nameField.setBounds(201, 5, 120, 25);
		getContentPane().add(nameField);
		nameField.setColumns(10);
		
		portField = new JTextField();
		portField.setEnabled(false);
		portField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		portField.setBounds(201, 35, 120, 25);
		getContentPane().add(portField);
		portField.setColumns(10);
		
		baudBox = new JComboBox<String>();
		baudBox.setEnabled(false);
		baudBox.setModel(new DefaultComboBoxModel<String>(new String[] {"110", "300", "600", "1200", "2400", "4800", "9600", "19200", "38400", "115200"}));
		baudBox.setBackground(SystemColor.menu);
		baudBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		baudBox.setBounds(200, 65, 120, 25);
		getContentPane().add(baudBox);
		
		parityBox = new JComboBox<String>();
		parityBox.setEnabled(false);
		parityBox.setModel(new DefaultComboBoxModel<String>(new String[] {"None", "Odd", "Even", "Mark", "Space"}));
		parityBox.setBackground(SystemColor.menu);
		parityBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		parityBox.setBounds(201, 125, 120, 25);
		getContentPane().add(parityBox);
		
		stopbitsGroup = new ButtonGroup();
		oneStopbitsRadio = new JRadioButton("1");
		oneStopbitsRadio.setEnabled(false);
		stopbitsGroup.add(oneStopbitsRadio);
		oneStopbitsRadio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		oneStopbitsRadio.setBackground(SystemColor.controlHighlight);
		oneStopbitsRadio.setBounds(201, 155, 60, 25);
		getContentPane().add(oneStopbitsRadio);
		
		twoStopbitsRadio = new JRadioButton("2");
		twoStopbitsRadio.setEnabled(false);
		stopbitsGroup.add(twoStopbitsRadio);
		twoStopbitsRadio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		twoStopbitsRadio.setBackground(SystemColor.controlHighlight);
		twoStopbitsRadio.setBounds(261, 155, 60, 25);
		getContentPane().add(twoStopbitsRadio);
		
		databitsGroup = new ButtonGroup();
		sevenDatabitsRadio = new JRadioButton("7");
		sevenDatabitsRadio.setEnabled(false);
		databitsGroup.add(sevenDatabitsRadio);
		sevenDatabitsRadio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		sevenDatabitsRadio.setBackground(SystemColor.controlHighlight);
		sevenDatabitsRadio.setBounds(201, 95, 60, 25);
		getContentPane().add(sevenDatabitsRadio);
		
		eightDatabitsRadio = new JRadioButton("8");
		eightDatabitsRadio.setEnabled(false);
		databitsGroup.add(eightDatabitsRadio);
		eightDatabitsRadio.setBackground(SystemColor.controlHighlight);
		eightDatabitsRadio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		eightDatabitsRadio.setBounds(261, 95, 60, 25);
		getContentPane().add(eightDatabitsRadio);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 336, 326, 32);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		closeButton = new JButton("Close");
		closeButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		closeButton.setBackground(SystemColor.menu);
		closeButton.setBounds(251, 3, 70, 25);
		panel.add(closeButton);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBackground(SystemColor.menu);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblName.setBounds(131, 5, 65, 25);
		getContentPane().add(lblName);
		
		JLabel lblSerialPort = new JLabel("Serial Port:");
		lblSerialPort.setBackground(SystemColor.menu);
		lblSerialPort.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSerialPort.setBounds(131, 35, 65, 25);
		getContentPane().add(lblSerialPort);
		
		JLabel lblBaudRate = new JLabel("Baud Rate:");
		lblBaudRate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBaudRate.setBackground(SystemColor.menu);
		lblBaudRate.setBounds(131, 65, 65, 25);
		getContentPane().add(lblBaudRate);
		
		JLabel lblDatabits = new JLabel("DataBits:");
		lblDatabits.setBackground(SystemColor.menu);
		lblDatabits.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDatabits.setBounds(131, 95, 65, 25);
		getContentPane().add(lblDatabits);
		
		JLabel lblStopbits = new JLabel("StopBits:");
		lblStopbits.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStopbits.setBackground(SystemColor.menu);
		lblStopbits.setBounds(131, 155, 65, 25);
		getContentPane().add(lblStopbits);
		
		JLabel lblParity = new JLabel("Parity");
		lblParity.setBackground(SystemColor.menu);
		lblParity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblParity.setBounds(131, 125, 65, 25);
		getContentPane().add(lblParity);
		
		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		saveButton.setBounds(251, 185, 70, 25);
		getContentPane().add(saveButton);
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		saveButton.setBackground(SystemColor.controlHighlight);
		
		moveUpButton = new JButton("");
		moveUpButton.setEnabled(false);
		moveUpButton.setIcon(new ImageIcon(MachinesDialog.class.getResource("/toolbarButtonGraphics/navigation/Up24.gif")));
		moveUpButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		moveUpButton.setBackground(SystemColor.controlHighlight);
		moveUpButton.setBounds(5, 306, 58, 25);
		getContentPane().add(moveUpButton);
		
		moveDownButton = new JButton("");
		moveDownButton.setEnabled(false);
		moveDownButton.setIcon(new ImageIcon(MachinesDialog.class.getResource("/toolbarButtonGraphics/navigation/Down24.gif")));
		moveDownButton.setBackground(SystemColor.controlHighlight);
		moveDownButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		moveDownButton.setBounds(68, 306, 58, 25);
		getContentPane().add(moveDownButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(SystemColor.controlHighlight);
		scrollPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Machines:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(5, 55, 121, 246);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(machinesList);
		machinesList.setBorder(null);
		machinesList.setFont(new Font("Tahoma", Font.PLAIN, 13));
		machinesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		machinesList.setBackground(SystemColor.controlHighlight);
	}
	
	public void enableFields() {
		if(!deleteButton.isEnabled()) deleteButton.setEnabled(true);
		if(!nameField.isEnabled()) nameField.setEnabled(true);
		if(!portField.isEnabled()) portField.setEnabled(true);
		if(!baudBox.isEnabled()) baudBox.setEnabled(true);
		if(!parityBox.isEnabled()) parityBox.setEnabled(true);
		if(!oneStopbitsRadio.isEnabled()) oneStopbitsRadio.setEnabled(true);
		if(!twoStopbitsRadio.isEnabled()) twoStopbitsRadio.setEnabled(true);
		if(!sevenDatabitsRadio.isEnabled()) sevenDatabitsRadio.setEnabled(true);
		if(!eightDatabitsRadio.isEnabled()) eightDatabitsRadio.setEnabled(true);
		if(!moveUpButton.isEnabled()) moveUpButton.setEnabled(true);
		if(!moveDownButton.isEnabled()) moveDownButton.setEnabled(true);
	}
	public void disableFields() {
		if(deleteButton.isEnabled()) deleteButton.setEnabled(false);
		if(nameField.isEnabled()) nameField.setEnabled(false);
		if(portField.isEnabled()) portField.setEnabled(false);
		if(baudBox.isEnabled()) baudBox.setEnabled(false);
		if(parityBox.isEnabled()) parityBox.setEnabled(false);
		if(oneStopbitsRadio.isEnabled()) oneStopbitsRadio.setEnabled(false);
		if(twoStopbitsRadio.isEnabled()) twoStopbitsRadio.setEnabled(false);
		if(sevenDatabitsRadio.isEnabled()) sevenDatabitsRadio.setEnabled(false);
		if(eightDatabitsRadio.isEnabled()) eightDatabitsRadio.setEnabled(false);
		if(moveUpButton.isEnabled()) moveUpButton.setEnabled(false);
		if(moveDownButton.isEnabled()) moveDownButton.setEnabled(false);
	}
	public void enableSave() {
		if(!saveButton.isEnabled()) saveButton.setEnabled(true);
	}
	public void disableSave() {
		if(saveButton.isEnabled()) saveButton.setEnabled(false);
	}
}
