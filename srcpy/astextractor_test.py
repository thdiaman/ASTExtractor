from astextractor import ASTExtractor

if __name__ == '__main__':
	'''Used as a test for the python bindings'''
	ast_extractor = ASTExtractor("../target/ASTExtractor-0.5.jar", "../ASTExtractor.properties")
	ast = ast_extractor.parse_string(
			"import org.myclassimports;\n" + 
			"\n" + 
			"public class MyClass {\n" + 
			"   private int myvar;\n" + 
			"\n" + 
			"   public MyClass(int myvar) {\n" + 
			"      this.myvar = myvar;\n" + 
			"   }\n" + 
			"\n" + 
			"   public void getMyvar() {\n" + 
			"      return myvar;\n" + 
			"   }\n" + 
			"}\n"
	)
	print(ast)
	ast_extractor.close()
