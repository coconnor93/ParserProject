package testProj;

import java.awt.*;
import javax.swing.*;


/**
 * Class that contains the functionality of the spiral visualisation technique
 * @author Christopher O'Connor
 * @version 1.0
 *
 */

public class Spiral extends JPanel  {
	
	//Declaration of variables used
	java.util.List<Circle> circles;
	int number;
	int x;
	int y;
	int xsize = 250;
	int ysize = xsize;
	int diameter;
	boolean quit = false;
	private static final long serialVersionUID = 1L;
	private String legendURL = "/images/legend.png";
	
	/**
	 * Method that creates the frame and panel the visualisation technique will be drawn upon
	 */

	private void initUI() {
		JFrame frame = new JFrame();
		JPanel panel = this;
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JLabel legend = new JLabel();
		legend.setIcon(new ImageIcon(getClass().getResource(legendURL)));
		bottomPanel.add(legend, BorderLayout.EAST);
		panel.add(bottomPanel);
		frame.setContentPane(panel);
		frame.setTitle("Project Display");
		frame.pack();
		frame.setSize(xsize, ysize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Constructor that takes in the number of circles we want to print out as a parameter and 
	 * calculates the X, Y and diameter variables of each individual circle
	 * @param n The number of circles contained with the spiral
	 */
	public Spiral(int n) {
		initUI();
		number = n;
		circles = new java.util.ArrayList<Circle>();
		x = (int) Math.ceil(Math.sqrt((double) n));
		y = x;
		diameter = (xsize / x) - 1;

		for (int i = 0; i < n; i++) {
			Circle c = new Circle();
			c.xValue = (i % x) * (diameter + 1);
			c.yValue = (int) (i / x) * (diameter + 1);
			System.out.println("n=" + i + ",x=" + c.xValue + ",y=" + c.yValue);
			c.diameter = diameter;
			c.redValue = 0;
			c.greenValue = 0;
			c.blueValue = 0;
			circles.add(c);
		}
	}
	
	/**
	 * The paintComponent method contains the functionality needed to physically draw the circles on screen.
	 * The spiral itself is calculated by using sine and cosine algorithms. The starting angle increments each
	 * time until it hits 360 degrees, then resets. The radius variable consistently iterates each time a new circle
	 * is drawn to screen to widen the arc of the spiral.
	 */

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.DARK_GRAY);
		int angle = 25;
		int initialRadius = 6;
		int radiusIncrease = 2;
		int angleStep = 8;
		int radius = initialRadius;
		Insets insets = getInsets();
		int w = (int) ((xsize - insets.left - insets.right) / 2);
		int h = (int) ((ysize - insets.top - insets.bottom) / 2);
		g.translate(w, h);

		for (Circle c : circles) {
			g.setColor(c.getColor());
			c.xValue = xsize / 2;
			c.yValue = ysize / 2;
			int xp = c.xValue + (int) (Math.sin(Math.toRadians((double) angle)) * radius);
			int yp = c.yValue + (int) (Math.cos(Math.toRadians((double) angle)) * radius);
			g.fillOval(xp, yp, c.diameter, c.diameter);
			angle += angleStep;
			radius += radiusIncrease;

		}
	}
	/**
	 * The main method used to instigate a new instance of the spiral technique
	 * @param args
	 */

	public static void main(String[] args) {
		Spiral test = new Spiral(150);
	}

	/**
	 * Method used to call the decay method from the circle class
	 */
	public void Decay() {
		for (Circle c : circles)
			c.Decay();
	}
}
