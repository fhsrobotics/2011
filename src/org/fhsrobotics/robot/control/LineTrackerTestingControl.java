/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fhsrobotics.robot.control;

import edu.wpi.first.wpilibj.DigitalInput;
import org.fhsrobotics.robot.Drive;

/**
 *
 * @author Developer
 */
public class LineTrackerTestingControl extends Control
{
	public DigitalInput tracker1, tracker2, tracker3;

	public LineTrackerTestingControl(Drive drive)
	{
		super(drive);
		tracker1 = new DigitalInput(1);
		tracker2 = new DigitalInput(2);
		tracker3 = new DigitalInput(3);
	}

	public void update()
	{
		System.out.print("Tracker1 ");
		System.out.print(tracker1.get() ? "TRUE!" : "FALSE");
		System.out.print(" Tracker2 ");
		System.out.print(tracker2.get() ? "TRUE!" : "FALSE");
		System.out.print(" Tracker3 ");
		System.out.println(tracker3.get() ? "TRUE!" : "FALSE");
//		System.out.println("trackers: " + Arrays.toString(new boolean[] {
//			tracker1.get(), tracker2.get(), tracker3.get() }));
	}
}
