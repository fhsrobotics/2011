package org.fhsrobotics.robot.control;

import org.fhsrobotics.robot.Drive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * Tests the Drive class for proper wheel directions.
 * Use the primary trigger on a joystick to repeat a test.
 * Use the secondary button on a joystick to move to the next.
 * @author 2036
 */
public class MotorDirTestControl extends Control
{
	private Joystick joy;
	//Makes the joystick button only advance one test at a time.
	private boolean joy2last;

	private int testnum;

	/**
	 * The speed at which the test will run, a fraction of total
	 */
	private final double motorSpeed = 0.2;
	/**
	 * The time each test will remain active.
	 */
	private final double testTime = 0.5;

	public MotorDirTestControl(Drive drive)
	{
		super();

		joy = new Joystick(1);

		//Start on test 0. Can change this if you want a diff test.
		testnum = 0;

		//Was the joystick button pressed last update?
		joy2last = false;
	}

	/**
	 * This update uses the wait function for the tests. (To make it easier
	 *  to program and expand.)
	 */
	public void update()
	{
		if(joy.getRawButton(1))
		{
			switch(testnum)
			{
				case 0:   //Test the front-right wheel.
					drive.setWheels(motorSpeed, 0, 0, 0);
					break;
				case 1:   //Test the front-left wheel.
					drive.setWheels(0, motorSpeed, 0, 0);
					break;
				case 2:   //Test the rear-right wheel.
					drive.setWheels(0, 0, motorSpeed, 0);
					break;
				case 3:   //Test the rear-left wheel.
					drive.setWheels(0, 0, 0, motorSpeed);
					break;
				case 4:   //Test the forklift upwards.
					drive.setForklift(motorSpeed);
					break;
				case 5:   //Test the forklift downwards.
					drive.setForklift(-motorSpeed);
					break;
			}
			Timer.delay(testTime);
			//Now turn off everything! If it's breaking something, we only want
			// testTime seconds of breakage.
			drive.setWheels(0, 0, 0, 0);
			drive.setForklift(0);
		}
		//If the joystick's second button is pressed, switch to the next test.
		if(joy.getRawButton(2) && !joy2last)
		{
			joy2last = true;
			testnum++;
			if(testnum > 5) //Cycle to the first test if the last is surpassed.
				testnum = 0;
			System.out.println("Test running: "+testnum);
		}
		joy2last = joy.getRawButton(2);
	}
}
