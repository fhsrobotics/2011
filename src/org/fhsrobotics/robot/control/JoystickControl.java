package org.fhsrobotics.robot.control;

import edu.wpi.first.wpilibj.Joystick;
import org.fhsrobotics.robot.Sense;

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

	//---Switching forklift positional states---
	/** If the automatic leveling system should be active. */
	private boolean autoEnabled;
	/** The speed at which the fork automatically moves to the target. */
	private double forkAutoSpeed;
	/** The current level of the forklift. 0 = bottom */
	private int forkLevel;
	/** Target for the fork. Will move towards this from the last recorded
	location until the target is hit or one of the limits are hit. */
	private int forkTarget;
	/* A special case is needed:
	 * If any sensor is jammed, there is a possibility of the top or bottom not
	 * being detected--this would cause the lift to continue to raise, and
	 * potentially break the limit switches on either side.
	 */
	//Variables to track the pressed state of the joystick auto buttons
	private boolean button4last = false;
	private boolean button5last = false;
	

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

		forkLevel = Sense.FORK_TOP;
	}

	public void update()
	{
		double transX = -rJoy.getX();
		double transY = -rJoy.getY()-lJoy.getY();
		double rotate = -lJoy.getX();

		//---Forklift posititoning code---
		if(autoEnabled)
		{
			forkTarget = forkTarget < Sense.FORK_TOP ?
				forkTarget : (forkTarget > Sense.FORK_BOTTOM ? forkTarget : Sense.FORK_BOTTOM);
			if(forkLevel < forkTarget)
				
			{
				drive.setForklift(forkAutoSpeed);
			}
			else if(forkLevel > forkTarget)
			{
				drive.setForklift(-forkAutoSpeed);
			}
		}
		//Runs for every sensor BUT the top and bottom.
		for(int i = Sense.FORK_BOTTOM + 1; i <= Sense.FORK_TOP; i++)
		{
			if(sense.liftSensors[i].get())
			{
				forkLevel = i;
			}
		}
		//Check top and bottom afterwards, so that jams don't kill it all.
		if(sense.liftSensors[Sense.FORK_BOTTOM].get())
			forkLevel = Sense.FORK_BOTTOM;

		//---Relative posititoning, automatic---
		if(!button4last && (rJoy.getRawButton(4) || lJoy.getRawButton(4)))
		{
			--forkTarget;
			autoEnabled = true;
		}
		button4last = rJoy.getRawButton(4) || lJoy.getRawButton(4);
		if(!button5last && (rJoy.getRawButton(5) || lJoy.getRawButton(5)))
		{
			++forkTarget;
			autoEnabled = true;
		}
		button5last = rJoy.getRawButton(5) || lJoy.getRawButton(5);
		

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
		if(rollbackState == ROLLBACK_WAIT && !autoEnabled)
		{
			double forkspeed = (rJoy.getRawButton(3)||lJoy.getRawButton(3)?1:0)
							 - (rJoy.getRawButton(2)||lJoy.getRawButton(2)?1:0);
			forkspeed *= (rJoy.getRawButton(1) || lJoy.getRawButton(1)) ? 1.0 : 0.4;
			if(sense.liftSensors[Sense.FORK_BOTTOM].get())
			{
				forkspeed = Math.max(forkspeed, 0);
			}
			if(sense.liftSensors[Sense.FORK_TOP].get())
			{
				forkspeed = Math.min(forkspeed, 0);
			}
			if(forkspeed != 0.0)
			{
				autoEnabled = false;
				forkTarget = forkLevel;
				drive.setForklift(
					Math.max(Math.min(forkspeed, 1),-1)
				);
			}
		}

//		System.out.println("Right: "+rJoy.getX()+","+rJoy.getY()+" Left: "+lJoy.getX()+","+lJoy.getY());
//		System.out.println("13: "+sense.forkBottom.get());
	}
}
