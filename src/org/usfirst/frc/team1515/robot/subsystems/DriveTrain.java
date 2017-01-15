package org.usfirst.frc.team1515.robot.subsystems;

import org.usfirst.frc.team1515.robot.Robot;
import org.usfirst.frc.team1515.robot.commands.JoystickDrive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
	
	private MotorModule leftMotors;
	private MotorModule rightMotors;
	
	private static final double ROTATE_SIDE = 1.0;
	private static final double ROTATE_CORNER = 0.25;
		
	public DriveTrain() {		
		leftMotors = new MotorModule(23, 24);
		rightMotors = new MotorModule(22, 21);
	}
	
	public void setSpeeds(double left, double right) {
		leftMotors.setSpeed(-left);
		rightMotors.setSpeed(right);
	}
	
	public void drive() {
		double tilt = Robot.joystick.getRawAxis(0);
		double forward = -Robot.joystick.getRawAxis(1);
		double twist = Robot.joystick.getRawAxis(5);
		double throttle = (1 - Robot.joystick.getRawAxis(2)) / 2;
		tilt *= throttle;
		forward *= throttle;
		
		double y = Math.abs(forward);
		double x = Math.abs(twist);
		double a = ROTATE_SIDE;
		double b = ROTATE_CORNER;
		
		double left = a * x + y * (1 - a * x);
		double right = y * (1 + (a + b - 1) * x) - a * x;
		SmartDashboard.putString("why", "why");
		if (forward < 0) {
			left *= -1;
			right *= -1;
			twist *= -1;
		}
		if (twist < 0) {
			double temp = left;
			left = right;
			right = temp;
		}
		
		setSpeeds(left, right);
		
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());	
	}

}
