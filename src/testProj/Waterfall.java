package testProj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Waterfall extends JPanel {

	private static final long serialVersionUID = 1L;
	private SwingTest swing = null;
	ArrayList<ArrayList<Circle>> circles = new ArrayList<ArrayList<Circle>>();
	private int x;
	private int y;
	private int diameter;
	private int numberOfCircles = 40;
	private int maxRows = 50;
	int height = 1000;
	int width = 1000;
	private boolean quit = false;

	public Waterfall() {
		initUI();
		initCircles();
	}

	private void initUI() {
		JFrame frame = new JFrame();
		JPanel panel = this;
		frame.setContentPane(panel);
		frame.setTitle("Project Display");
		frame.pack();
		frame.setSize(height, width);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		panel.setBackground(Color.DARK_GRAY);
	}

	public void initCircles() {
		x = (int) Math.ceil(Math.sqrt((double) numberOfCircles));
		y = x;
		diameter = (250 / x) - 1;
	}

	public void AddLine(ArrayList<Circle> newLine) {
		circles.add(0, newLine);

		if (circles.size() > maxRows) {
			circles.remove(circles.size() - 1);
		}
	}

	public ArrayList<Circle> GetLastLineCopy() {
		ArrayList<Circle> newLine = new ArrayList<Circle>();
		if (circles.size() > 0) {
			newLine.addAll(circles.get(0));
		}
		return newLine;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		int row = 0;
		for (ArrayList<Circle> line : circles) {
			for (Circle c : line) {
				g.setColor(c.getColor());
				g.fillOval(c.xValue, c.yValue + (row * (c.diameter + 1)), c.diameter, c.diameter);
			}
			row++;
		}
	}

	public ArrayList<Circle> RandomLine(int n) {
		x = (int) Math.ceil(Math.sqrt((double) n));
		y = x;
		diameter = (250 / x) - 1;
		ArrayList<Circle> newLine = new ArrayList<Circle>();
		Random r = new Random();

		for (int i = 0; i < n; i++) {
			Circle c = new Circle();
			c.yValue = 0;
			c.xValue = i * (diameter + 1);
			c.diameter = diameter;
			c.redValue = 0;
			c.greenValue = 0;
			c.blueValue = 0;

			int chance = r.nextInt(100);

			if (chance <= 25) {
				int x = r.nextInt(numberOfCircles);
				c.touchWhite();
			} else if (chance <= 50) {
				int x = r.nextInt(numberOfCircles);
				c.touchBlue();
			} else if (chance <= 75) {
				int x = r.nextInt(numberOfCircles);
				c.touchRed();
			} else if (chance <= 100) {
				int x = r.nextInt(numberOfCircles);
				c.touchGreen();
			}
			newLine.add(c);
		}

		return newLine;
	}
	
	public void Decay() {
		for (ArrayList<Circle> line: circles)
			for (Circle c : line)
				c.Decay();
	}


	public static void main(String[] args) {
		Waterfall ex = new Waterfall();
	}
}