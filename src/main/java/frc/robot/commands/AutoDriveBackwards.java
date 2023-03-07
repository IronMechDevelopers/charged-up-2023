package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Wrist;

public class AutoDriveBackwards extends SequentialCommandGroup {
    public AutoDriveBackwards(Drivetrain m_drivetrain, Wrist m_wrist) {
        addCommands(
                new DriveBackwards(m_drivetrain).withTimeout(2.75));//,
                // new Spin(m_drivetrain).withTimeout(1.75));
                
    }
}