/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fhsrobotics.robot.control;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
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
	DigitalInput lfLeft, lfCenter, lfRight;
	Gyro gyro;
	
	double origRot; // original rotation of robot in degrees
	
	int dir; // where the line was last detected, 1 = left, 0 = center, -1 = right

	///TODO: Make this class name shorter while maintaining the meaning!
	public AutonomousLineFollowControl(Drive drive)
	{
		super(drive);

		lfLeft = new DigitalInput(1);
		lfCenter = new DigitalInput(2);
		lfRight = new DigitalInput(3);
		
		dir = 0; // start going forwards
		
		//gyro = new Gyro(4);
		
		//origRot = gyro.getAngle(); // degrees
	}

	public void update()
	{
		double rot = 0; // TODO: Make robot counter rotation drift
		
		double x, y;
		
		y = 0.2;
		
		if(lfLeft.get() || lfCenter.get() || lfRight.get())
		{
			dir = lfLeft.get()?1:0 - lfRight.get()?1:0;
		}
		
		x = 0.2 * dir;
		
		drive.move(x, y, rot);
	}
}
