// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DriveStraight;
import frc.robot.commands.DriveStraightUntilPitch;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.Angle;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.Balance;
import frc.robot.commands.Drive;
import frc.robot.commands.MoveArm;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  final Drivetrain m_drivetrain = new Drivetrain();
  final Arm m_arm = new Arm();

      // The robot's subsystems and commands are defined here...
      private final Joystick driverLeftStick = new Joystick(0);
      private final Joystick driverRightStick = new Joystick(1);
      private final Joystick copilot = new Joystick(2);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_drivetrain.setDefaultCommand(new Drive(driverLeftStick::getY,
    driverRightStick::getX,
    m_drivetrain));


    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
      
    final JoystickButton balanceButton = new JoystickButton(driverLeftStick,3);
    final JoystickButton armInButton = new JoystickButton(driverLeftStick, 7); //TODO Get button number
    final JoystickButton armOutButton = new JoystickButton(driverLeftStick, 8); //TODO Get button number

    //final JoystickButton balanceButton = new JoystickButton(driverLeftStick,3);
    balanceButton.whileTrue(new Balance(m_drivetrain));
    armInButton.whileTrue(new MoveArm(m_arm, -1, 1.0));
    armOutButton.whileTrue(new MoveArm(m_arm, 1, 1.0));




    final JoystickButton driveStraight = new JoystickButton(driverLeftStick,11);
    driveStraight.whileTrue(new AutoBalance(m_drivetrain));

    final JoystickButton angleCorrecter = new JoystickButton(driverLeftStick,6);
    angleCorrecter.toggleOnTrue(new TurnToAngle(m_drivetrain,m_drivetrain.getGoalAngle(90)));

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

