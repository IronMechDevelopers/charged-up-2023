package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class AutoCone extends SequentialCommandGroup {
    public AutoCone(Intake m_intake, Wrist m_wrist, Arm m_arm, Drivetrain m_drivetrain) {
        addCommands(
                new MoveArm(m_arm, 1, 1).withTimeout(1),
                new MoveWristToAngle(m_wrist, -116),
                new Collection(m_intake, 1, 0.75).withTimeout(1),
                new MoveWristToAngle(m_wrist, -5),
                new MoveArm(m_arm, -1, 1).withTimeout(1),
                new DriveStraightUntilPitch(m_drivetrain));

    }

}