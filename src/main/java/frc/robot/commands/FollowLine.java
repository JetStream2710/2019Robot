package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.PixyI2CDriver;
import frc.robot.util.PixyLine;

/**
 * Assumes that robot's center is near the ray
 * of the line and that the line is in view
 * 
 * Attempts to turn until the top point
 * 
 * of tape intersects the ideal line
 */ 
public class FollowLine extends Command {
    /**
     * References to "ideal" represent a theoretical line extending from
     * the center of the Robot perpendicular to the front of the chassis; 
     * 
     * This is our goal to line up the tape with
     */

    // Viewport Constants
    public static final int MAX_X = 78;
    public static final int MAX_Y = 51;

    // Ideal Constants
    public static final int IDEAL_BOTTOM_X_1 = 71;
    public static final int IDEAL_BOTTOM_Y_1 = 51;
    public static final double IDEAL_ANGLE_1 = 18.43;
    public static final double IDEAL_SLOPE_1 = (Math.tan(Math.toRadians(IDEAL_ANGLE_1))); // This is an x/y value, not y/x

    public static final int IDEAL_BOTTOM_X_2 = 7;
    public static final int IDEAL_BOTTOM_Y_2 = 0;
    public static final double IDEAL_ANGLE_2 = -18.43;
    public static final double IDEAL_SLOPE_2 = (Math.tan(Math.toRadians(IDEAL_ANGLE_2))); // This is an x/y value, not y/x

    // TODO: Test out values for ideal turning; below values taken from TurnDegrees.java
    private final double minSpinSpeed = 0.5;
    private final double maxSpinSpeed = 0.65;
    private final double startSlowingFrom = 45; // Angle at which spinning begins to slow

    private PixyLine line; // Line acquired by Pixy
    
    public FollowLine() {
        requires(Robot.drivetrain);
    }

    /**
     * Used to compare real offset to ideal
     * @param line Line Object returned by the Pixy
     * @return X value of any given Y point on the ideal line
     */
    public int getXOnIdealLine1(int y) {
        double intercept = IDEAL_BOTTOM_X_1 - (IDEAL_SLOPE_1*IDEAL_BOTTOM_Y_1);
        return (int)( (IDEAL_SLOPE_1*y) + intercept);
    }

    public int getXOnIdealLine2(int y) {
        double intercept = IDEAL_BOTTOM_X_2 - (IDEAL_SLOPE_2*IDEAL_BOTTOM_Y_2);
        return (int)( (IDEAL_SLOPE_2*y) + intercept);
    }

    /**
     * Used to faciliate proportional spinning
     * @param line Line Object returned by the Pixy
     * @return Value of the angle created by the line and vertical
     */
    public double getAngleFromVertical(PixyLine line) {

        int lowerX = line.getLowerX();
        int upperX = line.getUpperX();
        int lowerY = line.getLowerY();
        int upperY = line.getUpperY();

        double differX = (double)(upperX-lowerX);
        double differY = (double)(upperY-lowerY);

        double radians = Math.atan(differX/differY);
        double angle = radians*180/Math.PI;
        return angle;
    }

    private void pixy1Periodic() {
        double angle = getAngleFromVertical(line);
        double spinSpeed = Math.abs(angle/startSlowingFrom);
        if(spinSpeed > maxSpinSpeed) spinSpeed = maxSpinSpeed;
        else if(spinSpeed < minSpinSpeed) spinSpeed = minSpinSpeed;
        
        if(line.getUpperX() < (getXOnIdealLine1(line.getUpperY()))) { // If top of line is left/ideal is right
            Robot.drivetrain.tankDrive(-spinSpeed, spinSpeed); //spin left
        } else if(line.getUpperX() > (getXOnIdealLine1(line.getUpperY())) ) { // If top of line is right/iedal is left
            Robot.drivetrain.tankDrive(spinSpeed, -spinSpeed); //spin right
        }
    }

    private void pixy2Periodic() {
        double angle = getAngleFromVertical(line);
        double spinSpeed = Math.abs(angle/startSlowingFrom);
        if(spinSpeed > maxSpinSpeed) spinSpeed = maxSpinSpeed;
        else if(spinSpeed < minSpinSpeed) spinSpeed = minSpinSpeed;
        
        if(line.getUpperX() < (getXOnIdealLine2(line.getUpperY()))) { // If top of line is left/ideal is right
            Robot.drivetrain.tankDrive(-spinSpeed, spinSpeed); //spin left
        } else if(line.getUpperX() > (getXOnIdealLine2(line.getUpperY())) ) { // If top of line is right/iedal is left
            Robot.drivetrain.tankDrive(spinSpeed, -spinSpeed); //spin right
        }
    }

    @Override
    protected void initialize() {
    }

    @Override
    public void execute(){
        // Returns if there is no line to avoid NullPointerException(s)
        if(Robot.pixy.getLatestLine1() != null) {
            line = Robot.pixy.getLatestLine1();
            pixy1Periodic();
        } else if(Robot.pixy.getLatestLine2() != null) {
            line = Robot.pixy.getLatestLine2();
            pixy2Periodic();
        } else {
            line = null;
            return;
        }

    }

    @Override
    public boolean isFinished() {
        if(line == null) return false;

        /**  
        * Returns true if the top point of the real line
        * intersects some point on the ideal line
        */ 
        if(line.getUpperX() == (getXOnIdealLine1(line.getUpperY()) )) {
            return true;
        } else if(line.getUpperX() == (getXOnIdealLine2(line.getUpperY()) )) {
            return true;
        }

        return false;
    }

    @Override
	protected void end() {
        Robot.drivetrain.tankDrive(0.0, 0.0);
	}
	
    @Override
	protected void interrupted() {
		end();
    }
}