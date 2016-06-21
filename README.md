ASTExtractor: Abstract Syntax Tree Extractor for Java Source Code
=================================================================
ASTExtractor is an Abstract Syntax Tree (AST) extractor for Java source code, based
on the Eclipse compiler. The tool functions as a wrapper of the Eclipse compiler and
allows exporting the AST of source code files or projects in XML and JSON formats.
The tool has a command line interface and can also be used as a library.
The documentation is available at [http://thdiaman.github.io/ASTExtractor/](http://thdiaman.github.io/ASTExtractor/)

Executing in Command Line mode
------------------------------
Execute as: <pre><code>java -jar ASTExtractor.jar -project="path/to/project" -properties="path/to/propertiesfile" -repr=XML|JSON</code></pre>
for projects, or as: <pre><code>java -jar ASTExtractor.jar -file="path/to/file" -properties="path/to/propertiesfile" -repr=XML|JSON</code></pre>
for java files, where <code>-properties</code> allows setting the location of the properties file (default is no properties so all syntax tree 
nodes are returned) and <code>-repr</code> allows selecting the representation of the tree (default is XML).

Using as a library
------------------
Import the library in your code. Set a location for the properties file using <pre><code>ASTExtractorProperties.setProperties("ASTExtractor.properties");</code></pre>.
Then, you can use it as follows:
- For folders containing java files:<pre><code>String ast = ASTExtractor.parseFolder("path/to/folder/");</code></pre>
- For java files:<pre><code>String ast = ASTExtractor.parseFile("path/to/file.java");</code></pre>
- For contents of java files (i.e. strings):
<pre><code>String ast = ASTExtractor.parseString(""
			 + "import org.myclassimports;\n"
			 + "\n"
			 + "public class MyClass {\n"
			 + "	private int myvar;\n"
			 + "\n"
			 + "	public MyClass(int myvar) {\n"
			 + "		this.myvar = myvar;\n"
			 + "	}\n"
			 + "\n"
			 + "	public void getMyvar() {\n"
			 + "		return myvar;\n"
			 + "	}\n"
			 + "}\n"
			 );</code></pre>
If JSON is required as the output representation then use these functions with a second string
argument that can be either <code>"XML"</code> or <code>"JSON"</code>.

Using in Python
---------------
ASTExtractor also has python bindings. Using the python wrapper is simple. At first, the library
has to be imported and the ASTExtractor object has to be initialized given the path to the jar
of the library and the path to the properties file of the library:
<pre><code>ast_extractor = ASTExtractor("path/to/ASTExtractor.jar", "path/to/ASTExtractor.properties")</code></pre>
After that, you can use it as follows:
- For folders containing java files:<pre><code>ast = ast_extractor.parse_folder("path/to/folder/");</code></pre>
- For java files:<pre><code>ast = ast_extractor.parse_file("path/to/file.java");</code></pre>
- For contents of java files (i.e. strings):
<pre><code>ast = ast_extractor.parse_string(
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
	)</code></pre>
If JSON is required as the output representation then use these functions with a second string
argument that can be either <code>"XML"</code> or <code>"JSON"</code>.

Note that after using the library, you have to close the ASTExtractor object using function <code>close</code>, i.e.:<pre><code>ast_extractor.close()</code></pre>

Controlling the output
----------------------
An Abstract Syntax Tree can be very complex, including details for every identifier of the code.
In ASTExtractor, the complexity of the tree can be controlled using the ASTExtractor.properties
file. In this file, the user can select the nodes that should not appear in the final tree (<code>OMIT</code>)
and the nodes that should not be analyzed further, i.e. that should be forced to be leaf nodes (<code>LEAF</code>)
The default options are shown in the following example ASTExtractor.properties file:
<pre><code>LEAF = PackageDeclaration, ImportDeclaration, ParameterizedType, ArrayType, VariableDeclarationFragment
OMIT = Javadoc, Block</code></pre>

