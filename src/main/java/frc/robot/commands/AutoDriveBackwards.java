package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Wrist;
import static frc.robot.Constants.*;

public class AutoDriveBackwards extends SequentialCommandGroup {
    public AutoDriveBackwards(Drivetrain m_drivetrain, Wrist m_wrist) {
        addCommands(
            new DriveBackwards(m_drivetrain).withTimeout(2.5),
            new Spin(m_drivetrain).withTimeout(1.75),
            new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE));
    }
}