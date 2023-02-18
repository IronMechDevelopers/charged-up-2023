package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends SubsystemBase {
    private Encoder encoder;

    public Wrist() {
        super();
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

    public void periodic() {
        SmartDashboard.putNumber("wrist", encoder.getDistance());

    } 

    public double getDistance() {
        return encoder.getDistance();
    
    } 

    public double getRate () {
        return encoder.getRate(); 
    }
  
}