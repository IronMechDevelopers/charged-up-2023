package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PDP extends SubsystemBase {

    // private Encoder encoder;
    private final PowerDistribution m_pdp = new PowerDistribution();

    public PDP() {
        super();
    }

    public void periodic() {
        SmartDashboard.putNumber("Voltage", getVoltage());
        SmartDashboard.putNumber("Temperature", getTemperature());
        SmartDashboard.putNumber("Total Current", getTotalCurrent());
    }

    public double getVoltage() {
        return m_pdp.getVoltage();

    }

    // Get the current going through channel 7, in Amperes.
    // The PDP returns the current in increments of 0.125A.
    // At low currents the current readings tend to be less accurate.
    public double getCurrent(int channel) {
        return m_pdp.getCurrent(channel);
    }

    public double getTemperature() {
        double temperatureCelsius = m_pdp.getTemperature();
        return temperatureCelsius * (9.0 / 5) + 32;
    }

    public double getTotalCurrent() {
        return m_pdp.getTotalCurrent();
    }
}