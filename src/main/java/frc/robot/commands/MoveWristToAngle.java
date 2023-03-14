package frc.robot.commands;

import static frc.robot.Constants.MOTOR_SPEED_AUTO;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

public class MoveWristToAngle extends CommandBase {
  private Wrist m_wrist;
  private double m_angle;
  private double m_motorSpeed = MOTOR_SPEED_AUTO;
  private PIDController pid;

  public MoveWristToAngle(Wrist wrist, double angle) {
    super();
    m_wrist = wrist;
    m_angle = angle;
    pid = new PIDController(.1, 0, 0);
    pid.setTolerance(.25);
    addRequirements(m_wrist);
    pid.setSetpoint(angle);
  }

  public void initialize() {}

  // The closer we get to balancing the slower we go till eventully we stop
  @Override
  public void execute() {
    double output = MathUtil.clamp(pid.calculate(m_wrist.getAngle(), m_angle), -0.5, 0.5);

    m_wrist.setMotor(output);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_wrist.setMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // double error = m_wrist.getAngle() - m_angle;
    // return Math.abs(error) <= 2;
    return pid.atSetpoint();
  }
}
