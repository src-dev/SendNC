package net.src_dev.sendnc;

import java.util.HashMap;
import java.util.Map;

public class Machine {
	private static final Map<Integer, Integer> BAUD_MAP = new HashMap<Integer, Integer>();
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
	private static final Map<Integer, String> PARITY_MAP = new HashMap<Integer, String>();
	{
		PARITY_MAP.put(0, "None");
		PARITY_MAP.put(1, "Odd");
		PARITY_MAP.put(2, "Even");
		PARITY_MAP.put(3, "Mark");
		PARITY_MAP.put(4, "Space");
	}
	
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
	public String getParityAsString() {
		return PARITY_MAP.get(parity);
	}
	public int getRealBaud() {
		return BAUD_MAP.get(baud);
	}
}
