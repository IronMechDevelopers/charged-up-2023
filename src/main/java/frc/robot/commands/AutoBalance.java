package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import static frc.robot.Constants.*;

public class AutoBalance extends SequentialCommandGroup {
    // gives our AutoBalance a class to use in a button
    public AutoBalance(Drivetrain m_drivetrain) {
        addCommands(new DriveStraightUntilPitch(m_drivetrain), new DriveForwardDistance(m_drivetrain, BALANCE_DISTANCE), new Park(m_drivetrain));
    }

}
