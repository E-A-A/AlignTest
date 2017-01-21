package org.usfirst.frc.team1515.robot.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.usfirst.frc.team1515.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Align extends Command {
	
	static String url = "http://10.15.15.4:8080";
	static final double MIN_SPEED = 0.15;
	static final double MIN_ERROR = 0.3;
	static final int ERROR_INCREMENT_FINISH = 2;
	
	static final double p = 0.01;
	static final double i = 0.000000;
	
	State state;
	double targetAngle;
	double gyroStartAngle;
	
	double errorSum;
	double lastError = 0;
	int errorIncrement = 0;
	
    public Align() {
        requires(Robot.driveTrain);
    }

    protected void initialize() {
		state = State.PENDING;
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    		String res = reader.readLine();
    		System.out.println(res);
    		targetAngle = Double.parseDouble(res);
    		gyroStartAngle = Robot.gyro.getAngle();
    		lastError = 0;
    		errorIncrement = 0;
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

    protected void execute() {
    }

    protected boolean isFinished() {
    	if (state == State.FAILURE) {
    		return true;
    	}
    	if (state == State.PENDING) {
    		return false;
    	}
    	double error = targetAngle - (Robot.gyro.getAngle() - gyroStartAngle);
    	double speed = error * p  + errorSum * i;
    	errorSum += error;
    	if (Math.abs(speed) < MIN_SPEED) {
    		speed = Math.signum(speed) * MIN_SPEED;
    	}
    	Robot.driveTrain.setSpeeds(-speed, speed);
    	SmartDashboard.putNumber("gyro", Robot.gyro.getAngle());
    	SmartDashboard.putNumber("error", error);
    	SmartDashboard.putNumber("errorsum", errorSum);
    	if (Math.abs(error) <= MIN_ERROR && lastError > MIN_ERROR) {
    		errorIncrement++;
    	}
    	lastError = error;
    	return errorIncrement >= ERROR_INCREMENT_FINISH;
    }

    protected void end() {
    	Robot.driveTrain.setSpeeds(0, 0);
    }

    protected void interrupted() {
    	end();
    }
    
    private enum State {
    	PENDING,
    	SUCCESS,
    	FAILURE;
    }
    
}
