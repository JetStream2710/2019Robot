package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class JetstreamTalon {

    private WPI_TalonSRX talon;
    private double min;
    private double max;

    public JetstreamTalon(int port, double min, double max) {
        talon = new WPI_TalonSRX(port);
        this.min = min;
        this.max = max;
        talon.setSafetyEnabled(false);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.setSelectedSensorPosition(0);
    }

    public boolean isSpeedValid(double speed){
        double position = talon.getSelectedSensorPosition();
        if(position > max && speed > 0){
          return false;
        }
        if(position < min && speed < 0){
          return false;
        }
        return true;
    }

    public void setSpeed(double speed){
        if(isSpeedValid(speed)){  
            talon.set(speed);
        }
    }    

    public double getSpeed(){
        return talon.get();
    }

    public int getSensorPosition(){
        return talon.getSelectedSensorPosition();
    }
}