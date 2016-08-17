package astextractor;

import astextractor.ASTExtractorProperties;
import astextractor.ASTExtractor;

/**
 * Test for the {@link ASTExtractor} class.
 * 
 * @author themis
 */
public class ASTExtractorTest {

	/**
	 * Function used to test the AST extractor.
	 * 
	 * @param args unused parameter.
	 */
	public static void main(String[] args) {
		ASTExtractorProperties.setProperties("ASTExtractor.properties");

		// @formatter:off
		String ast = ASTExtractor.parseString(""
		         + "import org.myclassimports;\n"
		         + ""
		         + "public class MyClass {\n"
		         + "    private int myvar;\n"
		         + "\n"
		         + "    public MyClass(int myvar) {\n"
		         + "        this.myvar = myvar;\n"
		         + "    }\n"
		         + ""
		         + "    public void getMyvar() {\n"
		         + "        return myvar;\n"
		         + "    }\n"
		         + "}\n"
		         );
		// @formatter:on
		System.out.println(ast);
	}

}
