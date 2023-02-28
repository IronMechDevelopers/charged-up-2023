package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class AutoHighConeMoveBalance extends SequentialCommandGroup {
    public AutoHighConeMoveBalance(Drivetrain m_drivetrain, Arm m_arm, Wrist m_wrist, Intake m_intake) {
        addCommands(
                new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake),
                new DriveStraightUntilPitch(m_drivetrain),
                new DriveForwardDistance(m_drivetrain, -6),
                new Park(m_drivetrain));
    }
}
