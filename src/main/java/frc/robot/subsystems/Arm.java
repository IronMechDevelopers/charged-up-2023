package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Arm extends SubsystemBase {
    private final WPI_VictorSPX armMotorcontroller = new WPI_VictorSPX(Constants.ARM_MOTOR_CONTROLLER_CONSTANT);


    public Arm() {
        super();

    }

    public void periodic() {
        SmartDashboard.putNumber("arm voltage", armMotorcontroller.getMotorOutputVoltage());
}

    public void moveArm(int dir, double speed) {
        armMotorcontroller.set(speed * dir);
    }

}
