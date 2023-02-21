package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Encoder;

public class Arm extends SubsystemBase {
    private final WPI_VictorSPX armMotorcontroller = new WPI_VictorSPX(Constants.ARM_MOTOR_CONTROLLER_CONSTANT);
    private final WPI_VictorSPX leftIntakeMotorcontroller = new WPI_VictorSPX(
            Constants.LEFT_INTAKE_MOTOR_CONTROLLER_CONSTANT);
    private final WPI_VictorSPX rightIntakeMotorcontroller = new WPI_VictorSPX(
            Constants.RIGHT_INTAKE_MOTOR_CONTROLLER_CONSTANT);
    private AnalogPotentiometer pot;
    private Encoder encoder;

    public Arm() {
        super();
        // Initializes an AnalogPotentiometer on analog port 0
        pot = new AnalogPotentiometer(0, 6, 0);
        rightIntakeMotorcontroller.setInverted(true);

        encoder = new Encoder(1,
                2,
                false,
                Encoder.EncodingType.k4X);

        encoder.setDistancePerPulse(18);


        // Configures the encoder to consider itself stopped when its rate is below 10
        encoder.setMinRate(10);

        // Reverses the direction of the encoder
        encoder.setReverseDirection(true);

        // Configures an encoder to average its period measurement over 5 samples
        // Can be between 1 and 127 samples
        encoder.setSamplesToAverage(5);
    }

    public void moveArm(int dir, double speed) {
        armMotorcontroller.set(speed * dir);
    }

    public void runIntake(int dir, double speed) {
        leftIntakeMotorcontroller.set(speed * dir);
        rightIntakeMotorcontroller.set(speed * dir);
    }

    public double getPot() {
        return pot.get() * 1000;

    }

    public void periodic() {
        SmartDashboard.putNumber("Pot", getPot());
        SmartDashboard.putNumber("wrist", encoder.getDistance());

    }

}
