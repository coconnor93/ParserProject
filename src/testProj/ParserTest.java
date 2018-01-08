package testProj;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParserTest {

	public enum Block {
		DATA, MESSAGE, FILES, SAVE
	}

	public class CommitFile {
		public String filename;
		public String mode;

		public String toString() {
			return filename + " (" + mode + ")";
		}
	}

	public class Commit {
		public String commit;
		public String author;
		public String message;
		List<CommitFile> files;
	}

	public void readFile(String filePath) throws FileNotFoundException {
		List<Commit> commits = new ArrayList<Commit>();
		Block location = Block.DATA;
		Scanner input = new Scanner(new File(filePath));

		String commit = "";
		String author = "";
		String message = "";
		// int commitCount = 0;

		List<CommitFile> files = new ArrayList<CommitFile>();

		boolean alreadyReadLine = false;

		String line = "";

		while (input.hasNext()) {
			if (!alreadyReadLine) {
				line = input.nextLine().trim();
				System.out.println(">> " + line);
			} else {
				alreadyReadLine = false;
			}
			// System.out.println("INPUT: ["+line+"]");

			if (location == Block.DATA) {
				if (line.equalsIgnoreCase(""))
					location = Block.MESSAGE;
				else {
					int space = line.indexOf(" ");
					String left = line.substring(0, space);
					String right = line.substring(space + 1).trim();
					if (left.equalsIgnoreCase("commit") && !left.equalsIgnoreCase("Commit:"))
						commit = right;
					else if (left.equalsIgnoreCase("Author:"))
						author = right;
				}
			} else if (location == Block.MESSAGE) {
				if (line.equalsIgnoreCase("")) {
					// location = Block.FILES;
				} else if (line.indexOf("commit") == 0) {
					location = Block.SAVE;
					alreadyReadLine = true;
				} else {
					// Test if files or still message
					String[] parts = line.split("\t");
					if (parts.length > 1 && parts[0].length() == 1) {
						// System.out.println("P: "+parts.toString());
						// Files!
						location = Block.FILES;
						alreadyReadLine = true;
					} else {
						message += line;
					}
				}
			} else if (location == Block.FILES) {
				if (line.equalsIgnoreCase("")) {
					location = Block.SAVE;
				} else {
					if (line.indexOf("commit") == 0) {
						location = Block.SAVE;
						alreadyReadLine = true;
					} else {
						String[] parts = line.split("\t");
						CommitFile f = new CommitFile();
						f.filename = parts[1];
						f.mode = parts[0];
						files.add(f);
					}
				}
			}

			if (location == Block.SAVE || !input.hasNext()) {
				// END OF DATA.. SAVE AND START AGAIN
				// 1. Save that commit somewhere - memory as a class whatever
				// 2. Blank all the container strings and the files list
				// 3. Reset mode to be data again

				Commit c = new Commit();
				c.author = author;
				c.commit = commit;
				c.message = message;
				c.files = files;

				// System.out.println("Commit: "+commit+" by "+author);

				commits.add(c);
				// commitCount++;

				System.out.println("The commit:  " + commit + " contains these files: " + files.toString());

				author = "";
				commit = "";
				message = "";
				files = new ArrayList<CommitFile>();
				location = Block.DATA;
			}
		}
		input.close();
	}

	public static void main(String[] args) throws FileNotFoundException {
		ParserTest test = new ParserTest();
		test.readFile("C:\\Users\\Smithers\\gitdemo\\java_1\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\squaresquash\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\wikishare\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\javachat\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\waveguardhq\\log.txt");

	}

}
