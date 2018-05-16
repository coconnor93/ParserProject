package testProj;

import java.awt.Color;

/**
 * This class contains the basis of a Circle, the primary node that is used
 * across all visualisation types in the system.
 * @author Christopher O'Connor
 * @version 1.0
 *
 */

public class Circle {
	// Variable Declarations 
	int xValue;
	int yValue;
	int diameter;
	Color colour;
	
	/**
	 * Returns the X value of the circle
	 * @return xValue The X value of the circle
	 */

	public int getxValue() {
		return xValue;
	}
	
	/**
	 * Sets the X value of the circle
	 * @param xValue The X value of the circle
	 */

	public void setxValue(int xValue) {
		this.xValue = xValue;
	}
	
	/**
	 * Returns the Y value of the circle
	 * @return yValue The Y value of the circle
	 */

	public int getyValue() {
		return yValue;
	}
	
	/**
	 * Sets the Y value of the circle
	 * @param yValue The Y value of the circle
	 */

	public void setyValue(int yValue) {
		this.yValue = yValue;
	}
	
	/**
	 * Returns the diameter value of the circle
	 * @return diameter The diameter of the circle
	 */

	public int getDiameter() {
		return diameter;
	}
	
	/**
	 * Sets the diameter value of the circle
	 * @param diameter The diameter of the circle
	 */

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	
	/**
	 * Returns the colour value of the circle
	 * @return colour The colour value of the circle
	 */

	public Color getColour() {
		return colour;
	}
	
	/**
	 * Sets the colour value of the circle
	 * @param colour The colour value of the circle
	 */

	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	
	//RGB and decay rate variables declared
	int redValue;
	int greenValue;
	int blueValue;
	int decayRate = 10;
	
	/**
	 * Method that alters the RGB values of the specified circle to flash white
	 */

	public void touchWhite() {
		redValue = 255;
		greenValue = 255;
		blueValue = 255;
	}
	
	/**
	 * Method that alters the RGB values of the specified circle to flash red
	 */

	public void touchRed() {
		redValue = 255;
		greenValue = 0;
		blueValue = 0;
	}
	
	/**
	 * Method that alters the RGB values of the specified circle to flash blue
	 */

	public void touchBlue() {
		redValue = 0;
		greenValue = 0;
		blueValue = 255;
	}
	
	/**
	 * Method that alters the RGB values of the specified circle to flash green
	 */

	public void touchGreen() {
		redValue = 0;
		greenValue = 255;
		blueValue = 0;
	}
	
	/**
	 * Method that slowly decreases the RGB value of each circle. Used in the animation of the visualisation
	 * to show a class that has not been altered over time. Validation included to stop the RGB values falling 
	 * below 0.
	 */

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
	
	/**
	 * Returns the colour of the specified circle represeneted in RGB values
	 * @return colour The colour of the circle represented in RGB values
	 */

	public Color getColor() {
		colour = new Color(redValue, greenValue, blueValue);
		return colour;
	}
}