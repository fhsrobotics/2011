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

	boolean reachedPole;

	///TODO: Make this class name shorter while maintaining the meaning!
	public AutonomousLineFollowControl(Drive drive, Sense sense)
	{
		super(drive, sense);
		
		dir = 0; // start going forwards

		reachedPole = false;

		//origRot = sense.gyro.getAngle(); // degrees
	}

	public void update()
	{
		double rot = 0;//origRot - sense.gyro.getAngle();
		/*System.out.print("Gyro: ");
		System.out.print(origRot);
		System.out.print("    ");
		System.out.print(sense.gyro.getAngle());
		System.out.println();*/

		double x, y;

		if(reachedPole == false)
		{
			if(sense.lfCenter.get())
			{
				dir = 0;
			}
			else if(sense.lfLeft.get() || sense.lfCenter.get() || sense.lfRight.get())
			{
				if(sense.lfLeft.get() && sense.lfCenter.get() && sense.lfRight.get())
				{
					reachedPole = true;
				}
				dir = (sense.lfLeft.get()?1:0) - (sense.lfRight.get()?1:0);
			}
			y = 0.25;
		}
		else
		{

		}

		System.out.print(" lfL ");
		System.out.print(sense.lfLeft.get() ? "tr   " : "  lse");
		System.out.print(" lfC ");
		System.out.print(sense.lfCenter.get() ? "tr   " : "  lse");
		System.out.print(" lfR ");
		System.out.println(sense.lfRight.get() ? "tr   " : "  lse");
		
		x = 0.25 * dir;		
		//drive.move(x, y, rot);
	}
}
