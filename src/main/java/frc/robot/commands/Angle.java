package frc.robot.commands;



import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Angle extends CommandBase {
    
    private final Drivetrain m_drivetrain;
    private double goalAngle; 
    private double angleToTurnBy;

// Shows angle to turn by
    public Angle( Drivetrain drivetrain, double angleToTurnBy) {
        super();
        m_drivetrain = drivetrain;
        this.angleToTurnBy=angleToTurnBy;
        addRequirements(m_drivetrain);
    }

     

 // Called when the command is initially scheduled.
 // Shows current angle and changes to goal angle
     @Override
     public void initialize() { 
        goalAngle=m_drivetrain.getYaw()+angleToTurnBy;
        goalAngle = goalAngle % 360;
    }

    // Called every time the scheduler runs while the command is scheduled.
    // Turns to goal angle depending on current angle
    @Override
    public void execute() { 

    // shows what our angle from the drivetrain and puts it on the dashboard
        SmartDashboard.putNumber("Yaw", m_drivetrain.getYaw());
        SmartDashboard.putNumber("goalAngle",goalAngle);
        System.out.println( m_drivetrain.getYaw());

        double angle = m_drivetrain.getYaw();
        if(angle-goalAngle>0)
        {
            //means the front is in the air so we should move forward;
            m_drivetrain.arcadeDrive(0,-0.5);
        }
        else if(angle-goalAngle<0)
        {
            m_drivetrain.arcadeDrive(0, 0.5);
        }
        //means the back is in the air so we should move backwards;
    }

 // Called once the command ends or is interrupted.
 @Override
 public void end (boolean interrupted) { 
     m_drivetrain.arcadeDrive(0, 0);
 }
 

 // Returns true when the command should end.
 @Override
 public boolean isFinished() {
    return Math.abs(goalAngle-m_drivetrain.getYaw())<3;
 }

}
