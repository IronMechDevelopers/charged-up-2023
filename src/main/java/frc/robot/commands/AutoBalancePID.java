package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoBalancePID  extends CommandBase {

    private final Drivetrain m_drivetrain;
    private PIDController pid;

    // Adds drive train components to balance command
    public AutoBalancePID(Drivetrain drivetrain) {
        m_drivetrain = drivetrain;
        pid = new PIDController( 0.6/10, 0, 0.02);
        pid.setTolerance(2);
        addRequirements(m_drivetrain);
        pid.setSetpoint(0); 

       //SmartDashboard.putNumber("Auto Balance Max Output", 0.53);
       //SmartDashboard.putNumber("Auto Balance P", 0.6/10);
       //SmartDashboard.putNumber("Auto Balance D", 0.02);

    }

    @Override
    public void execute() {
        double max_output = SmartDashboard.getNumber("Auto Balance Max Output", 0.53);
        pid.setP(SmartDashboard.getNumber("Auto Balance P", 0.033));
        pid.setD( SmartDashboard.getNumber("Auto Balance D", 0.0045));
         double output = MathUtil.clamp(pid.calculate(m_drivetrain.getPitch(), 0), -max_output, max_output);
         m_drivetrain.arcadeDrive(-output,0);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drivetrain.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;

        //pid.atSetpoint();
    }

}
