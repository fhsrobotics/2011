package org.fhsrobotics.robot;

import edu.wpi.first.wpilibj.Jaguar;

/**
 * Allows access to all robot-controlled devices.
 * @author 2036
 */
public class Drive
{
	/*
		PWM
		1: Front right motor
		2: Front left motor
		3: Back right motor
		4: Back left motor
		5: Forklift elevation winch
	*/
	public Jaguar fRight, fLeft, rRight, rLeft;
	public Jaguar forklift;

	public Drive()
	{
		fRight = new Jaguar(1);
		fLeft = new Jaguar(2);
		rRight = new Jaguar(3);
		rLeft = new Jaguar(4);

		forklift = new Jaguar(5);
	}

	/**
	 * Sets the speed of each wheel motor individually.
	 */
	public void setWheels(double frontRight, double frontLeft,
	                   double rearRight,  double rearLeft)
	{
		fRight.set(frontRight);
		fLeft.set(frontLeft);
		rRight.set(rearRight);
		rLeft.set(rearLeft);
	}

	/**
	 * Sets the speed of the forklift winch.
	 * @param spd Winch speed. 1.0 = up
	 */
	public void setForklift(double spd)
	{
		forklift.set(spd);
	}
}
