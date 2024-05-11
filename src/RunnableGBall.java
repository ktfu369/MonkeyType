/*
 * File: RunnableGBall.java
 * ------------------------
 * This file defines an extension to the GBall class that is
 * designed to run as a separate thread of control.
 */

public class RunnableGBall extends GBall implements Runnable {

/** Creates a new ball with radius r centered at the origin */
	public RunnableGBall(double r) {
		super(r);
	}

/** Sets the size of the enclosure */
	public void setEnclosureSize(double width, double height) {
		enclosureWidth = width;
		enclosureHeight = height;
	}

/** Sets the velocity of the ball */
	public void setVelocity(double vx, double vy) {
		dx = vx;
		dy = vy;
	}

/** Run forever bouncing the ball */
	public void run() {
		while (true) {
			advanceOneTimeStep();
			pause(PAUSE_TIME);
		}
	}

/** Check for bounces and advance the ball */
	private void advanceOneTimeStep() {
		double bx = getX();
		double by = getY();
		double r = getWidth() / 2;
		if (bx < r || bx > enclosureWidth - r) dx = -dx;
		if (by < r || by > enclosureHeight - r) dy = -dy;
		move(dx, dy);
	}

/* Private constants */
	private static final int PAUSE_TIME = 20;

/* Private instance variables */
	private double enclosureWidth;
	private double enclosureHeight;
	private double dx;
	private double dy;

}
