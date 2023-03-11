package frc.robot.subsystems;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.AutoDriveBackwards;
import frc.robot.commands.AutoLevelThreeCone;
import frc.robot.commands.AutoLevelThreeCube;
import frc.robot.commands.BalanceDumb;

public class Auto_Selector  extends SubsystemBase {
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
            SmartDashboard.putString("1st Auto", "high cube");
        } else if (rightPot < -0.5) {
            SmartDashboard.putString("1st Auto", "high cone");
        } else {
            SmartDashboard.putString("1st Auto", "nothing");
        }

        if (leftPot > 0.5) {
            SmartDashboard.putString("2nd Auto", "balance");
        } else if (leftPot < -0.5) {
            SmartDashboard.putString("2nd Auto", "drive backwards");
        } else {
            SmartDashboard.putString("2nd Auto", "nothing");
        }
    }

    public Command getAuto() {
        Command firstCommand = new WaitCommand(0.02);
        Command secondCommand = new WaitCommand(0.02);
        double leftPot = m_left.getRawAxis(3);
        double rightPot = m_right.getRawAxis(3);

        if (rightPot > 0.5) {
            firstCommand = new AutoLevelThreeCube(RobotContainer.m_drivetrain, RobotContainer.m_arm,RobotContainer.m_wrist,  RobotContainer.m_intake);
        } else if (rightPot < -0.5) {
            firstCommand = new AutoLevelThreeCone(RobotContainer.m_drivetrain, RobotContainer.m_arm,
                    RobotContainer.m_wrist, RobotContainer.m_intake);
        } else {
            SmartDashboard.putString("1st Auto", "nothing");
        }

        if (leftPot > 0.5) {
            firstCommand = new BalanceDumb(RobotContainer.m_drivetrain);
        } else if (leftPot < -0.5) {
            firstCommand = new AutoDriveBackwards(RobotContainer.m_drivetrain, RobotContainer.m_wrist);
        } else {
            SmartDashboard.putString("2nd Auto", "nothing");
        }

        return firstCommand.andThen(secondCommand);
    }
}
