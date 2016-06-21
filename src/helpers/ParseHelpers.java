package helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper functions for parsing arguments or properties files.
 * 
 * @author themis
 */
public class ParseHelpers {

	/**
	 * Parses the command line arguments.
	 * 
	 * @param args the arguments to be parsed.
	 * @return a string with the values of the arguments.
	 */
	public static String[] parseArgs(String[] args) {
		List<String> col = new ArrayList<String>();
		for (String arg : args) {
			String narg = arg.trim();
			if (narg.contains("=")) {
				for (String n : narg.split("=")) {
					col.add(n);
				}
			} else
				col.add(arg.trim());
		}
		boolean sproject = false;
		boolean sfile = false;
		boolean sproperties = false;
		boolean srepr = false;
		String project = "";
		String file = "";
		String properties = "";
		String repr = "";
		for (String c : col) {
			if (c.startsWith("-project")) {
				sproject = true;
				sfile = false;
				sproperties = false;
				srepr = false;
			} else if (c.startsWith("-file")) {
				sproject = false;
				sfile = true;
				sproperties = false;
				srepr = false;
			} else if (c.startsWith("-properties")) {
				sproject = false;
				sfile = false;
				sproperties = true;
				srepr = false;
			} else if (c.startsWith("-repr")) {
				sproject = false;
				sfile = false;
				sproperties = false;
				srepr = true;
			} else {
				if (sproject)
					project += c + " ";
				else if (sfile)
					file += c + " ";
				else if (sproperties)
					properties += c + " ";
				else if (srepr)
					repr += c + " ";
			}
		}
		return new String[] { project.trim(), file.trim(), properties.trim(), repr.trim().toUpperCase() };
	}

	/**
	 * Parses a properties file with the OMIT and ASIS types of nodes.
	 * 
	 * @param filename the filename of the properties file
	 * @return an ArrayList containing node types and category (ASIS or OMIT).
	 */
	public static ArrayList<String> parseProperties(String filename) {
		ArrayList<String> rules = new ArrayList<String>();
		if (new File(filename).isFile()) {
			String propertiesString = FileSystemHelpers.readFileToString(filename);
			String lines[] = propertiesString.split("\\r?\\n");
			for (String line : lines) {
				line = line.trim();
				if (line.startsWith("LEAF")) {
					String[] sline = line.split("=")[1].trim().split(",");
					for (String string : sline)
						rules.add(string.trim() + "=LEAF");
				} else if (line.startsWith("OMIT")) {
					String[] sline = line.split("=")[1].trim().split(",");
					for (String string : sline)
						rules.add(string.trim() + "=OMIT");
				}
			}
		}
		return rules;
	}

}
