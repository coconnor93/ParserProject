package testProj;

import java.awt.*;
import javax.swing.*;

public class Spiral extends JPanel implements Runnable {

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

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.black);
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

	public static void main(String[] args) {
		Spiral test = new Spiral(150);
	}

	@Override
	public void run() {
		while (!quit) {

			for (Circle c : circles)
				c.Decay();

			this.repaint();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void Decay() {
		for (Circle c : circles)
			c.Decay();
	}
}
