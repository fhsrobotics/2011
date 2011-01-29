/*----------------------------------------------------------------------------*/
/* Copyright (c) FHS 2010. All Rights Reserved.                               */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.   October Pi                                                  */
/*----------------------------------------------------------------------------*/

package org.fhsrobotics.robot;


import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import org.fhsrobotics.robot.control.Control;
import org.fhsrobotics.robot.control.JoystickControl;


/**
 * The RobotMain is the iterative class that makes the robot operate!
 *
 * @author 2036
 */
public class RobotMain extends IterativeRobot
{
	//How motors are driven.
	public Drive drive;

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

		Timer.delay(10);

		//Initialize
		drive = new Drive();
    }

	/**
	 * This function is called at the beginning of the autonomous period.
	 */
	public void autonomousInit()
	{
	}

    /**
     * This function is called periodically during autonomous.
     */
    public void autonomousPeriodic()
	{

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
}
