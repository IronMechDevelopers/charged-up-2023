package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

public class Drive extends CommandBase {

    private final Drivetrain m_drivetrain;
    private final DoubleSupplier m_left;
    private final DoubleSupplier m_right;

    /**
     * Creates a new TankDrive command.
     *
     * @param left       The control input for the left side of the drive
     * @param right      The control input for the right sight of the drive
     * @param drivetrain The drivetrain subsystem to drive
     */
    public Drive(DoubleSupplier left, DoubleSupplier right, Drivetrain drivetrain) {
        super();
        // labels the drivetrain dir
        m_drivetrain = drivetrain;
        m_left = left;
        m_right = right;
        addRequirements(m_drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double forward = m_left.getAsDouble();
        double rotation = m_right.getAsDouble();
        // flipping joystick to make postive be up
        forward = forward * -1;
        // SmartDashboard.putNumber("left joystick", forward);
        // SmartDashboard.putNumber("right joystick", rotation);

        m_drivetrain.arcadeDrive(forward, rotation);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drivetrain.arcadeDrive(0,
                0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
