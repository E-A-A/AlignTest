package org.usfirst.frc.team1515.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Thing extends CommandGroup {

	public Thing() {
		System.out.println("a");
		addSequential(new Forward(1));
		addSequential(new Align(-30));
//		addSequential(new Forward(2));
		addSequential(new Align());
	}
}
