/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fhsrobotics.robot.control;

import edu.wpi.first.wpilibj.DigitalInput;
import org.fhsrobotics.robot.Drive;
import org.fhsrobotics.robot.Sense;

/**
 *
 * @author Developer
 */
public class LineTrackerTestingControl extends Control
{
	public DigitalInput tracker1, tracker2, tracker3;

	public LineTrackerTestingControl(Drive drive, Sense sense)
	{
		super(drive, sense);
	}

	public void update()
	{
		System.out.print("Tracker1 ");
		System.out.print(sense.lfLeft.get() ? "TRUE!" : "FALSE");
		System.out.print(" Tracker2 ");
		System.out.print(sense.lfCenter.get() ? "TRUE!" : "FALSE");
		System.out.print(" Tracker3 ");
		System.out.println(sense.lfRight.get() ? "TRUE!" : "FALSE");
//		System.out.println("trackers: " + Arrays.toString(new boolean[] {
//			tracker1.get(), tracker2.get(), tracker3.get() }));
	}
}
