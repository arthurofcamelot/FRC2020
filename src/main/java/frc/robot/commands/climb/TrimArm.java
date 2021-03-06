package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.LoggerAxis;
import frc.robot.subsystems.ClimberArm;
import frc.robot.subsystems.ClimberPneumatics;
import frc.robot.utilities.Functions;
import frc.robot.utilities.RollingAverage;

public class TrimArm extends CommandBase {

	private ClimberArm arm;
	private LoggerAxis slider;

	private double startingPosition;
	private double sliderOffset;

	private RollingAverage average;

	public TrimArm(ClimberArm arm, LoggerAxis slider) {
		this.arm = arm;
		this.slider = slider;

		average = new RollingAverage(8);

		addRequirements(arm);
	}

	@Override
	public void initialize() {
		startingPosition = arm.getEncoderPosition();
		sliderOffset = slider.get();
	}

	@Override
	public void execute() {
		average.update(slider.get() - sliderOffset);

		double target = average.getAverage();
		target = (150 * target) + startingPosition;

		double currentPosition = arm.getEncoderPosition();

		if (Functions.isWithin(currentPosition, target, 10)) {
			arm.setPower(0);
		} else {
			arm.setPower(Math.copySign(0.5, target - currentPosition));
		}
	}

	@Override
	public void end(boolean interrupted) {
		arm.stop();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}