package org.usfirst.frc.team1515.robot;

import org.usfirst.frc.team1515.robot.commands.Align;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	private Button align;
	
	public OI() {
		align = new JoystickButton(Robot.joystick, 1);
		align.whenPressed(new Align());
	}

}
