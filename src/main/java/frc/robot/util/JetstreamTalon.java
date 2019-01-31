package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class JetstreamTalon {

    private static final boolean DEBUG = false;

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
        
        debug("constructor");
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
        debug("setSpeed speed: " + speed);
        if(isSpeedValid(speed)){  
            talon.set(speed);
        }
    }    

    public double getSpeed(){
        double speed = talon.get();
        debug("getSpeed speed: " + speed);
        return speed;
    }

    public int getSensorPosition(){
        int position = talon.getSelectedSensorPosition();
        debug("getSensorPosition position: " + position);
        return position;
    }

    public int getPort(){
        debug("getPort: " + talon.getDeviceID());
        return talon.getDeviceID();
    }

    //ATTEMPT NUMERO UNO
    public SpeedControllerGroup speedControlGroup(JetstreamTalon talon1, JetstreamTalon talon2){
         SpeedControllerGroup group;
         WPI_TalonSRX talonA;
         WPI_TalonSRX talonB;

         talonA = new WPI_TalonSRX(talon1.getPort());
         talonB = new WPI_TalonSRX(talon2.getPort());
         group = new SpeedControllerGroup(talonA, talonB);
         return group;
     }

     private void debug(String s) {
        if (DEBUG) {
          System.out.println("Elevator Subsystem: " + s);
        }
      }
    
}