package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveBackwards extends CommandBase {

    // SmartDashboard.putNumber("Pitch", gyro.getPitch());
    // SmartDashboard.putNumber("Yaw", gyro.getYaw());
    // SmartDashboard.putNumber("Roll", gyro.getRoll());

    private final Drivetrain m_drivetrain;
    private double goalAngle;

    public DriveBackwards(Drivetrain drivetrain) {
        super();
        m_drivetrain = drivetrain;
        addRequirements(m_drivetrain);
    }

    // Called when the command is initially scheduled.
    // gets Yaw
    @Override
    public void initialize() {
        goalAngle = m_drivetrain.getYaw();
    }

    // Called every time the scheduler runs while the command is scheduled.
    // If any interference to change direction, command would realine itself.
    @Override
    public void execute() {

        double errorAngle = m_drivetrain.getYaw() - goalAngle;
        if (errorAngle > 3) {
            m_drivetrain.arcadeDrive(-0.6, -0.2);
        } else if (errorAngle < -3) {
            m_drivetrain.arcadeDrive(-0.6, 0.2);
        } else {
            m_drivetrain.arcadeDrive(-0.6, 0.0);
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

        return false;
    }

}
