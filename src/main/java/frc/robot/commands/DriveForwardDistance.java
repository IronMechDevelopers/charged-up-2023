package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardDistance extends CommandBase {
    private Drivetrain m_Drivetrain;
    private double inches;
//basiclly this allows us to move forward a set amount of inches
    public DriveForwardDistance(Drivetrain _drivetrain, double _inches) {
        m_Drivetrain = _drivetrain;
        inches = _inches;

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_Drivetrain.resetEncoders();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        SmartDashboard.putNumber("distance2",inches);
        m_Drivetrain.driveForwardDistance(inches);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Drivetrain.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        System.out.println((m_Drivetrain.getRightEncoderCount() - m_Drivetrain.convertInchesToTicks(inches)) +" < " + m_Drivetrain.convertInchesToTicks(1));
        return (Math.abs(m_Drivetrain.getRightEncoderCount() - m_Drivetrain.convertInchesToTicks(inches))) < m_Drivetrain.convertInchesToTicks(1); 
    }

}
