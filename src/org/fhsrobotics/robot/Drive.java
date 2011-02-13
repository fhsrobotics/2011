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
		6: Forklift elevation winch reverse
	*/
	public Jaguar fRight, fLeft, rRight, rLeft;
	public Jaguar forklift1, forklift2;

	public Drive()
	{
		fRight = new Jaguar(1);
		fLeft = new Jaguar(2);
		rRight = new Jaguar(3);
		rLeft = new Jaguar(4);

		forklift1 = new Jaguar(5);
		forklift2 = new Jaguar(6);
	}

	/**
	 * Sets the speed of each wheel motor individually.
	 * The following correspondences are guaranteed:
	 * 1 means that the wheel specified will push the robot forwards.
	 * -1 pushes the robot backwards.
	 */
	public void setWheels(double frontRight, double frontLeft,
	                   double rearRight,  double rearLeft)
	{
		fRight.set(frontRight);
		fLeft.set(-frontLeft);
		rRight.set(-rearRight);
		rLeft.set(rearLeft);
	}
	
	/**
	 * Interprets translation and rotation inputs to make the robot move.
	 * @param	x	1 = right, -1 = left
	 * @param	y	1 = forwards, -1 = backwards
	 * @param	rot	1 = ccw, -1 = cw
	 */
	public void move(double x, double y, double rot)
	{
		setWheels(
			Math.max(Math.min(y + rot + x, 1),-1),
			Math.max(Math.min(y - rot - x, 1),-1),
			Math.max(Math.min(y + rot - x, 1),-1),
			Math.max(Math.min(y - rot + x, 1),-1)
		);
	}

	/**
	 * Sets the speed of the forklift winch.
	 * @param spd Winch speed. 1.0 = up
	 */
	public void setForklift(double spd)
	{
		forklift1.set(spd);
		forklift2.set(-spd);
	}
}
