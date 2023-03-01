package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static frc.robot.Constants.MINIMUM_ANGLE;
import static frc.robot.Constants.MAXIMUM_ANGLE;
import static frc.robot.Constants.*;
public class Wrist extends SubsystemBase {

        // private Encoder encoder;
        private final WPI_TalonSRX wristMotor = new WPI_TalonSRX(Constants.WRIST_MOTOR_CONTROLLER_CONSTANT);
        private final NeutralMode brakeMode = NeutralMode.Brake;
        private final double feedForward;

        public Wrist() {
                super();
                wristMotor.configFactoryDefault();
                wristMotor.setNeutralMode(brakeMode);
                setTalon(wristMotor);
                

                wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,
                0,
                10);
                feedForward=.01;

        }

        public void periodic() {
                // SmartDashboard.putNumber("WristAngle", getAngle());
        }

        public double getAngle() {
                double ticks = wristMotor.getSelectedSensorPosition();
                
                return ticks * 360 / COUNTS_PER_REVOLUTION-WRIST_OFFSET;

        }

        public void resetEncoder() {
                wristMotor.setSelectedSensorPosition(0);
        }

        public void setMotor(double speed) {
                if(getAngle() >MAXIMUM_ANGLE && speed>0) 
                {
                    speed=0;
                        // SmartDashboard.putString("Wrist Saftey", "Active");
                }
                else if(getAngle() < MINIMUM_ANGLE && speed<0) 
                {
                        speed=0;
                        // SmartDashboard.putString("Wrist Saftey", "Active");
                }
                // SmartDashboard.putString("Wrist Saftey", "Inactive");
                wristMotor.set(speed);
        }

        public void setAngle(double angle) {
                wristMotor.set(ControlMode.MotionMagic, angle, DemandType.ArbitraryFeedForward, feedForward);
        }

        public void setTalon(final WPI_TalonSRX _talon) {

                /* Set relevant frame periods to be at least as fast as periodic rate */
                _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0,
                                10,
                                Constants.kTimeoutMs);
                _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic,
                                10,
                                Constants.kTimeoutMs);

                /* Set the peak and nominal outputs */
                _talon.configNominalOutputForward(0,
                                Constants.kTimeoutMs);
                _talon.configNominalOutputReverse(0,
                                Constants.kTimeoutMs);
                _talon.configPeakOutputForward(1,
                                Constants.kTimeoutMs);
                _talon.configPeakOutputReverse(-1,
                                Constants.kTimeoutMs);

                /* Set Motion Magic gains in slot0 - see documentation */
                _talon.selectProfileSlot(Constants.kSlotIdx,
                                Constants.kPIDLoopIdx);
                _talon.config_kF(Constants.kSlotIdx,
                                Constants.kGains_wrist_kF,
                                Constants.kTimeoutMs);
                _talon.config_kP(Constants.kSlotIdx,
                                Constants.kGains_wrist_kP,
                                Constants.kTimeoutMs);
                _talon.config_kI(Constants.kSlotIdx,
                                Constants.kGains_wrist_kI,
                                Constants.kTimeoutMs);
                _talon.config_kD(Constants.kSlotIdx,
                                Constants.kGains_wrist_kD,
                                Constants.kTimeoutMs);

                _talon.config_IntegralZone(0, 150, 0);

                /* Set acceleration and vcruise velocity - see documentation */
                _talon.configMotionCruiseVelocity(15000,
                                Constants.kTimeoutMs);
                _talon.configMotionAcceleration(6000,
                                Constants.kTimeoutMs);

                /* Zero the sensor once on robot boot up */
                _talon.setSelectedSensorPosition(0,
                                Constants.kPIDLoopIdx,
                                Constants.kTimeoutMs);

              
        }

}