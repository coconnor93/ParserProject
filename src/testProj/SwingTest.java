package testProj;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class SwingTest extends JPanel implements Runnable {
	public class Circle {
		int xValue;
		int yValue;
		int diameter;
		Color colour;

		int redValue;
		int greenValue;
		int blueValue;

		int decayRate = 10;

		public void Touch() {
			redValue = 255;
			greenValue = 255;
			blueValue = 255;
		}

		public void Decay() {
			redValue -= decayRate;
			if (redValue < 0)
				redValue = 0;
			greenValue -= decayRate;
			if (greenValue < 0)
				greenValue = 0;
			blueValue -= decayRate;
			if (blueValue < 0)
				blueValue = 0;
		}

		public Color getColor() {
			colour = new Color(redValue, greenValue, blueValue);
			return colour;
		}
	}

	JFrame frame;
	JPanel panel;
	java.util.List<Circle> circles;
	int number;
	int x;
	int y;
	int xsize = 500;
	int ysize = 500;
	int diameter;

	boolean quit = false;

	private static final long serialVersionUID = 1L;

	public SwingTest(int n) {
		number = n;
		frame = new JFrame();
		panel = this;
		circles = new java.util.ArrayList<Circle>();

		x = (int) Math.ceil(Math.sqrt((double) n));
		y = x;

		diameter = (xsize / x) - 1;

		frame.setContentPane(panel);
		frame.setTitle("Project Display");
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		setPreferredSize(new Dimension(xsize, ysize));

		for (int i = 0; i < n; ++i) {
			Circle c = new Circle();
			c.xValue = (i % x) * (diameter + 1);
			c.yValue = (int) (i / x) * (diameter + 1);
			System.out.println("n=" + i + ",x=" + c.xValue + ",y=" + c.yValue);
			c.diameter = diameter;
			c.redValue = 128;
			c.greenValue = 128;
			c.blueValue = 128;
			circles.add(c);
		}

		this.run();
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(0x17202a));
		
		for (Circle c : circles) {
			g.setColor(c.getColor());
			g.fillOval(c.xValue, c.yValue, c.diameter, c.diameter);
		}
	}

	public static void main(String[] args) {
		SwingTest test = new SwingTest(1000);
	}

	@Override
	public void run() {
		while (!quit) {
			Random r = new Random();
			int chance = r.nextInt(100);

			if (chance <= 30) {
				int x = r.nextInt(number);
				circles.get(x).Touch();
			}

			for (Circle c : circles)
				c.Decay();

			this.repaint();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
