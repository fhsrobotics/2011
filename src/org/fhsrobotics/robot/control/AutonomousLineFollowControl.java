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
	
	enum LineState 
	{
		GET_OFF_TUBE,
		PREPARE_TUBE,
		APPROACH_RACK,
		LOWER_ON_RACK,
		DEPROACH_RACK
	}

	///TODO: Make this class name shorter while maintaining the meaning!
	public AutonomousLineFollowControl(Drive drive, Sense sense)
	{
		super(drive, sense);
		
		dir = 0; // start going forwards

		reachedPole = false;
		
		state = LineState.GET_OFF_TUBE;
		
		//origRot = sense.gyro.getAngle(); // degrees
	}

	public void update()
	{
		double rot = 0;

		double x, y;


		switch(state)
		{
			case LineState.GET_OFF_TUBE:
				y = 0;
				x = 0;
				state = LineState.PREPARE_TUBE;
				break;
			case LineState.PREPARE_TUBE:
				y = 0;
				x = 0;
				state = LineState.APPROACH_RACK;
				break;
			case LineState.APPROACH_RACK:
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
						state = LineState.LOWER_ON_RACK;
					}
					dir = (sense.lfLeft.get()?1:0) - (sense.lfRight.get()?1:0);
				}
				x = 0.25 * dir;
				y = 0.25;
				break;
			case LineState.LOWER_ON_RACK:
				y = 0;
				x = 0;
				state = LineState.DEPROACH_RACK;
				break;
			case LineState.DEPROACH_RACK:
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
