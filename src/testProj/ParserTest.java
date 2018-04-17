package testProj;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ParserTest {

	public enum Block {
		DATA, MESSAGE, FILES, SAVE
	}

	private ArrayList<String> totalFiles;
	private int totalFileCount;
	private List<Commit> commits;

	public int getTotalFileCount() {
		return totalFileCount;
	}

	public ArrayList<String> getTotalFiles() {
		return totalFiles;
	}
	
	public List<Commit> getCommits() {
		return commits;
	}

	public class CommitFile {
		public String filename;
		public String mode;

		public String getFilename() {
			return filename;
		}

		public String getMode() {
			return mode;
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

		public String getAuthor() {
			return author;
		}

		public String getMessage() {
			return message;
		}

		public List<CommitFile> getFiles() {
			return files;
		}

	}

	public void readFile(String filePath) throws FileNotFoundException {
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

		while (input.hasNext()) {
			if (!finishedReading) {
				line = input.nextLine().trim();
				System.out.println(line);
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
						totalFiles.add(f.getFilename());
					}
				}
			}

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

		totalFiles = (ArrayList<String>) totalFiles.stream().distinct().collect(Collectors.toList());
		totalFiles.sort(String::compareToIgnoreCase);
		System.out.println(totalFiles);
		totalFileCount = totalFiles.size();
	}

	public static void main(String[] args) throws FileNotFoundException {
		ParserTest test = new ParserTest();
		test.readFile("C:\\Users\\Smithers\\gitdemo\\jsoniterator\\log.txt");
		//test.readFile("C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt");
	}
}
