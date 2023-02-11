package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class Intake  extends CommandBase {
    private Arm m_arm;
    private int m_dir;
    private double m_speed;

    public Intake (Arm arm, int dir, double speed) {
        super();
        m_arm = arm; 
        m_dir = dir;
        m_speed = speed;
 
        addRequirements(m_arm);
    } 
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_arm.runIntake(m_dir, m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_arm.runIntake(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
