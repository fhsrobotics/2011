/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fhsrobotics.robot;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author 2036
 */
public class Sense
{
	private static Sense instance;
	public static Sense inst()
	{
		if(instance == null)
			instance = new Sense();
		return instance;
	}

	/**
	 * Line follow sensors. Three are mounted on the robot, on the front.
	 * left = 1
	 * center = 2
	 * right = 3
	 * Stands for "linefollowXpos"
	 */
	public DigitalInput lfLeft, lfCenter, lfRight;

	public Gyro gyro;

	//public DigitalInput forkBottom, forkTop; //Deprecated in favor of an array.
	public DigitalInput[] liftSensors;
	public static final int FORK_BOTTOM = 0; //By index.
	public static final int FORK_TOP = 2;

	public Sense()
	{
		lfLeft = new DigitalInput(1);
		lfCenter = new DigitalInput(2);
		lfRight = new DigitalInput(3);

		//forkBottom = new DigitalInput(13);
		//forkTop = new DigitalInput(14);

		//Populate the liftSensors array. Fork top + 1 to be inclusive.
		liftSensors = new DigitalInput[FORK_TOP+1];
		for(int i = 0; i < liftSensors.length; i++)
		{
			liftSensors[i] = new DigitalInput(i+4);
		}

		gyro = new Gyro(1, 1);
		gyro.setSensitivity(0.0266666666);
	}
}
