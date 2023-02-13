package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class AutoPark extends SequentialCommandGroup {
    // gives our AutoBalance a class to use in a button
    public AutoPark(Drivetrain m_drivetrain) {
        addCommands(new DriveStraightUntilPitch(m_drivetrain), new Park(m_drivetrain));
    }

}
