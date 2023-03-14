package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Wrist;

public class RaiseArmAndWristToAngle extends ParallelCommandGroup {
  public RaiseArmAndWristToAngle(
      Wrist m_wrist, Arm m_arm, double angle, int armDir, double armSpeed, double delay) {
    addCommands(
        new MoveArm(m_arm, armDir, armSpeed).withTimeout(1),
        new WaitCommand(delay).andThen(new MoveWristToAngle(m_wrist, angle)));
  }
}
