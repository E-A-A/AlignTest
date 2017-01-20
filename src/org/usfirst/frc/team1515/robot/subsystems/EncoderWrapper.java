package org.usfirst.frc.team1515.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;

public class EncoderWrapper {

	AnalogInput input;
	int lastValue;
	int value;
	
	final int MAX_TICKS = 3900;
	
	public EncoderWrapper() {
		input = new AnalogInput(0);
		lastValue = 0;
	}
	
	public int get() {
		return value;
	}
	
	public void update() {
		int encoderValue = input.getAverageValue();
		if (Math.abs((Math.abs(lastValue) - encoderValue)) > MAX_TICKS / 2) {
			if (encoderValue < MAX_TICKS / 2 ) {
				value += MAX_TICKS - lastValue + encoderValue;
			} else {
				value += MAX_TICKS - encoderValue + lastValue; 
			}
		} else if (lastValue != encoderValue) {
			value += (encoderValue - lastValue);
		}
		lastValue = encoderValue;
	}
	
}

