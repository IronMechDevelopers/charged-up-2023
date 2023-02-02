package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class AutoBalance extends SequentialCommandGroup {

    public AutoBalance(Drivetrain m_drivetrain)
    {
        addCommands(new DriveStraightUntilPitch(m_drivetrain),new Balance(m_drivetrain) );
    }
    
}
