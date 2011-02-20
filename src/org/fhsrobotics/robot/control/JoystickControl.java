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
	
	boolean enabled;
	//If the toggle was pressed last update.
	boolean enableEdge;
	
	public JoystickControl(Drive drive)
	{
		super(drive, null);
		rJoy = new Joystick(1);
		lJoy = new Joystick(2);

		enabled = true;
		enableEdge = false;
	}

	public void update()
	{
		if(enabled)
			drive.move(-rJoy.getX(), -rJoy.getY()-lJoy.getY(), -lJoy.getX());
		else
			drive.setWheels(0.0, 0.0, 0.0, 0.0);

		boolean disablePress = (rJoy.getRawButton(6) && rJoy.getRawButton(7) && rJoy.getRawButton(11) && rJoy.getRawButton(10))
			|| (rJoy.getRawButton(6) && rJoy.getRawButton(7) && rJoy.getRawButton(11) && rJoy.getRawButton(10));
		if(disablePress && !enableEdge)
		{
			enabled = !enabled;
		}
		enableEdge = disablePress;

		double forkspeed = (rJoy.getRawButton(3)||lJoy.getRawButton(3)?1:0)
			             - (rJoy.getRawButton(2)||lJoy.getRawButton(2)?1:0);
		forkspeed *= (rJoy.getRawButton(1) || lJoy.getRawButton(1)) ? 1.0 : 0.4;
		drive.setForklift(
			Math.max(Math.min(forkspeed, 1),-1)
		);
//		System.out.println("Right: "+rJoy.getX()+","+rJoy.getY()+" Left: "+lJoy.getX()+","+lJoy.getY());
	}
}
