
package org.usfirst.frc.team1515.robot;

import org.usfirst.frc.team1515.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1515.robot.subsystems.EncoderWrapper;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	public static OI oi;
	
	public static Joystick joystick = new Joystick(0);
	public static final Gyro gyro = new ADXRS450_Gyro();
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final EncoderWrapper encoder = new EncoderWrapper();
	
	@Override
	public void robotInit() {
		oi = new OI();
	}
	
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		
	}


	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("gyro", gyro.getRate());
		encoder.update();
		SmartDashboard.putNumber("encoder", encoder.get());
	}

	@Override
	public void testPeriodic() {
	}
}
