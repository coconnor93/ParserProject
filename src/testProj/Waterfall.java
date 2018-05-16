package testProj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that contains the functionality of the waterfall visualisation technique
 * @author Christopher O'Connor
 * @version 1.0
 *
 */

public class Waterfall extends JPanel {
	
	//Declaration of variables used
	private static final long serialVersionUID = 1L;
	private Spiral swing = null;
	ArrayList<ArrayList<Circle>> circles = new ArrayList<ArrayList<Circle>>();
	private int x;
	private int y;
	private int diameter;
	private int numberOfCircles = 40;
	private int maxRows = 50;
	int height = 1000;
	int width = 1000;
	private boolean quit = false;
	private String legendURL = "/images/legend.png";
	
	/**
	 * Constructor that calls the methods to initialise the UI and the circles used in the animation
	 */
	public Waterfall() {
		initUI();
		initCircles();
	}
	
	/**
	 * Method that creates the frame and panel the visualisation technique will be drawn upon
	 */

	private void initUI() {
		JFrame frame = new JFrame();
		JPanel panel = this;
		JPanel bottomPanel = new JPanel(new BorderLayout());
		/*JLabel legend = new JLabel();
		legend.setIcon(new ImageIcon(getClass().getResource(legendURL)));
		bottomPanel.add(legend, BorderLayout.PAGE_END);
		panel.add(bottomPanel);
		panel.repaint();*/
		frame.setContentPane(panel);
		frame.setTitle("Project Display");
		frame.pack();
		frame.setSize(height, width);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		panel.setBackground(Color.black);
	}
	
	/**
	 * Method that calculates the X, Y and diameter values of the circles used
	 */

	public void initCircles() {
		x = (int) Math.ceil(Math.sqrt((double) numberOfCircles));
		y = x;
		diameter = (250 / x) - 1;
	}
	/**
	 * Method that creates a new ArrayList of circles to be drawn onto screen to continue the animation
	 * @param newLine The new line of circles added that are contained with the ArrayList
	 */

	public void AddLine(ArrayList<Circle> newLine) {
		circles.add(0, newLine);

		if (circles.size() > maxRows) {
			circles.remove(circles.size() - 1);
		}
	}
	
	/**
	 * Method that copies the first line printed to screen in order to push it down to continue the animation
	 * @return newLine The first line printed to screen
	 */

	public ArrayList<Circle> GetLastLineCopy() {
		ArrayList<Circle> newLine = new ArrayList<Circle>();
		if (circles.size() > 0) {
			newLine.addAll(circles.get(0));
		}
		return newLine;
	}
	
	/**
	 * The paintComponent method contains the functionality that draws the circles to screen. Nested for loops
	 * are used to traverse through every line contained in the overall circles ArrayList then the second loop travels 
	 * through each circle contained within each line.
	 */

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

	/**
	 * This method uses nested for loops in the same way the paintComponent class does to traverse through
	 * every circle used in the visualisation to call the decay() method in the Circle class
	 */
	public void Decay() {
		for (ArrayList<Circle> line: circles)
			for (Circle c : line)
				c.Decay();
	}
	
	/**
	 * Main method to instigate a new instance of the waterfall visualisation
	 * @param args
	 */

	public static void main(String[] args) {
		Waterfall ex = new Waterfall();
	}
}