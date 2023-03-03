// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.AutoBalance;
import frc.robot.commands.AutoBalanceDumb;
import frc.robot.commands.AutoDriveBackwards;
import frc.robot.commands.AutoLevelOneCone;
import frc.robot.commands.AutoLevelOneCube;
import frc.robot.commands.AutoLevelThreeCone;
import frc.robot.commands.AutoLevelThreeCube;
import frc.robot.commands.AutoLevelTwoCone;
import frc.robot.commands.AutoLevelTwoCube;
import frc.robot.commands.Collection;
import frc.robot.commands.Drive;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.DriveStraightUntilPitch;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveWrist;
import frc.robot.commands.MoveWristToAngle;
import frc.robot.commands.SlowSpeed;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PDP;
import frc.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
        // public static final PDP pdp = new PDP();

        private final boolean simpleAutoSelector = true;

        SendableChooser<Command> first = new SendableChooser<>();
        SendableChooser<Command> second = new SendableChooser<>();
        SendableChooser<Command> auto = new SendableChooser<>();

        private final Trigger dpadDownButton = new Trigger(() -> copilotXbox.getPOV() == 180);
        private final Trigger dpadUpButton = new Trigger(() -> copilotXbox.getPOV() == 0);
        private final Trigger dpadRightButton = new Trigger(() -> copilotXbox.getPOV() == 90);
        private final Trigger dpadLeftButton = new Trigger(() -> copilotXbox.getPOV() == 270);

        private final Trigger leftTigger = new Trigger(() -> copilotXbox.getRawAxis(2) > .5);
        private final Trigger rightTigger = new Trigger(() -> copilotXbox.getRawAxis(3) > .5);
        private final JoystickButton aButton = new JoystickButton(copilotXbox, Button.kA.value);
        private final JoystickButton bButton = new JoystickButton(copilotXbox, Button.kB.value);
        private final JoystickButton xButton = new JoystickButton(copilotXbox, Button.kX.value);
        private final JoystickButton yButton = new JoystickButton(copilotXbox, Button.kY.value);
        private final JoystickButton rightBumper = new JoystickButton(copilotXbox, Button.kRightBumper.value);
        private final JoystickButton leftBumper = new JoystickButton(copilotXbox, Button.kLeftBumper.value);

        private final JoystickButton leftFire = new JoystickButton(driverLeftStick, 1);
        private final JoystickButton rightFire = new JoystickButton(driverRightStick, 1);
        private final JoystickButton leftFive = new JoystickButton(driverLeftStick, 5);
        private final JoystickButton leftThree = new JoystickButton(driverLeftStick, 3);
        private final JoystickButton rightTwo = new JoystickButton(driverRightStick, 2);
        private final JoystickButton leftTen = new JoystickButton(driverLeftStick, 10);
        private final JoystickButton rightTen = new JoystickButton(driverRightStick, 10);
        private final JoystickButton leftTwelve = new JoystickButton(driverLeftStick, 12);

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

                if (simpleAutoSelector) {
                        auto.setDefaultOption("Nothing", new WaitCommand(0.02));
                        first.addOption("High Cube|balance without encoders",
                                        new AutoLevelThreeCube(m_drivetrain, m_arm, m_wrist, m_intake)
                                                        .andThen(new AutoBalanceDumb(m_drivetrain)));
                        first.addOption("High Cube|drive backwards",
                                        new AutoLevelThreeCube(m_drivetrain, m_arm, m_wrist, m_intake)
                                                        .andThen(new AutoDriveBackwards(m_drivetrain, m_wrist)));
                        first.addOption("High Cube|drive backwards with wrist", new AutoLevelThreeCube(m_drivetrain,
                                        m_arm, m_wrist, m_intake)
                                        .andThen(new AutoDriveBackwards(m_drivetrain, m_wrist).andThen(
                                                        new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE))));

                        first.addOption("High Cone|balance without encoders",
                                        new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake)
                                                        .andThen(new AutoBalanceDumb(m_drivetrain)));
                        first.addOption("High Cone|drive backwards",
                                        new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake)
                                                        .andThen(new AutoDriveBackwards(m_drivetrain, m_wrist)));
                        first.addOption("High CuConebe|drive backwards with wrist", new AutoLevelThreeCone(m_drivetrain,
                                        m_arm, m_wrist, m_intake)
                                        .andThen(new AutoDriveBackwards(m_drivetrain, m_wrist).andThen(
                                                        new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE))));

                        SmartDashboard.putData(auto);
                } else {

                        first.setDefaultOption("Nothing", new WaitCommand(0.02));
                        first.addOption("High Cube", new AutoLevelThreeCube(m_drivetrain, m_arm, m_wrist, m_intake));
                        first.addOption("High Cone", new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake));
                        first.addOption("Middle Cube", new AutoLevelTwoCube(m_drivetrain, m_arm, m_wrist, m_intake));
                        first.addOption("Middle Cone", new AutoLevelTwoCone(m_drivetrain, m_arm, m_wrist, m_intake));
                        first.addOption("Low Cube", new AutoLevelOneCube(m_drivetrain, m_arm, m_wrist, m_intake));
                        first.addOption("Low Cone", new AutoLevelOneCone(m_drivetrain, m_arm, m_wrist, m_intake));

                        second.setDefaultOption("Nothing", new WaitCommand(0.02));
                        second.setDefaultOption("balance with encoders", new AutoBalance(m_drivetrain));
                        second.setDefaultOption("balance without encoders", new AutoBalanceDumb(m_drivetrain));
                        second.setDefaultOption("drive backwards", new AutoDriveBackwards(m_drivetrain, m_wrist));
                        second.setDefaultOption("drive to Charge Station", new DriveStraightUntilPitch(m_drivetrain));
                        second.setDefaultOption("drive backwards with wrist",
                                        new AutoDriveBackwards(m_drivetrain, m_wrist).andThen(
                                                        new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE)));
                        second.setDefaultOption("drive backwards and collect", new AutoDriveBackwards(m_drivetrain,
                                        m_wrist)
                                        .andThen(new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE))
                                        .andThen(new DriveStraight(m_drivetrain).alongWith(new Collection(m_intake,
                                                        CONE_COLLECTION_IN_DITRECTION, CUBE_COLLECTION_IN_SPEED))
                                                        .withTimeout(.25)));

                        SmartDashboard.putData(first);
                        SmartDashboard.putData(second);
                }

                aButton.whileTrue(new Collection(m_intake, CONE_COLLECTION_IN_DITRECTION, CONE_COLLECTION_OUT_SPEED));
                bButton.whileTrue(new Collection(m_intake, CONE_COLLECTION_OUT_DITRECTION, CONE_COLLECTION_IN_SPEED));
                yButton.whileTrue(new MoveArm(m_arm, ARM_UP, ARM_UP_SPEED));
                xButton.whileTrue(new MoveArm(m_arm, ARM_DOWN, ARM_DOWN_SPEED));
                leftBumper.whileTrue(new MoveWrist(m_wrist, WRIST_IN, MOTOR_SPEED));
                rightBumper.whileTrue(new MoveWrist(m_wrist, WRIST_OUT, MOTOR_SPEED));
                leftTigger.whileTrue(new MoveArm(m_arm, ARM_UP, ARM_UP_SPEED));
                rightTigger.whileTrue(new MoveArm(m_arm, ARM_DOWN, ARM_DOWN_SPEED));
                dpadDownButton.toggleOnTrue(new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_ONE_ANGLE));
                dpadUpButton.toggleOnTrue(new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_THREE_ANGLE));
                dpadRightButton.toggleOnTrue(new MoveWristToAngle(m_wrist, COLLECTION_LEVEL_TWO_ANGLE));
                dpadLeftButton.toggleOnTrue(new MoveWristToAngle(m_wrist, COLLECTION_HUMAN_PLAYER_ANGLE));

                leftFire.whileTrue(new MoveArm(m_arm, ARM_UP, ARM_UP_SPEED));
                rightFire.whileTrue(new MoveArm(m_arm, ARM_DOWN, ARM_DOWN_SPEED));

                leftFive.toggleOnTrue(new MoveArm(m_arm, ARM_UP, ARM_UP_SPEED).withTimeout(1));
                leftThree.toggleOnTrue(new MoveArm(m_arm, ARM_DOWN, ARM_DOWN_SPEED).withTimeout(1));
                leftTwelve.toggleOnTrue(new AutoBalance(m_drivetrain));

                rightTwo.toggleOnTrue(new SlowSpeed(driverLeftStick::getY,
                                driverRightStick::getX,
                                m_drivetrain));

                leftTen.toggleOnTrue(new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake)
                                .andThen(new AutoDriveBackwards(m_drivetrain, m_wrist)));

                rightTen.toggleOnTrue(new AutoLevelThreeCone(m_drivetrain, m_arm, m_wrist, m_intake)
                                .andThen(new AutoDriveBackwards(m_drivetrain, m_wrist)));

        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                // An example command will be run in autonomous

                // Unhandled exception: java.lang.IllegalArgumentException: Commands that have
                // been composed may not be added to another composition or scheduled
                // individually!
                if (simpleAutoSelector) {
                        return auto.getSelected();
                } else {
                        Command firstCommand = first.getSelected();
                        Command secondCommand = second.getSelected();
                        return firstCommand.andThen(secondCommand);
                }
                // SequentialCommandGroup temp = new AutoLevelThreeCone(m_drivetrain, m_arm,
                // m_wrist, m_intake).andThen(new AutoBalanceDumb(m_drivetrain));
                // return temp;
        }
}
