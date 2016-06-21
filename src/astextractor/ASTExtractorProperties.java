package astextractor;

import java.util.HashSet;

import helpers.ParseHelpers;

/**
 * Handles setting the properties for omitting nodes or keeping them as leafs.
 * 
 * @author themis
 */
public class ASTExtractorProperties {

	/**
	 * The nodes of the AST that should be printed as they are.
	 */
	public static HashSet<String> LEAF = new HashSet<String>();

	/**
	 * The nodes of the AST that should be omitted.
	 */
	public static HashSet<String> OMIT = new HashSet<String>();

	/**
	 * Sets the properties given a properties file.
	 * 
	 * @param propertiesFile the file that contains the properties.
	 */
	public static void setProperties(String propertiesFile) {
		LEAF.clear();
		OMIT.clear();
		for (String rule : ParseHelpers.parseProperties(propertiesFile)) {
			String[] srule = rule.split("=");
			if (srule[1].equals("LEAF"))
				LEAF.add(srule[0]);
			else if (srule[1].equals("OMIT"))
				OMIT.add(srule[0]);
		}
	}

}
