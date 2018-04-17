package testProj;

import java.io.FileNotFoundException;
import java.util.Iterator;
import testProj.ParserTest.Commit;
import testProj.ParserTest.CommitFile;

public class ProcessTest implements Runnable {

	private String filePath = "C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt";
	private ParserTest parse = new ParserTest();
	private SwingTest swing = null;
	private Iterator<Commit> commitIterator;

	public static void main(String args[]) throws FileNotFoundException {
		ProcessTest p = new ProcessTest();
		p.run();
	}

	public void run() {
		try {
			parse.readFile(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

		// parse.getTotalFileCount();
		// parse.getTotalFiles();

		commitIterator = parse.getCommits().iterator();
		boolean quit = false;
		swing = new SwingTest(parse.getTotalFileCount());

		while (!quit) {
			if (commitIterator.hasNext()) {
				Commit c = commitIterator.next();

				for (CommitFile cf : c.getFiles()) {
					String filename = cf.getFilename();
					String mode = cf.getMode();
					int x = parse.getTotalFiles().indexOf(filename);
					if (mode.equals("A"))
						swing.circles.get(x).touchGreen();
					else if (mode.equals("M"))
						swing.circles.get(x).touchBlue();
					else if (mode.equals("D"))
						swing.circles.get(x).touchRed();
					else
						swing.circles.get(x).touchWhite();
				}
			} else {
				System.out.println("Finished");
				quit = true;
			}

			swing.Decay();
			swing.repaint();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
