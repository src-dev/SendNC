package net.src_dev.sendnc;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class NCFileFilter extends FileFilter {

	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		if(!f.getName().contains(".")) return true;
		if(f.getName().toLowerCase().endsWith(".nc")) return true;
		return false;
	}

	public String getDescription() {
		return "Numerical Control (*.nc, No Extension)";
	}

}
