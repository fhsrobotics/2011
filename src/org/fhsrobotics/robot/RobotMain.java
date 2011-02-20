/*----------------------------------------------------------------------------*/
/* Copyright (c) FHS 2010. All Rights Reserved.                               */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.fhsrobotics.robot;


import edu.wpi.first.wpilibj.*;

import org.fhsrobotics.robot.control.AutonomousLineFollowControl;
import org.fhsrobotics.robot.control.Control;
import org.fhsrobotics.robot.control.JoystickControl;
import org.fhsrobotics.robot.control.LineTrackerTestingControl;
import org.fhsrobotics.robot.control.MotorDirTestControl;


/**
 * The RobotMain is the iterative class that makes the robot operate!
 *
 * @author 2036
 */
public class RobotMain extends IterativeRobot
{
	//How motors are driven.
	public Drive drive;

	//How the sensors are polled.
	public Sense sense;

	//How does control map to the robot?
	public Control control;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
	{
		//Kill the watchdog.
		Watchdog.getInstance().setEnabled(false);
		//Eh, screw it, we don't need to get away from the robot
		//It won't kill us, right?
		//... Right?

		//Initialize the drive.
		drive = new Drive();

		//Initialize the sensors,
		sense = new Sense();
    }

	/**
	 * This function is called at the beginning of the autonomous period.
	 */
	public void autonomousInit()
	{
		//Initialize the autonomous control.
		control = new AutonomousLineFollowControl(drive, sense);
	}

    /**
     * This function is called periodically during autonomous.
     */
    public void autonomousPeriodic()
	{
		control.update();
	}

	/**
	 * This function is called at the beginning of the teleoperated period.
	 */
	public void teleopInit()
	{
		//Initialize the control method.
		control = new JoystickControl(drive);
	}
	
    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic()
	{
		control.update();
    }

	/**
	 * When the robot is disabled, try to garbage collect the control.
	 */
	public void disabledInit()
	{
		super.disabledInit();
		control = null;
		System.gc();
	}
}
