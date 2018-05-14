package testProj;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import testProj.ParserTest.Commit;
import testProj.ParserTest.CommitFile;

public class ProcessWaterfall {

	private String filePath = "C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt";
	private ParserTest parse = new ParserTest();
	private Waterfall wf = new Waterfall();
	private Iterator<Commit> commitIterator;
	private Circle c = new Circle();

	public static void main(String[] args) throws FileNotFoundException {
		ProcessWaterfall p = new ProcessWaterfall();
		p.run();
	}

	public void run() {
		try {
			parse.readFile(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Starting to iterate");

		commitIterator = parse.getCommits().iterator();
		boolean quit = false;
		// wf.RandomLine(parse.getTotalFileCount());

		while (!quit) {
			if (commitIterator.hasNext()) {
				wf.Decay();
				Commit c = commitIterator.next();
				ArrayList<Circle> newLine = new ArrayList<Circle>();

				for (int i = 0; i < parse.getTotalFileCount(); ++i) {
					int xrange = 800;
					int diameter = xrange / parse.getTotalFileCount();
					Circle cn = new Circle();
					cn.xValue = (diameter + 1) * i;
					cn.yValue = 0;
					cn.setDiameter(diameter);
					newLine.add(cn);
				}

				for (CommitFile cf : c.getFiles()) {
					String filename = cf.getFilename();
					String mode = cf.getMode();
					int x = parse.getTotalFiles().indexOf(filename);

					if (mode.equals("A")) {
						newLine.get(x).touchGreen();
					} else if (mode.equals("M"))
						newLine.get(x).touchBlue();
					else if (mode.equals("D"))
						newLine.get(x).touchRed();
					else
						newLine.get(x).touchWhite();

				}
				wf.AddLine(newLine);
			} else {
				System.out.println("Finished");
				quit = true;
			}

			wf.repaint();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
