/*
 * File: BouncingBallUsingTimer.java
 * ---------------------------------
 * This file implements a simple bouncing ball using a Timer to
 * implement the animation.
 */

import acm.program.*;
import acm.util.*;
import java.awt.event.*;

public class BouncingBallUsingTimer extends GraphicsProgram {

/** Initialize the ball and its velocity components */
	public void init() {
		ball = new GBall(BALL_RADIUS);
		add(ball, getWidth() / 2, getHeight() / 2);
		dx = 2;
		dy = 1;
	}

/** Create a timer to advance the ball */
	public void run() {
		waitForClick();
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				advanceOneTimeStep();
			}
		};
		SwingTimer timer = new SwingTimer(TIMER_RATE, listener);
		timer.start();
	}

/** Check for bounces and advance the ball */
	private void advanceOneTimeStep() {
		double bx = ball.getX();
		double by = ball.getY();
		if (bx < BALL_RADIUS || bx > getWidth() - BALL_RADIUS) dx = -dx;
		if (by < BALL_RADIUS || by > getHeight() - BALL_RADIUS) dy = -dy;
		ball.move(dx, dy);
	}

/* Private constants */
	private static final double BALL_RADIUS = 10;
	private static final int TIMER_RATE = 20;

/* Private instance variables */
	private GBall ball;
	private double dx;
	private double dy;

/* Standard Java entry point */
/* This method can be eliminated in most Java environments */
	public static void main(String[] args) {
		new BouncingBallUsingTimer().start(args);
	}
}
