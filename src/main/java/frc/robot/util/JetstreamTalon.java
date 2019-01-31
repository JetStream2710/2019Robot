package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class JetstreamTalon {

    private Logger logger = new Logger(JetstreamTalon.class.getName());

    private WPI_TalonSRX talon;
    private double min;
    private double max;

    public JetstreamTalon(int port, double min, double max) {
        logger.detail("constructor");
        talon = new WPI_TalonSRX(port);
        this.min = min;
        this.max = max;
        talon.setSafetyEnabled(false);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.setSelectedSensorPosition(0);        
    }

    public boolean isSpeedValid(double speed){
        double position = talon.getSelectedSensorPosition();
        // TODO: we can also write the following as:
        // return !(position > max && speed > 0) && !(position < min && speed < 0);
        if (position > max && speed > 0) {
          return false;
        }
        if (position < min && speed < 0) {
          return false;
        }
        return true;
    }

    public void setSpeed(double speed){
        logger.info("setSpeed speed: " + speed);
        if (isSpeedValid(speed)) {
            talon.set(speed);
        }
    }    

    public double getSpeed(){
        // TODO: in general, we don't need to log getter methods since they're called a lot
        double speed = talon.get();
        return speed;
    }

    public int getSensorPosition(){
        return talon.getSelectedSensorPosition();
    }

    public static SpeedControllerGroup createGroup(JetstreamTalon talon1, JetstreamTalon talon2) {
        return new SpeedControllerGroup(talon1.talon, talon2.talon);
    }
}