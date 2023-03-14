package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoBalancePID extends CommandBase {

  private final Drivetrain m_drivetrain;
  private PIDController pid;

  // Adds drive train components to balance command
  public AutoBalancePID(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    pid = new PIDController(.5, 0, 0);
    pid.setTolerance(2);
    addRequirements(m_drivetrain);
    pid.setSetpoint(0);
  }

  @Override
  public void execute() {
    double output = MathUtil.clamp(pid.calculate(m_drivetrain.getPitch(), 0), -0.4, 0.4);
    m_drivetrain.arcadeDrive(-1 * output, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pid.atSetpoint();
  }
}
