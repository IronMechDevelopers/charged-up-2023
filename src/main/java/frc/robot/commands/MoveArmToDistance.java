package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class MoveArmToDistance extends CommandBase {
    private Arm m_arm;
    private double m_speed;
    private double m_goalDistnace;

    public MoveArmToDistance(Arm arm, double goalDistnace, double speed) {
        super();
        m_arm = arm;
        m_speed = speed;
        m_goalDistnace = goalDistnace;
        addRequirements(m_arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double current = m_arm.getPot();
        double error = current - m_goalDistnace;
        if (error > 0) {
            m_arm.moveArm(-1, m_speed);
        }
        if (error < 0) {
            m_arm.moveArm(1, m_speed);
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_arm.moveArm(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        double current = m_arm.getPot();
        return Math.abs(current - m_goalDistnace) <= 2.5;
    }
}
