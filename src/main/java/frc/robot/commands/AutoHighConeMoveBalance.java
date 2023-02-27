package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class AutoHighConeMoveBalance extends SequentialCommandGroup {
    public AutoHighConeMoveBalance(Arm m_arm, Wrist m_wrist, Intake m_Intake, Drivetrain m_drivetrain) {
        addCommands(
                new RaiseArmAndWristToAngle(m_wrist, m_arm, -126, 1,1, .1),
                new WaitCommand(.25).andThen(new Collection(m_Intake, 1, 1).withTimeout(1)),
                new DriveBackwards(m_drivetrain).withTimeout(.25),
                new RaiseArmAndWristToAngle(m_wrist, m_arm, -5, -1,.8, 0),
                new DriveStraightUntilPitch(m_drivetrain),
                new DriveForwardDistance(m_drivetrain, -6));
    }
}
