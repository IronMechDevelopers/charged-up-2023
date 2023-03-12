package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;
import static frc.robot.Constants.*;

public class AutoLevelThreeCube extends SequentialCommandGroup {
    public AutoLevelThreeCube(Drivetrain m_drivetrain, Arm m_arm, Wrist m_wrist, Intake m_intake) {
        addCommands(


        new RaiseArmAndWristToAngle(m_wrist, m_arm,  COLLECTION_LEVEL_THREE_CUBE_ANGLE , ARM_UP,ARM_UP_SPEED, .1),
        new DriveStraight(m_drivetrain).withTimeout(0.1),
        new WaitCommand(1).andThen(new Collection(m_intake, CUBE_COLLECTION_OUT_DITRECTION, CUBE_COLLECTION_OUT_SPEED).withTimeout(1)),
        new MoveWristToAngle(m_wrist,HOME_ANGLE ).withTimeout(2),
        new DriveBackwards(m_drivetrain).withTimeout(.25),//.raceWith(new MoveWristToAngle(m_wrist,HOME_ANGLE )),
        new RaiseArmAndWristToAngle(m_wrist, m_arm, HOME_ANGLE, ARM_DOWN,ARM_DOWN_SPEED, 0));

    }

}