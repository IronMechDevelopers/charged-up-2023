package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class AutoBalanceDumb extends SequentialCommandGroup {
    // gives our AutoBalance a class to use in a button
    public AutoBalanceDumb(Drivetrain m_drivetrain) {
        addCommands(new DriveStraightUntilPitch(m_drivetrain).withTimeout(4),
                new DriveBackwards(m_drivetrain).withTimeout(1.45), new Spin(m_drivetrain).withTimeout(1.5));
                //was 1.25
    }

}