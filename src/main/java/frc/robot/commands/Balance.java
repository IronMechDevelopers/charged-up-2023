package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Balance extends CommandBase {

    private final Drivetrain m_drivetrain;
    private double goalAngle;

    public Balance( Drivetrain drivetrain) {
        super();
        m_drivetrain = drivetrain;
        addRequirements(m_drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        goalAngle=3;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double angle = m_drivetrain.getPitch();
        if(angle<0)
        {
            //this means the front is in the air so we should move forward;
            m_drivetrain.arcadeDrive(.5,0);
        }
        if(angle>0)
        {
            m_drivetrain.arcadeDrive(-.5, 0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
       m_drivetrain.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        double angle = m_drivetrain.getPitch();
        return angle > -1* goalAngle && angle < goalAngle;
    }
    
}
