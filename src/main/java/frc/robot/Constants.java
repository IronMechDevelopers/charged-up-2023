// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
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

  /**
   * set to zero to skip waiting for confirmation, set to nonzero to wait and
   * report to DS if action fails.
   */
  public static final int kTimeoutMs = 30;
  public static final double kGains_kF =0.0;
  public static final double kGains_kP = 1.5 ;
  public static final double kGains_kI =0.000;
  public static final double kGains_kD =30;
  


  public static final int RIGHT_SON_CANBUS_NUMBER = 3;
  public static final int LEFT_SON_CANBUS_NUMBER = 2;
  public static final int RIGHT_FATHER_CANBUS_NUMBER = 0;
  public static final int LEFT_FATHER_CANBUS_NUMBER = 1;

public static final int ARM_MOTOR_CONTROLLER_CONSTANT = 4; 
}
