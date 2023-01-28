package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Arm extends SubsystemBase{
    private final WPI_TalonSRX armMotorcontroller = new WPI_TalonSRX(Constants.ARM_MOTOR_CONTROLLER_CONSTANT);

    public Arm () {
         super();
    }

    public void move(int dir , double speed){
        armMotorcontroller.set(speed*dir);
    }
}
