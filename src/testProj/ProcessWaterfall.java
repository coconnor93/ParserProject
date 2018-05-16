package testProj;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import testProj.Parser.Commit;
import testProj.Parser.CommitFile;

/**
 * The processor class for the waterfall visualisation technique. Implements the runnable interface 
 * to handle the execution and the Visualisation interface we have created.
 * @author Christopher O'Connor 
 * @version 1.0
 */

public class ProcessWaterfall implements Visualisation {
	
	//Declaration of variables used including instances of parser and waterfall
	private String filePath = "C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt";
	private Parser parse = new Parser();
	private Waterfall wf = new Waterfall();
	private Iterator<Commit> commitIterator;
	private Circle c = new Circle();
	private int duration = 50;
	
	/**
	 * The main method which executes the processor
	 * @param args
	 * @throws FileNotFoundException
	 */

	public static void main(String[] args) throws FileNotFoundException {
		ProcessWaterfall p = new ProcessWaterfall();
		p.run();
	}
	
	/**
	 * The run() method uses an iterator to go through the entire list of commits obtained from the
	 * parser class. It then gets access to the list of files that are altered during each commit and 
	 * calls the relevant touch method from the Circle class to signify a change occurred to a 
	 * certain class during a certain commit. 
	 */

	public void run() {
		try {
			parse.readFile(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

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

	/**
	 * This method called from the Visualisation interface sets the duration the visualisation will run
	 */

	@Override
	public void setDuration(int duration) {
		this.duration = duration;		
	}
	
	/**
	 * This method called from the Visualisation interface sets the filePath that will be used
	 */

	@Override
	public void setFilepath(String filePath) {
		this.filePath = filePath;
		
	}
	
	/**
	 * This method called from the Visualisation interface calls the run() method to instigate the processor
	 */

	@Override
	public void Start() {
		this.run();
		
	}
}
