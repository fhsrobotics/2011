package org.fhsrobotics.robot.control;

import org.fhsrobotics.robot.Drive;

/**
 *
 * @author 2036
 */
public abstract class Control
{
	//Used to control motors on the robot.
	public Drive drive;

	public Control(Drive drive)
	{
		this.drive = drive;
	}

	public abstract void update();
}
