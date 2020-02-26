package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.oi.LoggerAxis;
import frc.robot.oi.LoggerButton;
import frc.robot.subsystems.IntakeArm;

/**
 * Manual override for the intake arm
 */
public class IntakeArmMOProxy extends CommandBase {

    private IntakeArm intakeArm;
    private LoggerAxis controlAxis;

    private LoggerButton controlButton;

    private static final double INTAKE_DEFAULT_POWER = .7;

    public IntakeArmMOProxy(IntakeArm intakeArm, LoggerAxis controlAxis, LoggerButton controlButton) {
        super();

        this.intakeArm = intakeArm;
        this.controlAxis = controlAxis;

        this.controlButton = controlButton;

        /*
        bindCommand(controlButton, Trigger::whileActiveOnce, new StartEndCommand(
            () -> {
                intakeArm.setIntakePower(INTAKE_DEFAULT_POWER);
                System.out.println(INTAKE_DEFAULT_POWER);
            }, 
            () -> intakeArm.setIntakePower(0)
        ));
        */

        addRequirements(intakeArm);
    }

    @Override
    public void initialize() {
        intakeArm.stop();
    }

    @Override
    public void execute() {
        intakeArm.setPivotPower(controlAxis.get());

        if (controlButton.get()) {
            intakeArm.setIntakePower(INTAKE_DEFAULT_POWER);
        } else {
            intakeArm.setIntakePower(0);
        }
    }

    @Override
    public void end(final boolean interrupted) {
        intakeArm.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}