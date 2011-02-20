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
	
	LineState state;

	boolean reachedPole;
	
	final int GET_OFF_TUBE = 0;
	final int PREPARE_TUBE = 1;
	final int APPROACH_RACK = 2;
	final int LOWER_ON_RACK = 3;
	final int DEPROACH_RACK = 4;

	///TODO: Make this class name shorter while maintaining the meaning!
	public AutonomousLineFollowControl(Drive drive, Sense sense)
	{
		super(drive, sense);
		
		dir = 0; // start going forwards

		reachedPole = false;
		
		state = GET_OFF_TUBE;
		
		//origRot = sense.gyro.getAngle(); // degrees
	}

	public void update()
	{
		double rot = 0;

		double x, y;


		switch(state)
		{
			case GET_OFF_TUBE:
				y = 0;
				x = 0;
				state = PREPARE_TUBE;
				break;
			case PREPARE_TUBE:
				y = 0;
				x = 0;
				state = APPROACH_RACK;
				break;
			case APPROACH_RACK:
				if(sense.lfCenter.get())
				{
					dir = 0;
				}
				else if(sense.lfLeft.get() || 
					    sense.lfCenter.get() || 
						sense.lfRight.get())
				{
					if(sense.lfLeft.get() && 
					   sense.lfCenter.get() && 
					   sense.lfRight.get())
					{
						state = LOWER_ON_RACK;
					}
					dir = (sense.lfLeft.get()?1:0) - (sense.lfRight.get()?1:0);
				}
				x = 0.25 * dir;
				y = 0.25;
				break;
			case LOWER_ON_RACK:
				y = 0;
				x = 0;
				state = DEPROACH_RACK;
				break;
			case DEPROACH_RACK:
				y = 0;
				x = 0;
				break;
		}

		System.out.print("L ");
		System.out.print(sense.lfLeft.get() ? "1" : "0");
		System.out.print(" C ");
		System.out.print(sense.lfCenter.get() ? "1" : "0");
		System.out.print(" R ");
		System.out.println(sense.lfRight.get() ? "1" : "0");
			
		drive.move(x, y, rot);
	}
}
