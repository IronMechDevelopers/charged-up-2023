package frc.robot.commands;

import static frc.robot.Constants.ARM_DOWN;
import static frc.robot.Constants.ARM_DOWN_SPEED;
import static frc.robot.Constants.COLLECTION_LEVEL_ONE_ANGLE;
import static frc.robot.Constants.CUBE_COLLECTION_OUT_DITRECTION;
import static frc.robot.Constants.CUBE_COLLECTION_OUT_SPEED;
import static frc.robot.Constants.HOME_ANGLE;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;
public class AutoLevelOneCube   extends SequentialCommandGroup {
    public AutoLevelOneCube(Drivetrain m_drivetrain, Arm m_arm, Wrist m_wrist, Intake m_intake) {
        addCommands(


        new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE),
        new WaitCommand(1).andThen(new Collection(m_intake, CUBE_COLLECTION_OUT_DITRECTION, CUBE_COLLECTION_OUT_SPEED).withTimeout(1)),
        new DriveBackwards(m_drivetrain).withTimeout(.25),
        new RaiseArmAndWristToAngle(m_wrist, m_arm, HOME_ANGLE, ARM_DOWN,ARM_DOWN_SPEED, 0));

    }

}