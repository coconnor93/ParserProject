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

		public int getxValue() {
			return xValue;
		}

		public void setxValue(int xValue) {
			this.xValue = xValue;
		}

		public int getyValue() {
			return yValue;
		}

		public void setyValue(int yValue) {
			this.yValue = yValue;
		}

		public int getDiameter() {
			return diameter;
		}

		public void setDiameter(int diameter) {
			this.diameter = diameter;
		}

		public Color getColour() {
			return colour;
		}

		public void setColour(Color colour) {
			this.colour = colour;
		}

		int redValue;
		int greenValue;
		int blueValue;

		int decayRate = 10;

		public void touchWhite() {
			redValue = 255;
			greenValue = 255;
			blueValue = 255;
		}
		
		public void touchRed() {
			redValue = 255;
			greenValue = 0;
			blueValue = 0;
		}
		
		public void touchBlue() {
			redValue = 0;
			greenValue = 0;
			blueValue = 255;
		}
		
		public void touchGreen() {
			redValue = 0;
			greenValue = 255;
			blueValue = 0;
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
	int xsize = 250;
	int ysize = xsize;
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
		frame.setSize(xsize, ysize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

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

		this.run();
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(0x17202a));

		for (Circle c : circles) {
			g.setColor(c.getColor());
			g.fillOval(c.xValue, c.yValue, c.diameter, c.diameter);
			int initialRadius = 8;
			int radiusIncrease = 2;
			int angleStep = 12;
			c.xValue = xsize / 2;
			c.yValue = ysize / 2;
			int angle = 10;
			int radius = initialRadius;
			for (int p = 0; p < circles.size(); ++p) {
				int xp = c.xValue + (int) (Math.sin(Math.toRadians((double) angle)) * radius);
				int yp = c.yValue + (int) (Math.cos(Math.toRadians((double) angle)) * radius);
				g.fillOval(xp, yp, c.diameter, c.diameter);
				angle += angleStep;
				radius += radiusIncrease;
			}
		}
	}

	public static void main(String[] args) {
		SwingTest test = new SwingTest(100);
	}

	@Override
	public void run() {
		while (!quit) {
			Random r = new Random();
			int chance = r.nextInt(100);

			if (chance <= 25) {
				int x = r.nextInt(number);
				circles.get(x).touchWhite();
			} else if (chance <=50){
				int x = r.nextInt(number);
				circles.get(x).touchBlue();
			}	else if (chance <=75){
				int x = r.nextInt(number);
				circles.get(x).touchRed();
			}	else if (chance <=100){
				int x = r.nextInt(number);
				circles.get(x).touchGreen();
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
