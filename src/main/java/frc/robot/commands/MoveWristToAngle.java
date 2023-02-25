package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

public class MoveWristToAngle  extends CommandBase {
    private Wrist m_wrist;
    private double m_angle;
    private double motorSpeed=.75;


    public MoveWristToAngle(Wrist wrist, double angle) {
        super();
        m_wrist = wrist;
        m_angle = angle;

        addRequirements(m_wrist);
    }

    public void initialize() {
        
    }

    // The closer we get to balancing the slower we go till eventully we stop
    @Override
    public void execute() {
        double error = m_wrist.getAngle() - m_angle;
        if(error >0 )
        {
            m_wrist.setMotor(-1*motorSpeed);
        }
        else
        {
            m_wrist.setMotor(motorSpeed);
        }
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_wrist.setMotor(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        double error = m_wrist.getAngle() - m_angle;
        return Math.abs(error) <= 5;
    }
}