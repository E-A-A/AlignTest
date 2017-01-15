package org.usfirst.frc.team1515.robot.commands;

import java.io.*;
import java.net.URL;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1515.robot.Robot;

public class Align extends Command {
	
	static String url = "";
	static final double MIN_SPEED = 0.09;
	
	static final double SPEED = 0.3;
	double targetAngle;
	double gyroStartAngle;
	
	public double p = 0.01;
	public double i = 0.000005;
	public double d = 0;
	public double errorSum;
	
    public Align() {
//    	try {
			//BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			//targetAngle = Integer.parseInt(reader.readLine());
    		targetAngle = 90;
//		} catch (IOException e) {
	//		e.printStackTrace();
		//}
    	errorSum = targetAngle;
        requires(Robot.driveTrain);
    }

    protected void initialize() {
		gyroStartAngle = Robot.gyro.getAngle();
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
    	return Math.abs(error) < 1 && Math.abs(Robot.gyro.getRate()) < 25;
    }

    protected void end() {
    	Robot.driveTrain.setSpeeds(0, 0);
    }

    protected void interrupted() {
    	end();
    }
    
    private class Source implements PIDSource {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return targetAngle - (Robot.gyro.getAngle() - gyroStartAngle);
		}
    	
    }
    
    private class Output implements PIDOutput {

		@Override
		public void pidWrite(double output) {
			Robot.driveTrain.setSpeeds(-output, output); // this was wrong
		}
    	
    }
}
