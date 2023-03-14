package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardDistance extends CommandBase {
  private Drivetrain m_Drivetrain;
  private double inches;
  private double leftGoal;
  private double rightGoal;

  // basiclly this allows us to move forward a set amount of inches
  public DriveForwardDistance(Drivetrain _drivetrain, double _inches) {
    m_Drivetrain = _drivetrain;
    inches = _inches;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    int toAdd = m_Drivetrain.convertInchesToTicks(inches);
    leftGoal = m_Drivetrain.getLeftEncoderCount() + toAdd;
    rightGoal = m_Drivetrain.getRightEncoderCount() + toAdd;
    // SmartDashboard.putNumber("leftGoal", leftGoal);
    // SmartDashboard.putNumber("rightGoal", rightGoal);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Drivetrain.setPosition(leftGoal, rightGoal);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // SmartDashboard.putNumber("leftDistance", m_Drivetrain.getLeftEncoderCountInInches());
    // SmartDashboard.putNumber("rightDistance", m_Drivetrain.getRightEncoderCountInInches());
    m_Drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double leftError = Math.abs(leftGoal - m_Drivetrain.getLeftEncoderCount());
    double rightError = Math.abs(rightGoal - m_Drivetrain.getRightEncoderCount());

    // SmartDashboard.putNumber("leftError", leftError);
    // SmartDashboard.putNumber("rightError", rightError);

    return leftError < 1000 && rightError < 1000;
  }
}
