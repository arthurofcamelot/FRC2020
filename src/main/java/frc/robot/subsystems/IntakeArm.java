/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.logging.Logger;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Functions;

/**
 * Add your docs here.
 */
public class IntakeArm extends SubsystemBase implements Logger{

  private CANSparkMax intake = new CANSparkMax(Constants.INTAKE_ARM_INTAKE, MotorType.kBrushless);
  private CANSparkMax pivot = new CANSparkMax(Constants.INTAKE_ARM_PIVOT, MotorType.kBrushless);

  private CANEncoder pivotEncoder = new CANEncoder(pivot);
  
  private CANPIDController pivotPID = pivot.getPIDController();

  //data for logger
  private double pivotPower = 0, intakePower = 0;

  private int
  //PID Config
  PID_OUTPUT_MIN = -1,
  PID_OUTPUT_MAX = 1;

  public IntakeArm(){
    pivotEncoder.setPosition(0);
    pivotPID.setP(Constants.INTAKE_ARM_PIVOT_P);
    pivotPID.setI(Constants.INTAKE_ARM_PIVOT_I);
    pivotPID.setD(Constants.INTAKE_ARM_PIVOT_D);
    pivotPID.setOutputRange(PID_OUTPUT_MIN, PID_OUTPUT_MAX);
  }

  @Override
  public double[] getValues(double[] values) {
    values[Constants.LoggerRelations.INTAKE_ARM_INTAKE_POWER.value] = intakePower;
    values[Constants.LoggerRelations.INTAKE_ARM_PIVOT_POWER.value] = pivotPower;
    values[Constants.LoggerRelations.INTAKE_ARM_PIVOT_POSITION.value] = getPivotEncoderPosition();
    return values;
  }

  public void setPivotPower(double power){
    power = Functions.clampDouble(power, 1, -1);
    pivotPower = power;
    pivot.set(power);
  }

  public void setIntakePower(double power){
    power = Functions.clampDouble(power, 1, -1);
    intakePower = power;
    intake.set(power);
  }

  public void setPivotEndoerPosition(double position){
    pivotEncoder.setPosition(position);
  }

  public double getPivotEncoderPosition(){
    return pivotEncoder.getPosition();
  }

  public void setPivotMotorPosition(double position){
    pivotPID.setReference(position, ControlType.kPosition);
  }

  public void stop(){
    intake.set(0);
    pivot.set(0);
  }
}
