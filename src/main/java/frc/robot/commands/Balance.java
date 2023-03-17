package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Balance extends CommandBase {

    private final Drivetrain m_drivetrain;
    private int sixInches;

    // Adds drive train components to balance command
    public Balance(Drivetrain drivetrain) {
        super();
        m_drivetrain = drivetrain;
        addRequirements(m_drivetrain);
        sixInches = m_drivetrain.convertInchesToTicks(6);
    }

    // Called when the command is initially scheduled.
    // Gets pitch
    // Goal angle is 5, closest number to 0
    @Override
    public void initialize() {
    }

    // The closer we get to balancing the slower we go till eventully we stop
    @Override
    public void execute() {
        double left = m_drivetrain.getLeftEncoderCount();
        double right = m_drivetrain.getRightEncoderCount();
        double angle = m_drivetrain.getPitch();

        if (angle > 5) {
            // this means the front is up and we should drive forward 6 inches
            m_drivetrain.driveForwardDistanceToCount(left + sixInches, right + sixInches);
        }
        else if (angle < -5)  {
            m_drivetrain.driveForwardDistanceToCount(left - sixInches, right - sixInches);
        }
        else{
            m_drivetrain.arcadeDrive(0, 0);
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
