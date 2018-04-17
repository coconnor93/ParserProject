package testProj;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class SwingTest extends JPanel implements Runnable, KeyListener {
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

	java.util.List<Circle> circles;
	int number;
	int x;
	int y;
	int xsize = 250;
	int ysize = xsize;
	int diameter;
	boolean quit = false;
	private static final long serialVersionUID = 1L;
	
	private void initUI(){
		JFrame frame = new JFrame();
		JPanel panel = this;
		frame.setContentPane(panel);
		frame.setTitle("Project Display");
		frame.pack();
		frame.setSize(xsize, ysize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public SwingTest(int n) {
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

		//this.run();
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(0x17202a));
		//setBackground(Color.white);
		int angle = 12;
		int initialRadius = 1;
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
		SwingTest test = new SwingTest(150);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void Decay()
	{
		for (Circle c : circles)
			c.Decay();
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
