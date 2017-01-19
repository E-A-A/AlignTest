package org.usfirst.frc.team1515.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderWrapper {

	Encoder encoder;
	int lastValue;
	int value;
	
	final int MAX_TICKS = 4096;
	
	public EncoderWrapper(Encoder encoder) {
		this.encoder = encoder;
		lastValue = 0;
	}
	
	public int get() {
		return value;
	}
	
	public void update() {
		double encoderValue = encoder.get();
		if ((Math.abs(lastValue) - encoderValue) > MAX_TICKS / 2) {
			if (encoderValue < MAX_TICKS /2 ) {
				value += MAX_TICKS - lastValue + encoderValue;
			} else {
				value += MAX_TICKS - encoderValue + lastValue; 
			}
		}
		lastValue = encoder.get();
	}
	
}

