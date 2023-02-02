package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;

public class TurnToAngle extends PIDCommand{

    public TurnToAngle( Drivetrain drive, double goalAngle) {
        super(
            new PIDController(.005, 0, .005/2),
            // Close loop on heading
            drive::getYaw,
            // Set reference to target
            goalAngle,
            // Pipe output to turn robot
            output -> drive.arcadeDrive(0, output),
            // Require the drive
            drive);
    
        // Set the controller to be continuous (because it is an angle controller)
        getController().enableContinuousInput(-180, 180);
        // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        getController()
            .setTolerance(3, .5);

            SmartDashboard.putNumber("timestamp", System.currentTimeMillis());
      }
    
}
