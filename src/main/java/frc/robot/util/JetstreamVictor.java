package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class JetstreamVictor {

    private WPI_VictorSPX victor;
    private double min;
    private double max;

    public JetstreamVictor(int port, double min, double max) {
        victor = new WPI_VictorSPX(port);
        this.min = min;
        this.max = max;
        victor.setSafetyEnabled(false);
        victor.setNeutralMode(NeutralMode.Brake);
        victor.setSelectedSensorPosition(0);
    }

    public boolean isSpeedValid(double speed){
        double position = victor.getSelectedSensorPosition();
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
            victor.set(speed);
        }
    }    

    public double getSpeed(){
        return victor.get();
    }
}