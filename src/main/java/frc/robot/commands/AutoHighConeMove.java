package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;
import static frc.robot.Constants.*;

public class AutoHighConeMove extends SequentialCommandGroup {
    public AutoHighConeMove(Drivetrain m_drivetrain, Arm m_arm, Wrist m_wrist, Intake m_intake) {
        addCommands(
                new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake),
                new DriveBackwards(m_drivetrain).withTimeout(2.5),
                new Spin(m_drivetrain).withTimeout(1.75),
                new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE));
    }
}