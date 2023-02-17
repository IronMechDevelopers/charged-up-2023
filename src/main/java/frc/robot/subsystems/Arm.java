package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Arm extends SubsystemBase {
    private final WPI_VictorSPX armMotorcontroller = new WPI_VictorSPX(Constants.ARM_MOTOR_CONTROLLER_CONSTANT);
    private final WPI_VictorSPX leftIntakeMotorcontroller = new WPI_VictorSPX(
            Constants.LEFT_INTAKE_MOTOR_CONTROLLER_CONSTANT);
    private final WPI_VictorSPX rightIntakeMotorcontroller = new WPI_VictorSPX(
            Constants.RIGHT_INTAKE_MOTOR_CONTROLLER_CONSTANT);
    // private AnalogPotentiometer pot;

    public Arm() {
        super(); 
// Initializes an AnalogPotentiometer on analog port 0
        AnalogPotentiometer pot = new AnalogPotentiometer(0, 180, 30);
        // AnalogInput input = new AnalogInput(0);
        // input.setAverageBits(2);
        // pot = new AnalogPotentiometer(input, 6, 0); 
    }

    public void move(int dir, double speed) {
        armMotorcontroller.set(speed * dir);
    }

    public void runIntake(int dir, double speed) {
        leftIntakeMotorcontroller.set(speed * dir);
        rightIntakeMotorcontroller.set(speed * dir);
    }
    public double getPot()
    {
        // return pot.get();
        return 5684;
    }
}
