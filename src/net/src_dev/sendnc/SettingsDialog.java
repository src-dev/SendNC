package net.src_dev.sendnc;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class SettingsDialog extends JDialog {
	
	public JButton saveButton;
	public JButton cancelButton;
	public JSpinner timeoutSpinner;
	public JButton folderButton;
	public JTextField folderField;
	public JFileChooser folderChooser;
	
	public SettingsDialog() {
		setBackground(SystemColor.controlHighlight);
		getContentPane().setBackground(SystemColor.controlHighlight);		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsDialog.class.getResource("/toolbarButtonGraphics/general/Edit24.gif")));
		setTitle("Settings");
		setBounds(0, 0, 456, 156);
		setResizable(false);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e) {
			
		}
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		getContentPane().setLayout(null);
		
		timeoutSpinner = new JSpinner();
		timeoutSpinner.setFont(new Font("Tahoma", Font.PLAIN, 13));
		timeoutSpinner.setModel(new SpinnerNumberModel(new Integer(2000), new Integer(10), null, new Integer(10)));
		timeoutSpinner.setBounds(65, 62, 380, 30);
		getContentPane().add(timeoutSpinner);
		
		folderButton = new JButton("");
		folderButton.setBackground(SystemColor.controlHighlight);
		folderButton.setIcon(new ImageIcon(SettingsDialog.class.getResource("/toolbarButtonGraphics/general/Open24.gif")));
		folderButton.setBounds(395, 27, 50, 30);
		getContentPane().add(folderButton);
		
		folderField = new JTextField();
		folderField.setBackground(SystemColor.controlHighlight);
		folderField.setEditable(false);
		folderField.setBounds(5, 27, 385, 30);
		getContentPane().add(folderField);
		folderField.setColumns(10);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(SystemColor.menu);
		buttonPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		buttonPanel.setBounds(0, 97, 451, 32);
		getContentPane().add(buttonPanel);
		buttonPanel.setLayout(null);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(300, 3, 70, 25);
		buttonPanel.add(saveButton);
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(375, 3, 70, 25);
		buttonPanel.add(cancelButton);
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblTimeout = new JLabel("Timeout:");
		lblTimeout.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTimeout.setBounds(5, 62, 52, 30);
		getContentPane().add(lblTimeout);
		
		JLabel lblNewLabel = new JLabel("Default Directory:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(5, 5, 198, 17);
		getContentPane().add(lblNewLabel);
		
		folderChooser = new JFileChooser();
		folderChooser.setCurrentDirectory(SendNC.DEFAULT_DIR);
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.setDialogTitle("Select Directory");
	}
}
