package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

public class MoveWrist extends CommandBase {
    private Wrist m_wrist;
    private double m_dir;
    private double m_speed;

    public MoveWrist(Wrist wrist, int dir, double speed) {
        super();
        m_wrist = wrist;
        m_dir = dir;
        m_speed = speed;

        addRequirements(m_wrist);
    }

    public void initialize() {
        m_wrist.getDistance();
    }

    // The closer we get to balancing the slower we go till eventully we stop
    @Override
    public void execute() {
        m_wrist.setMotor(m_dir * m_speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_wrist.setMotor(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
