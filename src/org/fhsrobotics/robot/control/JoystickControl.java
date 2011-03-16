package org.fhsrobotics.robot.control;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Uses the joysticks to control the robot, with a mecanum drive.
 * @author 2036
 */
public class JoystickControl extends Control
{
	//The joysticks used to control the drive.
	Joystick rJoy, lJoy;

	//Robot can be disabled by the disable combination.
	boolean enabled;
	//If the toggle was pressed last update.
	boolean enableLast;

	//---Specraise button---
	private static final int SPECRAISE_BUTTON = 4; //Button #
	private static final float SPECRAISE_AMOUNT = 0.5f; //In seconds.
	private int specraiseState;
	private static final int SPECSTATE_WAIT = 0;
	private static final int SPECSTATE_DROP = 1;
	private static final int SPECSTATE_RAISE = 2;

	//---Rollback button---
	private static final int ROLLBACK_BUTTON = 5; //Button #
	private static final float ROLLBACK_TIME = 0.5f; //How long the robot will RETREAT!!!!
	private int rollbackState;
	private static final int ROLLBACK_WAIT = 0;
	private static final int ROLLBACK_DROP = 1;
	private static final int ROLLBACK_BACKUP = 2;

	
	public JoystickControl()
	{
		super();

		rJoy = new Joystick(1);
		lJoy = new Joystick(2);

		enabled = true;
		enableLast = false;
	}

	public void update()
	{
		double transX = -rJoy.getX();
		double transY = -rJoy.getY()-lJoy.getY();
		double rotate = -lJoy.getX();

		//---Specraise code---
		switch(specraiseState)
		{
			case SPECSTATE_WAIT:
			break;
			case SPECSTATE_DROP:
			break;
			
		}

		//---Drive, if the disable combination not pressed---
		if(enabled)
			drive.move(transX, transY, rotate);
		else
			drive.setWheels(0.0, 0.0, 0.0, 0.0);

		//---Disable combination---
		boolean disablePress = (rJoy.getRawButton(6) && rJoy.getRawButton(7) && rJoy.getRawButton(11) && rJoy.getRawButton(10))
			|| (rJoy.getRawButton(6) && rJoy.getRawButton(7) && rJoy.getRawButton(11) && rJoy.getRawButton(10));
		if(disablePress && !enableLast)
		{
			enabled = !enabled;
		}
		enableLast = disablePress;

		//---Forklift control---
		if(rollbackState == ROLLBACK_WAIT && specraiseState == SPECSTATE_WAIT)
		{
			double forkspeed = (rJoy.getRawButton(3)||lJoy.getRawButton(3)?1:0)
							 - (rJoy.getRawButton(2)||lJoy.getRawButton(2)?1:0);
			forkspeed *= (rJoy.getRawButton(1) || lJoy.getRawButton(1)) ? 1.0 : 0.4;
			if(sense.forkBottom.get())
			{
				forkspeed = Math.max(forkspeed, 0);
			}
			if(sense.forkTop.get())
			{
				forkspeed = Math.min(forkspeed, 0);
			}

			drive.setForklift(
				Math.max(Math.min(forkspeed, 1),-1)
			);
		}

//		System.out.println("Right: "+rJoy.getX()+","+rJoy.getY()+" Left: "+lJoy.getX()+","+lJoy.getY());
//		System.out.println("13: "+sense.forkBottom.get());
	}
}
