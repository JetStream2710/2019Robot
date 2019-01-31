package frc.robot.util;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;


// ATTEMPT NUMERO TRES -- SPOILER: NONE OF THEM WORKED :O
public class SpeedControlGroup{

    private WPI_VictorSPX victor1;
    private WPI_VictorSPX victor2;

    public SpeedControllerGroup speedyControlGroup(JetstreamVictor victorA, JetstreamVictor victorB){
        victor1 = new WPI_VictorSPX(victorA.getPort());
        victor2 = new WPI_VictorSPX(victorB.getPort());
        return new SpeedControllerGroup(victor1, victor2);
    }

}