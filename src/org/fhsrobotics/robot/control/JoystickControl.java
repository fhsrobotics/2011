package org.fhsrobotics.robot.control;

import org.fhsrobotics.robot.Drive;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Uses the joysticks to control the robot, with a mecanum drive.
 * @author 2036
 */
public class JoystickControl extends Control
{
	//The joysticks used to control the drive.
	Joystick rJoy, lJoy;

	public JoystickControl(Drive drive)
	{
		super(drive);
		rJoy = new Joystick(1);
		lJoy = new Joystick(2);	}

	public void update()
	{
		drive.move(-rJoy.getX(), -rJoy.getY()-lJoy.getY(), -lJoy.getX());

		double forkspeed = (rJoy.getRawButton(3)||lJoy.getRawButton(3)?1:0)
			             - (rJoy.getRawButton(2)||lJoy.getRawButton(2)?1:0);
		drive.setForklift(
			Math.max(Math.min(forkspeed, 1),-1)
		);
//		System.out.println("Right: "+rJoy.getX()+","+rJoy.getY()+" Left: "+lJoy.getX()+","+lJoy.getY());
	}
}
