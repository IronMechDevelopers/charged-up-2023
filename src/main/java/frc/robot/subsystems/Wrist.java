package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Wrist extends SubsystemBase {


    // private Encoder encoder;
    private static final int kCanID = Constants.WRIST_MOTOR_CONTROLLER_CONSTANT;
    private static final MotorType kMotorType = MotorType.kBrushed;
    private static final SparkMaxAlternateEncoder.Type kAltEncType = SparkMaxAlternateEncoder.Type.kQuadrature;
    private static final int kCPR = 8192;
    private CANSparkMax m_motor;

    /**
   * An alternate encoder object is constructed using the GetAlternateEncoder() 
   * method on an existing CANSparkMax object. If using a REV Through Bore 
   * Encoder, the type should be set to quadrature and the counts per 
   * revolution set to 8192
   */
  private RelativeEncoder m_alternateEncoder;

    public Wrist() {
        super();
        m_motor = new CANSparkMax(kCanID, kMotorType);
        m_motor.restoreFactoryDefaults();
        m_motor.setInverted(true);
    
        // m_alternateEncoder = m_motor.getAlternateEncoder(kAltEncType, kCPR);


        
        // encoder = new Encoder(1,
        //         2,
        //         false,
        //         Encoder.EncodingType.k4X);

        // encoder.setDistancePerPulse(18);

        // // Configures the encoder to consider itself stopped when its rate is below 10
        // encoder.setMinRate(10);

        // // Reverses the direction of the encoder
        // encoder.setReverseDirection(true);

        // // Configures an encoder to average its period measurement over 5 samples
        // // Can be between 1 and 127 samples
        // encoder.setSamplesToAverage(5);

    }

    public void periodic() {
        // SmartDashboard.putNumber("wrist", encoder.getDistance());
        // SmartDashboard.putNumber("ProcessVariable", m_alternateEncoder.getPosition());

    } 

    public double getDistance() {
        // return encoder.getDistance();
        return 0;
    
    } 

    public double getRate () {
        // return encoder.getRate(); 
        return 0;
    }

    public void setMotor(double speed)
    {
        m_motor.set(speed);
    }
  
}