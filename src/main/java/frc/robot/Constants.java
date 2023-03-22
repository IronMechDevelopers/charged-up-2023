// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;

  }

  /**
   * Which PID slot to pull gains from. Starting 2018, you can choose from
   * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
   * configuration.
   */
  public static final int kSlotIdx = 0;

  /**
   * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
   * now we just want the primary one.
   */
  public static final int kPIDLoopIdx = 0;

  // Controller constants
  public static final int left_joystick_index = 0;
  public static final int righh_joystick_index = 1;
  public static final int xbox_index = 2;

  // Drive constants
  public static final int kTimeoutMs = 30;
  public static final double kGains_drivetrain_kF = 0.0;
  public static final double kGains_drivetrain_kP = 1.5;
  public static final double kGains_drivetrain_kI = 0.008;

  public static final double kTurnP = 1;
  public static final double kTurnI = 0;
  public static final double kTurnD = 0;

  public static final double kMaxTurnRateDegPerS = 100;
  public static final double kMaxTurnAccelerationDegPerSSquared = 300;

  public static final double kTurnToleranceDeg = 5;
  public static final double kTurnRateToleranceDegPerS = 10; // degrees per second

  public static final double kGains_drivetrain_kD = 70;
  public static final double kDriveWheelDiameter = 3;
  public static final double CIRCUMFERENCE = 2 * Math.PI * Constants.kDriveWheelDiameter;
  public static final double SLOW_SPEED_RATE_FOWRARD = 0.5;
  public static final double SLOW_SPEED_RATE_TURN = 0.75;
  public static final double BALANCE_DISTANCE = -18.5;
  public static final int COUNTS_PER_REVOLUTION = 4096;

  public static final double SPIN_SPEED = .5;

  // Wrist constants
  public static final double kGains_wrist_kF = 0.0;
  public static final double kGains_wrist_kP = 1.5;
  public static final double kGains_wrist_kI = 0.008;
  public static final double kGains_wrist_kD = 70;
  public static final int WRIST_OUT = -1;
  public static final int WRIST_IN = 1;
  public static final double MINIMUM_ANGLE = -153;
  public static final double MAXIMUM_ANGLE = 0;
  public static final double MOTOR_SPEED_AUTO = .8;
  public static final double MOTOR_SPEED = 1;
  public static final double WRIST_OFFSET = 328;

  // Arm constants
  public static final int ARM_DOWN = -1;
  public static final int ARM_UP = 1;
  public static final double ARM_DOWN_SPEED = .9;
  public static final double ARM_UP_SPEED = 1;

  // Collection constants
  public static final double CONE_COLLECTION_OUT_SPEED = .75;
  public static final double CONE_COLLECTION_IN_SPEED = .75;
  public static final int CONE_COLLECTION_OUT_DITRECTION = 1;
  public static final int CONE_COLLECTION_IN_DITRECTION = -1;

  public static final double CUBE_COLLECTION_OUT_SPEED = .75;
  public static final double CUBE_COLLECTION_IN_SPEED = .75;
  public static final int CUBE_COLLECTION_OUT_DITRECTION = -1;
  public static final int CUBE_COLLECTION_IN_DITRECTION = 1;

  public static final double CUBE_COLLECTION_GROUND_ANGLE = -120;
  public static final double COLLECTION_HUMAN_PLAYER_ANGLE = -129;
  public static final double COLLECTION_LEVEL_ONE_ANGLE = -86;
  public static final double COLLECTION_LEVEL_TWO_ANGLE = -103;
  public static final double COLLECTION_LEVEL_THREE_ANGLE = -123.5;
  public static final double COLLECTION_LEVEL_THREE_CUBE_ANGLE = -120;
  public static final double COLLECTION_CUBE_SHOOT = -27;
  public static final double HOME_ANGLE = MAXIMUM_ANGLE;

  // CanBus constants
  public static final int RIGHT_FATHER_CANBUS_NUMBER = 0;
  public static final int LEFT_FATHER_CANBUS_NUMBER = 1;
  public static final int LEFT_SON_CANBUS_NUMBER = 2;
  public static final int RIGHT_SON_CANBUS_NUMBER = 3;
  public static final int ARM_MOTOR_CONTROLLER_CONSTANT = 4;
  public static final int LEFT_INTAKE_MOTOR_CONTROLLER_CONSTANT = 5;
  public static final int RIGHT_INTAKE_MOTOR_CONTROLLER_CONSTANT = 6;
  public static final int WRIST_MOTOR_CONTROLLER_CONSTANT = 7;

  // AUTO constantt
  public static final String HIGH_CUBE = "High Cube";
  public static final String HIGH_CONE = "High Cone";
  public static final String DO_NOTHING = "Do Nothing";
  public static final String BALANCE = "Balance";
  public static final String BALANCE_PID = "Balance PID";
  public static final String DRIVE_BACKWARDS = "Drive Backwards";

}
