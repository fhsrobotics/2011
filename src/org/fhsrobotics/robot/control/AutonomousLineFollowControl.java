/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fhsrobotics.robot.control;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import org.fhsrobotics.robot.Drive;
import org.fhsrobotics.robot.Sense;

/**
 * Follows the line to the rack, and places the ubertube.
 *
 * @author 2036
 */
public class AutonomousLineFollowControl extends Control
{
	double origRot; // original rotation of robot in degrees
	
	int dir; // where the line was last detected, 1 = left, 0 = center, -1 = right

	///TODO: Make this class name shorter while maintaining the meaning!
	public AutonomousLineFollowControl(Drive drive, Sense sense)
	{
		super(drive, sense);
		
		dir = 0; // start going forwards
		
		//origRot = gyro.getAngle(); // degrees
	}

	public void update()
	{
		double rot = 0; // TODO: Make robot counter rotation drift
		
		double x, y;
		
		y = 0.0;

		if(sense.lfCenter.get())
		{
			dir = 0;
		}
		else if(sense.lfLeft.get() || sense.lfCenter.get() || sense.lfRight.get())
		{
			dir = (sense.lfLeft.get()?1:0) - (sense.lfRight.get()?1:0);
		}

		System.out.print("lfLeft ");
		System.out.print(sense.lfLeft.get() ? "TRUE!" : "FALSE");
		System.out.print(" lfCenter ");
		System.out.print(sense.lfCenter.get() ? "TRUE!" : "FALSE");
		System.out.print(" lfRight ");
		System.out.println(sense.lfRight.get() ? "TRUE!" : "FALSE");
		
		x = 0.2 * dir;
		
		drive.move(x, y, rot);
	}
}
