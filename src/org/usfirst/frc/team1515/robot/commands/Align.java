package org.usfirst.frc.team1515.robot.commands;

import java.io.*;
import java.net.URL;
import java.util.logging.ErrorManager;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1515.robot.Robot;

public class Align extends Command {
	
	static String url = "";
	static final double MIN_SPEED = 0.1;
	static final double MIN_ERROR = 0.3;
	static final int ERROR_INCREMENT_FINISH = 2;
	
	static final double p = 0.01;
	static final double i = 0.000000;
	
	double targetAngle;
	double gyroStartAngle;
	
	double errorSum;
	double lastError = 0;
	int errorIncrement = 0;
	
    public Align() {
//    	try {
			//BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			//targetAngle = Integer.parseInt(reader.readLine());
    		targetAngle = -5;
//		} catch (IOException e) {
	//		e.printStackTrace();
		//}
    	errorSum = targetAngle;
        requires(Robot.driveTrain);
    }

    protected void initialize() {
		gyroStartAngle = Robot.gyro.getAngle();
		lastError = 0;
		errorIncrement = 0;
    }

    protected void execute() {
    }

    protected boolean isFinished() {
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
    
}
