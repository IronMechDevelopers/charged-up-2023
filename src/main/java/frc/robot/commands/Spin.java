package frc.robot.commands;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Spin extends CommandBase {
  private final Drivetrain m_drivetrain;

  public Spin(Drivetrain m_drivetrain) {
    super();
    this.m_drivetrain = m_drivetrain;

    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.arcadeDrive(0, SPIN_SPEED);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(0, SPIN_SPEED);
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
