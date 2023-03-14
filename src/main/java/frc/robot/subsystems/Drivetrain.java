package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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
  private final DifferentialDriveOdometry m_odometry;

  /** */
  public Drivetrain(XboxController copilotXbox) {
    super();

    gyro = new AHRS(SPI.Port.kMXP);
    new Thread(
            () -> {
              try {
                // SmartDashboard.putString("gyro", "No good");
                Thread.sleep(1000);
                zeroHeading();
                // SmartDashboard.putString("gyro", "Good");
              } catch (Exception e) {

              }
            })
        .start();
    gyro.calibrate();

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

    leftFather.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    rightFather.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

    m_leftMotor = new MotorControllerGroup(leftFather, leftSon);
    m_rightMotor = new MotorControllerGroup(rightFather, rightSon);
    m_drive = new DifferentialDrive(m_leftMotor, m_rightMotor);
    m_drive.setSafetyEnabled(false);
    addChild("Drive", m_drive);

    resetEncoders();
    m_odometry =
        new DifferentialDriveOdometry(
            gyro.getRotation2d(), getLeftEncoderCountInInches(), getRightEncoderCountInInches());
  }

  public void periodic() {
    SmartDashboard.putNumber("Left", leftFather.getSelectedSensorPosition());
    SmartDashboard.putNumber("right", rightFather.getSelectedSensorPosition());
    SmartDashboard.putNumber("Pitch1", getPitch());
  }

  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
        leftFather.getSelectedSensorVelocity(), rightFather.getSelectedSensorVelocity());
  }

  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(
        gyro.getRotation2d(), getLeftEncoderCountInInches(), getRightEncoderCountInInches(), pose);
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
    // SmartDashboard.putNumber("Fwd", fwd);
    // SmartDashboard.putNumber("rot", rot);
    // making rot so that postive goes clockwise instead of WPILIB standard
    m_drive.arcadeDrive(fwd, -1 * rot);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {

    leftFather.set(leftVolts / 12.0);
    rightFather.set(leftVolts / 12.0);
    m_drive.feed();
  }

  public void arcadeDriveBySpeed(double fwd, double rot) {
    // SmartDashboard.putNumber("Fwd", fwd);
    // SmartDashboard.putNumber("rot", rot);

    leftFather.set(ControlMode.Velocity, fwd);
    rightFather.set(ControlMode.Velocity, rot);
  }

  public void setTalon(final WPI_TalonSRX _talon) {

    /* Set relevant frame periods to be at least as fast as periodic rate */
    _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    _talon.setStatusFramePeriod(
        StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    /* Set the peak and nominal outputs */
    _talon.configNominalOutputForward(0, Constants.kTimeoutMs);
    _talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
    _talon.configPeakOutputForward(1, Constants.kTimeoutMs);
    _talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    /* Set Motion Magic gains in slot0 - see documentation */
    _talon.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    _talon.config_kF(Constants.kSlotIdx, Constants.kGains_drivetrain_kF, Constants.kTimeoutMs);
    _talon.config_kP(Constants.kSlotIdx, Constants.kGains_drivetrain_kP, Constants.kTimeoutMs);
    _talon.config_kI(Constants.kSlotIdx, Constants.kGains_drivetrain_kI, Constants.kTimeoutMs);
    _talon.config_kD(Constants.kSlotIdx, Constants.kGains_drivetrain_kD, Constants.kTimeoutMs);

    _talon.config_IntegralZone(0, 150, 0);

    /* Set acceleration and vcruise velocity - see documentation */
    _talon.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
    _talon.configMotionAcceleration(6000, Constants.kTimeoutMs);

    /* Zero the sensor once on robot boot up */
    _talon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
  }

  public double getYaw() {
    return gyro.getYaw();
  }

  public double getAltitude() {
    return gyro.getAltitude();
  }

  public void driveForwardDistanceToCountLeft(double leftTickCountGoal) {
    // SmartDashboard.putNumber("leftTickCountGoal", leftTickCountGoal);
    leftFather.set(ControlMode.Position, leftTickCountGoal);
  }

  public void driveForwardDistanceToCountRight(double rightTickCountGoal) {
    // SmartDashboard.putNumber("rightTickCountGoal", rightTickCountGoal);
    rightFather.set(ControlMode.Position, rightTickCountGoal);
  }

  public void driveForwardDistanceToCount(double leftTickCountGoal, double rightTickCountGoal) {
    driveForwardDistanceToCountLeft(leftTickCountGoal);
    driveForwardDistanceToCountRight(rightTickCountGoal);
  }

  public void zeroHeading() {
    gyro.reset();
  }

  public double getHeading() {
    return gyro.getAngle();
  }

  public double getLeftEncoderCount() {
    return leftFather.getSelectedSensorPosition();
  }

  public void resetEncoders() {
    leftFather.setSelectedSensorPosition(0);
    rightFather.setSelectedSensorPosition(0);
  }

  public double getRightEncoderCount() {
    return rightFather.getSelectedSensorPosition();
  }

  public double getLeftEncoderCountInInches() {
    double temp = leftFather.getSelectedSensorPosition();
    return convertTicksToInches(temp);
  }

  public double getRightEncoderCountInInches() {
    double temp = rightFather.getSelectedSensorPosition();
    return convertTicksToInches(temp);
  }

  public int convertInchesToTicks(double inches) {
    return (int) ((COUNTS_PER_REVOLUTION / CIRCUMFERENCE) * inches);
  }

  public double convertTicksToInches(double ticks) {
    return ticks * (1.0 / COUNTS_PER_REVOLUTION) * CIRCUMFERENCE;
  }

  public void setPosition(double leftTickCountGoal, double rightTickCountGoal) {
    leftFather.set(ControlMode.Position, leftTickCountGoal);
    rightFather.set(ControlMode.Position, rightTickCountGoal);
  }
}
