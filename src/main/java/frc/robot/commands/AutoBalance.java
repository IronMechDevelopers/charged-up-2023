package frc.robot.commands;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class AutoBalance extends SequentialCommandGroup {
  // gives our AutoBalance a class to use in a button
  public AutoBalance(Drivetrain m_drivetrain) {
    addCommands(
        new DriveStraightUntilPitch(m_drivetrain),
        new AutoBalancePID(m_drivetrain),
        new Spin(m_drivetrain).withTimeout(0.9));
  }
}
