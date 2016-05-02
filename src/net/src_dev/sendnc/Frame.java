package net.src_dev.sendnc;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;
import javax.swing.JList;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class Frame extends JFrame {	
	public JList<String> machinesList = new JList<String>();
	public JButton fileButton;
	public JTextField fileField;
	public JButton sendButton;
	public JScrollPane statusPane;
	public JTextArea statusArea;
	public JFileChooser fileChooser;
	public JButton settingsButton;
	public JButton machinesButton;
	private JScrollPane scrollPane;

	public Frame() {
		setBackground(SystemColor.controlHighlight);
		getContentPane().setBackground(SystemColor.controlHighlight);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/net/src_dev/sendnc/logo.gif")));		
		setResizable(false);
		setTitle("SendNC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 736, 406);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e) {
			
		}
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setVisible(true);
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("Select File");
		
		fileButton = new JButton("");
		fileButton.setBounds(5, 342, 50, 30);
		fileButton.setBackground(SystemColor.controlHighlight);
		fileButton.setIcon(new ImageIcon(Frame.class.getResource("/toolbarButtonGraphics/general/Open24.gif")));
		fileButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(fileButton);
		
		fileField = new JTextField();
		fileField.setBounds(60, 342, 560, 30);
		fileField.setBackground(SystemColor.controlHighlight);
		fileField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fileField.setEditable(false);
		getContentPane().add(fileField);
		fileField.setColumns(10);
		
		sendButton = new JButton("Send");
		sendButton.setBounds(625, 342, 100, 30);
		sendButton.setEnabled(false);
		sendButton.setBackground(SystemColor.controlHighlight);
		sendButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		sendButton.setIconTextGap(10);
		getContentPane().add(sendButton);
		
		statusPane = new JScrollPane();
		statusPane.setBounds(131, 31, 594, 306);
		statusPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		statusPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		statusPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(statusPane);
		
		statusArea = new JTextArea();
		statusArea.setBorder(null);
		statusArea.setBackground(SystemColor.menu);
		statusArea.setEditable(false);
		((DefaultCaret) statusArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		statusPane.setViewportView(statusArea);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 731, 26);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBackground(SystemColor.menu);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		settingsButton = new JButton("Settings");
		settingsButton.setBackground(SystemColor.menu);
		settingsButton.setBounds(90, 3, 80, 20);
		panel.add(settingsButton);
		settingsButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		machinesButton = new JButton("Machines");
		machinesButton.setBackground(SystemColor.menu);
		machinesButton.setBounds(5, 3, 80, 20);
		panel.add(machinesButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBackground(SystemColor.controlHighlight);
		scrollPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Machines:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(5, 31, 121, 306);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(machinesList);
		machinesList.setBorder(null);
		machinesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		machinesList.setBackground(SystemColor.controlHighlight);
		machinesList.setFont(new Font("Tahoma", Font.PLAIN, 13));
		machinesList.grabFocus();
	}
	
	public void setSendingState(boolean state) {
		fileButton.setEnabled(state);
		sendButton.setEnabled(state);
		machinesList.setEnabled(state);
		settingsButton.setEnabled(state);
		machinesButton.setEnabled(state);
	}
}
