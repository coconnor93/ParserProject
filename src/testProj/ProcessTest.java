package testProj;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import testProj.ParserTest.Commit;
import testProj.ParserTest.CommitFile;
import testProj.SwingTest.Circle;

public class ProcessTest {

	static String filePath = "C:\\Users\\Smithers\\gitdemo\\java_1\\log.txt";
	private static ParserTest parse = new ParserTest();

	private static List<CommitFile> files;
	private static List<Commit> commits;
	
	static int n = 1;
	private static SwingTest swing = new SwingTest(n);
	private static List<Circle> circles;
	
	public void touchCircles(){
		List<CommitFile> files = new ArrayList<CommitFile>();
		
	}

	public static void main(String args[]) throws FileNotFoundException {
		parse.sortClasses(filePath);

	}

}
