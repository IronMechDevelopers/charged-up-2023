package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardDistance extends CommandBase {
    private Drivetrain m_Drivetrain;
    private double inches;
    private  double Kp=0.5; // <- when i say change Kp, it’s this!

    private  double Ki=0.0;

    private double Kf=0.0;  //no feed-forward on position control

    private double Kd=0;

    private  int IZone; //IZone, this is explained below

    private PIDController leftDriveController;
    private PIDController rightDriveController;

//basiclly this allows us to move forward a set amount of inches
    /**
     * @param _drivetrain
     * @param _inches
     */
    public DriveForwardDistance(Drivetrain _drivetrain, double _inches) {
        m_Drivetrain = _drivetrain;
        inches = _inches;
        leftDriveController = new PIDController(Kp, Ki, Kd);
        rightDriveController = new PIDController(Kp, Ki, Kd);
        
        this.IZone  = m_Drivetrain.convertInchesToTicks(1);

        leftDriveController.setIntegratorRange(-IZone, IZone);
        rightDriveController.setIntegratorRange(-IZone, IZone);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_Drivetrain.resetEncoders();
        leftDriveController.setSetpoint(inches);
        rightDriveController.setSetpoint(inches);
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        SmartDashboard.putNumber("distance2",inches);
        double leftCount = m_Drivetrain.getLeftEncoderCountInInches();
        double rightCount = m_Drivetrain.getLeftEncoderCountInInches();
        double leftCalculate = leftDriveController.calculate(leftCount);
        double rightCalculate = leftDriveController.calculate(rightCount);
        m_Drivetrain.driveForwardDistance(leftCalculate,rightCalculate);
        SmartDashboard.putNumber("rightCount",rightCount);
        SmartDashboard.putNumber("leftCount",leftCount);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Drivetrain.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
       return leftDriveController.atSetpoint() && rightDriveController.atSetpoint();
    }

}
