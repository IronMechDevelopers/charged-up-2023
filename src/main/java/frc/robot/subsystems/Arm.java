package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Arm extends SubsystemBase {
    private final WPI_VictorSPX armMotorcontroller = new WPI_VictorSPX(Constants.ARM_MOTOR_CONTROLLER_CONSTANT);
    private final WPI_VictorSPX leftIntakeMotorcontroller = new WPI_VictorSPX(
            Constants.LEFT_INTAKE_MOTOR_CONTROLLER_CONSTANT);
    private final WPI_VictorSPX rightIntakeMotorcontroller = new WPI_VictorSPX(
            Constants.RIGHT_INTAKE_MOTOR_CONTROLLER_CONSTANT);

    public Arm() {
        super();
    }

    public void move(int dir, double speed) {
        armMotorcontroller.set(speed * dir);
    }

    public void runIntake(int dir, double speed) {
        leftIntakeMotorcontroller.set(speed * dir);
        rightIntakeMotorcontroller.set(speed * dir);
    }
}
