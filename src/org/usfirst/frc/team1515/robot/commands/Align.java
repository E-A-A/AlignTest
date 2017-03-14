package org.usfirst.frc.team1515.robot.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.usfirst.frc.team1515.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Align extends Command {
	
	static final String url = "http://10.15.15.4:8080";
	
	static final double MIN_SPEED = 0.15;
	static final double MIN_ERROR = 0.75;
	static final int ERROR_INCREMENT_FINISH = 2;
	static final double FORWARD_SPEED = 0.1;

	static final double P = 0.0000000000000000000000000000001;
	static final double I = 0.0000000;
	static final double D = 0;

	double targetAngle;
	double gyroStartAngle;
	boolean usingVision;

	double errorSum;
	double lastError = 0;
	int errorIncrement = 0;
	long startTime;
	
	State state;
	
	public Align() {
		requires(Robot.driveTrain);
		usingVision = true;
	}

	public Align(double angle) {
		requires(Robot.driveTrain);
		usingVision = false;
		targetAngle = angle;
	}

	
	@Override
	protected void initialize() {
		gyroStartAngle = Robot.gyro.getAngle();
		startTime = System.currentTimeMillis();
		errorIncrement = 0;
		state = State.PENDING;
		try {
			if (usingVision) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
				String res = reader.readLine();
				targetAngle = Double.parseDouble(res);
			}
			state = State.SUCCESS;
		} catch (NumberFormatException ex) {
			state = State.FAILURE;
			System.out.println("No tape found.");
		} catch (Exception ex) {
			state = State.FAILURE;
			System.out.println("Server error");
			ex.printStackTrace();
		}
	}

	@Override
	protected boolean isFinished() {
		if (state == State.SUCCESS) {
			
			double error = targetAngle - (Robot.gyro.getAngle() - gyroStartAngle);
			double speed = error * P + errorSum * I + (error - lastError) * D;
			errorSum += error;
			if (Math.abs(speed) < MIN_SPEED) {
				speed = Math.signum(speed) * MIN_SPEED;
			}
			if (errorIncrement >= ERROR_INCREMENT_FINISH) {
				if (usingVision) {
					Robot.driveTrain.setSpeeds(FORWARD_SPEED - speed, FORWARD_SPEED + speed);
					return false;
				} else {
					return true;
				}
			}
			Robot.driveTrain.setSpeeds(-speed , speed);
			if (Math.abs(error) <= MIN_ERROR && lastError > MIN_ERROR) {
				errorIncrement++;
			}
			lastError = error;
		}
		if (state == State.FAILURE) {
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrain.setSpeeds(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
	
	private enum State {
		PENDING,
		SUCCESS,
		FAILURE;
	}
    
}
