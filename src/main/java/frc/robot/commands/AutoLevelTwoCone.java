package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;
import static frc.robot.Constants.*;

public class AutoLevelTwoCone extends SequentialCommandGroup {
    public AutoLevelTwoCone(Drivetrain m_drivetrain, Arm m_arm, Wrist m_wrist, Intake m_intake) {
        addCommands(

        new DriveBackwards(m_drivetrain).withTimeout(.4),
        new RaiseArmAndWristToAngle(m_wrist, m_arm, COLLECTION_LEVEL_TWO_ANGLE, ARM_UP,ARM_UP_SPEED, .1),
        new WaitCommand(1).andThen(new Collection(m_intake, CONE_COLLECTION_OUT_DITRECTION, CONE_COLLECTION_OUT_SPEED).withTimeout(1)),
        new MoveWristToAngle(m_wrist,HOME_ANGLE ),
        new DriveBackwards(m_drivetrain).withTimeout(.25),
        new RaiseArmAndWristToAngle(m_wrist, m_arm, HOME_ANGLE, ARM_DOWN,ARM_DOWN_SPEED, 0));

    }

}