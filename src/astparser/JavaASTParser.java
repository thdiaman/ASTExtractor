package astparser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;

import astextractor.ASTExtractorProperties;

/**
 * Handles the parsing of java files and the extraction of their Abstract Syntax Trees (ASTs).
 * 
 * @author themis
 */
public class JavaASTParser {

	/**
	 * Retrieves the children of an ASTNode.
	 * 
	 * @param node the ASTNode of which the children are retrieved.
	 * @return the children of the given ASTNode.
	 */
	@SuppressWarnings("unchecked")
	private static ArrayList<ASTNode> getChildren(ASTNode node) {
		ArrayList<ASTNode> flist = new ArrayList<ASTNode>();
		List<Object> list = node.structuralPropertiesForType();
		for (int i = 0; i < list.size(); i++) {
			StructuralPropertyDescriptor curr = (StructuralPropertyDescriptor) list.get(i);
			Object child = node.getStructuralProperty(curr);
			if (child instanceof List) {
				flist.addAll((Collection<? extends ASTNode>) child);
			} else if (child instanceof ASTNode) {
				flist.add((ASTNode) child);
			} else {
			}
		}
		return flist;
	}

	/**
	 * Recursively visits all nodes of the AST and exports it as an XML StringBuffer.
	 * 
	 * @param result the result as a StringBuffer.
	 * @param indent the indent at the current level.
	 * @param node the current ASTNode that is examined.
	 */
	private static void visitNode(StringBuffer result, String indent, ASTNode node) {
		ArrayList<ASTNode> children = getChildren(node);
		String nodeType = ASTNode.nodeClassForType(node.getNodeType()).getSimpleName();
		if (ASTExtractorProperties.OMIT.contains(nodeType)) {
			// Do nothing
		} else if (ASTExtractorProperties.LEAF.contains(nodeType)) {
			result.append(indent + "<" + nodeType + ">");
			result.append(node.toString().trim().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;"));
			result.append("</" + nodeType + ">\n");
		} else if (children.size() > 0) {
			result.append(indent + "<" + nodeType + ">\n");
			for (ASTNode child : children) {
				visitNode(result, indent + "   ", child);
			}
			result.append(indent + "</" + nodeType + ">\n");
		} else {
			result.append(indent + "<" + nodeType + ">");
			result.append(node.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;"));
			result.append("</" + nodeType + ">\n");
		}
	}

	/**
	 * Visits an AST and exports it as an XML string.
	 * 
	 * @param root the root ASTNode of the tree.
	 * @return an XML string representation of the tree.
	 */
	protected static String visitTree(ASTNode root) {
		StringBuffer result = new StringBuffer("");
		visitNode(result, "", root);
		return result.toString();
	}

	/**
	 * Parses the contents of a java file and returns its AST as an XML string.
	 * 
	 * @param str the contents of a java file given as a string.
	 * @return an XML string representation of the AST of the java file contents.
	 */
	public static String parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS14);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		return visitTree(cu);
	}

}
