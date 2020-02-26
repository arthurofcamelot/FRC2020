package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.logging.Logger;
import frc.robot.logging.LoggerRelations;
import frc.robot.utilities.Functions;
import frc.robot.utilities.Ports;

/**
 * Subsystem to control the intake and its arm
 */
public class IntakeArm extends SubsystemBase implements Logger {

	public enum States{
		UP,
		DOWN_NO_INTAKE,
		DOWN_YES_INTAKE;
	}

	public final static double intakePower = 0.7;

	public States state;

	private VictorSPX 
	intake,
	pivot;

	private DigitalInput upperLimit;

	// data for logger
	private double 
	loggerPivotPower = 0, 
	loggerIntakePower = 0;

	public IntakeArm() {
		intake = new VictorSPX(Ports.INTAKE_ARM_INTAKE);
		pivot = new VictorSPX(Ports.INTAKE_ARM_PIVOT);

		upperLimit = new DigitalInput(Ports.UPPER_LIMIT);
		
		state = States.UP;
		brake();
	}

	public void setState(States newState){
		state = newState;
	}

	public States getState(){
		return state;
	}

	/**
	 * Sets the power of the intake arm motor
	 * @param power the new power for the motor
	 */
	public void setPivotPower(double power) {
		power = Functions.clampDouble(power, 1, -1);
		loggerPivotPower = power;
		pivot.set(ControlMode.PercentOutput, power);
	}

	/**
	 * Sets the power of the intake roller motor
	 * @param power the new power for the motor
	 */
	public void setIntakePower(double power) {
		power = Functions.clampDouble(power, 1, -1);
		loggerIntakePower = power;
		intake.set(ControlMode.PercentOutput, power);
	}

	/**
	 * Stops the intake arm and intake roller motors
	 */
	public void stop() {
		intake.set(ControlMode.PercentOutput, 0);
		pivot.set(ControlMode.PercentOutput, 0);
	}

	public boolean getUpperLimit() {
		return !upperLimit.get();
	}

	public void coast() {
		pivot.setNeutralMode(NeutralMode.Coast);
	}

	public void brake() {
		pivot.setNeutralMode(NeutralMode.Brake);
	}

	public boolean isUp() {
		return state == States.UP;
	}

	public boolean isDown() {
		return state == States.DOWN_YES_INTAKE;
	}

	public boolean isLoading() {
		return state == States.DOWN_NO_INTAKE;
	}

	@Override
	public double[] getValues(double[] values) {
		values[LoggerRelations.INTAKE_ARM_INTAKE_POWER.value] = loggerIntakePower;
		values[LoggerRelations.INTAKE_ARM_PIVOT_POWER.value] = loggerPivotPower;
		return values;
	}
}
