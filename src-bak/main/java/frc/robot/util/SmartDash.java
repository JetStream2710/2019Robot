package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDash {
    public static void put(String key, Integer value) {
        SmartDashboard.putNumber(key, value);
    }
    public static void put(String key, Double value) {
        SmartDashboard.putNumber(key, value);
    }
    public static void put(String key, boolean value) {
        SmartDashboard.putBoolean(key, value);
    }
    public static void put(String key, String value) {
        SmartDashboard.putString(key, value);
    }

    public static String justify(String s) {
        int len = 50 - s.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(" ");
        }
        sb.append(s);
        if (!s.endsWith(":")) {
            sb.append(":");
        }
        return sb.toString();
    }
}