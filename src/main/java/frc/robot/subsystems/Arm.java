package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Arm extends SubsystemBase {
    private final WPI_VictorSPX armMotorcontroller = new WPI_VictorSPX(Constants.ARM_MOTOR_CONTROLLER_CONSTANT);
    private final WPI_VictorSPX leftIntakeMotorcontroller = new WPI_VictorSPX(
            Constants.LEFT_INTAKE_MOTOR_CONTROLLER_CONSTANT);
    private final WPI_VictorSPX rightIntakeMotorcontroller = new WPI_VictorSPX(
            Constants.RIGHT_INTAKE_MOTOR_CONTROLLER_CONSTANT);
    private AnalogPotentiometer pot;
    private DutyCycleEncoder encoder;

    public Arm() {
        super();
        // Initializes an AnalogPotentiometer on analog port 0
        pot = new AnalogPotentiometer(0, 6, 0);
        encoder = new DutyCycleEncoder(0);
        encoder.setDistancePerRotation(360);
        rightIntakeMotorcontroller.setInverted(true);

    }

    public void moveArm(int dir, double speed) {
        armMotorcontroller.set(speed * dir);
    }

    public void runIntake(int dir, double speed) {
        leftIntakeMotorcontroller.set(speed * dir);
        rightIntakeMotorcontroller.set(speed * dir);
    }

    public double getPot() {
        return pot.get()*1000;

    }
}
