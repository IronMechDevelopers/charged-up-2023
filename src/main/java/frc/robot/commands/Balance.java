package frc.robot.commands;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Balance extends CommandBase {

    private final Drivetrain m_drivetrain;
    private double goalAngle;
    private double lastAngle;
    private int counter;

    // Adds drive train components to balance command
    public Balance( Drivetrain drivetrain) {
        super();
        m_drivetrain = drivetrain;
        addRequirements(m_drivetrain);
    }

    // Called when the command is initially scheduled.
    // Gets pitch
    // Goal angle is 5, closest number to 0
    @Override
    public void initialize() {
        goalAngle=5;
        lastAngle = m_drivetrain.getPitch();
    }

    // The closer we get to balancing the slower we go till eventully we stop
    @Override
    public void execute() {
        double angle = m_drivetrain.getPitch();
        if(counter++%10<2)
        {
            SmartDashboard.putString("Balance state","skipping");
            m_drivetrain.arcadeDrive(0,0);
        }
        else{
            //go reverse
        }
        if(Math.abs(angle) < goalAngle)
        {
            SmartDashboard.putString("Balance state","Balnced");
            m_drivetrain.arcadeDrive(0,0);
        }
        else if( Math.abs(lastAngle-angle) >= 5)
        {
            SmartDashboard.putString("Balance state","Moving to fast");
            counter=0;
            m_drivetrain.arcadeDrive(0,0);
        }
        
        else if(angle>15)
        {
            //this means the back is in the air so we should move back;
            m_drivetrain.arcadeDrive(-.6,0);
            SmartDashboard.putString("Balance state",">15");
        }
        else if(angle>10)
        {
            m_drivetrain.arcadeDrive(-.5,0);
            SmartDashboard.putString("Balance state",">10");
        }
        else if(angle>5)
        {
            m_drivetrain.arcadeDrive(-.4,0);
            SmartDashboard.putString("Balance state",">5");
        }
        else if(angle<-15)
        {
            m_drivetrain.arcadeDrive(.6,0);
            SmartDashboard.putString("Balance state",">-15");
        }
        else if(angle<-10)
        {
            m_drivetrain.arcadeDrive(.5,0);
            SmartDashboard.putString("Balance state",">-10");
        }
        else if(angle<-5)
        {
            m_drivetrain.arcadeDrive(.4,0);
            SmartDashboard.putString("Balance state",">-5");
        }
    
        lastAngle=angle;
// Speed varies on angle positions
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
        SmartDashboard.putNumber("Balance angle", angle);
        SmartDashboard.putNumber("getPitchAcc",m_drivetrain.getPitchAcc());
        SmartDashboard.putNumber("getAltitude", m_drivetrain.getAltitude());

        // return Math.abs(angle) < goalAngle;
        return false;
    }
    
}
