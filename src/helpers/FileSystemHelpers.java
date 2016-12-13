package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Helper functions for handling file system operations.
 * 
 * @author themis
 */
public class FileSystemHelpers {

	/**
	 * Reads a file into a string.
	 * 
	 * @param filename the filename of the file to be read.
	 * @return the contents of the file, or null if the file does not exist.
	 */
	public static String readFileToString(String filename) {
		try {
			Scanner scanner = new Scanner(new File(filename));
			scanner.useDelimiter("\\A");
			String text = scanner.hasNext() ? scanner.next() : "";
			scanner.close();
			return text;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Finds all the java files of a folder and all its subfolders recursively.
	 * 
	 * @param folderName the path to the folder.
	 * @param files a list that is filled with the java files of the folder and its subfolders.
	 */
	private static void findJavaFilesOfFolderRecursively(String folderName, ArrayList<File> files) {
		File root = new File(folderName);
		File[] list = root.listFiles();
		if (list == null)
			return;
		for (File file : list) {
			if (file.isDirectory())
				findJavaFilesOfFolderRecursively(file.getAbsolutePath(), files);
			else if (file.getName().endsWith(".java"))
				files.add(file.getAbsoluteFile());
		}
	}

	/**
	 * Finds all the java files of a folder and all its subfolders recursively.
	 * 
	 * @param folderName the path to the folder.
	 * @return a list containing the java files of the folder and its subfolders.
	 */
	public static ArrayList<File> getJavaFilesOfFolderRecursively(String folderName) {
		ArrayList<File> files = new ArrayList<File>();
		findJavaFilesOfFolderRecursively(folderName, files);
		return files;
	}

	/**
	 * Returns a relative path given two paths, a base on and the one to be relativized.
	 * 
	 * @param base the base path.
	 * @param path the path to be relativized.
	 * @return the relative path.
	 */
	public static String getRelativePath(String base, String path) {
		return new File(base).toURI().relativize(new File(path).toURI()).getPath();
	}

}
