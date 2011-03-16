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
	
	int state;
	int stateLast;

	long lasttime;

	boolean reachedPole;

	//Whether the tube has reached the top, ever.
	boolean topped;

	long timeInState = 0;
	
	final int GET_OFF_TUBE = 0;
	final int PREPARE_TUBE = 1;
	final int APPROACH_RACK = 2;
	final int LOWER_ON_RACK = 3;
	final int DEPROACH_RACK = 4;

	///TODO: Make this class name shorter while maintaining the meaning!
	public AutonomousLineFollowControl()
	{
		super();

		dir = 0; // start going forwards

		reachedPole = false;
		
		state = GET_OFF_TUBE;
		
		origRot = sense.gyro.getAngle(); // degrees

		lasttime = System.currentTimeMillis();
	}

	public void update()
	{
		double rot = (sense.gyro.getAngle()-origRot);
		rot /= 20.0;

		double x = 0, y = 0;

		switch(state)
		{
			case GET_OFF_TUBE:
				y = 0;
				x = 0;
				setState(PREPARE_TUBE);
				break;
			case PREPARE_TUBE:
				y = 0;
				x = 0;
				setState(APPROACH_RACK);
				break;
			case APPROACH_RACK:
				if(!topped)
				{
					drive.setForklift(0.8);
					topped = sense.forkTop.get();
				}
				if(topped)
					drive.setForklift(0);

				if(sense.lfCenter.get())
				{
					dir = 0;
				}
				else if(sense.lfLeft.get() || 
					    sense.lfCenter.get() || 
						sense.lfRight.get())
				{
					
					dir = (sense.lfLeft.get()?1:0) - (sense.lfRight.get()?1:0);
				}
				if(sense.lfLeft.get() &&
				   sense.lfCenter.get() &&
				   sense.lfRight.get())
				{
					setState(LOWER_ON_RACK);
				}
				x = 0.5 * dir;
				if(timeInState < 650)
					y = 0.55;
				else
				{
					y = 0.25;
					if(dir != 0)
						y = 0.15;
				}
				break;
			case LOWER_ON_RACK:
				y = 0;
				if(timeInState <= 1250)
					y = -1.0;
				x = 0;
				setState(DEPROACH_RACK);
				break;
			case DEPROACH_RACK:
				y = 0;
				x = 0;
				break;
		}
		
		long time = System.currentTimeMillis();
		timeInState += time - lasttime;
		lasttime = time;

		System.out.print("             L ");
		System.out.print(sense.lfLeft.get() ? "1" : "0");
		System.out.print(" C ");
		System.out.print(sense.lfCenter.get() ? "1" : "0");
		System.out.print(" R ");
		System.out.print(sense.lfRight.get() ? "1" : "0");
		System.out.print(" G ");
		System.out.println(sense.gyro.getAngle());
			
		drive.move(x, y, rot);
	}
	
	private void setState(int state)
	{
		this.state = state;
		timeInState = 0;
	}
}
