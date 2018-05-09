package testProj;

import java.awt.Color;

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