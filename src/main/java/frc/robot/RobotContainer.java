// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.TurnToAngle;
import frc.robot.commands.Angle;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.Balance;
import frc.robot.commands.Collection;
import frc.robot.commands.Drive;
import frc.robot.commands.DriveForwardDistance;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveArmToDistance;
import frc.robot.commands.MoveWrist;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
  final Drivetrain m_drivetrain = new Drivetrain();
  final Arm m_arm = new Arm();
  final Intake m_intake = new Intake();
  final Wrist m_wrist = new Wrist();

  // The robot's subsystems and commands are defined here...
  private final Joystick driverLeftStick = new Joystick(0);
  private final Joystick driverRightStick = new Joystick(1);
  private final Joystick copilot = new Joystick(2);

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

    final JoystickButton balanceButton = new JoystickButton(driverLeftStick, 3);

    final JoystickButton armDownButton = new JoystickButton(driverRightStick, 7);
    final JoystickButton armUpButton = new JoystickButton(driverRightStick, 8);

    final JoystickButton rightFire = new JoystickButton(driverRightStick, 1);
    final JoystickButton leftFire = new JoystickButton(driverLeftStick, 1);

    final JoystickButton armLowButton = new JoystickButton(driverLeftStick, 7);
    final JoystickButton armHighButton = new JoystickButton(driverLeftStick, 8);

    final JoystickButton oneEighty = new JoystickButton(driverRightStick, 10);

    final JoystickButton intakeInButton = new JoystickButton(driverLeftStick, 4);
    final JoystickButton intakeOutButton = new JoystickButton(driverRightStick, 3);

    final JoystickButton wristDownButton = new JoystickButton(driverRightStick, 4);
    final JoystickButton wristUpButton = new JoystickButton(driverRightStick, 6);



    oneEighty.toggleOnTrue(new Angle(m_drivetrain, 180));

    balanceButton.whileTrue(new Balance(m_drivetrain));

    armLowButton.toggleOnTrue(new MoveArmToDistance(m_arm, 0, 1));
    armHighButton.toggleOnTrue(new MoveArmToDistance(m_arm, 6, 1));

    armDownButton.whileTrue(new MoveArm(m_arm, -1, 1));
    armUpButton.whileTrue(new MoveArm(m_arm, 1, 1));


    intakeInButton.whileTrue(new Collection(m_intake, 1, .75));
    intakeOutButton.whileTrue(new Collection(m_intake, -1, .75));

    leftFire.whileTrue(new MoveArm(m_arm,1,1));
    rightFire.whileTrue(new MoveArm(m_arm,-1,.75));

    wristDownButton.whileTrue(new MoveWrist(m_wrist, -1, .5));
    wristUpButton.whileTrue(new MoveWrist(m_wrist, 1, .5));

    final JoystickButton driveStraight = new JoystickButton(driverRightStick, 11);
    driveStraight.toggleOnTrue(new AutoBalance(m_drivetrain));

    final JoystickButton angleCorrecter = new JoystickButton(driverLeftStick, 6);
    angleCorrecter.toggleOnTrue(new TurnToAngle(m_drivetrain, m_drivetrain.getGoalAngle(90)));

    final JoystickButton driveForwardDistanceButton = new JoystickButton(driverLeftStick, 5);
    driveForwardDistanceButton.toggleOnTrue(new DriveForwardDistance(m_drivetrain, -36));

    final JoystickButton driveForwardDistanceButton2 = new JoystickButton(driverRightStick, 5);
    driveForwardDistanceButton2.toggleOnTrue(new DriveForwardDistance(m_drivetrain, -6));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new AutoBalance(m_drivetrain);
  }
}
