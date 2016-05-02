package net.src_dev.sendnc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

public class SendNC {
	public static final String VERSION = "1.0.14";
	
	public static final File APPDATA_DIR = new File(new File(System.getenv("AppData") + "\\SendNC").getAbsolutePath());
	public static final File SETTINGS_FILE = new File(APPDATA_DIR.getAbsolutePath() + "\\settings.dat");
	public static final File MACHINES_FILE = new File(APPDATA_DIR.getAbsolutePath() + "\\machines.dat");
	public static final File DEFAULT_DIR = new File("C:\\");	
	public static final int DEFAULT_TIMEOUT = 2000;
	public static final Machine NEW_MACHINE = new Machine("New Machine", "COM1", 0, SerialPort.DATABITS_7, SerialPort.PARITY_NONE, SerialPort.STOPBITS_1);	
	public static final Map<Integer, Integer> BAUD_MAP = new HashMap<Integer, Integer>();
	{
		BAUD_MAP.put(0, 110);
		BAUD_MAP.put(1, 300);
		BAUD_MAP.put(2, 600);
		BAUD_MAP.put(3, 1200);
		BAUD_MAP.put(4, 2400);
		BAUD_MAP.put(5, 4800);
		BAUD_MAP.put(6, 9600);
		BAUD_MAP.put(7, 19200);
		BAUD_MAP.put(8, 38400);
		BAUD_MAP.put(9, 115200);
	}
	public static final Map<Integer, String> PARITY_MAP = new HashMap<Integer, String>();
	{
		PARITY_MAP.put(0, "None");
		PARITY_MAP.put(1, "Odd");
		PARITY_MAP.put(2, "Even");
		PARITY_MAP.put(3, "Mark");
		PARITY_MAP.put(4, "Space");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SendNC();
			}
		});
	}
	
	private Frame frame;
	
	private Enumeration<CommPortIdentifier> ports;
	private Map<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>();
	
	public int timeout;
	public File defaultDir;	
	public File currentDir;
	public List<Machine> machines = new ArrayList<Machine>();
	public DefaultListModel<String> machinesListModel = new DefaultListModel<String>();
	
	private File settingsDefaultDir;
	private int settingsTimeout;
	
	private boolean machinesSelectionChanged = false;
		
	@SuppressWarnings("unchecked")
	public SendNC() {
		APPDATA_DIR.mkdirs();
		try {
			if(!SETTINGS_FILE.exists()){
				BufferedWriter settingsWriter = new BufferedWriter(new FileWriter(SETTINGS_FILE));
				settingsWriter.write(DEFAULT_DIR + "\n" + DEFAULT_TIMEOUT);
				settingsWriter.close();
			}
			BufferedReader settingsReader = new BufferedReader(new InputStreamReader(new FileInputStream(SETTINGS_FILE)));
			List<String> settingsLines = new ArrayList<String>();
			String line = null;
			while((line = settingsReader.readLine()) != null) {
				settingsLines.add(line);
			}
			settingsReader.close();
			defaultDir = new File(settingsLines.get(0));
			timeout = Integer.parseInt(settingsLines.get(1));
		} catch (IOException e) {
			System.exit(1);
		}
		try {
			if(!MACHINES_FILE.exists()) {
				BufferedWriter machinesWriter = new BufferedWriter(new FileWriter(MACHINES_FILE));
				machinesWriter.write(NEW_MACHINE.getName() + "`" + NEW_MACHINE.getPort() + "`" + NEW_MACHINE.getBaud() + "`" + NEW_MACHINE.getDatabits() + "`" + NEW_MACHINE.getParity() + "`" + NEW_MACHINE.getStopbits());
				machinesWriter.close();
			}
			BufferedReader machinesReader = new BufferedReader(new InputStreamReader(new FileInputStream(MACHINES_FILE)));
			List<String> machinesLines = new ArrayList<String>();
			String line = null;
			while((line = machinesReader.readLine()) != null) {
				machinesLines.add(line);
			}
			machinesReader.close();
			for(String l : machinesLines) {
				String[] split = l.split("`");
				machines.add(new Machine(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5])));
			}
		}catch(IOException e) {
			System.exit(1);
		}
		
		currentDir = defaultDir;
		ports = CommPortIdentifier.getPortIdentifiers();
		while(ports.hasMoreElements()) {
			CommPortIdentifier currentPort = ports.nextElement();
			if(currentPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portMap.put(currentPort.getName(), currentPort);
			}
		}
		
		frame = new Frame();
		frame.machinesList.setModel(machinesListModel);
		for(Machine m : machines) {
			machinesListModel.addElement(m.getName());
		}		
		
		log("SendNC " + SendNC.VERSION);
		log("");
		
		log("Default Directory:");
		log(defaultDir.getAbsolutePath());
		log("");
		
		log("Timeout:");
		log("" + timeout);
		log("");
		
		log("Machines:");
		for(Machine machine : machines) {
			log("------------------------------");
			log(machine.getName());
			log("Port: " + machine.getPort());
			log("Baud Rate: " + BAUD_MAP.get(machine.getBaud()));
			log("DataBits: " + machine.getDatabits());
			log("Parity: " + PARITY_MAP.get(machine.getParity()));
			log("StopBits: " + machine.getStopbits());
			log("------------------------------");
		}
		log("");
				
		log("Ready..");
		log("");
		
		frame.machinesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MachinesDialog dialog = new MachinesDialog();
				dialog.setLocationRelativeTo(frame);
				dialog.machinesList.setModel(machinesListModel);
				dialog.newButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						machines.add(SendNC.NEW_MACHINE);
						machinesListModel.addElement(SendNC.NEW_MACHINE.getName());
						updateMachinesFile();
					}
				});
				dialog.deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectedIndex = dialog.machinesList.getSelectedIndex();
						Object[] options = {"Yes", "Cancel"};
						int choice = JOptionPane.showOptionDialog(dialog, "Are you sure you want to delete that machine?", "Please Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
						if(choice == 0) {
							machines.remove(selectedIndex);
							machinesListModel.remove(selectedIndex);
							updateMachinesFile();
						}
						if(frame.machinesList.getSelectedIndex() == -1) {
							frame.sendButton.setEnabled(false);
						}
					}
				});
				dialog.machinesList.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						dialog.disableSave();
						machinesSelectionChanged = true;	
						
						if(dialog.machinesList.getSelectedIndex() == -1) {
							dialog.disableFields();
							dialog.nameField.setText("");
							dialog.portField.setText("");
							dialog.baudBox.setSelectedIndex(0);
							dialog.parityBox.setSelectedIndex(0);
							dialog.databitsGroup.clearSelection();
							dialog.stopbitsGroup.clearSelection();
						}
						
						else {
							dialog.enableFields();							
							Machine machine = machines.get(dialog.machinesList.getSelectedIndex());
							dialog.nameField.setText(machine.getName());
							dialog.portField.setText(machine.getPort());
							dialog.baudBox.setSelectedIndex(machine.getBaud());
							int databits = machine.getDatabits();
							if(databits == 7) dialog.sevenDatabitsRadio.setSelected(true);
							else if(databits == 8) dialog.eightDatabitsRadio.setSelected(true);
							dialog.parityBox.setSelectedIndex(machine.getParity());
							int stopbits = machine.getStopbits();
							if(stopbits == 1) dialog.oneStopbitsRadio.setSelected(true);
							else if(stopbits == 2) dialog.twoStopbitsRadio.setSelected(true);
					
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									machinesSelectionChanged = false;
								}
							});
						}
					}
				});
				dialog.nameField.getDocument().addDocumentListener(new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
					public void removeUpdate(DocumentEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
					public void insertUpdate(DocumentEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
				});
				dialog.portField.getDocument().addDocumentListener(new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
					public void removeUpdate(DocumentEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
					public void insertUpdate(DocumentEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
				});
				dialog.baudBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
				});
				dialog.sevenDatabitsRadio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
				});
				dialog.eightDatabitsRadio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
				});
				dialog.parityBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
				});
				dialog.oneStopbitsRadio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
				});
				dialog.twoStopbitsRadio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!machinesSelectionChanged) dialog.enableSave();
					}
				});
				dialog.moveUpButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectionIndex = dialog.machinesList.getSelectedIndex();
						if(selectionIndex > 0) {
							int relativeIndex = selectionIndex - 1;
							Machine selectionMachine = machines.get(selectionIndex);
							Machine relativeMachine = machines.get(relativeIndex);
							machines.set(selectionIndex, relativeMachine);
							machines.set(relativeIndex, selectionMachine);
							machinesListModel.set(selectionIndex, relativeMachine.getName());
							machinesListModel.set(relativeIndex, selectionMachine.getName());
							dialog.machinesList.setSelectedIndex(relativeIndex);
							if(frame.machinesList.getSelectedIndex() != -1) frame.machinesList.setSelectedIndex(relativeIndex);
							updateMachinesFile();
						}
					}
				});
				dialog.moveDownButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectionIndex = dialog.machinesList.getSelectedIndex();
						if(selectionIndex < machines.size() - 1) {
							int relativeIndex = selectionIndex + 1;
							Machine selectionMachine = machines.get(selectionIndex);
							Machine relativeMachine = machines.get(relativeIndex);
							machines.set(selectionIndex, relativeMachine);
							machines.set(relativeIndex, selectionMachine);
							machinesListModel.set(selectionIndex, relativeMachine.getName());
							machinesListModel.set(relativeIndex, selectionMachine.getName());
							dialog.machinesList.setSelectedIndex(relativeIndex);
							if(frame.machinesList.getSelectedIndex() != -1) frame.machinesList.setSelectedIndex(relativeIndex);
							updateMachinesFile();
						}
					}
				});
				dialog.saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {				
						int lastDatabits = 7;
						int lastStopbits = 2;
						if(dialog.sevenDatabitsRadio.isSelected()) lastDatabits = 7;
						else if(dialog.eightDatabitsRadio.isSelected()) lastDatabits = 8;
						if(dialog.oneStopbitsRadio.isSelected()) lastStopbits = 1;
						else if(dialog.twoStopbitsRadio.isSelected()) lastStopbits = 2;						
						if(dialog.nameField.getText().trim().equalsIgnoreCase("")) dialog.nameField.setText("Unnamed");
						if(dialog.portField.getText().trim().equalsIgnoreCase("")) dialog.portField.setText(NEW_MACHINE.getPort());						
						String name = dialog.nameField.getText();
						String port = dialog.portField.getText();
						name = name.replace("`", "'");
						port = port.replace("`", "'");						
						machines.set(dialog.machinesList.getSelectedIndex(), new Machine(name, port, dialog.baudBox.getSelectedIndex(), lastDatabits, dialog.parityBox.getSelectedIndex(), lastStopbits));
						machinesListModel.set(dialog.machinesList.getSelectedIndex(), name);
						updateMachinesFile();
						dialog.disableSave();
					}
				});
				dialog.closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});
				dialog.setVisible(true);
			}
		});
		
		frame.settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SettingsDialog dialog = new SettingsDialog();
				dialog.setLocationRelativeTo(frame);
				settingsDefaultDir = new File(defaultDir.getPath());
				settingsTimeout = timeout;
				dialog.folderField.setText(settingsDefaultDir.getPath());
				dialog.timeoutSpinner.setValue(settingsTimeout);
				dialog.folderButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.folderChooser.setCurrentDirectory(settingsDefaultDir);
						int returnVal = dialog.folderChooser.showOpenDialog(dialog);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							settingsDefaultDir = dialog.folderChooser.getSelectedFile();
							dialog.folderField.setText(settingsDefaultDir.getPath());
						}
					}
				});
				dialog.saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {	
						defaultDir = new File(settingsDefaultDir.getPath());
						timeout = settingsTimeout;
						currentDir = defaultDir;
						updateSettingsFile();
						dialog.dispose();
					}
				});
				dialog.cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dialog.dispose();
					}
				});
				dialog.setVisible(true);
			}
		});
		frame.machinesList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(!frame.sendButton.isEnabled()){
					if(!frame.fileField.getText().equalsIgnoreCase("")) frame.sendButton.setEnabled(true);
				}
			}
		});
		frame.fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.fileChooser.setCurrentDirectory(currentDir);
				frame.fileChooser.setFileFilter(new FileFilter() {
					public boolean accept(File f) {
						if(f.isDirectory()) return true;
						if(!f.getName().contains(".")) return true;
						if(f.getName().toLowerCase().endsWith(".nc")) return true;
						return false;
					}
					public String getDescription() {
						return "Numerical Control (*.nc, No Extension)";
					}
				});
				int returnVal = frame.fileChooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					currentDir = frame.fileChooser.getSelectedFile();
					frame.fileField.setText(currentDir.getName());
					if(!frame.sendButton.isEnabled()){
						if(frame.machinesList.getSelectedIndex() != -1) frame.sendButton.setEnabled(true);
					}
				}
			}
		});
		frame.sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Machine selectedMachine = machines.get(frame.machinesList.getSelectedIndex());	
				String selectedName = selectedMachine.getName();
				String selectedPort = selectedMachine.getPort();
				int selectedBaud = BAUD_MAP.get(selectedMachine.getBaud());
				int selectedDatabits = selectedMachine.getDatabits();
				int selectedParity = selectedMachine.getParity();
				int selectedStopbits = selectedMachine.getStopbits();
				
				log("------------------------------");
				log("Job: Send " + currentDir.getName() + " to " + selectedName + ".");	
				log("");
				log("Connecting to serial port " + selectedPort + "..");
				CommPortIdentifier selectedPortIdentifier = portMap.get(selectedPort);
				SerialPort serialPort;
				try {
					CommPort commPort = selectedPortIdentifier.open(selectedName, timeout);
					serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(selectedBaud, selectedDatabits, selectedStopbits, selectedParity);
				}catch(PortInUseException e) {
					log("ERROR: Port is already in use.");
					log("------------------------------");
					log("");
					return;
				}catch(Exception e) {
					log("ERROR: Failed to connect to port.");
					log("------------------------------");
					log("");
					return;
				}
				frame.setSendingState(false);
				log("Connected to " + selectedPort + ".");
				
				log("Reading file " + currentDir.getName() + "..");
				byte[] fileBytes;
				try {
					fileBytes = Files.readAllBytes(currentDir.toPath());
				}catch(IOException e) {
					log("ERROR: Failed to read file.");
					log("------------------------------");
					log("");
					serialPort.close();
					return;
				}
				log("File " + currentDir.getName() + " read.");
				
				log("Attatching to output stream..");
				OutputStream output;
				try {
					output = serialPort.getOutputStream();
					output.flush();
				}catch(IOException e) {
					log("ERROR: Failed to attatch to output stream.");
					log("------------------------------");
					log("");
					serialPort.close();
					return;
				}
				log("Attatched to output stream.");
				
				log("Sending file " + currentDir.getName() + " over port " + selectedPort + "..");
				new Thread() {
					public void run() {
						try {
							output.write(fileBytes);
						}catch(IOException e) {
							log("ERROR: Failed while sending the file.");
							log("------------------------------");
							log("");
							serialPort.close();
							return;
						}
						log("File sent.");
						log("Detatching from stream and closing port..");
						try {
							output.flush();
							output.close();
						}catch(IOException e) {
							log("ERROR: Problem occured while detatching from stream. Continuing..");
						}finally {
							serialPort.close();
						}
						log("Port closed.");
						log("");
						log("Job complete.");
						log("------------------------------");
						log("");
						frame.setSendingState(true);
						log("Ready..");
						log("");
					}
				}.start();
			}
		});
	}
	
	private void log(String line) {
		frame.statusArea.append(line + "\n");
	}
	private void updateMachinesFile() {
		try {
			String output = "";
			boolean first = true;
			for(Machine m : machines) {
				if(first) first = false;
				else output += "\n";
				output += m.getName() + "`" + m.getPort() + "`" + m.getBaud() + "`" + m.getDatabits() + "`" + m.getParity() + "`" + m.getStopbits();
			}
			BufferedWriter machinesWriter = new BufferedWriter(new FileWriter(MACHINES_FILE));
			machinesWriter.write(output);
			machinesWriter.close();
		}catch(IOException e) {
			log("ERROR: Failed to save machines to file.");
			log("");
		}
	}
	private void updateSettingsFile() {
		try {
			BufferedWriter settingsWriter = new BufferedWriter(new FileWriter(SETTINGS_FILE));
			settingsWriter.write(defaultDir + "\n" + timeout);
			settingsWriter.close();
		}catch (IOException e) {
			log("ERROR: Failed to save settings to file.");
			log("");
		}
	}
}
