package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Park extends CommandBase {

  private final Drivetrain m_drivetrain;
  private double leftGoal;
  private double rightGoal;

  // Adds drive train components to balance command
  public Park(Drivetrain drivetrain) {
    super();
    m_drivetrain = drivetrain;
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  // Gets pitch
  // Goal angle is 5, closest number to 0
  @Override
  public void initialize() {
    leftGoal = m_drivetrain.getLeftEncoderCount();
    rightGoal = m_drivetrain.getRightEncoderCount();
  }

  // The closer we get to balancing the slower we go till eventully we stop
  @Override
  public void execute() {
    m_drivetrain.driveForwardDistanceToCount(leftGoal, rightGoal);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
