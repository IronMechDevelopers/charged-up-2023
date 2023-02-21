package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Intake extends SubsystemBase {
    private final WPI_VictorSPX leftIntakeMotorcontroller = new WPI_VictorSPX(
    Constants.LEFT_INTAKE_MOTOR_CONTROLLER_CONSTANT);

private final WPI_VictorSPX rightIntakeMotorcontroller = new WPI_VictorSPX(
    Constants.RIGHT_INTAKE_MOTOR_CONTROLLER_CONSTANT);
    
    public Intake () {
      super(); 
    //   rightIntakeMotorcontroller.setInverted(true);
    }

    public void runIntake(int dir, double speed) {
        leftIntakeMotorcontroller.set(speed * dir);
        rightIntakeMotorcontroller.set(speed * dir);
    }
}
