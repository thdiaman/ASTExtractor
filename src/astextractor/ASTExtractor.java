package astextractor;

import java.io.File;
import java.util.ArrayList;

import org.json.XML;

import astparser.JavaASTParser;
import helpers.FileSystemHelpers;
import helpers.XMLHelpers;

/**
 * Contains all functions for extracting Abstract Syntax Trees (ASTs) from java files.
 * 
 * @author themis
 */
public class ASTExtractor {

	/**
	 * Parses the contents of a java file and returns its AST.
	 * 
	 * @param fileContents the contents of a java file, given as a String.
	 * @return a String containing the AST of the java file in XML format.
	 */
	public static String parseString(String fileContents) {
		return parseString(fileContents, "XML");
	}

	/**
	 * Parses the contents of a java file and returns its AST.
	 * 
	 * @param fileContents the contents of a java file, given as a String.
	 * @param astFormat the format of the returned AST, either "XML" or "JSON".
	 * @return a String containing the AST of the java file in XML or JSON format.
	 */
	public static String parseString(String fileContents, String astFormat) {
		String result = JavaASTParser.parse(fileContents);
		if (astFormat.equals("JSON"))
			return XML.toJSONObject(result).toString(3);
		else
			return XMLHelpers.formatXML(result, 3);
	}

	/**
	 * Parses a java file and returns its AST.
	 * 
	 * @param filename the filename of the java file to be parsed.
	 * @return a String containing the AST of the java file in XML format.
	 */
	public static String parseFile(String filename) {
		return parseFile(filename, "XML");
	}

	/**
	 * Parses a java file and returns its AST.
	 * 
	 * @param filename the filename of the java file to be parsed.
	 * @param astFormat the format of the returned AST, either "XML" or "JSON".
	 * @return a String containing the AST of the java file in XML or JSON format.
	 */
	public static String parseFile(String filename, String astFormat) {
		String result = parseString(FileSystemHelpers.readFileToString(filename));
		if (astFormat.equals("JSON"))
			return XML.toJSONObject(result).toString(3);
		else
			return XMLHelpers.formatXML(result, 3);
	}

	/**
	 * Parses all the files of a folder and returns a unified AST.
	 * 
	 * @param folderName the path of the folder of which the files are parsed.
	 * @return an AST containing all the files of a folder in XML format.
	 */
	public static String parseFolder(String folderName) {
		return parseFolder(folderName, "XML");
	}

	/**
	 * Parses all the files of a folder and returns a unified AST.
	 * 
	 * @param folderName the path of the folder of which the files are parsed.
	 * @param astFormat the format of the returned AST, either "XML" or "JSON".
	 * @return an AST containing all the files of a folder in XML or JSON format.
	 */
	public static String parseFolder(String folderName, String astFormat) {
		String folderAbsolutePath = new File(folderName).getAbsolutePath();
		ArrayList<File> files = FileSystemHelpers.getJavaFilesOfFolderRecursively(folderName);
		StringBuilder results = new StringBuilder("<folder>\n");
		for (File file : files) {
			String fileAbsolutePath = file.getAbsolutePath();
			String filePath = FileSystemHelpers.getRelativePath(folderAbsolutePath, fileAbsolutePath);
			String result = parseFile(fileAbsolutePath);
			results.append("<file>\n<path>" + filePath + "</path>\n<ast>\n" + result + "</ast>\n</file>\n");
		}
		results.append("</folder>\n");
		if (astFormat.equals("JSON"))
			return XML.toJSONObject(results.toString()).toString(3);
		else
			return XMLHelpers.formatXML(results.toString(), 3);
	}
}
