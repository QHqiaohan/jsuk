package com.jh.jsuk.utils;

public class Distance {
    // 未考虑南半球和西经情况，遇到负数请自己调整代码
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double PI = 3.141592653589793231462643383279169399375;
        double latR = 6378140, lonR = 6356755;
        double j = ((lon2 - lon1) * PI * lonR * Math.cos(Math.toRadians(((lat1 + lat2) / 2) * PI / 180)) / 180) * Math.cos(Math.toRadians((lat1 + lat2) / 2));
        double w = (lat2 - lat1) * PI * latR / 180;
        return Math.hypot(j, w);
    }
}
