package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class BalanceDumb extends CommandBase {

  private final Drivetrain m_drivetrain;
  private double m_speed;
  private double m_accelerationThreshold;
  private long m_lockTime;
  private long m_lockTime_delay;

  // Adds drive train components to balance command
  public BalanceDumb(Drivetrain drivetrain) {
    super();
    m_drivetrain = drivetrain;
    m_speed = .4;
    m_lockTime_delay = 500;
    m_accelerationThreshold = .75;
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  // Gets pitch
  // Goal angle is 5, closest number to 0
  @Override
  public void initialize() {
    m_lockTime = System.currentTimeMillis();
  }

  // The closer we get to balancing the slower we go till eventully we stop
  @Override
  public void execute() {

    double speed = 0;
    double angle = m_drivetrain.getPitch();
    double acc = m_drivetrain.getPitchAcc();
    long currentTime = System.currentTimeMillis();

    if (angle > 5) {
      speed = m_speed;
    } else if (angle < -5) {
      speed = -1 * m_speed;
    } else {
      speed = 0;
    }
    if (Math.abs(acc) > m_accelerationThreshold) {
      speed = 0;
      m_lockTime = currentTime + m_lockTime_delay;
    }
    if (currentTime <= m_lockTime) {
      speed = 0;
    }

    m_drivetrain.arcadeDrive(speed, 0);
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
