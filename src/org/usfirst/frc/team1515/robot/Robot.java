
package org.usfirst.frc.team1515.robot;


import org.usfirst.frc.team1515.robot.commands.Thing;
import org.usfirst.frc.team1515.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	public static OI oi;
	
	public static Joystick joystick = new Joystick(0);
	public static final Gyro gyro = new ADXRS450_Gyro();
	public static final DriveTrain driveTrain = new DriveTrain();
//	public static final EncoderWrapper encoder = new EncoderWrapper();
	public static final DigitalInput limitSwitch = new DigitalInput(0);
	
	Relay relay = new Relay(0);
	
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
		new Thing().start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		
	}

	long time = System.currentTimeMillis();
	boolean toggle = false;
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("gyro", gyro.getRate());
//		encoder.update();
//		SmartDashboard.putNumber("encoder", encoder.get());
		
		if (time + 75 < System.currentTimeMillis()) {
			relay.set(toggle ? Relay.Value.kForward : Relay.Value.kOff);
			toggle = !toggle;
			time = System.currentTimeMillis();
		}
		System.out.println(limitSwitch.get());
	}

	@Override
	public void testPeriodic() {
		relay.set(Relay.Value.kOff);
	}
}
