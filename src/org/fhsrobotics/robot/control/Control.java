package org.fhsrobotics.robot.control;

import org.fhsrobotics.robot.Drive;
import org.fhsrobotics.robot.Sense;

/**
 * Controls the robot.
 * 
 * This part decides what to do, the drive does it. Used for both teleop and
 * autonomous code.
 *
 * @author 2036
 */
public abstract class Control
{
	//Used to control motors on the robot.
	public Drive drive;
	public Sense sense;

	public Control()
	{
		drive = Drive.inst();
		sense = Sense.inst();
	}

	/**
	 * Updates the drive object passed with new movement speeds in accordance
	 *  to the specified goal of the particular Control subclass.
	 */
	public abstract void update();
}
