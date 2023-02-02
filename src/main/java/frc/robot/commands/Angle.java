package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Angle extends CommandBase {
    
    private final Drivetrain m_drivetrain;
    private double goalAngle;

    public double getYaw()
    {return gyro.getYaw();
}

    public Angle( Drivetrain drivetrain) {
        super();
        m_drivetrain = drivetrain;
        addRequirements(m_drivetrain);
    }

     private AHRS gyro;

 // Called when the command is initially scheduled.
     @Override
     public void initialize() { 
        goalAngle=3;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() { 

        
        SmartDashboard.putNumber("Yaw", gyro.getYaw());
        System.out.println( gyro.getYaw());

        double angle = m_drivetrain.getYaw();
        if(angle>1)
        {
            //this means the front is in the air so we should move forward;
            m_drivetrain.arcadeDrive(0.5,0);
        }
        if(angle<1)
        {
            m_drivetrain.arcadeDrive(-0.5, 0);
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

     double angle = m_drivetrain.getYaw();
     return angle > -1* goalAngle && angle < goalAngle;
 }

}
