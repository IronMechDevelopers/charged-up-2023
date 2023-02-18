package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

public class MoveWrist extends CommandBase {
    private  Wrist m_wrist;
    
    public void initialize() {
        m_wrist.getDistance();
        m_wrist.getRate();
    }

    // The closer we get to balancing the slower we go till eventully we stop
    @Override
    public void execute() {
       
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
       
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
