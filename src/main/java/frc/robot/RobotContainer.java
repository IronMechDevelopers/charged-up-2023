// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.AutoBalance;
import frc.robot.commands.AutoBalanceDumb;
import frc.robot.commands.AutoDriveBackwards;
import frc.robot.commands.AutoLevelThreeCone;
import frc.robot.commands.AutoLevelThreeCube;
import frc.robot.commands.Collection;
import frc.robot.commands.Drive;
import frc.robot.commands.DriveBackwards;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveWrist;
import frc.robot.commands.MoveWristToAngle;
import frc.robot.commands.SlowSpeed;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import static frc.robot.Constants.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private static final Joystick driverLeftStick = new Joystick(0);
    private static final Joystick driverRightStick = new Joystick(1);
    private static final XboxController copilotXbox = new XboxController(2);

    // The robot's subsystems and commands are defined here...
    public static final Drivetrain m_drivetrain = new Drivetrain(copilotXbox);
    public static final Arm m_arm = new Arm();
    public static final Intake m_intake = new Intake();
    public static final Wrist m_wrist = new Wrist();

    SendableChooser<Command> first = new SendableChooser<>();
    SendableChooser<Command> second = new SendableChooser<>();

    private final Trigger dpadDownButton = new Trigger(() -> copilotXbox.getPOV() == 180);
    private final Trigger dpadUpButton = new Trigger(() -> copilotXbox.getPOV() == 0);
    private final Trigger dpadRightButton = new Trigger(() -> copilotXbox.getPOV() == 90);
    private final Trigger dpadLeftButton = new Trigger(() -> copilotXbox.getPOV() == 270);

    private final Trigger leftTigger = new Trigger(() -> copilotXbox.getRawAxis(2) > .5);
    private final Trigger rightTigger = new Trigger(() -> copilotXbox.getRawAxis(3) > .5);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        m_drivetrain.setDefaultCommand(new Drive(driverLeftStick::getY,
                driverRightStick::getX,
                m_drivetrain));

        // Configure the trigger bindings
        configureBindings();
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be
     * created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
     * an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
     * {@link
     * CommandXboxController
     * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or
     * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {

        first.setDefaultOption("Nothing", new WaitCommand(.1));
        first.addOption("High Cube", new AutoLevelThreeCube(m_drivetrain, m_arm, m_wrist, m_intake));
        first.addOption("High Cone", new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake));
        first.addOption("Nothing", new WaitCommand(.1));

        second.setDefaultOption("Nothing", new WaitCommand(.1));
        second.setDefaultOption("balance with encoders", new AutoBalance(m_drivetrain));
        second.setDefaultOption("balance without encoders", new AutoBalanceDumb(m_drivetrain));
        second.setDefaultOption("drive backwards", new AutoDriveBackwards( m_drivetrain,  m_wrist));

        

        // m_chooser.addOption("HighCone Move", new AutoHighConeMove(m_drivetrain,
        // m_arm, m_wrist, m_intake));

        SmartDashboard.putData(first);
        SmartDashboard.putData(second);

        new JoystickButton(copilotXbox, Button.kA.value)
                .whileTrue(new Collection(m_intake, CONE_COLLECTION_OUT_DITRECTION, CONE_COLLECTION_OUT_SPEED));

        new JoystickButton(copilotXbox, Button.kB.value)
                .whileTrue(new Collection(m_intake, CONE_COLLECTION_IN_DITRECTION, CONE_COLLECTION_IN_SPEED));

        new JoystickButton(copilotXbox, Button.kLeftBumper.value)
                .whileTrue(new MoveWrist(m_wrist, WRIST_IN, MOTOR_SPEED));

        new JoystickButton(copilotXbox, Button.kRightBumper.value)
                .whileTrue(new MoveWrist(m_wrist, WRIST_OUT, MOTOR_SPEED));

        new JoystickButton(copilotXbox, Button.kY.value)
                .whileTrue(new MoveArm(m_arm, ARM_UP, ARM_UP_SPEED));

        new JoystickButton(copilotXbox, Button.kX.value)
                .whileTrue(new MoveArm(m_arm, ARM_DOWN, ARM_DOWN_SPEED));

        new JoystickButton(driverLeftStick, 1).whileTrue(new MoveArm(m_arm, ARM_UP, ARM_UP_SPEED));
        new JoystickButton(driverRightStick, 1).whileTrue(new MoveArm(m_arm, ARM_DOWN, ARM_DOWN_SPEED));

        leftTigger.whileTrue(new MoveArm(m_arm, ARM_UP, ARM_UP_SPEED));
        rightTigger.whileTrue(new MoveArm(m_arm, ARM_DOWN, ARM_DOWN_SPEED));

        new JoystickButton(driverLeftStick, 5).toggleOnTrue(new MoveArm(m_arm, ARM_UP, ARM_UP_SPEED).withTimeout(1));
        new JoystickButton(driverLeftStick, 3)
                .toggleOnTrue(new MoveArm(m_arm, ARM_DOWN, ARM_DOWN_SPEED).withTimeout(1));

        new JoystickButton(driverRightStick, 2).toggleOnTrue(new SlowSpeed(driverLeftStick::getY,
                driverRightStick::getX,
                m_drivetrain));

        // new JoystickButton(driverRightStick, 3)
        // .toggleOnTrue(new IntakeCone (m_intake, m_wrist, m_arm ));

        // -120 should be for ground pick up cube when arm is out a little bit
        dpadDownButton.toggleOnTrue(new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE));
        dpadUpButton.toggleOnTrue(new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_THREE_ANGLE));
        dpadRightButton.toggleOnTrue(new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_TWO_ANGLE));
        dpadLeftButton.toggleOnTrue(new MoveWristToAngle(m_wrist, COLLECTION_HUMAN_PLAYER_ANGLE));

        new JoystickButton(driverLeftStick, 10)
                .toggleOnTrue(new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake).andThen(new AutoDriveBackwards(m_drivetrain, m_wrist)));

        new JoystickButton(driverRightStick, 10)
                .toggleOnTrue(new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake).andThen(new AutoDriveBackwards(m_drivetrain, m_wrist)));

        // new JoystickButton(driverLeftStick, 4).toggleOnTrue(new
        // DriveForwardDistance(m_drivetrain, 12));
        new JoystickButton(driverLeftStick, 12).toggleOnTrue(new AutoBalance(m_drivetrain));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        Command firstCommand = first.getSelected();
        Command secondCommand = second.getSelected();
        return firstCommand.andThen(secondCommand);
    }
}
