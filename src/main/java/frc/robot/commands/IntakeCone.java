package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class IntakeCone extends SequentialCommandGroup {
    public IntakeCone(Intake m_intake, Wrist m_wrist, Arm m_arm) {
        addCommands(
                new Collection(m_intake, -1, 0.75).withTimeout(3),
                new ParallelCommandGroup(
                        new Collection(m_intake, -1, 0.75).withTimeout(3),
                        new MoveWristToAngle(m_wrist, -90)),
                new MoveArm(m_arm, 1, 0.75).withTimeout(2),
                new ParallelCommandGroup(
                        new Collection(m_intake, 1, 0.75).withTimeout(3),
                        new MoveWristToAngle(m_wrist, -5)),
                new MoveArm(m_arm, -1, 0.50).withTimeout(2));

    }

}
