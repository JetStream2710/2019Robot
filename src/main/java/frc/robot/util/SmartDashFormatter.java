package frc.robot.util;

public class SmartDashFormatter {
    public String[] motorNames = {
        "Swivel Talon [12]",
        "Arm Talon [13]",
        "Elevator Talon [2]",
        "Drivetrain Right Talon [15]",
        "Drivetrain Left Talon [0]",
    };
    public String[] typeNames = {
        "output",
        "bus voltage",
        "motor voltage",
        "temperature"
    };
/*
    public String createDash() {
        StringBuilder sb = new StringBuilder();
        for () {
            for () {
                sb.append(String.format("<widget field=\"%s %s:\" type=\"Number\" class=\"edu.wpi.first.smartdashboard.gui.elements.TextBox\">\n"));
                sb.append(String.format("<location x=\"%d\" y=\"%d\"/>\n", x, y));
                sb.append(String.format("</widget>"));
            }
        }
    }
    */
}