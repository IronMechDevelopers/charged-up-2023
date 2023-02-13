package frc.robot.commands;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveStraightUntilPitch extends CommandBase  {

    private final Drivetrain m_drivetrain;
    private double goalAngle;

    public DriveStraightUntilPitch( Drivetrain drivetrain) {
        super();
        m_drivetrain = drivetrain;
        addRequirements(m_drivetrain);
    }

    // Called when the command is initially scheduled.
    // Gets yaw
    @Override
    public void initialize() {
        goalAngle=m_drivetrain.getYaw();
        m_drivetrain.resetEncoders();
    }

    // Called every time the scheduler runs while the command is scheduled.
    // If any interference to change direction, command would realine itself.
    @Override
    public void execute() {
        double speed =  -.6;
        double angle = m_drivetrain.getPitch();
        double errorAngle=angle-goalAngle;
        SmartDashboard.putNumber("errorAngle",errorAngle);
        if(errorAngle>1)
        {
            m_drivetrain.arcadeDrive(speed,-0.4);
        }
       else if(errorAngle<-1)
        {
            m_drivetrain.arcadeDrive(speed, 0.4);
        }
        else{
            m_drivetrain.arcadeDrive(speed, 0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end (boolean interrupted) { 
        m_drivetrain.arcadeDrive(0, 0);
    }
    

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        return Math.abs(m_drivetrain.getPitch())>14;
    }

}
