package org.usfirst.frc.team1515.robot.commands;

import org.usfirst.frc.team1515.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Forward extends Command {
	
	public Forward(double timeout) {
		requires(Robot.driveTrain);
		setTimeout(timeout);
	}
	
	@Override
	protected boolean isFinished() {
		System.out.println("b");
		Robot.driveTrain.setSpeeds(.2, 0.2);
		return isTimedOut();
	}
}
