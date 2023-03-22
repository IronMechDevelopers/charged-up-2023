package frc.robot.commands;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveStraightUntilPitch extends CommandBase  {

    private final Drivetrain m_drivetrain;
    private double goalAngle;
    private double defaultSpeed=.7;

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
    }

    // Called every time the scheduler runs while the command is scheduled.
    // If any interference to change direction, command would realine itself.
    @Override
    public void execute() {
       // double driveSpeed = SmartDashboard.getNumber("Auto Speed", defaultSpeed);
       double driveSpeed = 0.76;
        SmartDashboard.putNumber(
            "Pitch1", m_drivetrain.getPitch());
        double errorAngle = m_drivetrain.getYaw() - goalAngle;
        if (errorAngle > 3) {
            m_drivetrain.arcadeDrive(-driveSpeed, -0.2);
        } else if (errorAngle < -3) {
            m_drivetrain.arcadeDrive(-driveSpeed, 0.2);
        } else {
            m_drivetrain.arcadeDrive(-driveSpeed, 0.0);
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
        // was 24.5
    }

}
