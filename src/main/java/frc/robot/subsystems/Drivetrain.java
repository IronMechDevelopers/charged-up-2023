package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.SPI;

public class Drivetrain extends SubsystemBase {

        private final MotorControllerGroup m_leftMotor;
        private final MotorControllerGroup m_rightMotor;
        private final DifferentialDrive m_drive;
        // parent motors
        private final WPI_TalonSRX leftFather = new WPI_TalonSRX(Constants.LEFT_FATHER_CANBUS_NUMBER);
        private final WPI_TalonSRX rightFather = new WPI_TalonSRX(Constants.RIGHT_FATHER_CANBUS_NUMBER);
        // son motors
        private final WPI_VictorSPX leftSon = new WPI_VictorSPX(Constants.LEFT_SON_CANBUS_NUMBER);
        private final WPI_VictorSPX rightSon = new WPI_VictorSPX(Constants.RIGHT_SON_CANBUS_NUMBER);
        private final NeutralMode brakeMode = NeutralMode.Brake;

        private AHRS gyro;

        /**
         * 
         */
        public Drivetrain() {
                super();

                leftFather.configFactoryDefault();
                rightFather.configFactoryDefault();

                rightFather.setNeutralMode(brakeMode);
                leftFather.setNeutralMode(brakeMode);
                rightSon.setNeutralMode(brakeMode);
                leftSon.setNeutralMode(brakeMode);
                leftFather.setInverted(false);
                rightFather.setInverted(true);
                rightSon.setInverted(true);
                leftSon.setInverted(false);

                leftFather.setSensorPhase(true);
                rightFather.setSensorPhase(true);

                leftSon.follow(leftFather);
                rightSon.follow(rightFather);

                setTalon(leftFather);
                setTalon(rightFather);

                leftFather.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                0,
                10);
            rightFather.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                0,
                10);

                m_leftMotor = new MotorControllerGroup(leftFather,
                                leftSon);
                m_rightMotor = new MotorControllerGroup(rightFather,
                                rightSon);
                m_drive = new DifferentialDrive(m_leftMotor,
                                m_rightMotor);
                m_drive.setSafetyEnabled(false);
                addChild("Drive",
                                m_drive);

                gyro = new AHRS(SPI.Port.kMXP);

        }

        public void periodic() {
                SmartDashboard.putNumber("Pitch", gyro.getPitch());
                SmartDashboard.putNumber("Yaw", gyro.getYaw());
                SmartDashboard.putNumber("Roll", gyro.getRoll());
                SmartDashboard.putNumber("LeftWheelEncoder", leftFather.getSelectedSensorPosition());
                SmartDashboard.putNumber("RightWheelEncoder", rightFather.getSelectedSensorPosition());
                SmartDashboard.putNumber("Left wheel velocity", leftFather.getSelectedSensorVelocity());
                SmartDashboard.putNumber("Right wheel velocity", rightFather.getSelectedSensorVelocity());
        }

        /***
         * A negative number means the front is up in the air.
         * 
         * @return
         */
        public double getPitch() {
                return gyro.getPitch();

        }

        public double getPitchAcc() {
                return gyro.getWorldLinearAccelX();
        }

        /**
         * a method that will drive the robot
         * 
         * @param fwd a number from -1 to 1 with 1 moving forward
         * @param rot a number from -1 to 1 with 1 moving clockwise
         */
        public void arcadeDrive(double fwd, double rot) {
                SmartDashboard.putNumber("Fwd", fwd);
                SmartDashboard.putNumber("rot", rot);
                //making rot so that postive goes clockwise instead of WPILIB standard
                m_drive.arcadeDrive(fwd, -1*rot);
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
                                Constants.kGains_kF,
                                Constants.kTimeoutMs);
                _talon.config_kP(Constants.kSlotIdx,
                                Constants.kGains_kP,
                                Constants.kTimeoutMs);
                _talon.config_kI(Constants.kSlotIdx,
                                Constants.kGains_kI,
                                Constants.kTimeoutMs);
                _talon.config_kD(Constants.kSlotIdx,
                                Constants.kGains_kD,
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

        public double getYaw() {
                return gyro.getYaw();
        }



        public double getAltitude() {
                return gyro.getAltitude();
        }

        public double getGoalAngle(double angleToTurnBy) {
                System.out.println("yaw:" + getYaw());
                double goalAngle = getYaw() + angleToTurnBy;
                SmartDashboard.putNumber("goal Angle2", goalAngle);
                return goalAngle;
        }

        public int driveForwardDistance(double distance) {
                int tickCountGoal = convertInchesToTicks(distance);
                SmartDashboard.putNumber("driveForwardDistance", tickCountGoal);
                leftFather.set(ControlMode.Position, tickCountGoal);
                rightFather.set(ControlMode.Position, tickCountGoal);
                return tickCountGoal;
        }

        public void resetEncoders()
        {
                leftFather.setSelectedSensorPosition(0);
                rightFather.setSelectedSensorPosition(0);
        }

        public double getLeftEncoderCount()
        {
                return leftFather.getSelectedSensorPosition();
        }
        public double getRightEncoderCount()
        {
                return rightFather.getSelectedSensorPosition();
        }

public int convertInchesToTicks (double inches) {
        double circumference = 2*Math.PI*3;
        double a = 4096/circumference; 
        return (int)(a*inches);
}
}