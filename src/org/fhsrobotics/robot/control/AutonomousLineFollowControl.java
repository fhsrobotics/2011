/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fhsrobotics.robot.control;

import edu.wpi.first.wpilibj.DigitalInput;
import org.fhsrobotics.robot.Drive;

/**
 * Follows the line to the rack, and places the ubertube.
 *
 * @author 2036
 */
public class AutonomousLineFollowControl extends Control
{
	/**
	 * Line follow sensors. Three are mounted on the robot, on the front: to the
	 * left of the line and right of the line; on the back: on the line.
	 * frontleft = 1
	 * frontright = 2
	 * rear = 3
	 * Stands for "linefollowYposXpos"
	 */
	DigitalInput lfFrontRight, lfFrontLeft, lfRear;

	///TODO: Make this class name shorter while maintaining the meaning!
	public AutonomousLineFollowControl(Drive drive)
	{
		super(drive);

		lfFrontRight = new DigitalInput(1);
		lfFrontLeft = new DigitalInput(2);
		lfRear = new DigitalInput(3);
	}

	public void update()
	{
	}
}
