/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fhsrobotics.robot;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author 2036
 */
public class Sense
{
	/**
	 * Line follow sensors. Three are mounted on the robot, on the front.
	 * left = 1
	 * center = 2
	 * right = 3
	 * Stands for "linefollowXpos"
	 */
	public DigitalInput lfLeft, lfCenter, lfRight;

	public Sense()
	{
		lfLeft = new DigitalInput(1);
		lfCenter = new DigitalInput(2);
		lfRight = new DigitalInput(3);
	}
}
