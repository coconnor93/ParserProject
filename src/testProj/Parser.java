package testProj;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class contains one of the integral parts of the system. The parser traverses through
 * an entire commit history of a project and pulls out specific variables that are used by the 
 * processor class of a visualisation to display the correct information.
 * @author Christopher O'Connor
 * @version 1.0
 */

public class Parser {
	
	/*
	 * Enumerations are declared and used to specify what part of the commit history we are
	 * currently traversing through.
	 */
	public enum Block {
		DATA, MESSAGE, FILES, SAVE
	}
	
	//Important declarations that are used in the processor class
	private ArrayList<String> totalFiles;
	private int totalFileCount;
	private List<Commit> commits;

	
	/**
	 * Method that returns the total file amount contained in a project
	 * @return totalFileCount The total amount of files contained in a project
	 */
	public int getTotalFileCount() {
		return totalFileCount;
	}

	/**
	 * Method that returns the total files contained in a project as an array of strings
	 * @return totalFiles The total files contained in a project
	 */
	
	public ArrayList<String> getTotalFiles() {
		return totalFiles;
	}
	
	/**
	 * Method that returns a full list of commits contained within a project's entire commit history
	 * @return commits Every commit contained with a project
	 */
	
	public List<Commit> getCommits() {
		return commits;
	}

	public class CommitFile {
		//Declarations of variables that files modified during a commit contain
		public String filename;
		public String mode;
		
		/**
		 * Method that returns a specific name of a file contain in a project
		 * @return filename The name of the queried file
		 */
		
		public String getFilename() {
			return filename;
		}
		
		/**
		 * Method that returns the specific alteration a file occurs during a commit
		 * @return mode Either add, delete, modify
		 */

		public String getMode() {
			return mode;
		}
		
		/**
		 * Method that returns the changed files during a commit to string mode
		 * @return filename + mode e.g. README.md M
		 */

		public String toString() {
			return filename + " (" + mode + ")";
		}
	}

	public class Commit {
		//Declaration of variables 
		public String commit;
		public String author;
		public String message;
		List<CommitFile> files;
		
		/**
		 * Method that returns the unique commit identifier hash
		 * @return commit The unique commit identifier hash
		 */

		public String getCommit() {
			return commit;
		}
		
		/**
		 * Method that returns the author of the commit
		 * @return author The author of the commit
		 */

		public String getAuthor() {
			return author;
		}
		
		/**
		 * Method that returns the commit message added
		 * @return message The commit message added by the author
		 */

		public String getMessage() {
			return message;
		}
		
		/**
		 * Method that returns the files modified during a commit stored in an ArrayList
		 * @return files The files contained within a single commit
		 */

		public List<CommitFile> getFiles() {
			return files;
		}

	}
	/**
	 * Method that traverses through an entire commit log. It identifies what section of the log is it at 
	 * and proceeds to gather specific information needed and stores them in the declarations at the top
	 * of the class declaration
	 * @param filePath The project being queried
	 * @throws FileNotFoundException
	 */
	public void readFile(String filePath) throws FileNotFoundException {
		//Initialise arrays and blank String values, location set to Data
		commits = new ArrayList<Commit>();
		totalFiles = new ArrayList<String>();
		Block location = Block.DATA;
		Scanner input = new Scanner(new File(filePath));

		String commit = "";
		String author = "";
		String message = "";

		List<CommitFile> files = new ArrayList<CommitFile>();

		boolean finishedReading = false;

		String line = "";
		
		//Checks to see there is input available, if not the parser is finished
		while (input.hasNext()) {
			if (!finishedReading) {
				line = input.nextLine().trim();
				System.out.println(line);
			} else {
				finishedReading = false;
			}
			
			//If theres any whitespace, the Data block is finished at the parser is at the commit message
			if (location == Block.DATA) {
				if (line.equalsIgnoreCase(""))
					location = Block.MESSAGE;
				else {
					int space = line.indexOf(" ");
					String left = line.substring(0, space);
					String right = line.substring(space + 1).trim();
					//"commit" is the commit hash line, "Commit:" is the committer
					if (left.equalsIgnoreCase("commit") && !left.equalsIgnoreCase("Commit:"))
						commit = right;
					else if (left.equalsIgnoreCase("Author:"))
						author = right;
				}
			} else if (location == Block.MESSAGE) {
				//If we're in the message block, with whitespace and no line contains the word "commit",
				//the parser is finished this specific commit
				if (line.equalsIgnoreCase("")) {
				} else if (line.indexOf("commit") == 0) {
					location = Block.SAVE;
					finishedReading = true;
				} else {
					//Validates that the current line conforms to the files layout i.e. "A		README.md"
					String[] parts = line.split("\t");
					if (parts.length > 1 && parts[0].length() == 1) {
						location = Block.FILES;
						finishedReading = true;
					} else {
						message += line;
					}
				}
			} else if (location == Block.FILES) {
				//Signifies that the parser has completed traversing through the files and we're ready to save
				if (line.equalsIgnoreCase("")) {
					location = Block.SAVE;
				} else {
					if (line.indexOf("commit") == 0) {
						location = Block.SAVE;
						finishedReading = true;
					} else {
						//Adds the details of the files altered to the totalFiles array
						String[] parts = line.split("\t");
						CommitFile f = new CommitFile();
						f.filename = parts[1];
						f.mode = parts[0];
						files.add(f);
						totalFiles.add(f.getFilename());
					}
				}
			}
			
			//We're finished traversing through one commit log. Save the details of that to the commits array
			//and blank String values to begin the next commit log

			if (location == Block.SAVE || !input.hasNext()) {
				Commit c = new Commit();
				c.author = author;
				c.commit = commit;
				c.message = message;
				c.files = files;
				
				commits.add(c);
				System.out.println("The commit: " + commit + " contains these files: " + files.toString());

				author = "";
				commit = "";
				message = "";
				files = new ArrayList<CommitFile>();
				location = Block.DATA;
			}
		}
		input.close();
		
		//Cuts out repeating file names and sorts them in alphabetically order to keep files within
		//the same directory together
		totalFiles = (ArrayList<String>) totalFiles.stream().distinct().collect(Collectors.toList());
		totalFiles.sort(String::compareToIgnoreCase);
		System.out.println(totalFiles);
		totalFileCount = totalFiles.size();
	}
	
	/**
	 * The main method to instigate the parser
	 * @param args
	 * @throws FileNotFoundException
	 */

	public static void main(String[] args) throws FileNotFoundException {
		Parser test = new Parser();
		//test.readFile("C:\\Users\\Smithers\\gitdemo\\jsoniterator\\log.txt");
		test.readFile("C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt");
	}
}
