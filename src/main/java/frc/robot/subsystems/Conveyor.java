/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.logging.Logger;
import frc.robot.logging.LoggerRelations;
import frc.robot.utilities.Functions;
import frc.robot.utilities.Ports;

/**
 * Subsystem to control the internal conveyor mechanism of the robot
 */
public class Conveyor extends SubsystemBase implements Logger {

	// powers saved for faster logging
	private double 
	leftMotorPower, 
	rightMotorPower;

	// conveyor motors
	private CANSparkMax leftConveyor;
	private CANSparkMax rightConveyor;

	// breakbeam sensors
	private DigitalInput breakbeamEnter;
	private DigitalInput breakbeamExit;

	public Conveyor() {
		leftConveyor = new CANSparkMax(Ports.CONVEYOR_LEFT.port, MotorType.kBrushless);
		rightConveyor = new CANSparkMax(Ports.CONVEYOR_RIGHT.port, MotorType.kBrushless);

		breakbeamEnter = new DigitalInput(Ports.BREAKBEAM_ENTER.port);
		breakbeamExit = new DigitalInput(Ports.BREAKBEAM_EXIT.port);

		leftMotorPower = 0;
		rightMotorPower = 0;
	}

	/**
	 * Sets the power of the left conveyor motor
	 * 
	 * @param power the new power
	 */
	public void setLeftConveyor(double power) {
		power = Functions.clampDouble(power, 1, -1);
		leftMotorPower = power;
		leftConveyor.set(power);
	}

	/**
	 * Sets the power of the left conveyor motor
	 * 
	 * @param power the new power
	 */
	public void setRightConveyor(double power) {
		power = Functions.clampDouble(power, 1, -1);
		rightMotorPower = power;
		rightConveyor.set(power);
	}

	/**
	 * Sets the power of both conveyor motors
	 * 
	 * @param power the new power
	 */
	public void setConveyor(double power) {
		setRightConveyor(power);
		setLeftConveyor(power);
	}

	//TODO - Make sure that the docs are correct on the nature of breakbeam returns
	/**
	 * Gets the state of the entry breakbeam sensor
	 * 
	 * @return the breakbeam state where true is unbroken and false is broken
	 */
	public boolean getBreakBeamEnter() {
		return breakbeamEnter.get();
	}

	/**
	 * Gets the state of the entry breakbeam sensor
	 * 
	 * @return the breakbeam state where true is unbroken and false is broken
	 */
	public boolean getBreakBeamExit() {
		return breakbeamExit.get();
	}

	/**
	 * Stops both conveyor motors
	 */
	public void stop() {
		leftConveyor.set(0);
		rightConveyor.set(0);
	}

	@Override
	public double[] getValues(double[] values) {
		values[LoggerRelations.CONVEYOR_LEFT.value] = leftMotorPower;
		values[LoggerRelations.CONVEYOR_RIGHT.value] = rightMotorPower;
		values[LoggerRelations.CONVEYOR_BREAKBEAM_ENTER.value] = getBreakBeamEnter() ? 1 : 0;
		values[LoggerRelations.CONVEYOR_BREAKBEAM_EXIT.value] = getBreakBeamExit() ? 1 : 0;
		return values;
	}
}