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
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
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
  XboxController copilotXbox = new XboxController(3); 

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

     //final JoystickButton balanceButton = new JoystickButton(driverLeftStick, 12);

    // final JoystickButton armDownButton = new JoystickButton(driverRightStick, 7);
    // final JoystickButton armUpButton = new JoystickButton(driverRightStick, 8); 


    //final JoystickButton AutoBalance= new JoystickButton(driverRightStick, 11);

    

    new JoystickButton(copilotXbox, Button.kLeftBumper.value)
    .whileTrue(new Collection(m_intake, -1, .75));

    new JoystickButton(copilotXbox, Button.kRightBumper.value)
    .whileTrue(new Collection(m_intake, 1, .75));

    new JoystickButton(copilotXbox, Button.kA.value)
    .whileTrue(new MoveWrist(m_wrist, 1, .5));

    new JoystickButton(copilotXbox, Button.kB.value)
    .whileTrue(new MoveWrist(m_wrist, -1, .5));

    new JoystickButton(copilotXbox, Button.kY.value)
    .whileTrue(new MoveArm(m_arm,1,1));

    new JoystickButton(copilotXbox, Button.kX.value)
    .whileTrue(new MoveArm(m_arm,-1,.75));
  

    // // final JoystickButton driveForwardDistanceButton2 = new JoystickButton(driverRightStick, 5);


    // // final JoystickButton driveForwardDistanceButton = new JoystickButton(driverLeftStick, 5);

    // // final JoystickButton angleCorrecter = new JoystickButton(driverLeftStick, 6);

    // // final JoystickButton driveStraight = new JoystickButton(driverRightStick, 11);

    // oneEighty.toggleOnTrue(new Angle(m_drivetrain, 180));

    // // balanceButton.whileTrue(new Balance(m_drivetrain));

    // armLowButton.toggleOnTrue(new MoveArmToDistance(m_arm, 0, 1));
    // armHighButton.toggleOnTrue(new MoveArmToDistance(m_arm, 6, 1));

    // // armDownButton.whileTrue(new MoveArm(m_arm, -1, 1));
    // // armUpButton.whileTrue(new MoveArm(m_arm, 1, 1));


    // intakeInButton.whileTrue(new Collection(m_intake, 1, .75));
    // intakeOutButton.whileTrue(new Collection(m_intake, -1, .75));

    // leftFire.whileTrue(new MoveArm(m_arm,1,1));
    // rightFire.whileTrue(new MoveArm(m_arm,-1,.75));

    // wristDownButton.whileTrue(new MoveWrist(m_wrist, -1, .5));
    // wristUpButton.whileTrue(new MoveWrist(m_wrist, 1, .5));

    
    // // driveStraight.toggleOnTrue(new AutoBalance(m_drivetrain));

    
    // // angleCorrecter.toggleOnTrue(new TurnToAngle(m_drivetrain, m_drivetrain.getGoalAngle(90)));

    
    // // driveForwardDistanceButton.toggleOnTrue(new DriveForwardDistance(m_drivetrain, -36));

   
    // // driveForwardDistanceButton2.toggleOnTrue(new DriveForwardDistance(m_drivetrain, -6));
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
