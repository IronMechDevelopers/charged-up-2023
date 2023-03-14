package frc.robot.subsystems;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.AutoBalanceDumb;
import frc.robot.commands.AutoDriveBackwards;
import frc.robot.commands.AutoLevelThreeCone;
import frc.robot.commands.AutoLevelThreeCube;

public class Auto_Selector extends SubsystemBase {
  private Joystick m_left;
  private Joystick m_right;

  // private ArrayList<Command> m_firstOption;
  /**
   * @param left
   * @param right
   * @param firstOption
   * @param secondOption
   */
  public Auto_Selector(Joystick left, Joystick right) {
    m_left = left;
    m_right = right;
  }

  public void periodic() {
    double leftPot = m_left.getRawAxis(3);
    double rightPot = m_right.getRawAxis(3);

    if (rightPot > 0.5) {
      SmartDashboard.putString("1st Auto", HIGH_CUBE);
    } else if (rightPot < -0.5) {
      SmartDashboard.putString("1st Auto", HIGH_CONE);
    } else {
      SmartDashboard.putString("1st Auto", DO_NOTHING);
    }

    if (leftPot > 0.66) {
      SmartDashboard.putString("2nd Auto", BALANCE);
    } else if (leftPot > 0.33) {
      SmartDashboard.putString("2nd Auto", BALANCE_PID);
    } else if (leftPot < -0.25) {
      SmartDashboard.putString("2nd Auto", DRIVE_BACKWARDS);
    } else {
      SmartDashboard.putString("2nd Auto", DO_NOTHING);
    }
  }

  public Command getAuto() {
    Command firstCommand = new WaitCommand(0.02);
    Command secondCommand = new WaitCommand(0.02);

    String rightSelected = SmartDashboard.getString("1st Auto", DO_NOTHING);
    String leftSelected = SmartDashboard.getString("2nd Auto", DO_NOTHING);

    switch (rightSelected) {
      case HIGH_CUBE:
        firstCommand =
            new AutoLevelThreeCube(
                RobotContainer.m_drivetrain,
                RobotContainer.m_arm,
                RobotContainer.m_wrist,
                RobotContainer.m_intake);
        break;
      case HIGH_CONE:
        firstCommand =
            new AutoLevelThreeCone(
                RobotContainer.m_drivetrain,
                RobotContainer.m_arm,
                RobotContainer.m_wrist,
                RobotContainer.m_intake);
        break;
      default:
        firstCommand = new WaitCommand(0.02);
    }

    switch (leftSelected) {
      case BALANCE:
        secondCommand = new AutoBalanceDumb(RobotContainer.m_drivetrain);
        break;
      case DRIVE_BACKWARDS:
        secondCommand = new AutoDriveBackwards(RobotContainer.m_drivetrain, RobotContainer.m_wrist);
        break;
      case BALANCE_PID:
        secondCommand = new AutoBalance(RobotContainer.m_drivetrain);
        break;
      default:
        secondCommand = new WaitCommand(0.02);
    }

    return firstCommand.andThen(secondCommand);
  }
}
