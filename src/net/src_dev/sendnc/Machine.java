package net.src_dev.sendnc;

public class Machine {
	private String name;
	private String port;
	private int baud;
	private int databits;
	private int parity;
	private int stopbits;

	
	public Machine(String name, String port, int baud, int databits, int parity, int stopbits) {
		this.name = name;
		this.port = port;
		this.baud = baud;
		this.databits = databits;
		this.parity = parity;
		this.stopbits = stopbits;

	}
	
	public String getName() {
		return name;
	}
	public String getPort() {
		return port;
	}
	public int getBaud() {
		return baud;
	}
	public int getDatabits() {
		return databits;
	}
	public int getParity() {
		return parity;
	}
	public int getStopbits() {
		return stopbits;
	}
}
