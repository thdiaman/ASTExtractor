package astextractor;

import helpers.ParseHelpers;

/**
 * Contains the main class of the application.
 * 
 * @author themis
 */
public class MainApp {

	/**
	 * Prints the help message of the command line interface.
	 */
	private static void printHelpMessage() {
		System.out.println("ASTExtractor: Abstract Syntax Tree Extractor for Java Source Code\n");
		System.out.println("Run as:\n java -jar ASTExtractor.jar -project=\"path/to/project\" -repr=XML|JSON");
		System.out.println("Or as:\n java -jar ASTExtractor.jar -file=\"path/to/file\" -repr=XML|JSON");
		System.out.println("where repr allows selecting the representation of the tree (default is XML)");
	}

	/**
	 * Executes the application.
	 * 
	 * @param args arguments for executing in command line mode.
	 */
	public static void main(String args[]) {
		if (args.length > 0) {
			String[] arguments = ParseHelpers.parseArgs(args);
			String project = arguments[0];
			String file = arguments[1];
			String repr = "XML";
			if (project.length() > 0 ^ file.length() > 0) {
				if (arguments[2].length() > 0 && !(arguments[2].equals("JSON") || arguments[2].equals("XML")))
					printHelpMessage();
				else {
					if (arguments[2].equals("JSON") || arguments[2].equals("XML"))
						repr = arguments[2];
					String result = "";
					if (project.length() > 0)
						result = ASTExtractor.parseFolder(project, repr);
					else if (file.length() > 0)
						result = ASTExtractor.parseFile(file, repr);
					System.out.println(result);
				}
			} else {
				printHelpMessage();
			}
		} else {
			printHelpMessage();
		}
	}
}
