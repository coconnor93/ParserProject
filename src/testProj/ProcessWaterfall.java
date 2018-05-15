package testProj;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import testProj.Parser.Commit;
import testProj.Parser.CommitFile;

public class ProcessWaterfall implements Runnable,Visualisation {

	private String filePath = "C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt";
	private Parser parse = new Parser();
	private Waterfall wf = new Waterfall();
	private Iterator<Commit> commitIterator;
	private Circle c = new Circle();
	private int duration = 50;

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

		//int a = parse.getCommits().size() - 1;
		commitIterator = parse.getCommits().iterator();
		
		boolean quit = false;

		while (!quit) {
			if (commitIterator.hasNext()) {
				wf.Decay();
				Commit c = commitIterator.next();
				ArrayList<Circle> newLine = new ArrayList<Circle>();

				for (int i = 0; i < parse.getTotalFileCount(); ++i) {
					int xrange = 1000;
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
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setDuration(int duration) {
		this.duration = duration;
		
	}

	@Override
	public void setFilepath(String filePath) {
		this.filePath = filePath;
		
	}

	@Override
	public void Start() {
		this.run();
		
	}
}
