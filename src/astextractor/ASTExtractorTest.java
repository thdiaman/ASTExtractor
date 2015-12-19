package astextractor;

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
		// @formatter:off
		String ast = ASTExtractor.parseString(""
		         + "import org.myclassimports;"
		         + ""
		         + "public class MyClass {"
		         + "    private int myvar;"
		         + ""
		         + "    public MyClass(int myvar) {"
		         + "        this.myvar = myvar;"
		         + "    }"
		         + ""
		         + "    public void getMyvar() {"
		         + "        return myvar;"
		         + "    }"
		         + "}"
		         );
		// @formatter:on
		System.out.println(ast);
	}

}
