package org.fhsrobotics.robot.control;

import org.fhsrobotics.robot.Drive;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author 2036
 */
public class JoystickControl extends Control
{
	Joystick rJoy, lJoy;

	public JoystickControl(Drive drive)
	{
		super(drive);
		rJoy = new Joystick(1);
		lJoy = new Joystick(2);
	}

	public void update()
	{
		drive.setWheels(
		Math.max(Math.min(rJoy.getY() + lJoy.getY() - rJoy.getX() - lJoy.getX(), 1),-1),
		Math.max(Math.min(rJoy.getY() + lJoy.getY() + rJoy.getX() + lJoy.getX(), 1),-1),
		Math.max(Math.min(rJoy.getY() + lJoy.getY() + rJoy.getX() - lJoy.getX(), 1),-1),
		Math.max(Math.min(rJoy.getY() + lJoy.getY() - rJoy.getX() + lJoy.getX(), 1),-1)
		);
	}
}