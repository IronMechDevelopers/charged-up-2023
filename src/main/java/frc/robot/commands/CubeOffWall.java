package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class CubeOffWall extends CommandBase {

  private Intake m_intake;
  private int m_dir;
  private double m_speed;

  public CubeOffWall(Intake intake, int dir, double speed) {
    super();
    m_intake = intake;
    m_dir = dir;
    m_speed = Math.abs(speed);

    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intake.cubeOffWall(m_dir, m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.runIntake(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

