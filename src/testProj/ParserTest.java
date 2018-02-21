package testProj;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ParserTest {

	public enum Block {
		DATA, MESSAGE, FILES, SAVE
	}

	public class CommitFile {
		public String filename;
		public String mode;

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

		public String toString() {
			return filename + " (" + mode + ")";
		}
	}

	public class Commit {
		public String commit;
		public String author;
		public String message;
		List<CommitFile> files;

		public String getCommit() {
			return commit;
		}

		public void setCommit(String commit) {
			this.commit = commit;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public List<CommitFile> getFiles() {
			return files;
		}

		public void setFiles(List<CommitFile> files) {
			this.files = files;
		}

	}

	public void sortClasses(String filePath) throws FileNotFoundException {
		Scanner input = new Scanner(new File(filePath));
		List<CommitFile> files = new ArrayList<CommitFile>();
		ArrayList<String> totalFiles = new ArrayList<String>();

		boolean finishedReading = false;
		String line = "";

		while (input.hasNext()) {
			if (!finishedReading) {
				line = input.nextLine().trim();
			} else {
				finishedReading = false;
			}

			String[] parts = line.split("\t");
			if (parts.length > 1 && parts[0].length() == 1) {
				CommitFile f = new CommitFile();
				f.filename = parts[1];
				f.mode = parts[0];
				files.add(f);
				//int loopMax = files.size() / 100;
				System.out.println(f);
				String fileAdded = "";
				for (int i = 0; i < 0.38; i++) {
					fileAdded = f.getFilename();
					totalFiles.add(fileAdded);
				}
			}
		}

		totalFiles = (ArrayList<String>) totalFiles.stream().distinct().collect(Collectors.toList());
		totalFiles.sort(String::compareToIgnoreCase);
		System.out.println(totalFiles);
		int totalFileCount = totalFiles.size();
		System.out.println("TOTAL FILE COUNT: " +totalFileCount);
	}

	public void readFile(String filePath) throws FileNotFoundException {
		List<Commit> commits = new ArrayList<Commit>();
		Block location = Block.DATA;
		Scanner input = new Scanner(new File(filePath));

		String commit = "";
		String author = "";
		String message = "";

		List<CommitFile> files = new ArrayList<CommitFile>();

		boolean finishedReading = false;

		String line = "";

		while (input.hasNext()) {
			if (!finishedReading) {
				line = input.nextLine().trim();
				// System.out.println(">> " + line);
			} else {
				finishedReading = false;
			}

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
				} else if (line.indexOf("commit") == 0) {
					location = Block.SAVE;
					finishedReading = true;
				} else {
					String[] parts = line.split("\t");
					if (parts.length > 1 && parts[0].length() == 1) {
						location = Block.FILES;
						finishedReading = true;
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
						finishedReading = true;
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
				Commit c = new Commit();
				c.author = author;
				c.commit = commit;
				c.message = message;
				c.files = files;
				// int fileCount = 0;

				// System.out.println("Commit: "+commit+" by "+author);

				commits.add(c);

				System.out.println("The commit: " + commit + " contains these files: " + files.toString());
				// System.out.println(files.toString());

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
		// test.sortClasses("C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt");
		test.sortClasses("C:\\Users\\Smithers\\gitdemo\\java_1\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\java_1\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\squaresquash\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\wikishare\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\javachat\\log.txt");
		// test.readFile("C:\\Users\\Smithers\\gitdemo\\waveguardhq\\log.txt");

	}

}
