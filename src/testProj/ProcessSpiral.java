package testProj;

import java.io.FileNotFoundException;
import java.util.Iterator;
import testProj.Parser.Commit;
import testProj.Parser.CommitFile;

public class ProcessSpiral implements Runnable,Visualisation {

	private String filePath = "C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt";
	private Parser parse = new Parser();
	private Spiral swing = null;
	private Iterator<Commit> commitIterator;
	private int duration = 50;

	public static void main(String args[]) throws FileNotFoundException {
		ProcessSpiral p = new ProcessSpiral();
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

		commitIterator = parse.getCommits().iterator();
		boolean quit = false;
		swing = new Spiral(parse.getTotalFileCount());

		while (!quit) {
			if (commitIterator.hasNext()) {
				Commit c = commitIterator.next();

				for (CommitFile cf : c.getFiles()) {
					String filename = cf.getFilename();
					System.out.println(filename);
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
			System.out.println("Repaint");

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
